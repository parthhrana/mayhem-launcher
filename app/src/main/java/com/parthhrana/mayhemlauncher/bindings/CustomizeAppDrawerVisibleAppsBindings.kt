package com.parthhrana.mayhemlauncher.bindings

import androidx.activity.ComponentActivity
import com.parthhrana.mayhemlauncher.adapter.CustomizeAppDrawerVisibleAppsAdapter
import com.parthhrana.mayhemlauncher.databinding.CustomizeAppDrawerVisibleAppsBinding
import com.parthhrana.mayhemlauncher.datasource.DataRepository
import com.parthhrana.mayhemlauncher.datastore.proto.UnlauncherApps

fun setupVisibleAppsBackButton(activity: ComponentActivity) = { options: CustomizeAppDrawerVisibleAppsBinding ->
    options.headerBack.setOnClickListener { activity.onBackPressedDispatcher.onBackPressed() }
}

fun setupVisibleAppsList(appsRepo: DataRepository<UnlauncherApps>) = { options: CustomizeAppDrawerVisibleAppsBinding ->
    options.customizeAppDrawerVisibleAppsList.adapter = CustomizeAppDrawerVisibleAppsAdapter(appsRepo)
}
