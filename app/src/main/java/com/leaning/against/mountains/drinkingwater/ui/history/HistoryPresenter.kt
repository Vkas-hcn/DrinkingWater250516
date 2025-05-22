package com.leaning.against.mountains.drinkingwater.ui.history

import com.leaning.against.mountains.drinkingwater.ui.main.MainUtils
import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean

class HistoryPresenter(private val view: HistoryContract.View) : HistoryContract.Presenter {

    override fun loadHistoryData() {
        val hisList = MainUtils.getHistoryList()
        view.showData(hisList)
    }

    override fun calculateAverages(waterList: List<WaterDrinkBean>) {
        if (waterList.isEmpty()) {
            view.showAverages(0, 0)
            return
        }

        val groupedByDate = waterList.groupBy { it.date }
        val totalWater = waterList.sumOf { it.drinkNum }
        val totalCups = waterList.size
        val totalDays = groupedByDate.size

        view.showAverages(totalWater, totalDays)
    }
}
