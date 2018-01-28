package com.github.zackratos.rvitemam.sample2

import com.github.zackratos.rvitemam.SampleActivity
import kotlinx.android.synthetic.main.activity_sample.*
import android.support.v7.widget.SimpleItemAnimator


/**
 * author : Zhangwenchao
 * e-mail : zhangwch@yidianling.com
 * time   : 2018/01/26
 */
class Sample2Activity: SampleActivity<Sample2Adapter>() {

    override fun adapter(): Sample2Adapter {
        return Sample2Adapter(this)
    }

    override fun initView() {
        super.initView()
        // 屏蔽动画，防止更新 item 时闪烁
        (recycler_view.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }


}