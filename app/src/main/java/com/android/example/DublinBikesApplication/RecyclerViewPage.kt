package com.android.example.DublinBikesApplication

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view_list_places.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

//21609 Mark Christian ALbinto
class recyclerViewListPlaces : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_list_places)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager


        //Url of API Stations
        val url =
            "https://api.jcdecaux.com/vls/v1/stations/?apiKey=c7ae1083e3e00d6a25719924534dedd271eb96b4&contract=Dublin"
        AsyncTaskHandleJson().execute(url)
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            //opening a http connection
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

        //parsing the result
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }

    private fun handleJson(jsonString: String?) {
        //converting stations to json array
        val jsonArray = JSONArray(jsonString)
        val list = ArrayList<Station>()

        //getting json object
        var x = 0
        while (x < jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(x)
            list.add(
                Station(
                    jsonObject.getString("name"),
                    jsonObject.getString("address")
                )
            )
            x++
        }

        val adapter = StationsAdapter(this, list.toMutableList())
        recyclerView.adapter = adapter
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

    //switching screen to homePage
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
