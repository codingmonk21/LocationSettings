package com.codingmonk21.locationsettings

/**
 * Author: Prajwal C
 * Date: 28,June,2021
 */

import android.os.Bundle
import android.util.Log

class MainActivity : LocationSettingsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onLocationIsAlreadyEnabled() {
        log("onLocationIsAlreadyEnabled")
    }

    override fun onLocationEnableRequestSuccess() {
        log("onLocationEnableRequestSuccess")
    }

    override fun onLocationEnableRequestCancelled() {
        log("onLocationEnableRequestCancelled")
    }

    override fun fallbackToLegacyApproach() {
        log("fallbackToLegacyApproach")
    }

    override fun onLocationPermissionAlreadyGranted() {
        log("onLocationPermissionAlreadyGranted")
    }

    override fun onLocationPermissionGranted() {
        log("onLocationPermissionGranted")
    }

    override fun onLocationPermissionDenied() {
        log("onLocationPermissionDenied")
    }

    private fun log(message: String) {
        Log.d("location", message)
    }
}