package com.qlibrary.utils.extensions

import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.qlibrary.library.QRouter

fun toast(str: String?) = Toast.makeText(QRouter.activity,str,Toast.LENGTH_SHORT).show()
fun toast(strRes: Int) = Toast.makeText(QRouter.activity,strRes.resStr(),Toast.LENGTH_SHORT).show()
fun toastl(str: String?) = Toast.makeText(QRouter.activity,str,Toast.LENGTH_LONG).show()

fun loge(str: String) = Log.e("qwe",str)
fun log(str: String) = Log.d("qwe",str)


fun Int?.resStr() = if(this == null) "" else QRouter.activity.resources?.getString(this) ?: "null"
fun Int.resColor() =  QRouter.activity.resources?.getColor(this)!!

fun View.onClick(call: (View) -> Unit) = this.setOnClickListener {call(it)}
fun TextView.str() = this.text.toString()

fun EditText.onEdit(listener : (String)->Unit){
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {listener(p0.toString())}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    })
}

fun View.setVisible(visible: Boolean) {
    this.visibility = if(visible) View.VISIBLE else View.GONE
}

fun View.setEditable(x: Boolean) {
    this.setOnTouchListener{ v, event ->  !x}
}

fun EditText.onNumEdit(listener: (String) -> Unit) =
    this.onEdit{if(it.isNotEmpty()) listener(it.replace(",",".")) else listener("0")}

fun EditText.chainEdit(init: ()->String? = {null}, listener: (String) -> Unit){
    this.setText(init())
    this.onEdit(listener)
}

fun TextView.txt() = this.text.toString()

fun AutoCompleteTextView.setData(map: Map<String,Int>, callback: (Int)->Unit){
    this.setAdapter(ArrayAdapter<String>(this.context, android.R.layout.simple_dropdown_item_1line, map.keys.toList()))
   // this.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
    this.onEdit {
        if(map.containsKey(it)) callback(map[it]!!)
    }
}

fun EditText.clearOnZero(){
    this.setOnFocusChangeListener { _,hasFocus ->
        if(hasFocus && this.str().toCDouble() == 0.0) this.setText("")
    }
}

fun EditText.chainNumEdit(init: ()->String?, listener: (String) -> Unit){
    this.clearOnZero()
    this.setText(init())
    this.onNumEdit(listener)
}

fun CompoundButton.chainChange(listener: (Boolean) -> Unit){
    this.setOnCheckedChangeListener{
        _,state ->
        listener(state)
    }
}

fun CompoundButton.chainChange(init: ()->Boolean? = {false}, listener: (Boolean) -> Unit){
    this.isChecked = init() ?: false
    this.chainChange(listener)
}

fun followLink(url: String){
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    QRouter.activity.startActivity(intent)
}

interface BackPressListener{
    fun onBackPressed() : Boolean
}


