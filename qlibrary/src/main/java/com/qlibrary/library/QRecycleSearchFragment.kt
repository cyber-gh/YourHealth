package com.qlibrary.library

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import com.qlibrary.utils.Res
import com.qlibrary.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.recycle_search_fragment.*
import kotlin.reflect.KClass

open class QRecycleSearchFragment<T,V: QRecycleView<T>> : QFragment(R.layout.recycle_search_fragment) {

    lateinit var listFrag : QRecycleListFragment<T,V>
    var search = ""

    companion object {
        fun <T,V: QRecycleView<T>> newInstance(viewClass: KClass<V>,
                                               getMoreData: (page: Int) -> Observable<List<T>>,
                                               onDataSelected: ((data: T) -> Unit)? = null
        ) = QRecycleSearchFragment<T, V>().apply {
            setParameters(viewClass,getMoreData,onDataSelected)
        }
    }

    fun setParameters(viewClass: KClass<V>,
                      getMoreData: (page: Int) -> Observable<List<T>>,
                      onDataSelected: ((data: T) -> Unit)? = null){

        listFrag = QRecycleListFragment.newInstance(viewClass,true, getMoreData).apply {
            if(onDataSelected != null) {
                onItemClickListener = { data, poz, v ->
                    onDataSelected(data)
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        if(!listFrag.isAdded){
            childFragmentManager.beginTransaction()
                    .add(frame.id,listFrag)
                    .commit()
        }

        search_view.setIconifiedByDefault(false)
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                search = query ?: ""
                listFrag.refresh()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()){
                    search = ""
                    listFrag.refresh()
                }
                return false
            }
        })

        search_view.setOnCloseListener {
            search = ""
            listFrag.refresh()
            true
        }
    }

}