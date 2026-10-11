package com.focusly.app.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.focusly.app.R
import kotlin.math.sin

class DotWaveView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val dotCount = 3
    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.hero_subtext)
        style = Paint.Style.FILL
    }

    private var animPhase = 0f
    private var animator: ValueAnimator? = null

    init {
        startAnimation()
    }

    private fun startAnimation() {
        animator?.cancel()
        animator = ValueAnimator.ofFloat(0f, (2 * Math.PI).toFloat()).apply {
            duration = 1200
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { animation ->
                animPhase = animation.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val availableWidth = width.toFloat() - paddingLeft - paddingRight
        val availableHeight = height.toFloat() - paddingTop - paddingBottom
        val centerY = paddingTop + (availableHeight / 2f)

        val dotRadius = 4f * resources.displayMetrics.density
        val dotSpacing = 16f * resources.displayMetrics.density
        val bounceAmplitude = 6f * resources.displayMetrics.density

        val totalWidth = (dotCount * dotRadius * 2) + ((dotCount - 1) * dotSpacing)
        var startX = paddingLeft + ((availableWidth - totalWidth) / 2f) + dotRadius

        for (i in 0 until dotCount) {
            val offsetPhase = animPhase - (i * 0.8f)
            val offsetY = sin(offsetPhase) * bounceAmplitude
            val alpha = ((sin(offsetPhase) + 1f) / 2f * 155 + 100).toInt().coerceIn(0, 255)

            dotPaint.alpha = alpha
            canvas.drawCircle(startX, centerY + offsetY, dotRadius, dotPaint)
            startX += (dotRadius * 2) + dotSpacing
        }
    }
}
