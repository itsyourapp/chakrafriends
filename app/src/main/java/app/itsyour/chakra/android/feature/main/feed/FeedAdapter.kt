package app.itsyour.chakra.android.feature.main.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.itsyour.chakra.android.R
import kotlinx.android.synthetic.main.card_feed_event_convo.view.*
import kotlinx.android.synthetic.main.card_feed_event_upcoming.view.*
import javax.inject.Inject

sealed class FeedItem (val adapterType: Int) {

    data class UpcomingEventFeedItem(
        val date: String,
        val description: String) : FeedItem(0)

    data class FriendConvoFeedItem(
        val message: String) : FeedItem(1)
}

class FeedAdapter
    @Inject constructor()
        : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: List<FeedItem> = listOf(
        FeedItem.UpcomingEventFeedItem("Oct 26th", "You have an upcoming massage with Ericka."),
        FeedItem.FriendConvoFeedItem("You have a new message from Kate."))

    override fun getItemCount() = items.count()

    override fun getItemViewType(position: Int): Int = items[position].adapterType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> UpcomingEventViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_feed_event_upcoming, parent, false))
            else -> FriendConvoViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_feed_event_convo, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as FeedViewHolder).bind(items[position])

    interface FeedViewHolder {
        fun bind(item: FeedItem)
    }

    inner class UpcomingEventViewHolder(private val view: View)
        : FeedViewHolder, RecyclerView.ViewHolder(view) {

        override fun bind(item: FeedItem) {
            (item as FeedItem.UpcomingEventFeedItem).apply {
                view.card_feed_event_upcoming_date.text = item.date
                view.card_feed_event_upcoming_description.text = item.description
            }
        }
    }

    inner class FriendConvoViewHolder(private val view: View)
        : FeedViewHolder, RecyclerView.ViewHolder(view) {

        override fun bind (item: FeedItem) {
            (item as FeedItem.FriendConvoFeedItem).apply {
                view.card_feed_event_convo_update.text = item.message
            }
        }
    }
}