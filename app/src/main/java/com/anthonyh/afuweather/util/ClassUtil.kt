package com.anthonyh.afuweather.util

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object ClassUtil {
    /**
     * 从指定类的父类的泛型参数中找到目标类或其子类
     */
    fun findGenericClass(cls: Class<*>, targetCLass: Class<*>): Class<*>? {
        var tCls: Class<*>? = null

        // 获取父类（父类如果有泛型参数，则会带上泛型信息）
        var genericSuperclass = cls.genericSuperclass
        while (genericSuperclass != null) {
            // 判断该类是否是一个参数化类型（是否有泛型）
            if (genericSuperclass !is ParameterizedType) {
                genericSuperclass = (genericSuperclass as? Class<*>)?.genericSuperclass
                continue
            }

            // 获取实际的泛型类型
            val ars: Array<Type> = genericSuperclass.actualTypeArguments
            if (ars.isEmpty()) {
                genericSuperclass = (genericSuperclass as? Class<*>)?.genericSuperclass
                continue
            }

            for (ar in ars) {
                // 判断每个泛型的类型是否为目标类型或其子类
                if (ar is Class<*> && targetCLass.isAssignableFrom(ar)) {
                    tCls = ar
                    break
                }
            }

            if (tCls != null) {
                break
            }

            genericSuperclass = (genericSuperclass as? Class<*>)?.genericSuperclass
        }

        return tCls
    }
}