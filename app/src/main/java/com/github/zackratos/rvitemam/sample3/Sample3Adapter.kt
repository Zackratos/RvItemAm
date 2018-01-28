package com.github.zackratos.rvitemam.sample3


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.github.zackratos.rvitemam.R
import com.github.zackratos.rvitemam.SampleAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 同步播放状态，效率高
 * author : Zhangwenchao
 * e-mail : zhangwch@yidianling.com
 * time   : 2018/01/25
 */
class Sample3Adapter(context: Context): SampleAdapter<Sample3Adapter.ViewHolder>() {

    private val mContext = context

    // 布尔类型数组，用于记录每个 item 的播放状态
    private val flags = BooleanArray(100)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sample_1, parent, false))
    }

    override fun getItemCount(): Int { return 100 }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.textView?.text = position.toString()
        // 把当前位置 item 的播放状态同步给 viewholder
        holder?.playing = flags[position]
        holder?.setStatus()
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val button = itemView?.findViewById<Button>(R.id.button)
        val imageView = itemView?.findViewById<ImageView>(R.id.image_view)
        val textView = itemView?.findViewById<TextView>(R.id.text_view)
        // 是否播放动画的开关
        var playing: Boolean = false
        init {
            // 创建 viewholder 的时候立即开启定时器
            val d = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.computation())
                    .filter { playing } // 根据开关状态确定是否播放动画
                    .map {
                        when ((it % 5).toInt()) {
                            1 -> R.drawable.ic_battery_charging_1
                            2 -> R.drawable.ic_battery_charging_2
                            3 -> R.drawable.ic_battery_charging_3
                            4 -> R.drawable.ic_battery_charging_4
                            else -> R.drawable.ic_battery_charging_0
                        }
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { imageView?.setImageResource(it) }
            addDisposable(d)

            button?.setOnClickListener {
                val position = adapterPosition
                playing = !playing
                flags[position] = playing
                setStatus()
            }
        }

        fun setStatus() {
            when {
                playing -> button?.text = "stop"
                else -> {
                    button?.text = "start"
                    imageView?.setImageResource(R.drawable.ic_battery_charging_0)
                }
            }
        }
    }
}