package com.parthhrana.mayhemlauncher.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.parthhrana.mayhemlauncher.R
import com.parthhrana.mayhemlauncher.datasource.DataRepository
import com.parthhrana.mayhemlauncher.datasource.setSearchBarPosition
import com.parthhrana.mayhemlauncher.datastore.proto.CorePreferences
import com.parthhrana.mayhemlauncher.datastore.proto.SearchBarPosition
import com.parthhrana.mayhemlauncher.fragment.WithFragmentLifecycle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchBarPositionDialog : DialogFragment() {
    @Inject @WithFragmentLifecycle
    lateinit var corePreferencesRepo: DataRepository<CorePreferences>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog
        .Builder(context)
        .setTitle(R.string.choose_search_bar_position_dialog_title)
        .setSingleChoiceItems(
            R.array.search_bar_position_array,
            corePreferencesRepo.get().searchBarPosition.number,
            this::onSelection
        )
        .create()

    private fun onSelection(dialogInterface: DialogInterface, i: Int) = dialogInterface
        .dismiss()
        .also { corePreferencesRepo.updateAsync(setSearchBarPosition(SearchBarPosition.forNumber(i))) }
}
