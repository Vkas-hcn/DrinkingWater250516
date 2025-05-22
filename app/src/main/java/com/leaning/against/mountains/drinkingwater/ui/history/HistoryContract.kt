package com.leaning.against.mountains.drinkingwater.ui.history

import com.leaning.against.mountains.drinkingwater.utils.HistoryBean
import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean

interface HistoryContract {
    interface View {
        fun showData(data: List<HistoryBean>)
        fun showAverages(totalWater: Int, totalDays: Int)
    }

    interface Presenter {
        fun loadHistoryData()
        fun calculateAverages(waterList: List<WaterDrinkBean>)
    }
}
