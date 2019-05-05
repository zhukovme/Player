package com.zhukovme.player.ui.base

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.zhukovme.rxelm.program.State
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.retainedKodein

/**
 * Created by Michael Zhukov on 05.03.2018.
 * email: zhukovme@gmail.com
 */
abstract class BaseActivity<T> : AppCompatActivity(), MvpView<T>, KodeinAware
        where T : State, T : Parcelable {

    //region Kodein

    private val parentKodein by kodein()
    override val kodein: Kodein by retainedKodein {
        extend(parentKodein)
        import(depsModule())
    }

    abstract fun depsModule(): Kodein.Module

    //endregion

    abstract val presenter: BasePresenter<T>
    lateinit var mainView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        presenter.onAttachView(this)
        presenter.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        presenter.onAttachView(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        presenter.onSaveState(outState)
    }

    override fun onStop() {
        super.onStop()
        presenter.onDetachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy(isFinishing)
        presenter.onDetachView()
    }

    override fun showSnackbar(@StringRes message: Int) {
        Snackbar.make(mainView, message, Snackbar.LENGTH_LONG)
                .show()
    }

    override fun showSnackbar(message: CharSequence) {
        Snackbar.make(mainView, message, Snackbar.LENGTH_LONG)
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

    abstract fun setupViews()

    protected fun setupToolbar(toolbar: Toolbar,
                               title: String? = null,
                               withBackBtn: Boolean = false) {
        title?.let { toolbar.title = it }
        setSupportActionBar(toolbar)
        if (withBackBtn) {
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    protected fun setupToolbar(toolbar: Toolbar,
                               @StringRes title: Int,
                               withBackBtn: Boolean = false) {
        setupToolbar(toolbar, getString(title), withBackBtn)
    }

    protected fun setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }
}
