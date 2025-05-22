package com.leaning.against.mountains.drinkingwater.ui.main

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

class TodayWaterAdapter : RecyclerView.Adapter<TodayWaterAdapter.ViewHolder>() {
    private var items: List<WaterDrinkBean> = emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView = itemView.findViewById(R.id.tv_date_h)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date_d)
        val tvDrinkNum: TextView = itemView.findViewById(R.id.tvDrinkNum)
        val progressBar: ProgressBar = itemView.findViewById(R.id.pro_drink)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            tvTime.text = item.drinkTime
            tvDate.text = "Today"
            tvDrinkNum.text = "${item.drinkNum}ml"
            progressBar.progress = (item.drinkNum.toFloat() / item.goalNum * 100).toInt()
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newList: List<WaterDrinkBean>) {
        items = newList
        notifyDataSetChanged()
    }
}
