package com.github.zackratos.rvitemam.sample2

import android.content.Context
import android.support.annotation.DrawableRes
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
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 刷新 item，简单粗暴，但是性能影响较大
 * author : Zhangwenchao
 * e-mail : zhangwch@yidianling.com
 * time   : 2018/01/26
 */
class Sample2Adapter(context: Context): SampleAdapter<Sample2Adapter.ViewHolder>() {

    private val mContext = context

    // 用于显示对应 item 位置的图片
    @DrawableRes
    private val drawables = IntArray(100)

    // 定时器数组，每个 item 都需要一个定时器
    private val disposables = arrayOfNulls<Disposable>(100)

    init {
        for (i in drawables.indices) {
            drawables[i] = R.drawable.ic_battery_charging_0
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.textView?.text = position.toString()
        holder?.imageView?.setImageResource(drawables[position])
        if (disposables[position] == null) holder?.button?.text = "start"
        else holder?.button?.text = "stop"
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sample_1, parent, false))
    }

    override fun getItemCount(): Int {
        return 100
    }

    inner class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView) {
        val button = itemView?.findViewById<Button>(R.id.button)
        val imageView = itemView?.findViewById<ImageView>(R.id.image_view)
        val textView = itemView?.findViewById<TextView>(R.id.text_view)

        init {
            button?.setOnClickListener {
                val position = adapterPosition
                if (disposables[position] == null) {
                    // 定时器用于改变对应 item 位置的图片，然后刷新该位置的 item
                    disposables[position] = Observable.interval(0, 1, TimeUnit.SECONDS)
                            .subscribeOn(Schedulers.computation())
                            .map { when((it % 5).toInt()) {
                                1 -> R.drawable.ic_battery_charging_1
                                2 -> R.drawable.ic_battery_charging_2
                                3 -> R.drawable.ic_battery_charging_3
                                4 -> R.drawable.ic_battery_charging_4
                                else -> {
                                    R.drawable.ic_battery_charging_0
                                }
                            } }
                            .doOnNext { drawables[position] = it }
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { notifyItemChanged(position) }
                    addDisposable(disposables[position])
                } else {
                    removeDisposable(disposables[position])
                    disposables[position]?.dispose()
                    disposables[position] = null
                    drawables[position] = R.drawable.ic_battery_charging_0
                    notifyItemChanged(position)
                }
            }
        }
    }
}