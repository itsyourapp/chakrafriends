package app.itsyour.chakra.android.feature.main.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.itsyour.chakra.android.R
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_feed.*
import javax.inject.Inject

class FeedFragment : FeedContract, DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<FeedViewModel> { factory }

    private val adapter = FeedAdapter()

    private val subscriptions = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        subscriptions += viewModel.uiModels.subscribe(::onModel)
        viewModel.onAction(FeedContract.Action.GetFeed)
    }

    private fun setupAdapter() {
        val manager = LinearLayoutManager(activity)
        feed_items.adapter = adapter
        feed_items.layoutManager = manager
        feed_items.addItemDecoration(
            DividerItemDecoration(feed_items.context, manager.orientation))
    }

    private fun onModel(uiModel: FeedContract.UiModel) {
        when (uiModel) {
            is FeedContract.UiModel.Feed -> showItems(uiModel.items)
            is FeedContract.UiModel.Error -> showError(uiModel.errorMessage)
        }
    }

    private fun showItems(items: List<FeedContract.FeedItem>) {
        adapter.items = items
        feed_items_empty.visibility = View.GONE
        feed_items.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        feed_items_empty.visibility = View.VISIBLE
        feed_items.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        subscriptions.clear()
    }
}