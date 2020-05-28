package com.android.example.DublinBikesApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

//21609 Mark Christian Albinto
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
