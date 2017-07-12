package com.twitter.grishman.mytwitter.feed.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.grishman.mytwitter.R;
import com.twitter.grishman.mytwitter.model.FeedItem;

import java.util.List;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private final Context context;
    private List<FeedItem> feedItems;

    public FeedAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tweet_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        FeedItem feedItem = feedItems.get(i);
        Picasso.with(context)
                .load(feedItem.getUser().getProfileImageUrl())
                .fit()
                .into(viewHolder.avatarImageView);
        viewHolder.nameView.setText(feedItem.getUser().getName());
        viewHolder.handleView.setText(String.format("@%s", feedItem.getUser().getScreenName()));
        viewHolder.timeView.setText(feedItem.getCreatedAt());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            viewHolder.textView.setText(Html.fromHtml(feedItem.getText(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            //noinspection deprecation
            viewHolder.textView.setText(Html.fromHtml(feedItem.getText()));
        }
    }

    @Override
    public int getItemCount() {
        return feedItems != null ? feedItems.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView avatarImageView;
        TextView nameView;
        TextView handleView;
        TextView timeView;
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            avatarImageView = (ImageView) itemView.findViewById(R.id.tweet_picture);
            nameView = (TextView) itemView.findViewById(R.id.name);
            handleView = (TextView) itemView.findViewById(R.id.handle);
            timeView = (TextView) itemView.findViewById(R.id.tweet_time);
            textView = (TextView) itemView.findViewById(R.id.tweet_text);
        }
    }
}
