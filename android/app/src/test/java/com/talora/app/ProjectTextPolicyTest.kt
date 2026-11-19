package com.talora.app

import org.junit.Assert.assertEquals
import org.junit.Test

class ProjectTextPolicyTest {
    @Test
    fun preservesEthiopicAndMixedScriptSourceExactly() {
        val source = "ሰላም ዓለም — 123 ABC\nINT. ቤት - ሌሊት"
        assertEquals(source, ProjectTextPolicy.preserveSourceText(source))
    }

    @Test
    fun preservesEmptyDocument() {
        assertEquals("", ProjectTextPolicy.preserveSourceText(""))
    }
}
