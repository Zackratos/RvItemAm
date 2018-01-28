package com.github.zackratos.rvitemam.sample4

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.github.zackratos.rvitemam.R
import com.github.zackratos.rvitemam.SampleAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 同步播放进度
 * author : Zhangwenchao
 * e-mail : zhangwch@yidianling.com
 * time   : 2018/01/27
 */
class Sample4Adapter(context: Context): SampleAdapter<Sample4Adapter.ViewHolder>() {

    private val mContext = context

    // 定时器数组，每个 item 都需要一个定时器
    private val disposables = arrayOfNulls<Disposable>(100)

    // 是否更新 ui 的开关
    private val flags = BooleanArray(100)

    // 用于记录 item 中 progressBar 的进度
    private val progresss = IntArray(100)

    override fun getItemCount(): Int { return 100 }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sample_2, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.textView?.text = position.toString()
        holder?.progressBar?.progress = progresss[position]
        when {
            disposables[position] == null -> holder?.button?.text = "start"
            else -> holder?.button?.text = "stop"
        }
        // 关闭上一次位置的开关
        if (holder?.lastPosition != -1) {
            flags[holder?.lastPosition!!] = false
        }
        // 开启当前位置的开关
        flags[position] = true
        holder.lastPosition = position
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val button = itemView?.findViewById<Button>(R.id.button)
        val progressBar = itemView?.findViewById<ProgressBar>(R.id.progress_bar)
        val textView = itemView?.findViewById<TextView>(R.id.text_view)
        // 上一次存放在 viewholder 中的 item 的位置
        var lastPosition: Int = -1
        init {
            button?.setOnClickListener {
                val position = adapterPosition
                if (disposables[position] == null) {
                    disposables[position] = Observable.interval(0, 1, TimeUnit.SECONDS)
                            .subscribeOn(Schedulers.computation())
                            .filter { it <= 100 && flags[position] }
                            .map { it.toInt() }
                            .doOnNext { progresss[position] = it }
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { progressBar?.progress = it }
                    addDisposable(disposables[position])
                    button.text = "stop"
                } else {
                    disposables[position]?.dispose()
                    removeDisposable(disposables[position])
                    disposables[position] = null
                    progresss[position] = 0
                    progressBar?.progress = 0
                    button.text = "start"
                }
            }
        }
    }
}