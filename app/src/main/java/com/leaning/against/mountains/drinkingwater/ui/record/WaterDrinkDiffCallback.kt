package com.leaning.against.mountains.drinkingwater.ui.record

import androidx.recyclerview.widget.DiffUtil
import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean

// 新建WaterDrinkDiffCallback.kt文件
class WaterDrinkDiffCallback : DiffUtil.ItemCallback<WaterDrinkBean>() {
    override fun areItemsTheSame(oldItem: WaterDrinkBean, newItem: WaterDrinkBean): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WaterDrinkBean, newItem: WaterDrinkBean): Boolean {
        return oldItem == newItem
    }
}


