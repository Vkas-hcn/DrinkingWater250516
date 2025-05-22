package com.leaning.against.mountains.drinkingwater.ui.history

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leaning.against.mountains.drinkingwater.ui.record.RecordActivity
import com.leaning.against.mountains.drinkingwater.R
import com.leaning.against.mountains.drinkingwater.utils.HistoryBean
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryAdapter(private val  context: Context, private val data: MutableList<HistoryBean>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView = itemView.findViewById(R.id.tv_cejghk)
        val tvDate: TextView = itemView.findViewById(R.id.tv_vvngjt)
        val tvDrinkNum: TextView = itemView.findViewById(R.id.tv_his_cfds)
        val tvCupNumHis : TextView = itemView.findViewById(R.id.tv_ccccswe)
        val progressBar: ProgressBar = itemView.findViewById(R.id.sp_grjgr)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.apply {
            tvTime.text = item.date
            tvDate.text = if (isToday(item.date)) "Today" else "Past"
            tvDrinkNum.text = "${item.totalDrink}ml"
            tvCupNumHis.text = "${item.cupCount}"
            progressBar.progress = item.progress
            itemView.setOnClickListener {
                val intent = Intent(context, RecordActivity::class.java)
                intent.putExtra("SELECTED_DATE", item.date)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = data.size


    fun updateData(newData: List<HistoryBean>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }


    private fun isToday(date: String): Boolean {
        return date == SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }
}

