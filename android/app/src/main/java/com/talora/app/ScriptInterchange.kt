package com.talora.app

import java.nio.ByteBuffer
import java.nio.charset.CodingErrorAction

enum class TextImportFormat { PLAIN_TEXT, FOUNTAIN }

/** Explicitly supported UTF-8 interchange formats. Unknown formats fail closed. */
object ScriptInterchange {
    fun detectImportFormat(fileName: String, mediaType: String?): TextImportFormat? {
        return when (fileName.substringAfterLast('.', "").lowercase()) {
            "txt" -> TextImportFormat.PLAIN_TEXT
            "fountain" -> TextImportFormat.FOUNTAIN
            else -> if (mediaType?.startsWith("text/plain", ignoreCase = true) == true) {
                TextImportFormat.PLAIN_TEXT
            } else null
        }
    }

    fun decodeUtf8(bytes: ByteArray): String {
        return Charsets.UTF_8.newDecoder()
            .onMalformedInput(CodingErrorAction.REPORT)
            .onUnmappableCharacter(CodingErrorAction.REPORT)
            .decode(ByteBuffer.wrap(bytes))
            .toString()
    }

    fun exportPlainText(source: String): ByteArray = source.toByteArray(Charsets.UTF_8)

    fun exportFountain(scriptType: String, source: String): ByteArray {
        require(scriptType == "screenplay") {
            "Fountain export is for screenplay projects. Use plain text for stage plays."
        }
        return source.toByteArray(Charsets.UTF_8)
    }
}
