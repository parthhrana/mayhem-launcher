package com.parthhrana.mayhemlauncher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.parthhrana.mayhemlauncher.R
import com.parthhrana.mayhemlauncher.datasource.DataRepository
import com.parthhrana.mayhemlauncher.datasource.setDisplayInDrawer
import com.parthhrana.mayhemlauncher.datastore.proto.UnlauncherApps

class CustomizeAppDrawerVisibleAppsAdapter(private val appsRepo: DataRepository<UnlauncherApps>) :
    RecyclerView.Adapter<CustomizeAppDrawerVisibleAppsAdapter.ViewHolder>() {
    private var apps = appsRepo.get()

    override fun getItemCount(): Int = apps.appsCount

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = apps.getApps(position)
        holder.appName.apply {
            text = item.displayName
            isChecked = item.displayInDrawer
        }
        holder.appName.setOnCheckedChangeListener { _, isChecked ->
            appsRepo.updateAsync(setDisplayInDrawer(item, isChecked))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.customize_app_drawer_visible_apps_list_item, parent, false)
        .let { view -> ViewHolder(view) }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appName: CheckBox =
            itemView.findViewById(R.id.customize_app_drawer_fragment_app_list_item)
    }
}
