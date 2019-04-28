package com.zhukovme.player.ui.base

import android.os.Build
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.zhukovme.player.ui.screens.playback.MvpView
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

/**
 * Created by Michael Zhukov on 05.03.2018.
 * email: zhukovme@gmail.com
 */
abstract class BaseActivity : AppCompatActivity(), KodeinAware, MvpView {

    //region Kodein

    private val parentKodein: Kodein by lazy { (applicationContext as KodeinAware).kodein }
    override val kodein: Kodein by lazy {
        Kodein {
            extend(parentKodein)
            import(depsModule())
        }
    }

    abstract fun depsModule(): Kodein.Module

    //endregion

    lateinit var mainLayout: ViewGroup

    override fun showSnackbar(@StringRes message: Int) {
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_LONG)
                .show()
    }

    override fun showSnackbar(message: CharSequence) {
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_LONG)
                .show()
    }

    override fun showToast(@StringRes message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
                .show()
    }

    override fun showToast(message: CharSequence) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
                .show()
    }

    protected fun setupToolbar(toolbar: Toolbar, title: String? = null, withBackBtn: Boolean = false) {
        title?.let { toolbar.title = it }
        setSupportActionBar(toolbar)
        if (withBackBtn) {
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    protected fun setupToolbar(toolbar: Toolbar, @StringRes title: Int, withBackBtn: Boolean = false) {
        setupToolbar(toolbar, getString(title), withBackBtn)
    }

    protected fun setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }
}
