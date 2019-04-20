package com.qlibrary.library

import android.content.Context
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.qlibrary.R
import com.qlibrary.utils.extensions.onClick
import com.qlibrary.utils.extensions.resStr

fun singleChoiceAlert(context: Context, map: Map<String,Int>,callback: (MaterialDialog, View, Int, CharSequence) -> Boolean, selectedId: Int? = null){
    val entries = map.entries.toList()
    val id = entries.indexOfFirst{it.value == selectedId} ?: -1
    MaterialDialog.Builder(context)
            .title(R.string.choose_one)
            .items(entries.map { it.key })
            .itemsIds(entries.map { it.value }.toIntArray())
            .itemsCallbackSingleChoice(id,callback)
            .show()
}


fun confirmAlert(context: Context, resStr: Int, onPositive: ()->Unit, onNegative: ()->Unit = {}) =
    confirmAlert(context,resStr.resStr(),onPositive,onNegative)


fun confirmAlert(context: Context, resStr: String?, onPositive: ()->Unit, onNegative: ()->Unit = {}){
    MaterialDialog.Builder(context)
            .title(R.string.confirm_title)
            .content(resStr.orEmpty())
            .positiveText(R.string.ok)
            .negativeText(R.string.cancel)
            .onPositive{_,_ -> onPositive()}
            .onNegative{_,_ -> onNegative()}
            .show()
}

fun infoAlert(resStr: String){
    MaterialDialog.Builder(QRouter.activity)
            .title(R.string.information)
            .content(resStr)
            .show()
}

fun infoAlert(res: Int){
    MaterialDialog.Builder(QRouter.activity)
            .title(R.string.information)
            .content(res)
            .show()
}
fun errAlert(resStr: String){
    MaterialDialog.Builder(QRouter.activity)
            .title(R.string.error)
            .content(resStr)
            .show()
}

fun errAlert(res: Int,onPositive: () -> Unit = {}){
    MaterialDialog.Builder(QRouter.activity)
            .title(R.string.error)
            .content(res)
            .positiveText(R.string.ok)
            .onPositive { _, _ -> onPositive()}
            .show()
}

fun singleChoiceAlertById(context: Context, map: Map<String,Int>, callback: (Int) -> Unit, selectedId: Int? = null) {
    singleChoiceAlert(context, map, { dialog, view, i, charSequence ->
        callback(view.id)
        true
    }, selectedId)
}

fun singleChoiceAlertById(textView: TextView, map: Map<String,Int>, callback: (Int) -> Unit, selectedId: Int? = null) {
    if(selectedId != null) textView.tag = selectedId
    singleChoiceAlert(textView.context, map, { dialog, view, i, charSequence ->
        textView.text = charSequence
        textView.tag = view.id
        callback(view.id)
        true
    }, textView.tag as Int?)
}

fun singleChoiceAlertByIdOnClick(textView: TextView, map: Map<String,Int>, callback: (Int) -> Unit, selectedId: Int? = null) {
    if(selectedId != null && selectedId != -1){
        textView.text = map.entries.find { it.value == selectedId}?.key
        textView.tag = selectedId
        textView.invalidate()
        textView.requestLayout()
    }
    textView.onClick { singleChoiceAlertById(textView, map, callback) }
}
