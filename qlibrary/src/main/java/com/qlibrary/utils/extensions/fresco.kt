package com.qlibrary.utils.extensions

import android.net.Uri
import com.facebook.drawee.view.SimpleDraweeView
import java.net.URL

fun SimpleDraweeView.load(url: String?){
    if(url.isNullOrBlank()) return
    setImageURI(Uri.parse(url),context)
}