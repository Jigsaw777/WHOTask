package com.example.covid19tracker.views.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.Handler
import android.telephony.TelephonyManager
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.covid19tracker.R
import com.example.covid19tracker.data.constants.AppConstants
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

/** Ideally, location services can be decoupled to be used in another helper class.
 * For the sake of simplicity and permission checking, using location services in splash activity.*/

class SplashActivity : AppCompatActivity() {

    private val locationRequestCode = 1;
    private lateinit var locationManager: LocationManager

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            processLocation(location?.latitude ?: -1.1, location?.longitude ?: -1.1)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onProviderEnabled(provider: String?) {}
        override fun onProviderDisabled(provider: String?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        initViews()
    }

    private fun initViews() {

        /** This handler posts the runnable in the UI thread's event queue as we are executing this in the UI thread and the
         * handler references the UI thread. */
        Handler().postDelayed({
            tv_fetching_location.visibility = View.VISIBLE
            pb_loading.visibility = View.VISIBLE
            checkPermissionsAndGetLocation()
        }, 1500)
    }

    private fun checkPermissionsAndGetLocation() {

        if (isPermissionGranted(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            getLocation()
        } else if (isUserCheckNeverAskAgain()) {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.permission_reason))
                .setPositiveButton(
                    R.string.positive_response
                ) { dialog, _ ->
                    dialog.dismiss()
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        locationRequestCode
                    )
                }
                /** Here we have 2 choices :
                 * a. To direct the user to the settings and make him give the location permission.
                 * b. To get the location via Telephony manager (backup)
                 * Here, in this project I have chosen the 2nd option*/
                .setNegativeButton(
                    R.string.negative_response
                ) { dialog, _ ->
                    dialog.dismiss()
                    getTelephonyManagerLocation()
                }
                .show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != locationRequestCode) return

        if (isPermissionGranted(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            //success
            getLocation()
            return
        }

        if (isUserCheckNeverAskAgain()) {
            //never ask again has bee checked
            getTelephonyManagerLocation()
            return
        }

        /** Since for the current session, the user hasn't given the permission, show him location according to the telephony manager.
         * Also show him a dialog informing him/her that location data might not be accurate*/
        //didnt get the permission
        AlertDialog.Builder(this).setTitle(R.string.inaccurate_location_data)
            .setPositiveButton(
                R.string.ok_text
            ) { dialog, _ ->
                dialog?.dismiss()
                getTelephonyManagerLocation()
            }
    }

    private fun isUserCheckNeverAskAgain() =
        !ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            android.Manifest.permission_group.LOCATION
        )

    private fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    /** If permission has been granted then get the last known location. If permission is not granted apply the backup method */
    @SuppressLint("MissingPermission")
    private fun getTelephonyManagerLocation() {
        if (isPermissionGranted(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (location != null)
                processLocation(location.latitude, location.longitude)
            else
                getNearestPossibleLocation()
        } else
            getNearestPossibleLocation()
    }

    private fun getNearestPossibleLocation() {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val country = tm.networkCountryIso
        goToMainActvity(
            country.toUpperCase(Locale.getDefault()),
            Locale(Locale.getDefault().language, country).displayName
        )
    }

    /** No permission check required for this method as it has been already handled before calling this method. */
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val criteria = Criteria()
        val isNetworkEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        when {
            isNetworkEnabled -> criteria.accuracy = Criteria.ACCURACY_COARSE
            isGPSEnabled -> criteria.accuracy = Criteria.ACCURACY_FINE
            else -> {
                getTelephonyManagerLocation()
                return
            }
        }
        locationManager.requestSingleUpdate(criteria, locationListener, null)
    }

    /**https://stackoverflow.com/questions/14155049/iso2-country-code-from-country-name
     * A solution is there in the above link to tackle problems regarding countryISO codes when phone's default language is
     * something other than English. The solution is not included in the current project for sake of simplicity*/

    private fun processLocation(lat: Double, long: Double) {
        if (lat < 0 || long < 0)
            getTelephonyManagerLocation()
        else {
            val geoCoder = Geocoder(this, Locale.getDefault())
            val addresses = geoCoder.getFromLocation(lat, long, 1)

            if (addresses.size > 0) {
                val countryName = addresses[0].countryName
                for (iso in Locale.getISOCountries()) {
                    if (Locale(Locale.getDefault().language, iso).displayCountry.equals(
                            countryName,
                            false
                        )
                    ) {
                        goToMainActvity(iso.toUpperCase(Locale.getDefault()), countryName)
                        return
                    }
                }
            } else
                getTelephonyManagerLocation()
        }
    }

    private fun goToMainActvity(code: String, name: String) {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra(AppConstants.COUNTRY_CODE,code)
        intent.putExtra(AppConstants.COUNTRY_NAME, name)
        startActivity(intent)
        finish()
    }

}
