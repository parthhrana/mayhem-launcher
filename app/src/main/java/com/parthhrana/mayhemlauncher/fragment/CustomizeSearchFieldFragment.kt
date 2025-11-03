package com.parthhrana.mayhemlauncher.fragment

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.parthhrana.mayhemlauncher.R
import com.parthhrana.mayhemlauncher.bindings.setupBackButton
import com.parthhrana.mayhemlauncher.bindings.setupKeyboardSwitch
import com.parthhrana.mayhemlauncher.bindings.setupSearchAllAppsSwitch
import com.parthhrana.mayhemlauncher.bindings.setupSearchBarPositionOption
import com.parthhrana.mayhemlauncher.bindings.setupShowSearchBarSwitch
import com.parthhrana.mayhemlauncher.databinding.CustomizeAppDrawerSearchFieldOptionsBinding
import com.parthhrana.mayhemlauncher.datasource.DataRepository
import com.parthhrana.mayhemlauncher.datastore.proto.CorePreferences
import com.parthhrana.mayhemlauncher.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CustomizeSearchFieldFragment : BaseFragment() {
    @Inject
    lateinit var iActivity: ComponentActivity
    @Inject
    lateinit var iResources: Resources
    @Inject
    lateinit var iFragmentManager: FragmentManager
    @Inject @WithFragmentLifecycle
    lateinit var corePrefsRepo: DataRepository<CorePreferences>

    override fun getFragmentView(): ViewGroup =
        CustomizeAppDrawerSearchFieldOptionsBinding.bind(requireView()).customizeAppDrawerSearchFieldOptions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.customize_app_drawer_search_field_options, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CustomizeAppDrawerSearchFieldOptionsBinding
            .bind(view)
            .also(setupBackButton(iActivity))
            .also(setupShowSearchBarSwitch(corePrefsRepo))
            .also(setupSearchBarPositionOption(corePrefsRepo, iFragmentManager, iResources))
            .also(setupKeyboardSwitch(corePrefsRepo))
            .also(setupSearchAllAppsSwitch(corePrefsRepo))
    }

    override fun onBack(): Boolean {
        findNavController().popBackStack()
        return true
    }
}
