package com.github.zackratos.rvitemam.sample1

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
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 正常的实现思路，有错乱的问题
 * author : Zhangwenchao
 * e-mail : zhangwch@yidianling.com
 * time   : 2018/01/25
 */
class Sample1Adapter(context: Context): SampleAdapter<Sample1Adapter.ViewHolder>() {

    private val mContext = context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sample_1, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.textView?.text = position.toString()
    }

    override fun getItemCount(): Int { return 100 }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val button = itemView?.findViewById<Button>(R.id.button)
        val imageView = itemView?.findViewById<ImageView>(R.id.image_view)
        val textView = itemView?.findViewById<TextView>(R.id.text_view)
        private var disposable: Disposable? = null
        init {
            button?.setOnClickListener {
                if (disposable == null) {
                    disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
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
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe{imageView?.setImageResource(it)}
                    button.text = "stop"
                    addDisposable(disposable)
                } else {
                    removeDisposable(disposable)
                    disposable?.dispose()
                    disposable = null
                    imageView?.setImageResource(R.drawable.ic_battery_charging_0)
                    button.text = "start"
                }
            }
        }
    }
}