package com.qlibrary.library

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MapStyleOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.qlibrary.R

abstract class QMapFragment(layoutId: Int) : QFragment(layoutId), OnMapReadyCallback{
    private lateinit var mapView: MapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mapView = getMapView()
        mapView.onCreate(savedInstanceState?.getBundle("mapState"));
        mapView.getMapAsync(this)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val mapState = Bundle()
        mapView.onSaveInstanceState(mapState)
        outState.putBundle("mapState", mapState)
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(context, R.raw.mapstyle)
        )

        Dexter.withActivity(QRouter.activity)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) { googleMap?.isMyLocationEnabled = true }
                    override fun onPermissionDenied(repsonse: PermissionDeniedResponse) {/* ... */}
                    override fun onPermissionRationaleShouldBeShown(repsonse: PermissionRequest, token: PermissionToken) {/* ... */}
                }).check()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    abstract fun getMapView(): MapView
}