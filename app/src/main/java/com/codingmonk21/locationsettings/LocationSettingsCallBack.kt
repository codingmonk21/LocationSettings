package com.codingmonk21.locationsettings

/**
 * Author: Prajwal C
 * Date: 28,June,2021
 */
interface LocationSettingsCallBack {
    fun onLocationIsAlreadyEnabled()
    fun onLocationEnableRequestSuccess()
    fun onLocationEnableRequestCancelled()
    fun fallbackToLegacyApproach()
    fun onLocationPermissionAlreadyGranted()
    fun onLocationPermissionGranted()
    fun onLocationPermissionDenied()
}