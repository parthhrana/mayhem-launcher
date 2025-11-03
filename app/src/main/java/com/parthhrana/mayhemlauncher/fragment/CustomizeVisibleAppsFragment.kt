package com.parthhrana.mayhemlauncher.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.navigation.fragment.findNavController
import com.parthhrana.mayhemlauncher.R
import com.parthhrana.mayhemlauncher.bindings.setupVisibleAppsBackButton
import com.parthhrana.mayhemlauncher.bindings.setupVisibleAppsList
import com.parthhrana.mayhemlauncher.databinding.CustomizeAppDrawerVisibleAppsBinding
import com.parthhrana.mayhemlauncher.datasource.DataRepository
import com.parthhrana.mayhemlauncher.datastore.proto.UnlauncherApps
import com.parthhrana.mayhemlauncher.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CustomizeVisibleAppsFragment : BaseFragment() {
    @Inject
    lateinit var iActivity: ComponentActivity
    @Inject
    lateinit var unlauncherAppsRepo: DataRepository<UnlauncherApps>

    override fun getFragmentView(): ViewGroup =
        CustomizeAppDrawerVisibleAppsBinding.bind(requireView()).customizeAppDrawerVisibleApps

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.customize_app_drawer_visible_apps, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CustomizeAppDrawerVisibleAppsBinding
            .bind(view)
            .also(setupVisibleAppsBackButton(iActivity))
            .also(setupVisibleAppsList(unlauncherAppsRepo))
    }

    override fun onBack(): Boolean {
        findNavController().popBackStack()
        return true
    }
}
