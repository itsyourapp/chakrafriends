package app.itsyour.chakra.android.feature.main.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.itsyour.chakra.android.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : DaggerFragment() {

    private val adapter = FeedAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        val manager = LinearLayoutManager(activity)
        feed_items.adapter = adapter
        feed_items.layoutManager = manager
        feed_items.addItemDecoration(
            DividerItemDecoration(feed_items.context, manager.orientation))
    }
}