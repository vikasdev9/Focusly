package com.focusly.app.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.focusly.app.R

class ProgressRingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.soft_sage)
        style = Paint.Style.STROKE
        strokeWidth = 10f * resources.displayMetrics.density
        strokeCap = Paint.Cap.ROUND
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primary_green)
        style = Paint.Style.STROKE
        strokeWidth = 10f * resources.displayMetrics.density
        strokeCap = Paint.Cap.ROUND
    }

    private val bounds = RectF()
    private var progressRatio: Float = 1.0f // 0.0 to 1.0

    fun setProgress(progress: Float) {
        this.progressRatio = progress.coerceIn(0f, 1f)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val stroke = trackPaint.strokeWidth
        val padding = stroke / 2f + 8f
        bounds.set(
            paddingLeft + padding,
            paddingTop + padding,
            width - paddingRight - padding,
            height - paddingBottom - padding
        )

        // Draw background track
        canvas.drawArc(bounds, 0f, 360f, false, trackPaint)

        // Draw active progress
        val sweepAngle = 360f * progressRatio
        canvas.drawArc(bounds, -90f, sweepAngle, false, progressPaint)
    }
}
