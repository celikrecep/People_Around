package com.loyer.people_around

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.EditText

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG = "PermissionDemo"
    private val PERMISSIONS_REQUEST = 101
    private val MIN_TIME_BW_UPDATES: Long = 1
    private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Float = 1F
    private val ZOOM_CAMERA = 18F
    private var mMap: GoogleMap? = null
    private var location: Location? = null
    private var locationManager: LocationManager? = null
    private var latitude: Double? = 0.0
    private var longitude: Double? = 0.0
    private var mDatabaseReference: DatabaseReference? = null
    private var mFireBaseUser: FirebaseUser? = null
    private var reference: DatabaseReference? = null
    private lateinit var userList: ArrayList<User>
    private var mName: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        setupPermission()
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mDatabaseReference = FirebaseDatabase.getInstance().reference
        mFireBaseUser = FirebaseAuth.getInstance().currentUser
        reference = mDatabaseReference?.child("persons")
        userList = ArrayList()

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (isGpsEnabled){
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER
                    , MIN_TIME_BW_UPDATES
                    , MIN_DISTANCE_CHANGE_FOR_UPDATES
                    , locationListener)

            if (locationManager != null){
                location = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location != null){

                    latitude = location!!.latitude
                    longitude = location!!.longitude
                    // update  Longitude and Latitude
                    pushUser(latitude,longitude)
                    getUsers()
                }
            }
        }
 }

    //When the user has responded to the permission request
    // the onRequestPermissionsResult() method will be
    // called on the activity
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        when (requestCode) {
            PERMISSIONS_REQUEST -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
    }

    //we Used for receiving notifications from the
    // LocationManager when the location has changed
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(p0: Location) {
            location = p0
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    //requesting permission from the user
    private fun setupPermission(){
        val permission = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
        if(permission != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG,"Permission to record denied")
            makeRequest()
        }
    }

    //a method added to class to request a specific permission from the user
    private fun makeRequest(){
        ActivityCompat.requestPermissions(this
                , arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                ,PERMISSIONS_REQUEST)
    }

    //we'll only update specific properties.
 private fun pushUser(latitude: Double?, longitude: Double?){

     var id: String ? = mFireBaseUser?.uid
     mDatabaseReference?.child("persons")!!.child(id).child("latitude").setValue(latitude)
     mDatabaseReference?.child("persons")!!.child(id).child("longitude").setValue(longitude)

 }

    private fun addMarker(){

        for(user in userList){

            if(user.getId() == mFireBaseUser!!.uid){
               mName = user.getName()
                var coordinates = LatLng(latitude!!, longitude!!)
                mMap!!.addMarker(MarkerOptions()
                        .position(coordinates)
                        .title(mName))
                mMap!!.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(coordinates, ZOOM_CAMERA))
            }else {

                var coor = LatLng(user.getLatitude()!!, user.getLongitude()!!)
                mMap!!.addMarker(MarkerOptions()
                        .title(user.getName())
                        .position(coor))
            }
        }

    }
            //get all user from Database
    private fun getUsers(){

        val postListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
            }
            override fun onDataChange(p0: DataSnapshot?) {

                for(data in p0!!.children){
                    var userData = data.getValue<User>(User::class.java)
                     var usr = userData?.let { it }?:continue
                    userList.add(usr)

                }
                Log.d("List",userList.size.toString())
                addMarker()
            }
        }
        mDatabaseReference?.child("persons")!!.addValueEventListener(postListener)
    }





}

