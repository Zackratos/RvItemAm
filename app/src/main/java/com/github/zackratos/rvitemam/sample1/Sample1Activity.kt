package com.github.zackratos.rvitemam.sample1

import com.github.zackratos.rvitemam.SampleActivity

class Sample1Activity : SampleActivity<Sample1Adapter>() {

    override fun adapter(): Sample1Adapter {
        return Sample1Adapter(this)
    }

}
