package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppsAdapter(
    private val apps: List<String>,
    private val onOpenClick: (String) -> Unit,
    private val onCloseClick: (String) -> Unit
) : RecyclerView.Adapter<AppsAdapter.AppViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.app_item_layout, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]
        holder.appName.text = app
        holder.openButton.setOnClickListener { onOpenClick(app) }
        holder.closeButton.setOnClickListener { onCloseClick(app) }
    }

    override fun getItemCount() = apps.size

    inner class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appName: TextView = itemView.findViewById(R.id.app_name)
        val openButton: Button = itemView.findViewById(R.id.open_button)
        val closeButton: Button = itemView.findViewById(R.id.close_button)
    }
}