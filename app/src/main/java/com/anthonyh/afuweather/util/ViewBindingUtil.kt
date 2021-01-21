package com.anthonyh.afuweather.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

object ViewBindingUtil {
    /**
     *BaseActivity绑定视图调用
     *
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : ViewBinding> create(cls: Class<*>, inflater: LayoutInflater): T {
        val bindingClass = ClassUtil.findGenericClass(cls, ViewBinding::class.java)
            ?: throw IllegalArgumentException("Not found ViewBinding class")
        val method = bindingClass.getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, inflater) as T
    }

    /**
     * BaseFragment绑定视图调用
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : ViewBinding> create(
        cls: Class<*>,
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): T {
        val bindingClass = ClassUtil.findGenericClass(cls, ViewBinding::class.java)
            ?: throw IllegalArgumentException("Not found ViewBinding class")

        val method =
            bindingClass.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
        return method.invoke(null, inflater, container, attachToParent) as T
    }
}