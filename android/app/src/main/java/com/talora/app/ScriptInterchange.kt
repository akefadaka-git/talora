package com.talora.app

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlSerializer
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.StringWriter
import java.nio.ByteBuffer
import java.nio.charset.CodingErrorAction
import java.util.Locale
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

enum class TextImportFormat { PLAIN_TEXT, FOUNTAIN, FDX, DOCX, RTF }

/**
 * Explicit, bounded script interchange. Source text is preserved as paragraphs;
 * richer editor semantics are inferred only where the external format exposes them.
 */
object ScriptInterchange {
    private const val MAX_DOCUMENT_BYTES = 8 * 1024 * 1024
    private const val MAX_DOCX_ENTRIES = 128
    private const val MAX_DOCX_EXPANDED = 16 * 1024 * 1024

    fun detectImportFormat(fileName: String, mediaType: String?): TextImportFormat? {
        return when (fileName.substringAfterLast('.', "").lowercase(Locale.ROOT)) {
            "txt" -> TextImportFormat.PLAIN_TEXT
            "fountain" -> TextImportFormat.FOUNTAIN
            "fdx" -> TextImportFormat.FDX
            "docx" -> TextImportFormat.DOCX
            "rtf" -> TextImportFormat.RTF
            else -> when {
                mediaType?.startsWith("text/plain", true) == true -> TextImportFormat.PLAIN_TEXT
                mediaType?.contains("finaldraft", true) == true -> TextImportFormat.FDX
                mediaType?.contains("wordprocessingml", true) == true -> TextImportFormat.DOCX
                mediaType?.contains("rtf", true) == true -> TextImportFormat.RTF
                else -> null
            }
        }
    }

    fun decodeUtf8(bytes: ByteArray): String = Charsets.UTF_8.newDecoder()
        .onMalformedInput(CodingErrorAction.REPORT)
        .onUnmappableCharacter(CodingErrorAction.REPORT)
        .decode(ByteBuffer.wrap(bytes)).toString()

    fun importText(format: TextImportFormat, bytes: ByteArray): String {
        require(bytes.size <= MAX_DOCUMENT_BYTES) { "Script exceeds the import size limit." }
        return when (format) {
            TextImportFormat.PLAIN_TEXT, TextImportFormat.FOUNTAIN -> decodeUtf8(bytes)
            TextImportFormat.FDX -> importFdx(decodeUtf8(bytes))
            TextImportFormat.DOCX -> importDocx(bytes)
            TextImportFormat.RTF -> importRtf(bytes.toString(Charsets.ISO_8859_1))
        }
    }

    fun exportPlainText(source: String): ByteArray = source.toByteArray(Charsets.UTF_8)

    fun exportFountain(scriptType: String, source: String): ByteArray {
        require(scriptType == "screenplay") {
            "Fountain export is for screenplay projects. Use TXT or TALORA for stage plays."
        }
        return source.toByteArray(Charsets.UTF_8)
    }

    fun exportFdx(title: String, scriptType: String, source: String): ByteArray {
        require(source.length <= 1_000_000) { "Script is too large to export." }
        val writer = StringWriter()
        val xml = Xml.newSerializer()
        xml.setOutput(writer)
        xml.startDocument("UTF-8", true)
        xml.startTag(null, "FinalDraft")
        xml.attribute(null, "DocumentType", "Script")
        xml.attribute(null, "Template", "No")
        xml.attribute(null, "Version", "1")
        xml.startTag(null, "TitlePage")
        xml.startTag(null, "Content")
        xml.startTag(null, "Paragraph")
        xml.attribute(null, "Type", "Title")
        xml.startTag(null, "Text"); xml.text(title); xml.endTag(null, "Text")
        xml.endTag(null, "Paragraph")
        xml.endTag(null, "Content")
        xml.endTag(null, "TitlePage")
        xml.startTag(null, "Content")
        val lines = source.split('\n')
        var previousWasCharacter = false
        lines.forEach { raw ->
            val line = raw.removeSuffix("\r")
            val trimmed = line.trim()
            val type = when {
                trimmed.isEmpty() -> "Action"
                trimmed.startsWith("INT.", true) || trimmed.startsWith("EXT.", true) ||
                    trimmed.startsWith("INT/EXT.", true) || trimmed.startsWith("I/E.", true) -> "Scene Heading"
                trimmed.matches(Regex("""^(CUT TO:|FADE OUT\.?|FADE IN:|DISSOLVE TO:)$""", RegexOption.IGNORE_CASE)) -> "Transition"
                trimmed.startsWith("(") && trimmed.endsWith(")") -> "Parenthetical"
                trimmed == trimmed.uppercase(Locale.ROOT) &&
                    trimmed.any { it.isLetter() } && trimmed.length <= 45 && !previousWasCharacter -> "Character"
                previousWasCharacter -> "Dialogue"
                else -> "Action"
            }
            xml.startTag(null, "Paragraph"); xml.attribute(null, "Type", type)
            xml.startTag(null, "Text"); xml.text(line); xml.endTag(null, "Text")
            xml.endTag(null, "Paragraph")
            previousWasCharacter = type == "Character" || type == "Parenthetical"
        }
        xml.endTag(null, "Content")
        xml.endTag(null, "FinalDraft")
        xml.endDocument()
        xml.flush()
        return writer.toString().toByteArray(Charsets.UTF_8)
    }

