package com.richpathanimator

import android.animation.Animator
import android.animation.AnimatorSet
import android.view.animation.Interpolator
import com.richpath.RichPath
import com.richpath.model.Group

class RichPathAnimator {

    var duration = -1L
    var startDelay = -1L
    var interpolator: Interpolator? = null
    var repeatMode: RepeatMode = RepeatMode.None
    var repeatCount: Int? = null
    var animationListener: AnimationListener? = null

    private val animationBuilders = arrayListOf<AnimationBuilder>()

    private var animatorSet: AnimatorSet? = null

    private var prev: RichPathAnimator? = null
    private var next: RichPathAnimator? = null

    companion object {
        const val INFINITE = -1

        @JvmField
        val NONE = RepeatMode.None
        @JvmField
        val RESTART = RepeatMode.Restart
        @JvmField
        val REVERSE = RepeatMode.Reverse

        @JvmStatic
        fun animate(vararg paths: RichPath): AnimationBuilder {
            val viewAnimator = RichPathAnimator()
            return viewAnimator.addAnimationBuilder(paths)
        }

        @JvmStatic
        fun animate(vararg groups: Group): AnimationBuilder{
            val viewAnimator = RichPathAnimator()
            return viewAnimator.addAnimationBuilder(groups.map { it.paths }.flatten().toTypedArray())
        }

    }

    internal fun addAnimationBuilder(paths: Array<out RichPath>): AnimationBuilder {
        val animationBuilder = AnimationBuilder(this, paths)
        animationBuilders.add(animationBuilder)
        return animationBuilder
    }


    internal fun thenAnimate(paths: Array<out RichPath>): AnimationBuilder {
        val nextRichPathAnimator = RichPathAnimator()
        this.next = nextRichPathAnimator
        nextRichPathAnimator.prev = this
        return nextRichPathAnimator.addAnimationBuilder(paths)
    }

    private fun createAnimatorSet(): AnimatorSet {
        val animators = arrayListOf<Animator>()
        for (animationBuilder in animationBuilders) {
            val animatorList = animationBuilder.animators
            animators.addAll(animatorList)
            if (repeatMode != RepeatMode.None) {
                animationBuilder.repeatMode(repeatMode)
            }
            repeatCount?.let(animationBuilder::repeatCount)
        }

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animators)

        if (duration != -1L) {
            animatorSet.duration = duration
        }
        if (startDelay != -1L) {
            animatorSet.startDelay = startDelay
        }
        interpolator?.let { animatorSet.interpolator = it }

        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
                animationListener?.onStart()
            }

            override fun onAnimationEnd(p0: Animator) {
                animationListener?.onStop()
                next?.let {
                    it.prev = null
                    it.start()
                }
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
            }
        })

        return animatorSet
    }

    fun start(): RichPathAnimator {
        prev?.start() ?: run {
            animatorSet = createAnimatorSet().apply {
                start()
            }
        }
        return this
    }

    fun cancel() {
        animatorSet?.let {
            if (it.isRunning) {
                it.cancel()
            }
        }

        next?.let {
            it.cancel()
            next = null
        }
    }
}
