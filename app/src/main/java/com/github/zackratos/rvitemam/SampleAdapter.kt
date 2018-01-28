package com.github.zackratos.rvitemam

import android.support.v7.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * author : Zhangwenchao
 * e-mail : zhangwch@yidianling.com
 * time   : 2018/01/27
 */
abstract class SampleAdapter<T: RecyclerView.ViewHolder>: RecyclerView.Adapter<T>() {

    private val compositeDisposable = CompositeDisposable()

    fun cancelTimer() {
        compositeDisposable.clear()
    }

    fun addDisposable(disposable: Disposable?) {
        compositeDisposable.add(disposable)
    }

    fun removeDisposable(disposable: Disposable?) {
        compositeDisposable.remove(disposable)
    }
}