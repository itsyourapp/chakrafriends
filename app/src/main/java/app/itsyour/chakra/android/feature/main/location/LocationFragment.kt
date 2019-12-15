package app.itsyour.chakra.android.feature.main.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.itsyour.chakra.android.R
import dagger.android.support.DaggerFragment

class LocationFragment : DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }
}