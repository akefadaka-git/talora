package com.talora.app

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

class ScriptInterchangeTest {
    @Test
    fun detectsOnlySupportedPlainTextAndFountainFormats() {
        assertEquals(TextImportFormat.PLAIN_TEXT, ScriptInterchange.detectImportFormat("draft.txt", "text/plain"))
        assertEquals(TextImportFormat.FOUNTAIN, ScriptInterchange.detectImportFormat("draft.fountain", "application/octet-stream"))
        assertNull(ScriptInterchange.detectImportFormat("draft.fdx", "application/xml"))
    }

    @Test
    fun preservesUtf8SourceTextExactly() {
        val source = "ሰላም\nINT. ROOM - DAY\n"
        assertEquals(source, ScriptInterchange.decodeUtf8(ScriptInterchange.exportPlainText(source)))
    }

    @Test
    fun fountainExportRejectsStagePlayProjects() {
        assertThrows(IllegalArgumentException::class.java) {
            ScriptInterchange.exportFountain("stage_play", "ACT I")
        }
    }
}
