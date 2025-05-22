package com.leaning.against.mountains.drinkingwater.ui.main

import android.content.SharedPreferences
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
// 通用扩展函数：安全读取 JSON 数据
inline fun <reified T> SharedPreferences.getJson(key: String, default: T): T {
    return try {
        val json = getString(key, null) ?: return default
        Gson().fromJson(json, object : TypeToken<T>() {}.type) ?: default
    } catch (e: Exception) {
        default
    }
}

// 通用扩展函数：安全写入 JSON 数据
fun SharedPreferences.putJson(key: String, value: Any?) {
    edit().putString(key, Gson().toJson(value)).apply()
}
object MainUtils {

    // 获取/保存添加数量列表（带默认值保护）
    fun getAddNumList(): List<Int> {
        val defaultList = listOf(200, 400, 600)
        return SPUtils.get().sp.getJson("shownumjson", defaultList)
    }

    fun saveAddNumList(num: Int) {
        val newList = getAddNumList().toMutableList().apply { add(num) }
        SPUtils.get().sp.putJson("shownumjson", newList)
    }

    // 获取/保存喝水记录（空安全处理）
     fun getWaterList(): List<WaterDrinkBean> {
        return SPUtils.get().sp.getJson("waterJson", emptyList<WaterDrinkBean>())
    }

    private fun saveWaterList(list: List<WaterDrinkBean>) {
        SPUtils.get().sp.putJson("waterJson", list)
    }

    // 增删改喝水记录（函数式操作）
    fun addWaterBean(bean: WaterDrinkBean) {
        saveWaterList(getWaterList() + bean)
    }

    fun deleteWaterBean(id: String) {
        saveWaterList(getWaterList().filterNot { it.id == id })
    }

    fun updateWaterBean(bean: WaterDrinkBean) {
        saveWaterList(getWaterList().map { if (it.id == bean.id) bean else it })
    }

    // 获取今日数据（带默认值保护）
    private val todayDate by lazy {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    fun getTodayWaterList(date: String = todayDate): List<WaterDrinkBean> {
        return getWaterList().filter { it.date == date }
    }

    // 更新目标水量（函数式替换）
    fun updateTodayGoal(newGoal: Int) {
        val todayDate = todayDate
        saveWaterList(getWaterList().map {
            if (it.date == todayDate) it.copy(goalNum = newGoal) else it
        })
    }

    // 获取统计信息（带空安全保护）
    fun getTodayTotalDrink(date: String = todayDate): Int {
        return getTodayWaterList(date).sumOf(WaterDrinkBean::drinkNum)
    }

    fun getTodayProgress(date: String = todayDate): Int {
        val todayList = getTodayWaterList(date)
        return if (todayList.isEmpty()) 0 else {
            val total = todayList.sumOf(WaterDrinkBean::drinkNum)
            val goal = todayList.first().goalNum.takeIf { it > 0 } ?: return 0
            (total.toFloat() / goal * 100).toInt().coerceAtMost(100)
        }
    }

    // 获取历史记录（使用集合操作简化）
    fun getHistoryList(): List<HistoryBean> {
        return getWaterList()
            .groupBy(WaterDrinkBean::date)
            .map { (date, beans) ->
                val total = beans.sumOf(WaterDrinkBean::drinkNum)
                val goal = beans.firstOrNull()?.goalNum.takeIf { it!! > 0 } ?: 0
                HistoryBean(
                    date = date,
                    totalDrink = total,
                    cupCount = beans.size,
                    progress = if (goal == 0) 0 else (total.toFloat() / goal * 100).toInt()
                        .coerceAtMost(100)
                )
            }
            .sortedByDescending(HistoryBean::date)
    }

    // 输入法隐藏扩展（提升可读性）
    fun AppCompatActivity.hideKeyboard(view: View) {
        (getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as?
                android.view.inputmethod.InputMethodManager)
            ?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
