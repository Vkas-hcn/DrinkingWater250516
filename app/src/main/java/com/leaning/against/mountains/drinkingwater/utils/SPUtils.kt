package com.leaning.against.mountains.drinkingwater.utils

import android.content.Context
import android.content.SharedPreferences

class SPUtils private constructor(context: Context) {
    companion object {
        private const val DEFAULT_SP_NAME = "app_sp"
        private var instance: SPUtils? = null

        fun init(context: Context) {
            instance = SPUtils(context)
        }

        fun get(): SPUtils {
            return instance ?: throw IllegalStateException("SPUtils 未初始化")
        }
    }

    private val sp: SharedPreferences by lazy {
        context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE)
    }

    // === 存值方法 ===

    fun put(key: String, value: String): SPUtils {
        sp.edit().putString(key, value).apply()
        return this
    }

    fun put(key: String, value: Int): SPUtils {
        sp.edit().putInt(key, value).apply()
        return this
    }

    fun put(key: String, value: Boolean): SPUtils {
        sp.edit().putBoolean(key, value).apply()
        return this
    }

    fun put(key: String, value: Long): SPUtils {
        sp.edit().putLong(key, value).apply()
        return this
    }

    fun put(key: String, value: Float): SPUtils {
        sp.edit().putFloat(key, value).apply()
        return this
    }

    fun put(key: String, value: Set<String>): SPUtils {
        sp.edit().putStringSet(key, value).apply()
        return this
    }

    // === 取值方法 ===

    fun getString(key: String, default: String = ""): String {
        return sp.getString(key, default) ?: default
    }

    fun getInt(key: String, default: Int = 0): Int {
        return sp.getInt(key, default)
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return sp.getBoolean(key, default)
    }

    fun getLong(key: String, default: Long = 0L): Long {
        return sp.getLong(key, default)
    }

    fun getFloat(key: String, default: Float = 0f): Float {
        return sp.getFloat(key, default)
    }

    fun getStringSet(key: String, default: Set<String> = emptySet()): Set<String> {
        return sp.getStringSet(key, default) ?: default
    }

    // 删除某个 key
    fun remove(key: String): SPUtils {
        sp.edit().remove(key).apply()
        return this
    }

    // 清空所有数据
    fun clear(): SPUtils {
        sp.edit().clear().apply()
        return this
    }
}
