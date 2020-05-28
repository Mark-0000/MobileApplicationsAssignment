package com.android.example.DublinBikesApplication

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

//21609 Mark Christian Albinto
class mapPage : AppCompatActivity() {

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_page)

        //jcdecaux Bike Stations Url
        val url =
            "https://api.jcdecaux.com/vls/v1/stations/?apiKey=c7ae1083e3e00d6a25719924534dedd271eb96b4&contract=Dublin"
        AsyncTaskHandleJson().execute(url)
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            //opening http connection and getting stations as text
            var text: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text =
                    connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } finally {
                connection.disconnect()
            }
            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }

    private fun handleJson(jsonString: String?) {
        //converting stations to jsonarray
        val jsonArray = JSONArray(jsonString)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it
            //getting json object
            var y = 0
            while (y < jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(y).getJSONObject("position")
                //getting the latitude and longitude from json and asigning
                val location = LatLng(
                    jsonObject.getDouble("lat"),
                    jsonObject.getDouble("lng")
                )
                //adding a marker on the location variable and location name
                googleMap.addMarker(
                    (MarkerOptions().position(location).title(
                        jsonArray.getJSONObject(
                            y
                        ).getString("name")
                    ))
                )
                //zoom map
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
                y++
            }
        })
    }

    //switching screen to ListPage
    fun switchToListPage(view: View) {
        val intent = Intent(this, listPlacesPage::class.java)
        startActivity(intent)
    }

    //switching screen to RecyclerPage
    fun switchToListRecyclePage(view: View) {
        val intent = Intent(this, recyclerViewListPlaces::class.java)
        startActivity(intent)
    }

    //switching screen to HomePage
    fun switchToHomePage(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //switching screen to MapsPage
    fun switchToMapPage(view: View) {
        val intent = Intent(this, mapPage::class.java)
        startActivity(intent)
    }
}
