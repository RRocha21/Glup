package com.example.glup

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.util.*
import kotlin.math.round
import kotlin.math.roundToInt


class PaymentsActivity: AppCompatActivity() {

//    lateinit var locationManager: LocationManager
//    private var hasGPS = false
//    private  var locationGPS : Location? = null

    private lateinit var licensepress: Button
    private lateinit var timepress: Button
    private lateinit var locationpress: Button
    private lateinit var gpspress: Button

    private lateinit var showcostvalue: TextView
    private lateinit var showfinishtime: TextView

    private lateinit var showlicense1plate: TextView
    private lateinit var showlicense2plate: TextView
    private lateinit var showlicense3plate: TextView
    private lateinit var showlicense4plate: TextView
    private lateinit var showlicense5plate: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments)

        showlicense1plate = findViewById<TextView>(R.id.license1)
        showlicense2plate = findViewById<TextView>(R.id.license2)
        showlicense3plate = findViewById<TextView>(R.id.license3)
        showlicense4plate = findViewById<TextView>(R.id.license4)
        showlicense5plate = findViewById<TextView>(R.id.license5)

        showcostvalue = findViewById<TextView>(R.id.text_view_cost)
        showfinishtime = findViewById<TextView>(R.id.text_view_finishes)

        gpspress = findViewById<Button>(R.id.use_gps)
        licensepress = findViewById<Button>(R.id.license_press)
        timepress = findViewById<Button>(R.id.time_press)
        locationpress = findViewById<Button>(R.id.location_press)

        getlicense()
        getdate(0,getcost(0.0,0.0)," ",0.0," ")

        var cost_value: Double = 0.0
        var time_read = 0.0
        var zone_cost = 0.0

        var local_read : String = ""
        var license_read : String = ""


        licensepress.setOnClickListener {

            val license1 = showlicense1plate.text.toString()
            val license2 = showlicense2plate.text.toString()
            val license3 = showlicense3plate.text.toString()
            val license4 = showlicense4plate.text.toString()
            val license5 = showlicense5plate.text.toString()

            val popupMenu:PopupMenu = PopupMenu(this, licensepress)
            if (license1 != "A") popupMenu.menu.add(license1)
            if (license2 != "A") popupMenu.menu.add(license2)
            if (license3 != "A") popupMenu.menu.add(license3)
            if (license4 != "A") popupMenu.menu.add(license4)
            if (license5 != "A") popupMenu.menu.add(license5)

            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                Toast.makeText(this, "You Selected  " + item.title, Toast.LENGTH_SHORT).show()
                licensepress.setText(item.title)
                license_read  = item.title as String


                getdate(time_read.roundToInt(),getcost(zone_cost,time_read),license_read,time_read,local_read)

                true
            })
            true
            popupMenu.show()
        }

        timepress.setOnClickListener {

            val popupMenu: PopupMenu = PopupMenu(this, timepress)
            popupMenu.menu.add("0h10m")
            popupMenu.menu.add("0h20m")
            popupMenu.menu.add("0h30m")
            popupMenu.menu.add("1h00m")
            popupMenu.menu.add("1h30m")
            popupMenu.menu.add("2h00m")

            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                Toast.makeText(this, "You Selected  " + item.title, Toast.LENGTH_SHORT).show()
                timepress.setText(item.title)

                if (item.title.equals("0h10m")) time_read = 10.0
                if (item.title.equals("0h20m")) time_read = 20.0
                if (item.title.equals("0h30m")) time_read = 30.0
                if (item.title.equals("1h00m")) time_read = 60.0
                if (item.title.equals("1h30m")) time_read = 90.0
                if (item.title.equals("2h00m")) time_read = 120.0

                getdate(
                    time_read.roundToInt(),
                    getcost(zone_cost,time_read),
                    license_read,
                    time_read,
                    local_read
                )

                true
            })
            true
            popupMenu.show()
        }

        locationpress.setOnClickListener {
            val popupMenu:PopupMenu = PopupMenu(this, locationpress)
            popupMenu.menu.add("Zone A")
            popupMenu.menu.add("Zone B")
            popupMenu.menu.add("Zone C")
            popupMenu.menu.add("Zone D")
            popupMenu.menu.add("Zone E")

            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                Toast.makeText(this, "You Selected  " + item.title, Toast.LENGTH_SHORT).show()
                locationpress.setText(item.title)

                if (item.title.equals("Zone A")) zone_cost = 0.8
                if (item.title.equals("Zone B")) zone_cost = 0.9
                if (item.title.equals("Zone C")) zone_cost = 0.7
                if (item.title.equals("Zone D")) zone_cost = 0.6
                if (item.title.equals("Zone E")) zone_cost = 1.0

                local_read  = item.title as String

                getdate(
                    time_read.roundToInt(),
                    getcost(zone_cost,time_read),
                    license_read,
                    time_read,
                    local_read
                )

                true
            })
            true
            popupMenu.show()
        }

        gpspress.setOnClickListener {
//            getLocation()
        }

    }


    private fun getdate(d: Int, cost: Double, license: String, time: Double, local: String) {
        val rightnow: Calendar = Calendar.getInstance()


        var currentday: Int = rightnow.get(Calendar.DAY_OF_MONTH)
        val currentmonth: Int = rightnow.get(Calendar.MONTH)+1
        val currentyear: Int = rightnow.get(Calendar.YEAR)
        var currenthour: Int = rightnow.get(Calendar.HOUR_OF_DAY)
        var currentminute: Int = rightnow.get(Calendar.MINUTE)


        val user= FirebaseAuth.getInstance().currentUser

        val myRef= FirebaseDatabase.getInstance().getReference("users")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    val hourfromDb = dataSnapshot.child(user.uid).child("payments").child("hour").getValue<Int>()
                    val minutefromDb = dataSnapshot.child(user.uid).child("payments").child("minute").getValue<Int>()
                    val dayfromDb = dataSnapshot.child(user.uid).child("payments").child("day").getValue<Int>()
                    val monthfromDb = dataSnapshot.child(user.uid).child("payments").child("month").getValue<Int>()
                    val yearfromDb = dataSnapshot.child(user.uid).child("payments").child("year").getValue<Int>()
                    val balancefromDb = dataSnapshot.child(user.uid).child("balance").getValue<Double>()

                    if ((currentday==dayfromDb)and(currentmonth==monthfromDb)and(currentyear==yearfromDb))
                    {
                        if (hourfromDb != null) {
                            if(hourfromDb>currenthour) {
                                if (minutefromDb != null) {
                                    currentminute = minutefromDb
                                }
                                currenthour = hourfromDb
                            } else if (minutefromDb != null) {
                                if(minutefromDb>currentminute) {
                                    currentminute = minutefromDb
                                }
                            }
                        }

                    }

                    var exphour = currenthour
                    var expminute = d+currentminute

                    if (expminute>=60) {
                        exphour += + 1
                        expminute = expminute - 60
                    }
                    if (expminute>=60) {
                        exphour += + 1
                        expminute = expminute - 60
                    }
                    if(exphour>=24 ) {
                        exphour = exphour -24
                        currentday = currentday + 1
                    }



                    findViewById<Button>(R.id.paybutton).setOnClickListener {
                        if (balancefromDb != null) {
                            if (balancefromDb.toFloat() > cost) {
                                var balance = balancefromDb-cost
                                myRef.child(user.uid).child("payments").child("minute").setValue(expminute.toInt())
                                myRef.child(user.uid).child("payments").child("hour").setValue(exphour.toInt())
                                myRef.child(user.uid).child("payments").child("day").setValue(currentday.toInt())
                                myRef.child(user.uid).child("payments").child("month").setValue(currentmonth.toInt())
                                myRef.child(user.uid).child("payments").child("year").setValue(currentyear.toInt())
                                balance = (round(balance*100))/100
                                myRef.child(user.uid).child("balance").setValue(balance)
                                val randomnumber = (0..9999999999999999).random()

                                myRef.child(user.uid).child("tickets").child(randomnumber.toString()).child("payminute").setValue(currentminute.toInt())
                                myRef.child(user.uid).child("tickets").child(randomnumber.toString()).child("payhour").setValue(currenthour.toInt())
                                myRef.child(user.uid).child("tickets").child(randomnumber.toString()).child("payday").setValue(currentday.toInt())
                                myRef.child(user.uid).child("tickets").child(randomnumber.toString()).child("paymonth").setValue(currentmonth.toInt())
                                myRef.child(user.uid).child("tickets").child(randomnumber.toString()).child("payyear").setValue(currentyear.toInt())

                                myRef.child(user.uid).child("tickets").child(randomnumber.toString()).child("untilminute").setValue(expminute.toInt())
                                myRef.child(user.uid).child("tickets").child(randomnumber.toString()).child("unitlhour").setValue(exphour.toInt())

                                myRef.child(user.uid).child("tickets").child(randomnumber.toString()).child("Cost").setValue(cost)
                                myRef.child(user.uid).child("tickets").child(randomnumber.toString()).child("Balance").setValue(balance)
                                myRef.child(user.uid).child("tickets").child(randomnumber.toString()).child("License").setValue(license)
                                myRef.child(user.uid).child("tickets").child(randomnumber.toString()).child("Time").setValue(time)
                                myRef.child(user.uid).child("tickets").child(randomnumber.toString()).child("Zone").setValue(local)


                            }
                        }

                    }

                    var showtime = "Finishes at $exphour : $expminute"
                    showfinishtime.text= Editable.Factory.getInstance().newEditable(showtime.toString())

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        myRef.addValueEventListener(postListener)


    }

    private fun getlicense(){

         val user = FirebaseAuth.getInstance().currentUser

         val myRef = FirebaseDatabase.getInstance().getReference("users")


         val postListener = object : ValueEventListener {
             override fun onDataChange(dataSnapshot: DataSnapshot) {

                 if (dataSnapshot.exists()) {

                     val license1fromDb = dataSnapshot.child(user.uid).child("license").child("1").getValue<String>()
                     val license2fromDb = dataSnapshot.child(user.uid).child("license").child("2").getValue<String>()
                     val license3fromDb = dataSnapshot.child(user.uid).child("license").child("3").getValue<String>()
                     val license4fromDb = dataSnapshot.child(user.uid).child("license").child("4").getValue<String>()
                     val license5fromDb = dataSnapshot.child(user.uid).child("license").child("5").getValue<String>()

                     showlicense1plate.text = Editable.Factory.getInstance().newEditable(license1fromDb)
                     showlicense2plate.text = Editable.Factory.getInstance().newEditable(license2fromDb)
                     showlicense3plate.text = Editable.Factory.getInstance().newEditable(license3fromDb)
                     showlicense4plate.text = Editable.Factory.getInstance().newEditable(license4fromDb)
                     showlicense5plate.text = Editable.Factory.getInstance().newEditable(license5fromDb)
                 }


             }

             override fun onCancelled(databaseError: DatabaseError) {
                 // Getting Post failed, log a message
             }
         }
         myRef.addValueEventListener(postListener)

    }

    private fun getcost(zone: Double, time: Double): Double {
        var cost_value = (zone/60) * time
        cost_value = (round(cost_value*100))/100
        var x = "Cost : $cost_value"
        showcostvalue.text= Editable.Factory.getInstance().newEditable(x.toString().plus("$"))
        return cost_value
    }

//    @SuppressLint("MissingPermission")
//    private fun getLocation() {
//        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//        if (hasGPS){
//            Log.d("CodeAndroidLocation","hasGPS")
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0f,object :
//                LocationListener {
//                override fun onLocationChanged(location: Location) {
//                    if(location != null) {
//                        locationGPS = location
//                    }
//                }
//            })
//        }else{
//            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
//        }
//
//        if (locationGPS != null){
//            val latitude = locationGPS!!.latitude
//            val longitude = locationGPS!!.longitude
//            Log.d("CodeAndroidLocation", "Latitude : $latitude Longitude : $longitude")
//        }
//
//
//    }

}








