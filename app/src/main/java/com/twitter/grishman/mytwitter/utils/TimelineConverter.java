package com.twitter.grishman.mytwitter.utils;

import com.twitter.grishman.mytwitter.model.FeedItem;
import com.twitter.grishman.mytwitter.model.User;
import com.twitter.sdk.android.core.models.Tweet;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public final class TimelineConverter {

    private TimelineConverter() {
    }

    public static List<FeedItem> fromTweets(List<Tweet> tweets, long now) {
        List<FeedItem> timelineItems = new ArrayList<>();
        for (Tweet t : tweets) {
            User u = new User(t.user.name, t.user.screenName, t.user.profileImageUrl);
            timelineItems.add(new FeedItem("", t.text, u));
        }
        return timelineItems;
    }

    private static String dateToAge(String createdAt) {
        if (createdAt == null) {
            return "";
        }
        //TODO format properly
        return DateFormat.getInstance().format(createdAt);
    }
}
