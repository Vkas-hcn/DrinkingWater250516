package com.leaning.against.mountains.drinkingwater.wight

import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import android.widget.ImageView
import com.leaning.against.mountains.drinkingwater.R

class CustomCupProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    private var currentProgress = 0

    /**
     * 设置进度值 (0 - 100)
     */
    fun setProgress(progress: Int) {
        val targetResId = when (progress) {
            in 0..19 -> R.drawable.icon_cup_0
            in 20..39 -> R.drawable.icon_cup_1
            in 40..49 -> R.drawable.icon_cup_2
            in 50..69 -> R.drawable.icon_cup_3
            in 70..99 -> R.drawable.icon_cup_4
            100 -> R.drawable.icon_cup_5
            else -> R.drawable.icon_cup_5
        }

        // 如果是第一次设置或相同资源，直接设置
        if (currentProgress == 0 || drawable == null || !hasTransitionFromTo(currentProgress, progress)) {
            setImageResource(targetResId)
        } else {
            // 否则使用 TransitionDrawable 实现动画切换
            val transitionResId = getTransitionResourceId(currentProgress, progress)
            val transition = resources.getDrawable(transitionResId) as? TransitionDrawable
            transition?.let {
                setImageDrawable(it)
                it.startTransition(300) // 动画时长 300ms
            }
        }

        currentProgress = progress
    }

    /**
     * 判断是否有对应过渡动画资源
     */
    private fun hasTransitionFromTo(fromProgress: Int, toProgress: Int): Boolean {
        return getTransitionResourceId(fromProgress, toProgress) != -1
    }

    /**
     * 获取两个进度之间的过渡动画资源 ID
     */
    private fun getTransitionResourceId(fromProgress: Int, toProgress: Int): Int {
        val fromIndex = getIndexForProgress(fromProgress)
        val toIndex = getIndexForProgress(toProgress)

        if (fromIndex == -1 || toIndex == -1 || fromIndex == toIndex) return -1

        val transitionName = when {
            fromIndex == 0 && toIndex == 1 -> "transition_cup_0_to_1"
            fromIndex == 1 && toIndex == 2 -> "transition_cup_1_to_2"
            fromIndex == 2 && toIndex == 3 -> "transition_cup_2_to_3"
            fromIndex == 3 && toIndex == 4 -> "transition_cup_3_to_4"
            fromIndex == 4 && toIndex == 5 -> "transition_cup_4_to_5"
            fromIndex > toIndex -> getTransitionResourceId(toProgress, fromProgress) // 反向也支持
            else -> return -1
        }

        return resources.getIdentifier(transitionName.toString(), "drawable", context.packageName)
    }

    /**
     * 根据进度获取图标索引
     */
    private fun getIndexForProgress(progress: Int): Int {
        return when (progress) {
            in 0..19 -> 0
            in 20..39 -> 1
            in 40..49 -> 2
            in 50..69 -> 3
            in 70..99 -> 4
            100 -> 5
            else -> -1
        }
    }
}
