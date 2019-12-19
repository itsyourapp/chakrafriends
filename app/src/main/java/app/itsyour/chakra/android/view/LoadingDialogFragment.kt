package app.itsyour.chakra.android.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.itsyour.chakra.android.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoadingDialogFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = LoadingDialogFragment()
            .apply { isCancelable = false }
        const val TAG = "loadingDialogFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.bottom_sheet, container, false)
}