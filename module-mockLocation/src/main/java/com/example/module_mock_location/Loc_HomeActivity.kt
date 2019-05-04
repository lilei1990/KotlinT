package com.example.module_mock_location

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.config.ARouterConfig

import kotlinx.android.synthetic.main.activity_loc__home.*
@Route(path = ARouterConfig.LOC_HOME)
class Loc_HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loc__home)


    }

}
