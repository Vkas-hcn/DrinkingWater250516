package com.leaning.against.mountains.drinkingwater.ui.record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leaning.against.mountains.drinkingwater.R
import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecordAdapter : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    private var items = listOf<WaterDrinkBean>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView = itemView.findViewById(R.id.tv_zzzcd)
        val tvDate: TextView = itemView.findViewById(R.id.tv_cdcdee)
        val tvHisNum: TextView = itemView.findViewById(R.id.tv_cbbgrdfv)
        val progressBar: ProgressBar = itemView.findViewById(R.id.sp_bgff)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_record, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            tvTime.text = SimpleDateFormat("hh:mm a", Locale.getDefault())
                .format(Date(item.id.toLong()))
            tvDate.text = if (isToday(item.date)) "Today" else "Past"
            tvHisNum.text = "${item.drinkNum}ML"
            progressBar.progress = (item.drinkNum * 100 / item.goalNum).coerceAtMost(100)
        }
    }
    private fun isToday(date: String): Boolean {
        return date == SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }
    override fun getItemCount() = items.size

    fun submitList(newList: List<WaterDrinkBean>) {
        items = newList
        notifyDataSetChanged()
    }
}