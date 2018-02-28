package com.loyer.people_around

/**
 * Created by loyer on 24.02.2018.
 */
data class Person(private var id:String?,
                  private var latitude: Double?,
                  private var longitude: Double?) {
    constructor(id: String?,  name: String?, latitude: Double?, longitude: Double?):this(id,latitude,longitude){
        this.name = name

    }
    private var name: String? = null



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