package com.loyer.people_around

/**
 * Created by loyer on 24.02.2018.
 */
data class Person(private var name: String,
                  private var longitude: Double,
                  private var latitude: Double) {

        var mName: String = name
        var mLongitude : Double = longitude
        var mLatitude: Double = latitude


}