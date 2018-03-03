package com.loyer.people_around

/**
 * Created by loyer on 24.02.2018.
 */
  class User {
    constructor(id: String?,  name: String?, latitude: Double?, longitude: Double?):this(){
        this.name = name
        this.id = id
        this.latitude = latitude
        this.longitude = longitude

    }

    constructor()

    private var name: String? = null
    private var id: String? = null
    private var latitude: Double? = null
    private var longitude: Double? = null

    fun getId(): String?{
        return id
    }
    fun getName(): String?{
        return name
    }
    fun getLatitude(): Double?{
        return latitude
    }
    fun getLongitude(): Double?{
        return longitude
    }

}