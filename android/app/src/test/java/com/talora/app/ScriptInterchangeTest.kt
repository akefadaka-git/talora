package com.talora.app

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class ScriptInterchangeTest {
    @Test
    fun detectsOnlySupportedFormats() {
        assertEquals(TextImportFormat.PLAIN_TEXT, ScriptInterchange.detectImportFormat("draft.txt", "text/plain"))
        assertEquals(TextImportFormat.FOUNTAIN, ScriptInterchange.detectImportFormat("draft.fountain", "text/plain"))
        assertEquals(TextImportFormat.FDX, ScriptInterchange.detectImportFormat("draft.fdx", "application/xml"))
        assertEquals(TextImportFormat.DOCX, ScriptInterchange.detectImportFormat("draft.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
        assertEquals(TextImportFormat.RTF, ScriptInterchange.detectImportFormat("draft.rtf", "application/rtf"))
        assertNull(ScriptInterchange.detectImportFormat("draft.fdx.exe", "application/octet-stream"))
    }

    @Test
    fun preservesUtf8SourceTextExactly() {
        val source = "ሰላም\nINT. ROOM - DAY\n"
        assertEquals(source, ScriptInterchange.importText(TextImportFormat.PLAIN_TEXT, ScriptInterchange.exportPlainText(source)))
    }

    @Test
    fun fountainExportRejectsStagePlayProjects() {
        assertThrows(IllegalArgumentException::class.java) {
            ScriptInterchange.exportFountain("stage_play", "ACT I")
        }
    }

    @Test
    fun fdxRoundTripPreservesParagraphTextAndEthiopicCharacters() {
        val source = "INT. ROOM - DAY\nሰላም\nALMA\nHello there.\nCUT TO:"
        val imported = ScriptInterchange.importFdx(ScriptInterchange.exportFdx("A Test", "screenplay", source).toString(Charsets.UTF_8))
        assertEquals(source, imported)
    }

    @Test
    fun docxRoundTripPreservesParagraphsAndUnicode() {
        val source = "INT. ROOM - DAY\nሰላም\nA line with {braces} & symbols"
        val bytes = ScriptInterchange.exportDocx("A Test", source)
        assertTrue(bytes.size > 100)
        assertEquals(source, ScriptInterchange.importDocx(bytes))
    }

    @Test
    fun rtfRoundTripPreservesEscapesAndUnicode() {
        val source = "ሰላም {scene} \\ action\nNext line"
        val imported = ScriptInterchange.importRtf(ScriptInterchange.exportRtf(source).toString(Charsets.US_ASCII))
        assertEquals(source, imported)
    }

    @Test
    fun rejectsMalformedUtf8AndInvalidRtf() {
        assertThrows(Exception::class.java) { ScriptInterchange.decodeUtf8(byteArrayOf(0xC3.toByte(), 0x28)) }
        assertThrows(IllegalArgumentException::class.java) { ScriptInterchange.importRtf("plain text") }
    }
}
