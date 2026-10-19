package com.example.endoapp.map.place

import android.content.Context
import com.example.endoapp.R
import com.example.endoapp.map.BitmapHelper
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class PlaceRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Place>
) : DefaultClusterRenderer<Place>(context, map, clusterManager) {

    /**
     * Icon used for each cluster item
     */
    private val specialistIcon: BitmapDescriptor by lazy {
        BitmapHelper.vectorToBitmap(
            context,
            R.drawable.specialist_icon
        )
    }

    override fun onBeforeClusterItemRendered(item: Place, markerOptions: MarkerOptions) {
        markerOptions.title(item.name)
            .position(item.latLng)
            .icon(specialistIcon)
    }

    override fun onClusterItemRendered(clusterItem: Place, marker: Marker) {
        marker.tag = clusterItem
    }

}