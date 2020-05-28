package com.android.example.DublinBikesApplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

//21609 Mark Christian Albinto
class StationsAdapter(val context: Context, val stations: MutableList<Station>) :
    RecyclerView.Adapter<StationsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stations.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val station = stations[position]
        holder.setData(station, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var currentStation: Station? = null
        var currentPosition: Int = 0

        init {
            itemView.setOnClickListener {
                Toast.makeText(context, currentStation!!.name + " Clicked !", Toast.LENGTH_LONG)
                    .show()
            }
        }

        fun setData(station: Station, pos: Int) {
            itemView.txvName.text = station!!.name
            itemView.txvAddress.text = station!!.address

            this.currentStation = station
            this.currentPosition = pos
        }
    }
}