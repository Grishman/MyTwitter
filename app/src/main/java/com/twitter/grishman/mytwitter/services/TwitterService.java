package com.twitter.grishman.mytwitter.services;

import com.twitter.grishman.mytwitter.model.FeedItem;
import com.twitter.grishman.mytwitter.model.User;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface TwitterService {

    Observable<List<FeedItem>> getFeedItems();

    Observable<Boolean> sendTweet(String tweetText);
}
