package com.example.touremate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.touremate.R


class VstAdapter (private val vstList: ArrayList<VisitorModel>) : RecyclerView.Adapter<VstAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.vst_list_item,parent,false)
        return ViewHolder(itemView,mListener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentVst = vstList[position]
        holder.tvVstName.text = currentVst.vstName
    }



    override fun getItemCount(): Int {
        return vstList.size
    }

    class ViewHolder(itemView: View,clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView) {
        val tvVstName : TextView = itemView.findViewById(R.id.tvVstName)

        init{
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }


}