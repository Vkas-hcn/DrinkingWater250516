package com.leaning.against.mountains.drinkingwater.ui.main

import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    override fun loadTodayData() {
        val todayList = MainUtils.getTodayWaterList()
        view.showTodayList(todayList)
        calculateProgress(todayList)
    }

    override fun loadAddNumData() {
        val numList = MainUtils.getAddNumList()
        view.showAddNumList(numList)
    }

    override fun addWaterRecord(amount: Int) {
        val goal = getTodayGoal()
        if (goal <= 0) {
            view.showToast("Please enter the correct value")
            return
        }
        val newRecord = WaterDrinkBean(
            id = System.currentTimeMillis().toString(),
            date = getCurrentDate(),
            goalNum = goal,
            drinkNum = amount,
            drinkTime = getCurrentTime()
        )
        MainUtils.addWaterBean(newRecord)
        loadTodayData()
    }

    override fun updateGoal(goal: Int) {
        if (goal <= 0) {
            view.showToast("Please enter the correct value")
            return
        }
        MainUtils.updateTodayGoal(goal)
        loadTodayData()
        view.updateGoal(goal)
    }

    private fun calculateProgress(todayList: List<WaterDrinkBean>) {
        val totalDrink = todayList.sumOf { it.drinkNum }
        val goal = todayList.lastOrNull()?.goalNum ?: 2500
        val progress = (totalDrink.toFloat() / goal * 100).toInt()

        if (progress >= 100) {
            view.showFinishState()
        } else {
            view.hideFinishState()
            view.updateProgress(progress, totalDrink)
        }
    }

    private fun getTodayGoal(): Int {
        return MainUtils.getTodayWaterList().lastOrNull()?.goalNum ?: 2500
    }

    private fun getCurrentDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }
}
