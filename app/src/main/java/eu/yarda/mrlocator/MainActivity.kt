package eu.yarda.mrlocator

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource


class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var cancellationTokenSource: CancellationTokenSource = CancellationTokenSource()
    private val requestFineLocationPermission = 1
    private val loch: CharArray = "ABCDEFGHIJKLMNOPQRSTUVWX!".toCharArray()
    private val lod: CharArray = "0123456789!".toCharArray()
    // workaround for onclick to work on older devices
    private val clickListener = View.OnClickListener { view ->
        sendMessage(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // workaround for onclick to work on older devices
        val getlocationButton = findViewById<Button>(R.id.GetLocation)
        getlocationButton.setOnClickListener(clickListener)
     }

    private fun calcLocator(plon: Double, plat: Double): String {
        var lon: Double = plon + 180
        var lat: Double = plat + 90
        val loc = CharArray(8){'-'}

        if (lat < 0 || lat > 180 || lon < 0 || lon > 360) {
            return "ERROR"
        }

        // JN
        loc[0] = loch[(lon / 20.0).toInt()]
        loc[1] = loch[(lat / 10.0).toInt()]
        lon = lon.mod(20.0)
        lat = lat.mod(10.0)

        // JN89
        loc[2] = lod[(lon / 2.0).toInt()]
        loc[3] = lod[lat.toInt()]
        lon = lon.mod(2.0)
        lat = lat.mod(1.0)

        // JN89GE
        loc[4] = loch[(lon * 60.0 / 5.0).toInt()].lowercaseChar()
        loc[5] = loch[(lat * 60.0 / 2.5).toInt()].lowercaseChar()
        lon = lon.mod(5.0 / 60.0)
        lat = lat.mod(2.5 / 60.0)

        // JN89GE00
        loc[6] = lod[(lon * 600.0 / 5.0).toInt()]
        loc[7] = lod[(lat * 600.0 / 2.5).toInt()]

        return loc.joinToString("")
    }

    private fun sendMessage(view: View) {
        if (view == findViewById<Button>(R.id.GetLocation)) {
            val locatorText = findViewById<TextView>(R.id.LocatorText)
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    requestFineLocationPermission)
            }

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token)

            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    locatorText.text = calcLocator(location.longitude, location.latitude)
                }
            }
        }
    }
}
