package com.parthhrana.mayhemlauncher.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.parthhrana.mayhemlauncher.R
import com.parthhrana.mayhemlauncher.bindings.setupAddHomeAppButton
import com.parthhrana.mayhemlauncher.bindings.setupCustomizeQuickButtonsBackButton
import com.parthhrana.mayhemlauncher.bindings.setupHomeAppsList
import com.parthhrana.mayhemlauncher.bindings.setupQuickButtonIcons
import com.parthhrana.mayhemlauncher.databinding.CustomizeHomeBinding
import com.parthhrana.mayhemlauncher.datasource.DataRepository
import com.parthhrana.mayhemlauncher.datastore.proto.QuickButtonPreferences
import com.parthhrana.mayhemlauncher.datastore.proto.UnlauncherApps
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CustomizeHomeFragment : Fragment() {
    @Inject
    lateinit var iActivity: ComponentActivity
    @Inject
    lateinit var iFragmentManager: FragmentManager
    @Inject
    lateinit var quickButtonPreferencesRepo: DataRepository<QuickButtonPreferences>
    @Inject
    lateinit var appsRepo: DataRepository<UnlauncherApps>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.customize_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CustomizeHomeBinding
            .bind(view)
            .also(setupCustomizeQuickButtonsBackButton(iActivity))
            .also(setupQuickButtonIcons(quickButtonPreferencesRepo, iFragmentManager))
            .also(setupAddHomeAppButton(appsRepo))
            .also(setupHomeAppsList(appsRepo, iFragmentManager))
    }
}
