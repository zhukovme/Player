package com.zhukovme.player.ui.base

import android.os.Build
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.WindowManager

/**
 * Created by Michael Zhukov on 05.03.2018.
 * email: zhukovme@gmail.com
 */
abstract class BaseActivity : AppCompatActivity() {

    protected fun setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    protected fun setupToolbar(toolbar: Toolbar, title: Int? = null, subtitle: Int? = null,
                               withBackButton: Boolean = false) {
        setSupportActionBar(toolbar)
        title?.let { supportActionBar?.setTitle(it) }
        subtitle?.let { supportActionBar?.setSubtitle(it) }
        if (withBackButton) {
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}

interface MvpView {
    fun getString(@StringRes resId: Int): String
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String
}
