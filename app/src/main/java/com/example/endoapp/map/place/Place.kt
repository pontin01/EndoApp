package com.example.endoapp.map.place

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Place(
    val name: String,
    val latLng: LatLng,
    val address: String,
    val rating: Float?,
    override val zIndex: Float? = null,
) : ClusterItem {
    override val position: LatLng
        get() = latLng

    override val title: String
        get() = name

    override val snippet: String
        get() = address
}