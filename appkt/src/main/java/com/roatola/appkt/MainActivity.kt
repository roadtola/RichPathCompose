package com.roatola.appkt

import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.richpath.RichPath
import com.richpath.model.Group
import com.richpathanimator.RichPathAnimator
import com.roatola.appkt.databinding.ActivityAnimationSamplesBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationSamplesBinding
    private var reverse = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnimationSamplesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateCommand()

        binding.animalRichPathView.setOnClickListener { animateAnimal() }
        binding.icArrowSearchRichPathView.setOnClickListener { animateArrowToSearch() }
        binding.icNotificationsRichPathView.setOnClickListener { animateNotification() }
        binding.icPlaylistAddCheckRichPathView.setOnClickListener { animatePlaylistAddCheck() }
        binding.loveFaceRichPathView.setOnClickListener { animateLoveFace() }
    }

    private fun animateCommand() {

        val part1 = binding.icCommandRichPathView.findRichPathByName("part1")!!
        val part2 = binding.icCommandRichPathView.findRichPathByName("part2")!!
        val part3 = binding.icCommandRichPathView.findRichPathByName("part3")!!
        val part4 = binding.icCommandRichPathView.findRichPathByName("part4")!!
        val part5 = binding.icCommandRichPathView.findRichPathByName("part5")!!
        val part6 = binding.icCommandRichPathView.findRichPathByName("part6")!!
        val part7 = binding.icCommandRichPathView.findRichPathByName("part7")!!
        val part8 = binding.icCommandRichPathView.findRichPathByName("part8")!!

        RichPathAnimator
            .animate(part1)
            .trimPathOffset(0f, 1.0f)

            .andAnimate(part2)
            .trimPathOffset(0.125f, 1.125f)

            .andAnimate(part3)
            .trimPathOffset(0.250f, 1.250f)

            .andAnimate(part4)
            .trimPathOffset(0.375f, 1.375f)

            .andAnimate(part5)
            .trimPathOffset(0.500f, 1.500f)

            .andAnimate(part6)
            .trimPathOffset(0.625f, 1.625f)

            .andAnimate(part7)
            .trimPathOffset(0.750f, 1.750f)

            .andAnimate(part8)
            .trimPathOffset(0.875f, 1.875f)

            .durationSet(2000)
            .repeatModeSet(RichPathAnimator.RESTART)
            .repeatCountSet(RichPathAnimator.INFINITE)
            .interpolatorSet(LinearInterpolator())
            .start()
    }

    private fun animateAnimal() {

        val hippoPathData = getString(R.string.hippo_path)
        val elephantPathData = getString(R.string.elephant_path)
        val bullPathData = getString(R.string.bull_path)

        val richPath = binding.animalRichPathView.findFirstRichPath()!!

        RichPathAnimator
            .animate(richPath)
            .pathData(elephantPathData)
            .duration(600)

            .thenAnimate(richPath)
            .pathData(bullPathData)
            .duration(600)

            .thenAnimate(richPath)
            .pathData(hippoPathData)
            .duration(600)

            .start()
    }

    private fun animateArrowToSearch() {

        val searchCircle = binding.icArrowSearchRichPathView.findRichPathByName("search_circle")!!
        val stem = binding.icArrowSearchRichPathView.findRichPathByName("stem")!!
        val arrowTop = binding.icArrowSearchRichPathView.findRichPathByName("arrow_head_top")!!
        val arrowBottom = binding.icArrowSearchRichPathView.findRichPathByName("arrow_head_bottom")!!

        if (reverse) {
            RichPathAnimator.animate(stem)
                .trimPathStart(0f, 0.75f)
                .trimPathEnd(0.185f, 1f)
                .andAnimate(searchCircle)
                .trimPathEnd(1f, 0f)
                .andAnimate(arrowTop, arrowBottom)
                .trimPathEnd(0f, 1f)
                .start()
        } else {
            RichPathAnimator.animate(stem)
                .trimPathStart(0.75f, 0f)
                .trimPathEnd(1f, 0.185f)
                .andAnimate(searchCircle)
                .trimPathEnd(0f, 1f)
                .andAnimate(arrowTop, arrowBottom)
                .trimPathEnd(1f, 0f)
                .start()
        }
        reverse = !reverse
    }

    private fun animateNotification() {

        val top = binding.icNotificationsRichPathView.findRichPathByIndex(0)!!
        val bottom = binding.icNotificationsRichPathView.findRichPathByIndex(1)!!

        RichPathAnimator.animate(top)
            .interpolator(DecelerateInterpolator())
            .rotation(0f, 20f, -20f, 10f, -10f, 5f, -5f, 2f, -2f, 0f)
            .duration(4000)
            .andAnimate(bottom)
            .interpolator(DecelerateInterpolator())
            .rotation(0f, 10f, -10f, 5f, -5f, 2f, -2f, 0f)
            .startDelay(50)
            .duration(4000)
            .start()
    }

    private fun animatePlaylistAddCheck() {

        val line1 = binding.icPlaylistAddCheckRichPathView.findRichPathByName("line1")!!
        val line2 = binding.icPlaylistAddCheckRichPathView.findRichPathByName("line2")!!
        val line3 = binding.icPlaylistAddCheckRichPathView.findRichPathByName("line3")!!
        val tick = binding.icPlaylistAddCheckRichPathView.findRichPathByName("tick")!!
        val line3AndTick = binding.icPlaylistAddCheckRichPathView.findRichPathByName("line3_tick")!!

        line1.trimPathEnd = 0f
        line2.trimPathEnd = 0f
        line3.trimPathEnd = 0f
        tick.trimPathEnd = 0f
        line3AndTick.trimPathEnd = 0f
        line3AndTick.trimPathStart = 0f

        val duration = 400

        RichPathAnimator.animate(line1)
            .trimPathEnd(0f, 1f)
            .interpolator(DecelerateInterpolator())
            .duration(duration.toLong())

            .andAnimate(line2)
            .trimPathEnd(0f, 1f)
            .interpolator(DecelerateInterpolator())
            .startDelay(140)
            .duration(duration.toLong())

            .andAnimate(line3)
            .trimPathEnd(0f, 1f)
            .interpolator(LinearInterpolator())
            .startDelay(180)
            .duration(duration.toLong())

            .andAnimate(line3AndTick)
            .trimPathEnd(0.33f, 0.428f)
            .interpolator(LinearInterpolator())
            .startDelay((140 + duration).toLong())
            .duration((duration / 3).toLong())

            .thenAnimate(line3AndTick)
            .trimPathStart(0.33f, 0.428f)
            .interpolator(LinearInterpolator())
            .duration((duration / 3).toLong())

            .andAnimate(tick)
            .trimPathEnd(0f, 1f)
            .interpolator(LinearInterpolator())
            .duration(duration.toLong())

            .start()
    }

    private fun animateLoveFace() {

        val rEye = binding.loveFaceRichPathView?.findRichPathByName("r_eye")!!
        val lEye = binding.loveFaceRichPathView?.findRichPathByName("l_eye")!!

        rEye.isPivotToCenter = true
        lEye.isPivotToCenter = true

        RichPathAnimator
            .animate(rEye, lEye)
            .interpolator(LinearInterpolator())
            .duration(800)
            .repeatMode(RichPathAnimator.RESTART)
            .repeatCount(RichPathAnimator.INFINITE)
            .scale(1f, 0.9f, 1.07f, 1f)
            .fillColor(-0xad3a5, -0xdb68a, -0x29e5b4, -0xad3a5)
            .start()
    }


}