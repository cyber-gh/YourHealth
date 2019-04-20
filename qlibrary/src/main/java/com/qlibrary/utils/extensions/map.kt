package com.qlibrary.utils.extensions

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


fun GoogleMap.moveToPoint(lat: Double,long: Double){
    val point = LatLng(lat, long)
    this.clear()
    this.addMarker(MarkerOptions().position(point).title("Marker in Sydney"))
    this.moveCamera(CameraUpdateFactory.newLatLng(point))
}