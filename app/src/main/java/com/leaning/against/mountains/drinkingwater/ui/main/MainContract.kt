package com.leaning.against.mountains.drinkingwater.ui.main

import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean

interface MainContract {
    interface View {
        fun showTodayList(data: List<WaterDrinkBean>)
        fun showAddNumList(data: List<Int>)
        fun updateGoal(goal: Int)
        fun updateProgress(progress: Int, totalDrink: Int)
        fun showToast(message: String)
        fun showFinishState()
        fun hideFinishState()
    }

    interface Presenter {
        fun loadTodayData()
        fun loadAddNumData()
        fun addWaterRecord(amount: Int)
        fun updateGoal(goal: Int)
    }
}
