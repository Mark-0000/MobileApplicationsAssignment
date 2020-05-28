package com.android.example.DublinBikesApplication


//21609 mark Christian Albinto
data class fullStation(

    val number: Int,
    val contract_name: String,
    val name: String,
    val address: String,
    val position: Position,
    val banking: Boolean,
    val bonus: Boolean,
    val bike_stands: Int,
    val available_bike_stands: Int,
    val available_bikes: Int,
    val status: String,
    val last_update: Int
)
data class Position(

    val lat: Double,
    val lng: Double
)

data class Station(
    val name: String,
    val address: String
)