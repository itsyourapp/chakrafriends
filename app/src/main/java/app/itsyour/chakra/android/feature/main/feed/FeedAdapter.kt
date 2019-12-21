package app.itsyour.chakra.android.feature.main.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.itsyour.chakra.android.R
import kotlinx.android.synthetic.main.card_feed_event_convo.view.*
import kotlinx.android.synthetic.main.card_feed_event_upcoming.view.*
import javax.inject.Inject

class FeedAdapter
    @Inject constructor()
        : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<FeedContract.FeedItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.count()

    override fun getItemViewType(position: Int): Int = items[position].id

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
        fun bind(item: FeedContract.FeedItem)
    }

    inner class UpcomingEventViewHolder(private val view: View)
        : FeedViewHolder, RecyclerView.ViewHolder(view) {

        override fun bind(item: FeedContract.FeedItem) {
            (item as FeedContract.FeedItem.UpcomingEvent).apply {
                view.card_feed_event_upcoming_date.text = item.date
                view.card_feed_event_upcoming_description.text = item.description
            }
        }
    }

    inner class FriendConvoViewHolder(private val view: View)
        : FeedViewHolder, RecyclerView.ViewHolder(view) {

        override fun bind (item: FeedContract.FeedItem) {
            (item as FeedContract.FeedItem.FriendConvo).apply {
                view.card_feed_event_convo_update.text = item.message
            }
        }
    }
}