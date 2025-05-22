package com.leaning.against.mountains.drinkingwater.ui.record

import com.github.mikephil.charting.data.Entry
import com.leaning.against.mountains.drinkingwater.ui.main.MainUtils
import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean

class RecordPresenter(private val view: RecordContract.View) : RecordContract.Presenter {

    override fun loadRecordData(date: String?) {
        val todayList = MainUtils.getTodayWaterList(date ?: "")
        view.showRecords(todayList)
        prepareChartData(todayList, date)
    }

    override fun prepareChartData(todayList: List<WaterDrinkBean>, selectedDate: String?) {
        val entriesWithData = mutableListOf<Pair<Entry, WaterDrinkBean>>()

        val goalNum = todayList.firstOrNull()?.goalNum ?: 1 // 默认目标为1避免除零错误

        var cumulativeDrinkNum = 0f

        for (bean in todayList.sortedBy { it.drinkTime }) {
            val timeParts = bean.drinkTime.split(":")
            val hour = timeParts[0].toFloat()
            val minute = timeParts[1].toFloat()
            val timeInHours = hour + (minute / 60)

            cumulativeDrinkNum += bean.drinkNum

            val progress = (cumulativeDrinkNum / goalNum) * 100
            val entry = if (progress >= 100) Entry(timeInHours, 100f) else Entry(timeInHours, progress)

            entriesWithData.add(Pair(entry, bean))
        }

        view.updateChart(entriesWithData)
    }
}
