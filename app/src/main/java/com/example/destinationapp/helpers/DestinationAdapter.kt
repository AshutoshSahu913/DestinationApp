package com.example.destinationapp.helpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.destinationapp.activities.DestinationDetailActivity
import com.example.destinationapp.databinding.ListItemBinding
import com.example.destinationapp.models.Destination


class DestinationAdapter(val destinationList: List<Destination>) :
    RecyclerView.Adapter<DestinationAdapter.viewHolder>() {

    inner class viewHolder(var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val txvDestination = binding.txtDestination
        var destination: Destination? = null

        override fun toString(): String {
            return """${super.toString()} '${txvDestination.text}'"""
        }
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val model = destinationList[position]
        holder.binding.txtDestination.text = model.city
        holder.destination = model

        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, DestinationDetailActivity::class.java)
            intent.putExtra(DestinationDetailActivity.ARG_ITEM_ID, holder.destination?.id)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
        return destinationList.size
    }

}
