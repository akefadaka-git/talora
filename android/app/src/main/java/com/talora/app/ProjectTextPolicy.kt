package com.talora.app

/**
 * Canonical script text is stored as supplied. Search normalization and
 * presentation transforms must never overwrite this source value.
 */
object ProjectTextPolicy {
    fun preserveSourceText(value: String): String = value
}
