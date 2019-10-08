@file:Suppress("RedundantGetter")

package com.tayfuncesur.stepper

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.text.TextUtilsCompat
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.RelativeLayout
import java.util.*
import kotlin.math.abs


@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
class Stepper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    enum class Direction {
        NO_RTL, AUTO, FORCE
    }

    private var stepCount: Int

    private var currentStepCount: Int = 1

    private var defaultDuration: Long

    private var layoutDirection: Direction = Direction.NO_RTL

    val isLeftToRight =
        TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == ViewCompat.LAYOUT_DIRECTION_LTR

    private var screenWidth: Int = 0

    private var isRunning = false

    private var inProgress = false

    private lateinit var completeListener: () -> Unit

    private lateinit var progressValueAnimator: ValueAnimator

    private lateinit var progressAnimationListener: Animator.AnimatorListener


    fun addOnCompleteListener(addOnCompleteListener: () -> Unit) {
        this.completeListener = addOnCompleteListener
    }

    private fun getValueAnimator(
        animator: ValueAnimator = ValueAnimator.ofInt(
            getChildAt(0).width,
            (screenWidth / stepCount) * currentStepCount
        )
    ): ValueAnimator {
        animator.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = getChildAt(0).layoutParams
            layoutParams.width = `val`
            getChildAt(0).layoutParams = layoutParams
        }

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                isRunning = !isRunning
                if (::completeListener.isInitialized)
                    completeListener.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationStart(animation: Animator?) {
                isRunning = !isRunning
            }
        })

        animator.duration = defaultDuration

        return animator
    }

    init {
        with(context.obtainStyledAttributes(attrs, R.styleable.Stepper, defStyleAttr, 0)) {
            try {
                stepCount = getInt(R.styleable.Stepper_stepCount, 5)
                defaultDuration = getInt(R.styleable.Stepper_duration, 500).toLong()
                layoutDirection = Direction.values()[getInt(R.styleable.Stepper_useRTL, 0)]


                when (layoutDirection) {
                    Direction.FORCE -> rotate()
                    Direction.AUTO -> {
                        if (resources.getBoolean(R.bool.useRtl)) {
                            rotate()
                        }
                    }
                }
            } finally {
                recycle()
            }


            post {
                if (childCount != 1) throw IllegalStateException("Stepper must have only one child layout")
                if (layoutParams.width != ViewGroup.LayoutParams.MATCH_PARENT) throw IllegalStateException(
                    "Stepper must have match_parent width for correct support of RTL/LTR"
                )

                val metrics = context.resources.displayMetrics
                screenWidth = metrics.widthPixels

                getChildAt(0).layoutParams = (getChildAt(0).layoutParams as LayoutParams).apply {
                    width = (screenWidth / stepCount)


                }
            }
        }
    }

    private fun rotate() {
        this@Stepper.rotation = (180f)
    }

    fun forward() {
        if (inProgress) {
            stop()
        }

        if (!isRunning) {
            currentStepCount++
            getValueAnimator().start()
        }
    }

    fun back() {
        if (inProgress) {
            stop()
        }

        if (!isRunning) {
            currentStepCount--
            getValueAnimator().start()
        }
    }

    fun progress(loopSize: Int = 0): Stepper {
        if (!isRunning && !inProgress) {
            progressValueAnimator = getValueAnimator(
                animator = ValueAnimator.ofInt(
                    0,
                    screenWidth
                )
            )

            var leftOffset = 1

            progressValueAnimator.addUpdateListener { valueAnimator ->
                val currentVal = valueAnimator.animatedValue as Int
                val layoutParams = getChildAt(0).layoutParams as LayoutParams
                layoutParams.width =
                    if (layoutParams.leftMargin < screenWidth && currentVal < screenWidth) currentVal else 0
                layoutParams.leftMargin += leftOffset
                leftOffset += 1
                getChildAt(0).layoutParams = layoutParams
            }

            progressAnimationListener = object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                    val layoutParams = (getChildAt(0).layoutParams as LayoutParams)
                    layoutParams.leftMargin = 0
                    getChildAt(0).layoutParams = layoutParams
                    leftOffset = 1
                }

                override fun onAnimationEnd(animation: Animator?) {
                    stop()
                }

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationStart(animation: Animator?) {}
            }

            progressValueAnimator.apply {
                addListener(progressAnimationListener)
                repeatCount = if (loopSize == 0) Animation.INFINITE else loopSize - 1
                duration = 1000
                start()
            }

            inProgress = true
        }
        return this
    }

    fun stop() {
        if (::progressValueAnimator.isInitialized && inProgress) {
            progressValueAnimator.apply {
                removeAllListeners()
                end()
                cancel()
            }

            getChildAt(0).layoutParams =
                LayoutParams(
                    screenWidth - (screenWidth * abs(currentStepCount - stepCount)),
                    getChildAt(0).height
                )
        }

        isRunning = false
        inProgress = false

    }

}