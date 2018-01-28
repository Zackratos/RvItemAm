package com.github.zackratos.rvitemam

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_sample.*

/**
 * author : Zhangwenchao
 * e-mail : zhangwch@yidianling.com
 * time   : 2018/01/25
 */
@SuppressLint("Registered")
abstract class SampleActivity<out T: SampleAdapter<*>> : AppCompatActivity() {

    private var adapter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter?.cancelTimer()
    }

    protected open fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = adapter()
        recycler_view.adapter = adapter
    }

    abstract fun adapter(): T

}