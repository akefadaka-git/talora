package com.talora.app

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RevisionDiffTest {
    @Test
    fun reportsAddedRemovedAndUnchangedLines() {
        val result = RevisionDiff.compare("A\nB\nC", "A\nX\nC\nD")
        assertEquals(
            listOf(
                RevisionDiffLine(RevisionDiffKind.UNCHANGED, "A"),
                RevisionDiffLine(RevisionDiffKind.REMOVED, "B"),
                RevisionDiffLine(RevisionDiffKind.ADDED, "X"),
                RevisionDiffLine(RevisionDiffKind.UNCHANGED, "C"),
                RevisionDiffLine(RevisionDiffKind.ADDED, "D")
            ),
            result
        )
    }

    @Test
    fun largeInputUsesBoundedFallback() {
        val before = (1..600).joinToString("\n") { "line-$it" }
        val after = before.replace("line-300", "changed-300")
        val result = RevisionDiff.compare(before, after)
        assertTrue(result.size < 10)
        assertEquals(RevisionDiffKind.ADDED, result[1].kind)
    }
}
