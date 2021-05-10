package com.anthonyh.afuweather.mvvm.weather.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.liveData
import androidx.navigation.fragment.NavHostFragment
import com.anthonyh.afuweather.R
import com.anthonyh.afuweather.databinding.ActivityWeatherBindingImpl
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.flow.flow


/**
@author Anthony.H
@date: 2021/4/28
@desription:
 */
class WeatherActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "WeatherActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        XXPermissions.with(this)
            .permission(Permission.ACCESS_COARSE_LOCATION)
            .permission(Permission.ACCESS_FINE_LOCATION)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                    if (all) {
                        Toast.makeText(applicationContext, "授权成功", Toast.LENGTH_SHORT).show()
                        init()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "获取部分权限成功，但部分权限未正常授予",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }

                override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                    Toast.makeText(applicationContext, "授权失败", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })

    }

    private fun init() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        val activityWeatherBindingImpl = DataBindingUtil.setContentView<ActivityWeatherBindingImpl>(
            this,
            R.layout.activity_weather
        )
        setSupportActionBar(tool_bar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
            setDisplayShowTitleEnabled(false)
        }
        design_navigation_view.setCheckedItem(R.id.choose_place)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
//        navController.navigate(ChoosePlaceFragmentDirections.actionChoosePlaceFragmentToWeatherDetailFragment2())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}