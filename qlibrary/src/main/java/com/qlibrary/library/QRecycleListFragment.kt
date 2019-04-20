package com.qlibrary.library

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qlibrary.R
import com.qlibrary.utils.delegates.viewmodel
import com.qlibrary.utils.extensions.*
import io.reactivex.Observable
import kotlinx.android.synthetic.main.reycle_list_fragment.*
import kotlin.reflect.KClass

open class QRecycleListFragment<T,V: QRecycleView<T>> : QFragment(R.layout.reycle_list_fragment) {

    companion object {
        fun <T, V : QRecycleView<T>> newInstance(viewClass: KClass<V>, isVertical: Boolean = true, getMoreData: (page: Int) -> Observable<List<T>>) = QRecycleListFragment<T, V>().apply {
            this.viewClass = viewClass
            this.getMoreData = getMoreData
            this.isVertical = isVertical
        }
    }

    fun setParameters(viewClass: KClass<V>, isVertical: Boolean = true, getMoreData: (page: Int) -> Observable<List<T>>){
        this.viewClass = viewClass
        this.getMoreData = getMoreData
        this.isVertical = isVertical
    }

    var onItemClickListener: ((data: T, poz: Int, v: View) -> Unit)? = null
    var isVertical = true
    lateinit var getMoreData: (page: Int) -> Observable<List<T>>
    lateinit var listView: RecyclerView

    protected lateinit var viewClass: KClass<V>
    private val dataModel by viewmodel(this, EViewModel<T>()::class.java)
    private var eAdapter: EAdapter? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eAdapter = EAdapter(dataModel.data, viewClass)
        val layoutManager = LinearLayoutManager(context!!, if(isVertical) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL, false)

        dataModel.dataLoadedObs.dispOnDestroy { onDataLoaded() }
        dataModel.setDataLoader(getMoreData)

        listView = recycleView

        recycleView.apply {
            this.setAdapter(eAdapter)
            this.setLayoutManager(layoutManager)
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    bottomProgress.isVisible = dataModel.tryToLoad(layoutManager.findLastVisibleItemPosition())
                }
            })
        }

        if (dataModel.data.size == 0) {
            bottomProgress.isVisible = true
            dataModel.refresh()
            eAdapter?.notifyDataSetChanged()
        }

        swipeRefresh.setOnRefreshListener { refresh() }
    }

    private fun onDataLoaded() {
        eAdapter?.notifyDataSetChanged()
        noItems?.isVisible = dataModel.data.isEmpty()
        bottomProgress?.isVisible = false
        swipeRefresh?.isRefreshing = false
    }

    fun refresh() {
        dataModel.refresh()
        eAdapter?.notifyDataSetChanged()
    }


    class EViewModel<T> : QViewModel() {
        var page = 0
        var lastPageSize = 0
        val data: MutableList<T> = mutableListOf()
        val dataLoadedObs = PS.create<Unit>()
        var isLoading = false
        var finished = false

        lateinit var getMoreData: (page: Int) -> Observable<List<T>>
        fun setDataLoader(getMoreData: (page: Int) -> Observable<List<T>>) {
            this.getMoreData = getMoreData
        }

        fun tryToLoad(lastItem: Int): Boolean {
            if (lastItem + 5 > data.size && !isLoading && !finished) {
                load()
                return true
            }
            return isLoading
        }

        private fun load() {
            isLoading = true
            getMoreData(page).ioConsume({
                data.clear()
                data.addAll(it)
                dataLoadedObs.emit()
                if (lastPageSize > it.size) finished = true
                else lastPageSize = it.size
                page++
            }, {}, {
                isLoading = false
            })
        }

        fun refresh() {
            if(isLoading) return
            data.clear()
            finished = false
            isLoading = false
            page = 0
            lastPageSize = 0
            tryToLoad(0)
        }
    }

    inner class EAdapter(val list: MutableList<T>, val viewClass: KClass<V>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = object : RecyclerView.ViewHolder(
                (viewClass.constructors.first().call(parent.context) as QSimpleView<T>).apply {
                    layoutParams = if(isVertical)
                        RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    else
                        RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
                }
        ) {}

        override fun getItemCount() = list.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val view = holder.itemView as QRecycleView<T>
            view.setContent(list[position])
            if (onItemClickListener != null) {
                view.onClick {
                    onItemClickListener!!(list[position], position, it)
                }
            }
        }
    }
}
