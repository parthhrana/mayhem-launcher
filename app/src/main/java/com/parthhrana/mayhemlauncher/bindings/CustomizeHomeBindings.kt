package com.parthhrana.mayhemlauncher.bindings

import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import com.parthhrana.mayhemlauncher.R
import com.parthhrana.mayhemlauncher.adapter.CustomizeHomeAppsListAdapter
import com.parthhrana.mayhemlauncher.databinding.CustomizeHomeBinding
import com.parthhrana.mayhemlauncher.datasource.DataRepository
import com.parthhrana.mayhemlauncher.datasource.QuickButtonIcon
import com.parthhrana.mayhemlauncher.datasource.getHomeApps
import com.parthhrana.mayhemlauncher.datasource.getIconResourceId
import com.parthhrana.mayhemlauncher.datastore.proto.QuickButtonPreferences
import com.parthhrana.mayhemlauncher.datastore.proto.UnlauncherApps
import com.parthhrana.mayhemlauncher.dialog.QuickButtonIconDialog

fun setupCustomizeQuickButtonsBackButton(activity: ComponentActivity) = { options: CustomizeHomeBinding ->
    options.headerBack.setOnClickListener { activity.onBackPressedDispatcher.onBackPressed() }
}

private fun setIconResource(iconView: ImageView) = { resourceId: Int ->
    iconView.setImageResource(resourceId)
    when (resourceId) {
        R.drawable.ic_empty -> iconView.setBackgroundResource(R.drawable.imageview_border)
        else -> iconView.setBackgroundResource(0)
    }
}

private fun updateQuickButtonIcons(binding: CustomizeHomeBinding): (QuickButtonPreferences) -> Unit = { prefs ->
    prefs.leftButton.iconId
        .let(::getIconResourceId)
        ?.let(setIconResource(binding.quickButtonLeft))
    prefs.centerButton.iconId
        .let(::getIconResourceId)
        ?.let(setIconResource(binding.quickButtonCenter))
    prefs.rightButton.iconId
        .let(::getIconResourceId)
        ?.let(setIconResource(binding.quickButtonRight))
}

private fun showQuickButtonIconDialog(icon: QuickButtonIcon, fragmentManager: FragmentManager) = OnClickListener {
    QuickButtonIconDialog(icon.prefId).showNow(fragmentManager, null)
}

fun setupQuickButtonIcons(prefsRepo: DataRepository<QuickButtonPreferences>, fragmentManager: FragmentManager) =
    { binding: CustomizeHomeBinding ->
        prefsRepo.observe(updateQuickButtonIcons(binding))
        binding.quickButtonLeft.setOnClickListener(showQuickButtonIconDialog(QuickButtonIcon.IC_CALL, fragmentManager))
        binding.quickButtonCenter.setOnClickListener(showQuickButtonIconDialog(QuickButtonIcon.IC_COG, fragmentManager))
        binding.quickButtonRight.setOnClickListener(
            showQuickButtonIconDialog(QuickButtonIcon.IC_PHOTO_CAMERA, fragmentManager)
        )
    }

fun setupAddHomeAppButton(appsRepo: DataRepository<UnlauncherApps>): (CustomizeHomeBinding) -> Unit = { binding ->
    appsRepo.observe {
        if (getHomeApps(it).size > 5) {
            binding.addHomeApp.visibility = View.GONE
        } else {
            binding.addHomeApp.visibility = View.VISIBLE
        }
    }

    Navigation
        .createNavigateOnClickListener(R.id.customiseQuickButtonsFragment_to_customizeHomeAppsAddAppFragment)
        .also(binding.addHomeApp::setOnClickListener)
}

fun setupHomeAppsList(appsRepo: DataRepository<UnlauncherApps>, fragmentManager: FragmentManager) =
    { binding: CustomizeHomeBinding ->
        binding.customiseHomeAppsList.adapter = CustomizeHomeAppsListAdapter(appsRepo, fragmentManager)
    }
