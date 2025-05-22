package com.leaning.against.mountains.drinkingwater.ui.record

import com.github.mikephil.charting.data.Entry
import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean

interface RecordContract {
    interface View {
        fun showRecords(data: List<WaterDrinkBean>)
        fun updateChart(entriesWithData: List<Pair<Entry, WaterDrinkBean>>)
    }

    interface Presenter {
        fun loadRecordData(date: String?)
        fun prepareChartData(todayList: List<WaterDrinkBean>, selectedDate: String?)
    }
}
