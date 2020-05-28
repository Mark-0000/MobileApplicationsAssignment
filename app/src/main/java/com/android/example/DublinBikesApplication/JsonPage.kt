package com.android.example.DublinBikesApplication

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

//21609 Mark Christian Albinto
class listPlacesPage() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_places_page)

        //jcdecaux Bike Stations Url
        val url =
            "https://api.jcdecaux.com/vls/v1/stations/?apiKey=c7ae1083e3e00d6a25719924534dedd271eb96b4&contract=Dublin"

        //get stations from API
        AsyncTaskHandleJson().execute(url)
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            var text: String
            //Opening http connection
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

    //Parsing results
    private fun handleJson(jsonString: String?) {
        //getting textView variable for display
        val textView: TextView = findViewById(R.id.apiData) as TextView
        //getting Button variable
        val listButton: Button = findViewById(R.id.listStations) as Button

        //converting stations to JSONArray
        val jsonArray = JSONArray(jsonString)

        //displaying stations
        var x = 0
        var tempString = ""
        while (x < jsonArray.length()) {
            tempString += jsonArray.getJSONObject(x).toString() + "\n"
            x++
        }
        listButton.setOnClickListener {
            //setting apiData textview on button click
            textView.text = tempString
        }
    }

    //switching screen to ListPage
    fun switchToListPage(view: View) {
        val intent = Intent(this, listPlacesPage::class.java)
        startActivity(intent)
    }

    //switching screen to RecyclerList

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


