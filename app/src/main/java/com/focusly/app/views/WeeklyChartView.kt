package com.focusly.app.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.focusly.app.R

class WeeklyChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val barPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primary_green)
        style = Paint.Style.FILL
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.muted_text)
        textSize = 12f * resources.displayMetrics.density
        textAlign = Paint.Align.CENTER
    }

    // Normalized heights for 7 days (0.0 to 1.0)
    // Mon, Tue, Wed, Thu, Fri, Sat, Sun
    private var barValues = floatArrayOf(0.55f, 0.90f, 0.35f, 0.70f, 0.98f, 0.75f, 0.20f)
    private val dayLabels = arrayOf("M", "T", "W", "T", "F", "S", "S")
    private val barRect = RectF()

    fun setWeeklyData(values: FloatArray) {
        if (values.size == 7) {
            barValues = values
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val count = barValues.size
        if (count == 0) return

        val paddingLeft = paddingLeft.toFloat()
        val paddingTop = paddingTop.toFloat()
        val availableWidth = width.toFloat() - paddingLeft - paddingRight
        
        // Reserve height for day labels
        val textPaddingTop = 14f * resources.displayMetrics.density
        val textHeight = 18f * resources.displayMetrics.density
        val availableHeight = height.toFloat() - paddingTop - paddingBottom - textPaddingTop - textHeight

        val barSpacing = availableWidth * 0.045f
        val barWidth = (availableWidth - (barSpacing * (count - 1))) / count
        val cornerRadius = barWidth / 2f

        for (i in 0 until count) {
            val left = paddingLeft + i * (barWidth + barSpacing)
            val right = left + barWidth
            val value = barValues[i].coerceIn(0.15f, 1.0f)
            val barHeight = availableHeight * value
            val top = paddingTop + (availableHeight - barHeight)
            val bottom = paddingTop + availableHeight

            barRect.set(left, top, right, bottom)
            canvas.drawRoundRect(barRect, cornerRadius, cornerRadius, barPaint)

            // Draw day text below bar
            val textX = left + (barWidth / 2f)
            val textY = bottom + textPaddingTop + (textHeight * 0.6f)
            if (i < dayLabels.size) {
                canvas.drawText(dayLabels[i], textX, textY, textPaint)
            }
        }
    }
}
