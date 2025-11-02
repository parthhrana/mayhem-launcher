package com.parthhrana.mayhemlauncher.adapter

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.parthhrana.mayhemlauncher.datasource.getHomeApps
import com.parthhrana.mayhemlauncher.datasource.packageClassAppOrder
import com.parthhrana.mayhemlauncher.datasource.unlauncherAppNotFound
import com.parthhrana.mayhemlauncher.datastore.proto.UnlauncherApp
import com.parthhrana.mayhemlauncher.datastore.proto.UnlauncherApps

fun <T> notifyOfHomeAppChanges(
    adapter: T
): Observer<UnlauncherApps> where T : RecyclerView.Adapter<*>, T : UnlauncherAppListHolder {
    return Observer { updatedData ->
        val currentHomeApps = adapter.apps
        val updatedHomeApps = getHomeApps(updatedData)
        if (currentHomeApps == updatedHomeApps) {
            return@Observer
        }
        adapter.apps = updatedHomeApps

        val addedApps = updatedHomeApps.filter(unlauncherAppNotFound(currentHomeApps))
        addedApps.forEach { adapter.notifyItemInserted(it.homeAppIndex) }
        val removedApps = currentHomeApps.filter(unlauncherAppNotFound(updatedHomeApps))
        removedApps.forEach { adapter.notifyItemRemoved(it.homeAppIndex) }

        val appPairs = currentHomeApps
            .minus(removedApps.toSet())
            .sortedBy(::packageClassAppOrder)
            .zip(
                updatedHomeApps
                    .minus(addedApps.toSet())
                    .sortedBy(::packageClassAppOrder)
            )

        appPairs
            .filter { (oldApp, newApp) -> oldApp.displayName != newApp.displayName }
            .forEach { (_, newApp) ->
                adapter.notifyItemChanged(newApp.homeAppIndex)
            }

        // Avoid calling notifyItemMoved on adds/removes
        if (addedApps.isNotEmpty() || removedApps.isNotEmpty()) {
            return@Observer
        }

        appPairs
            .firstOrNull { (oldApp, newApp) -> oldApp.homeAppIndex != newApp.homeAppIndex }
            ?.let { (oldApp, newApp) -> adapter.notifyItemMoved(oldApp.homeAppIndex, newApp.homeAppIndex) }
    }
}

interface UnlauncherAppListHolder {
    var apps: List<UnlauncherApp>
}
