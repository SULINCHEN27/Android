package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.R

class ProgramAdapter(val programList:List<Program>):
    RecyclerView.Adapter<ProgramAdapter.ViewHolder>(){

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val programImage:ImageView = view.findViewById(R.id.programImage)
        val programName:TextView = view.findViewById(R.id.programName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view =LayoutInflater.from(parent.context).inflate(R.layout.program_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val program = programList[position]
        holder.programImage.setImageResource(program.imageId)
        holder.programName.text=program.name
    }

    override fun getItemCount()=programList.size


}