    fun importFdx(source: String): String {
        require(source.length <= MAX_DOCUMENT_BYTES) { "FDX document exceeds the import size limit." }
        val parser = Xml.newPullParser()
        parser.setInput(source.reader())
        val output = mutableListOf<String>()
        var inScriptContent = false
        var paragraphDepth = 0
        var textDepth = 0
        var paragraph = StringBuilder()
        var titlePageDepth = 0
        var totalChars = 0
        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            when (parser.eventType) {
                XmlPullParser.START_TAG -> when (parser.name.substringAfter(':')) {
                    "TitlePage" -> titlePageDepth++
                    "Content" -> if (titlePageDepth == 0) inScriptContent = true
                    "Paragraph" -> if (inScriptContent && titlePageDepth == 0) {
                        paragraphDepth++
                        paragraph = StringBuilder()
                    }
                    "Text" -> if (paragraphDepth > 0) textDepth++
                }
                XmlPullParser.TEXT -> if (textDepth > 0) {
                    paragraph.append(parser.text)
                    totalChars += parser.text.length
                    require(totalChars <= 1_000_000) { "FDX script exceeds the text limit." }
                }
                XmlPullParser.END_TAG -> when (parser.name) {
                    "Text" -> if (textDepth > 0) textDepth--
                    "Paragraph" -> if (paragraphDepth > 0) {
                        paragraphDepth--
                        output += paragraph.toString()
                    }
                    "Content" -> if (titlePageDepth == 0) inScriptContent = false
                    "TitlePage" -> if (titlePageDepth > 0) titlePageDepth--
                }
            }
            parser.next()
        }
        require(output.isNotEmpty()) { "FDX file contains no script paragraphs." }
        return output.joinToString("\n")
    }

    fun exportDocx(title: String, source: String): ByteArray {
        require(source.length <= 1_000_000) { "Script is too large to export." }
        val out = ByteArrayOutputStream()
        ZipOutputStream(out).use { zip ->
            fun entry(name: String, content: String) {
                zip.putNextEntry(ZipEntry(name))
                zip.write(content.toByteArray(Charsets.UTF_8))
                zip.closeEntry()
            }
            entry("[Content_Types].xml", """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
<Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
<Default Extension="xml" ContentType="application/xml"/>
<Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>

</Types>""")
            entry("_rels/.rels", """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
<Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
</Relationships>""")
            entry("word/document.xml", buildDocxDocument(title, source))
            entry("word/_rels/document.xml.rels", """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships"/>""")
        }
        return out.toByteArray()
    }

    private fun buildDocxDocument(title: String, source: String): String {
        fun esc(value: String) = value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
        val body = buildString {
            source.split('\n').forEach { line ->
                append("<w:p><w:r><w:t xml:space=\"preserve\">")
                append(esc(line.removeSuffix("\r"))); append("</w:t></w:r></w:p>")
            }
        }
        return """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<w:document xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"><w:body>$body<w:sectPr/></w:body></w:document>"""
    }

    fun importDocx(bytes: ByteArray): String {
        require(bytes.size <= MAX_DOCUMENT_BYTES) { "DOCX package exceeds the import size limit." }
        var documentXml: ByteArray? = null
        var entries = 0
        var expanded = 0
        ZipInputStream(ByteArrayInputStream(bytes)).use { zip ->
            while (true) {
                val entry = zip.nextEntry ?: break
                entries++
                require(entries <= MAX_DOCX_ENTRIES) { "DOCX contains too many package entries." }
                val name = entry.name
                require(!name.startsWith("/") && !name.split('/').any { it == ".." }) { "Unsafe DOCX package path." }
                if (name == "word/document.xml") {
                    val output = ByteArrayOutputStream()
                    val buffer = ByteArray(8192)
                    while (true) {
                        val read = zip.read(buffer)
                        if (read < 0) break
                        expanded += read
                        require(expanded <= MAX_DOCX_EXPANDED) { "Expanded DOCX exceeds the import limit." }
                        output.write(buffer, 0, read)
                    }
                    documentXml = output.toByteArray()
                } else {
                    val buffer = ByteArray(8192)
                    while (true) {
                        val read = zip.read(buffer)
                        if (read < 0) break
                        expanded += read
                        require(expanded <= MAX_DOCX_EXPANDED) { "Expanded DOCX exceeds the import limit." }
                    }
                }
                zip.closeEntry()
            }
        }
        val xml = documentXml ?: error("DOCX package is missing word/document.xml.")
        val parser = Xml.newPullParser()
        parser.setInput(ByteArrayInputStream(xml), "UTF-8")
        val paragraphs = mutableListOf<String>()
        var inParagraph = false
        var current = StringBuilder()
        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            when (parser.eventType) {
                XmlPullParser.START_TAG -> when (parser.name.substringAfter(':')) {
                    "p" -> { inParagraph = true; current = StringBuilder() }
                    "t" -> if (inParagraph) current.append(parser.nextText())
                    "tab" -> if (inParagraph) current.append('\t')
                    "br", "cr" -> if (inParagraph) current.append('\n')
                }
                XmlPullParser.END_TAG -> if (parser.name.substringAfter(':') == "p" && inParagraph) {
                    paragraphs += current.toString()
                    inParagraph = false
                }
            }
            parser.next()
        }
        require(paragraphs.isNotEmpty()) { "DOCX contains no readable paragraphs." }
        return paragraphs.dropWhile { it.isBlank() }.joinToString("\n")
    }

    fun exportRtf(source: String): ByteArray {
        require(source.length <= 1_000_000) { "Script is too large to export." }
        val out = StringBuilder("{\\rtf1\\ansi\\deff0{\\fonttbl{\\f0 Arial;}}\\f0\\fs22 ")
        source.forEach { ch ->
            when (ch) {
                '\\' -> out.append("\\\\")
                '{' -> out.append("\\{")
                '}' -> out.append("\\}")
                '\n' -> out.append("\\par\n")
                '\r' -> Unit
                else -> if (ch.code in 32..126) out.append(ch) else out.append("\\u").append(ch.code.toShort().toInt()).append("?")
            }
        }
        out.append('}')
        return out.toString().toByteArray(Charsets.US_ASCII)
    }

    fun importRtf(source: String): String {
        require(source.length <= MAX_DOCUMENT_BYTES) { "RTF document exceeds the import size limit." }
        require(source.trimStart().startsWith("{\\rtf")) { "Invalid RTF document header." }
        val out = StringBuilder()
        val skipGroups = mutableListOf<Boolean>()
        var depth = 0
        var skip = false
        var uc = 1
        var fallback = 0
        var i = 0
        while (i < source.length) {
            when (val ch = source[i]) {
                '{' -> { depth++; skipGroups.add(skip); i++ }
                '}' -> { if (depth > 0) { depth--; skip = skipGroups.removeAt(skipGroups.lastIndex) }; i++ }
                '\\' -> {
                    if (i + 1 >= source.length) break
                    val next = source[i + 1]
                    if (next == '\\' || next == '{' || next == '}') {
                        if (!skip && fallback == 0) out.append(next)
                        if (fallback > 0) fallback--
                        i += 2
                    } else if (next == '\'') {
                        if (i + 3 < source.length) {
                            val byte = source.substring(i + 2, i + 4).toInt(16)
                            if (!skip && fallback == 0) out.append(byte.toChar())
                            i += 4
                        } else i = source.length
                    } else if (!next.isLetter()) {
                        when (next) {
                            '*' -> { skip = true; i += 2 }
                            '~' -> { if (!skip && fallback == 0) out.append('\u00a0'); i += 2 }
                            '-' -> { if (!skip && fallback == 0) out.append('\u00ad'); i += 2 }
                            '_' -> { if (!skip && fallback == 0) out.append('\u2011'); i += 2 }
                            '\n', '\r' -> i += 2
                            else -> { i += 2 }
                        }
                    } else {
                        val start = i + 1
                        var end = start
                        while (end < source.length && source[end].isLetter()) end++
                        val word = source.substring(start, end).lowercase(Locale.ROOT)
                        var numberEnd = end
                        if (numberEnd < source.length && (source[numberEnd] == '-' || source[numberEnd].isDigit())) {
                            if (source[numberEnd] == '-') numberEnd++
                            while (numberEnd < source.length && source[numberEnd].isDigit()) numberEnd++
                        }
                        val arg = source.substring(end, numberEnd).toIntOrNull()
                        if (numberEnd < source.length && source[numberEnd] == ' ') numberEnd++
                        if (word == "uc" && arg != null) uc = arg.coerceIn(0, 8)
                        when (word) {
                            "par", "line" -> if (!skip && fallback == 0) out.append('\n')
                            "tab" -> if (!skip && fallback == 0) out.append('\t')
                            "u" -> if (!skip && arg != null) { out.append(arg.toChar()); fallback = uc }
                            "fonttbl", "colortbl", "stylesheet", "info", "pict", "object", "header", "footer", "filetbl", "listtable", "listoverridetable" -> skip = true
                        }
                        i = numberEnd
                    }
                }
                '\r', '\n' -> i++
                else -> {
                    if (!skip && fallback == 0) out.append(ch)
                    else if (fallback > 0 && !ch.isWhitespace()) fallback--
                    i++
                }
            }
            require(out.length <= 1_000_000) { "RTF script exceeds the text limit." }
        }
        return out.toString().trimEnd()
    }
}
