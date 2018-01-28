package com.github.zackratos.rvitemam.sample3

import com.github.zackratos.rvitemam.SampleActivity

/**
 * author : Zhangwenchao
 * e-mail : zhangwch@yidianling.com
 * time   : 2018/01/25
 */
class Sample3Activity : SampleActivity<Sample3Adapter>() {

    override fun adapter(): Sample3Adapter {
        return Sample3Adapter(this)
    }
}