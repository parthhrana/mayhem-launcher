package com.parthhrana.mayhemlauncher.fragment

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import com.parthhrana.mayhemlauncher.R
import com.parthhrana.mayhemlauncher.bindings.setupCustomizeAppDrawerBackButton
import com.parthhrana.mayhemlauncher.bindings.setupSearchFieldOptionsButton
import com.parthhrana.mayhemlauncher.bindings.setupShowHeadingSwitch
import com.parthhrana.mayhemlauncher.bindings.setupVisibleAppsButton
import com.parthhrana.mayhemlauncher.databinding.CustomizeAppDrawerBinding
import com.parthhrana.mayhemlauncher.datasource.DataRepository
import com.parthhrana.mayhemlauncher.datastore.proto.CorePreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CustomizeAppDrawerFragment : Fragment() {
    @Inject
    lateinit var iActivity: ComponentActivity
    @Inject
    lateinit var iResources: Resources
    @Inject @WithFragmentLifecycle
    lateinit var corePreferencesRepo: DataRepository<CorePreferences>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.customize_app_drawer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CustomizeAppDrawerBinding
            .bind(view)
            .also(::setupVisibleAppsButton)
            .also(setupCustomizeAppDrawerBackButton(iActivity))
            .also(setupSearchFieldOptionsButton(corePreferencesRepo, iResources))
            .also(setupShowHeadingSwitch(corePreferencesRepo))
    }
}
