package com.parthhrana.mayhemlauncher.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.parthhrana.mayhemlauncher.R
import com.parthhrana.mayhemlauncher.datasource.DataRepository
import com.parthhrana.mayhemlauncher.datasource.setTimeFormat
import com.parthhrana.mayhemlauncher.datastore.proto.CorePreferences
import com.parthhrana.mayhemlauncher.datastore.proto.TimeFormat
import com.parthhrana.mayhemlauncher.fragment.WithFragmentLifecycle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TimeFormatDialog : DialogFragment() {
    @Inject @WithFragmentLifecycle
    lateinit var corePreferencesRepo: DataRepository<CorePreferences>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog
        .Builder(context)
        .setTitle(R.string.choose_time_format_dialog_title)
        .setSingleChoiceItems(
            R.array.time_format_array,
            corePreferencesRepo.get().timeFormat.number,
            this::onSelection
        )
        .create()

    private fun onSelection(dialogInterface: DialogInterface, i: Int) = dialogInterface
        .dismiss()
        .also { corePreferencesRepo.updateAsync(setTimeFormat(TimeFormat.forNumber(i))) }
}
