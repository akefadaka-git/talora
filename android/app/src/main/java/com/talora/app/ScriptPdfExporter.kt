package com.talora.app

import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import java.io.ByteArrayOutputStream

/** Simple offline PDF renderer. Source text remains unchanged in the project store. */
object ScriptPdfExporter {
    private const val PAGE_WIDTH = 595
    private const val PAGE_HEIGHT = 842
    private const val MARGIN = 48f
    private const val MAX_SOURCE_CHARS = 1_000_000
    private const val MAX_PAGES = 500

    fun create(title: String, source: String): ByteArray {
        require(source.length <= MAX_SOURCE_CHARS) { "Script is too large for PDF export." }
        val document = PdfDocument()
        val output = ByteArrayOutputStream()
        val bodyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create("sans-serif", Typeface.NORMAL)
            textSize = 10f
        }
        val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            textSize = 16f
        }
        val lineHeight = 15f
        val maxWidth = PAGE_WIDTH - MARGIN * 2
        var pageNumber = 0
        var page: PdfDocument.Page? = null
        var y = MARGIN

        fun beginPage() {
            pageNumber++
            require(pageNumber <= MAX_PAGES) { "PDF export exceeds the page limit." }
            val info = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageNumber).create()
            page = document.startPage(info)
            y = MARGIN
        }

        fun endPage() {
            page?.let { document.finishPage(it) }
            page = null
        }

        fun drawWrapped(value: String, paint: Paint, spacing: Float) {
            if (value.isEmpty()) {
                y += spacing
                return
            }
            var offset = 0
            while (offset < value.length) {
                if (y > PAGE_HEIGHT - MARGIN) {
                    endPage()
                    beginPage()
                }
                val count = paint.breakText(value, offset, value.length, true, maxWidth, null).coerceAtLeast(1)
                page!!.canvas.drawText(value.substring(offset, offset + count), MARGIN, y, paint)
                y += spacing
                offset += count
            }
        }

        try {
            beginPage()
            drawWrapped(title.ifBlank { "TALORA Script" }, titlePaint, 24f)
            y += 12f
            source.split('\n').forEach { line -> drawWrapped(line, bodyPaint, lineHeight) }
            endPage()
            document.writeTo(output)
            return output.toByteArray()
        } finally {
            page?.let { document.finishPage(it) }
            document.close()
        }
    }
}
