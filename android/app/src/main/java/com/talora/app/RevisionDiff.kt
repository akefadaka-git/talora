package com.talora.app

enum class RevisionDiffKind { UNCHANGED, ADDED, REMOVED }

data class RevisionDiffLine(val kind: RevisionDiffKind, val text: String)

/** Bounded line comparison. Large inputs fall back to a compact changed-block view. */
object RevisionDiff {
    private const val MAX_MATRIX_CELLS = 250_000L

    fun compare(before: String, after: String): List<RevisionDiffLine> {
        val left = before.split('\n')
        val right = after.split('\n')
        if (left.size.toLong() * right.size.toLong() > MAX_MATRIX_CELLS) {
            return coarseCompare(left, right)
        }

        val lcs = Array(left.size + 1) { IntArray(right.size + 1) }
        for (i in left.indices.reversed()) {
            for (j in right.indices.reversed()) {
                lcs[i][j] = if (left[i] == right[j]) {
                    1 + lcs[i + 1][j + 1]
                } else {
                    maxOf(lcs[i + 1][j], lcs[i][j + 1])
                }
            }
        }

        val result = mutableListOf<RevisionDiffLine>()
        var i = 0
        var j = 0
        while (i < left.size && j < right.size) {
            when {
                left[i] == right[j] -> {
                    result += RevisionDiffLine(RevisionDiffKind.UNCHANGED, left[i])
                    i++
                    j++
                }
                lcs[i + 1][j] >= lcs[i][j + 1] -> {
                    result += RevisionDiffLine(RevisionDiffKind.REMOVED, left[i++])
                }
                else -> result += RevisionDiffLine(RevisionDiffKind.ADDED, right[j++])
            }
        }
        while (i < left.size) result += RevisionDiffLine(RevisionDiffKind.REMOVED, left[i++])
        while (j < right.size) result += RevisionDiffLine(RevisionDiffKind.ADDED, right[j++])
        return result
    }

    private fun coarseCompare(left: List<String>, right: List<String>): List<RevisionDiffLine> {
        var prefix = 0
        while (prefix < minOf(left.size, right.size) && left[prefix] == right[prefix]) prefix++
        var suffix = 0
        while (suffix < minOf(left.size - prefix, right.size - prefix) &&
            left[left.lastIndex - suffix] == right[right.lastIndex - suffix]
        ) suffix++

        val result = mutableListOf<RevisionDiffLine>()
        val prefixShown = minOf(prefix, 3)
        for (i in 0 until prefixShown) result += RevisionDiffLine(RevisionDiffKind.UNCHANGED, left[i])
        if (prefix > prefixShown) {
            result += RevisionDiffLine(RevisionDiffKind.UNCHANGED, "… ${prefix - prefixShown} common leading lines omitted …")
        }
        result += RevisionDiffLine(RevisionDiffKind.REMOVED, "… ${left.size - prefix - suffix} earlier-version lines changed/removed …")
        result += RevisionDiffLine(RevisionDiffKind.ADDED, "… ${right.size - prefix - suffix} later-version lines changed/added …")
        val suffixShown = minOf(suffix, 3)
        if (suffix > suffixShown) {
            result += RevisionDiffLine(RevisionDiffKind.UNCHANGED, "… ${suffix - suffixShown} common trailing lines omitted …")
        }
        for (i in suffixShown downTo 1) result += RevisionDiffLine(RevisionDiffKind.UNCHANGED, left[left.size - i])
        return result
    }
}
