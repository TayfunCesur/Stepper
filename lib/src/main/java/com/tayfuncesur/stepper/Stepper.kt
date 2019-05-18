package com.tayfuncesur.stepper

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.widget.RelativeLayout


class Stepper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var stepCount: Int = 5

    private var currentStepCount: Int = 1

    private var defaultDuration: Long = 500

    private var screenWidth: Int = 0

    private var isRunning = false

    private var inProgress = false

    private lateinit var completeListener: () -> Unit

    private lateinit var progressValueAnimator: ValueAnimator

    private lateinit var progressAnimationListener: Animator.AnimatorListener

    fun getStepCount(): Int {
        return stepCount
    }

    fun setStepCount(stepCount: Int) {
        this.stepCount = stepCount
    }

    fun getDefaultDuration(): Long {
        return defaultDuration
    }

    fun setDefaultDuration(defaultDuration: Long) {
        this.defaultDuration = defaultDuration
    }

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
        if (childCount != 1) throw IllegalStateException("Stepper must have only one child layout")

        val attr = context.obtainStyledAttributes(attrs, R.styleable.Stepper, defStyleAttr, 0)
        stepCount = attr.getInt(R.styleable.Stepper_stepCount, 5)
        defaultDuration = attr.getInt(R.styleable.Stepper_duration, 500).toLong()
        attr.recycle()

        post {
            val metrics = context.resources.displayMetrics
            screenWidth = metrics.widthPixels
            getChildAt(0).layoutParams = LayoutParams((screenWidth / stepCount), getChildAt(0).height)
        }
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

            progressValueAnimator.addListener(progressAnimationListener)


            progressValueAnimator.repeatCount = if (loopSize == 0) Animation.INFINITE else loopSize - 1
            progressValueAnimator.duration = 1000
            progressValueAnimator.start()
            inProgress = true
        }
        return this
    }

    fun stop() {
        if (::progressValueAnimator.isInitialized && inProgress) {
            progressValueAnimator.removeAllListeners()
            progressValueAnimator.end()
            progressValueAnimator.cancel()
            getChildAt(0).layoutParams =
                LayoutParams(screenWidth - (screenWidth * Math.abs(currentStepCount - stepCount)), getChildAt(0).height)
        }
        isRunning = false
        inProgress = false

    }

}