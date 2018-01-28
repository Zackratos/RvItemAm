package com.github.zackratos.rvitemam.sample4

import com.github.zackratos.rvitemam.SampleActivity

/**
 * author : Zhangwenchao
 * e-mail : zhangwch@yidianling.com
 * time   : 2018/01/27
 */
class Sample4Activity : SampleActivity<Sample4Adapter>() {

    override fun adapter(): Sample4Adapter {
        return Sample4Adapter(this)
    }


}