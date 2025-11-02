package com.parthhrana.mayhemlauncher.bindings

import androidx.activity.ComponentActivity
import com.parthhrana.mayhemlauncher.adapter.CustomizeHomeAppsAddAppAdapter
import com.parthhrana.mayhemlauncher.databinding.CustomizeHomeAppsAddAppBinding
import com.parthhrana.mayhemlauncher.datasource.DataRepository
import com.parthhrana.mayhemlauncher.datastore.proto.UnlauncherApps

fun setupAddAppBackButton(activity: ComponentActivity) = { options: CustomizeHomeAppsAddAppBinding ->
    options.headerBack.setOnClickListener { activity.onBackPressedDispatcher.onBackPressed() }
}

fun setupAddAppsList(appsRepo: DataRepository<UnlauncherApps>, activity: ComponentActivity) =
    { options: CustomizeHomeAppsAddAppBinding ->
        options.addAppList.adapter = CustomizeHomeAppsAddAppAdapter(appsRepo, activity)
    }
