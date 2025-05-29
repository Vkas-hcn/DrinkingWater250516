package com.leaning.against.mountains.drinkingwater.ui.record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.leaning.against.mountains.drinkingwater.R
import com.leaning.against.mountains.drinkingwater.databinding.ItemRecordBinding
import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class RecordAdapter : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    // 使用ViewBinding
    class ViewHolder private constructor(
        val binding: ItemRecordBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemRecordBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    // 日期格式化常量
    companion object {
        private val TIME_FORMAT = SimpleDateFormat("hh:mm a", Locale.getDefault())
        private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    // 进度计算扩展函数
    private fun calculateProgress(drainNum: Int, goalNum: Int): Int {
        return if (goalNum == 0) 0 else (drainNum * 100 / goalNum).coerceAtMost(100)
    }

    // 数据集合
    private var items = listOf<WaterDrinkBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            tvZzzcd.text = TIME_FORMAT.format(Date(item.id.toLong()))
            tvCdcdee.text = if (isToday(item.date)) "Today" else "Past"
            tvCbbgrdfv.text = "${item.drinkNum}ML"
            spBgff.progress = calculateProgress(item.drinkNum, item.goalNum)
        }
    }

    private fun isToday(date: String): Boolean {
        return date == DATE_FORMAT.format(Date())
    }

    override fun getItemCount() = items.size

    fun submitList(newList: List<WaterDrinkBean>) {
        items = newList
        notifyDataSetChanged()
    }

}
