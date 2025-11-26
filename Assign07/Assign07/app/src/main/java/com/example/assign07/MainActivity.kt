package com.example.assign07

import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.util.Locale

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        geocoder = Geocoder(this, Locale.getDefault())

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val addressInput = findViewById<EditText>(R.id.addressInput)
        val zoomInput = findViewById<EditText>(R.id.zoomInput)
        val searchButton = findViewById<Button>(R.id.searchButton)

        searchButton.setOnClickListener {
            val address = addressInput.text.toString()
            val zoomLevel = zoomInput.text.toString().toFloatOrNull() ?: 15f


            Thread {
                try {
                    val results = geocoder.getFromLocationName(address, 1)
                    if (!results.isNullOrEmpty()) {
                        val location = results[0]
                        val latLng = LatLng(location.latitude, location.longitude)

                        runOnUiThread {
                            map.clear()
                            map.addMarker(MarkerOptions().position(latLng).title(address))
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this, "위치를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this, "지오코딩 오류: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }
}