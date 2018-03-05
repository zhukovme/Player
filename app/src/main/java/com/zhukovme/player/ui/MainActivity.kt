package com.zhukovme.player.ui

import android.os.Bundle
import com.zhukovme.player.R
import com.zhukovme.player.ui.base.BaseActivity

/**
 * Created by Michael Zhukov on 05.03.2018.
 * email: zhukovme@gmail.com
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
