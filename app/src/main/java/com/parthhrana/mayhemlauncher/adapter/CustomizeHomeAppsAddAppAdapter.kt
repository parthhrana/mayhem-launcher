package com.parthhrana.mayhemlauncher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.parthhrana.mayhemlauncher.R
import com.parthhrana.mayhemlauncher.datasource.DataRepository
import com.parthhrana.mayhemlauncher.datasource.addHomeApp
import com.parthhrana.mayhemlauncher.datastore.proto.UnlauncherApp
import com.parthhrana.mayhemlauncher.datastore.proto.UnlauncherApps

class CustomizeHomeAppsAddAppAdapter(
    private val appsRepo: DataRepository<UnlauncherApps>,
    private val activity: ComponentActivity
) : RecyclerView.Adapter<CustomizeHomeAppsAddAppAdapter.ViewHolder>() {
    private var apps: List<UnlauncherApp> = emptyList()

    init {
        appsRepo.observe(Observer { unlauncherApps ->
            apps = unlauncherApps.appsList.filter { app: UnlauncherApp -> !app.hasHomeAppIndex() }
            notifyDataSetChanged()
        })
    }

    override fun getItemCount(): Int = apps.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = apps[position]
        holder.appName.text = item.displayName
        holder.appName.setOnClickListener {
            appsRepo.updateAsync(addHomeApp(item))
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.app_list_item, parent, false)
        .let { view -> ViewHolder(view) }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appName: TextView = itemView.findViewById(R.id.app_list_item_name)
    }
}
