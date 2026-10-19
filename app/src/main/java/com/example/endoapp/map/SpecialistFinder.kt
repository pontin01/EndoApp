package com.example.endoapp.map

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.alpha
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.endoapp.R
import com.example.endoapp.map.place.Place
import com.example.endoapp.map.place.PlaceRenderer
import com.example.endoapp.map.place.PlacesReader
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.collections.MarkerManager
import com.google.maps.android.ktx.addCircle
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad

class SpecialistFinder : AppCompatActivity() {

    private val places: List<Place> by lazy {
        PlacesReader(this).read()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.specialist_finder)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment


        lifecycleScope.launchWhenCreated {
            val googleMap = mapFragment.awaitMap()
            addClusterMarkers(googleMap)
            googleMap.awaitMapLoad()

            val bounds = LatLngBounds.builder()
            places.forEach { bounds.include(it.latLng) }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 200))

            // Zoom in/out button functionality
            val zoomInButton = findViewById<Button>(R.id.zoomInButton)
            zoomInButton.setOnClickListener {
                googleMap.animateCamera(CameraUpdateFactory.zoomIn())
            }
            val zoomOutButton = findViewById<Button>(R.id.zoomOutButton)
            zoomOutButton.setOnClickListener {
                googleMap.animateCamera(CameraUpdateFactory.zoomOut())
            }

        }
    }

    private fun addClusterMarkers(googleMap: GoogleMap) {
        val clusterManager = ClusterManager<Place>(this, googleMap)
        clusterManager.renderer =
            PlaceRenderer(
                this,
                googleMap,
                clusterManager
            )

        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))

        clusterManager.addItems(places)
        clusterManager.cluster()

        clusterManager.setOnClusterItemClickListener { item ->
            addMarkers(googleMap)
            return@setOnClusterItemClickListener false
        }

        googleMap.setOnCameraIdleListener {
            // Call clusterManager.onCameraIdle() when the camera stops moving so that re-clustering
            // can be performed when the camera stops moving
            clusterManager.onCameraIdle()
        }
    }

    private val specialistIcon: BitmapDescriptor by lazy {
        BitmapHelper.vectorToBitmap(this, R.drawable.specialist_icon)
    }

    private fun addMarkers(googleMap: GoogleMap) {
        places.forEach { place ->
            val marker = googleMap.addMarker {
                title(place.name)
                position(place.latLng)
                icon(specialistIcon)
            }
            marker?.tag = place
        }
    }

    companion object {
        val TAG = SpecialistFinder::class.java.name
    }

}