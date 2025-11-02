package com.parthhrana.mayhemlauncher.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.parthhrana.mayhemlauncher.R
import com.parthhrana.mayhemlauncher.datasource.DataRepository
import com.parthhrana.mayhemlauncher.datasource.setClockType
import com.parthhrana.mayhemlauncher.datastore.proto.ClockType
import com.parthhrana.mayhemlauncher.datastore.proto.CorePreferences
import com.parthhrana.mayhemlauncher.fragment.WithFragmentLifecycle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ClockTypeDialog : DialogFragment() {
    @Inject @WithFragmentLifecycle
    lateinit var corePreferencesRepo: DataRepository<CorePreferences>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog
        .Builder(context)
        .setTitle(R.string.choose_clock_type_dialog_title)
        .setSingleChoiceItems(
            R.array.clock_type_array,
            corePreferencesRepo.get().clockType.number,
            this::onSelection
        )
        .create()

    private fun onSelection(dialogInterface: DialogInterface, i: Int) = dialogInterface
        .dismiss()
        .also { corePreferencesRepo.updateAsync(setClockType(ClockType.forNumber(i))) }
}
