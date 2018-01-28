package com.github.zackratos.rvitemam

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.zackratos.rvitemam.sample1.Sample1Activity
import com.github.zackratos.rvitemam.sample2.Sample2Activity
import com.github.zackratos.rvitemam.sample3.Sample3Activity
import com.github.zackratos.rvitemam.sample4.Sample4Activity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        sample1.setOnClickListener {
            startActivity(Intent(this, Sample1Activity::class.java))
        }

        sample2.setOnClickListener {
            startActivity(Intent(this, Sample2Activity::class.java))
        }

        sample3.setOnClickListener {
            startActivity(Intent(this, Sample3Activity::class.java))
        }

        sample4.setOnClickListener {
            startActivity(Intent(this, Sample4Activity::class.java))
        }
    }
}
