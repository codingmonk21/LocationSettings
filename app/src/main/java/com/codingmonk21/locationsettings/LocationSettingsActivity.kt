package com.codingmonk21.locationsettings

/**
 * Author: Prajwal C
 * Date: 28,June,2021
 */

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest

abstract class LocationSettingsActivity : AppCompatActivity(), LocationSettingsCallBack {
    private val REQUEST_ENABLE_LOCATION = 3625
    private val REQUEST_LOCATION_PERMISSION = 5263

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS
        ) {
            enableLocation()
        } else {
            fallbackToLegacyApproach()
        }
    }

    private fun enableLocation() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = with(LocationSettingsRequest.Builder()) {
            addLocationRequest(locationRequest)
            setAlwaysShow(true)
        }

        val locationSettingsTask = LocationServices
            .getSettingsClient(this)
            .checkLocationSettings(builder.build())

        locationSettingsTask.addOnSuccessListener(this) {
            onLocationIsAlreadyEnabled()
            requestLocationPermission()
        }

        locationSettingsTask.addOnFailureListener(this) { e ->
            if (e is ResolvableApiException) e.startResolutionForResult(
                this,
                REQUEST_ENABLE_LOCATION
            ) else {
                fallbackToLegacyApproach()
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_LOCATION) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    onLocationEnableRequestSuccess()
                    requestLocationPermission()
                }
                Activity.RESULT_CANCELED -> onLocationEnableRequestCancelled()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onLocationPermissionGranted()
            } else {
                onLocationPermissionDenied()
            }
        } else {
            onLocationPermissionDenied()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onLocationPermissionAlreadyGranted()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
}