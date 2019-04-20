package com.qlibrary.utils

/*
fun setComboImages(colorRes: Int, listener : (ImageView, Int) -> Unit, vararg imgs: ImageView){
    fun enableImage(i : Int){
        listener(imgs[i],i)

        ImageViewCompat.setImageTintList(imgs[i], ColorStateList.valueOf(colorRes.resColor()))

        for(j in (0 until imgs.size))
            if(i != j) ImageViewCompat.setImageTintList(imgs[j], ColorStateList.valueOf(R.color.black.resColor()))

    }

    for (i in (0 until imgs.size)) imgs[i].onClick { enableImage(i) }

    enableImage(0)//default
}*/