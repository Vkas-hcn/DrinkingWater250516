package com.leaning.against.mountains.drinkingwater.ui.main

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leaning.against.mountains.drinkingwater.base.BaseActivity
import com.leaning.against.mountains.drinkingwater.utils.HistoryBean
import com.leaning.against.mountains.drinkingwater.utils.SPUtils
import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object MainUtils2 {
    fun getAddNumList(): MutableList<Int> {
        val defaultJson = "[200,400,600]".trimIndent()
        val showNumJson = SPUtils.get().getString("shownumjson", defaultJson)
        return try {
            Gson().fromJson(showNumJson, object : TypeToken<MutableList<Int>>() {}.type)
                ?: mutableListOf()
        } catch (e: Exception) {
            // 解析失败时返回默认值
            Gson().fromJson(defaultJson, object : TypeToken<MutableList<Int>>() {}.type)
        }
    }


    // 保存添加数量列表
    fun saveAddNumList(num: Int) {
        val list = getAddNumList().toMutableList()
        list.add(num)
        SPUtils.get().put("shownumjson", Gson().toJson(list))
    }

    // 获取当前喝水列表
    fun getWaterList(): MutableList<WaterDrinkBean> {
        val json = SPUtils.get().getString("waterJson", "")
        return try {
            Gson().fromJson(json, object : TypeToken<MutableList<WaterDrinkBean>>() {}.type)
                ?: mutableListOf()
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    // 保存喝水列表
    private fun saveWaterList(list: List<WaterDrinkBean>) {
        SPUtils.get().put("waterJson", Gson().toJson(list))
    }

    // 新增喝水记录
    fun addWaterBean(bean: WaterDrinkBean) {
        val list = getWaterList().toMutableList()
        list.add(bean)
        saveWaterList(list)
    }

    // 删除喝水记录
    fun deleteWaterBean(id: String) {
        val list = getWaterList().toMutableList()
        list.removeIf { it.id == id }
        saveWaterList(list)
    }

    // 更新喝水记录
    fun updateWaterBean(bean: WaterDrinkBean) {
        val list = getWaterList().toMutableList()
        val index = list.indexOfFirst { it.id == bean.id }
        if (index != -1) {
            list[index] = bean
            saveWaterList(list)
        }
    }

    // 获取今日喝水列表（按日期过滤）
    fun getTodayWaterList(
        date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            Date()
        )
    ):
            List<WaterDrinkBean> {
        return getWaterList().filter { it.date == date }
    }

    // WaterUtils.kt 新增方法
    fun updateTodayGoal(newGoal: Int) {
        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val list = getWaterList().toMutableList()

        list.replaceAll { bean ->
            if (bean.date == todayDate) {
                bean.copy(goalNum = newGoal)
            } else {
                bean
            }
        }

        saveWaterList(list)
    }

    fun getTodayTotalDrink(): Int {
        return getTodayWaterList().sumOf { it.drinkNum }
    }

    fun getTodayProgress(): Int {
        val todayList = getTodayWaterList()
        if (todayList.isEmpty()) return 0
        val total = todayList.sumOf { it.drinkNum }
        val goal =
            todayList.first().goalNum
        if (goal == 0)
            return 0
        return (total.toFloat() / goal * 100).toInt()
    }

    /**
     * 获取历史记录列表，包含每天的总喝水量、杯数和进度值。
     *
     * @return 返回一个 MutableList<HistoryBean>，表示历史记录列表。
     */
    fun getHistoryList(): MutableList<HistoryBean> {
        val waterList = getWaterList()
        val historyList = mutableListOf<HistoryBean>()

        // 按日期分组
        val groupedByDate = waterList.groupBy { it.date }

        for ((date, beans) in groupedByDate) {
            // 计算当天总喝水量
            val totalDrink = beans.sumOf { it.drinkNum }

            // 计算当天杯数（数据个数）
            val cupCount = beans.size

            // 获取当天的目标喝水量（取第一个数据的目标值）
            val goalNum = beans.firstOrNull()?.goalNum ?: 0

            // 计算喝水目标进度值
            val progress = if (goalNum == 0) 0 else (totalDrink.toFloat() / goalNum * 100).toInt()

            // 创建 HistoryBean 并添加到列表
            historyList.add(HistoryBean(date, totalDrink, cupCount, progress))
        }

        // 按日期倒序排序（最近日期在前）
        historyList.sortByDescending { it.date }

        return historyList
    }

    fun cloneKey(activity: AppCompatActivity, view: View) {
        val inputMethodManager =
            activity.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}