package com.twitter.grishman.mytwitter.data.remote;

import com.twitter.grishman.mytwitter.TwitterApp;
import com.twitter.grishman.mytwitter.data.AppDataStore;
import com.twitter.grishman.mytwitter.data.local.AppLocalDataStore;
import com.twitter.grishman.mytwitter.model.FeedItem;
import com.twitter.grishman.mytwitter.services.TwitterService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Remote data store
 */

public class AppRemoteDataStore implements AppDataStore {

    @Inject
    TwitterService service;

    @Inject
    AppLocalDataStore appLocalDataStore;

    public AppRemoteDataStore() {
        TwitterApp.getAppComponent().inject(this);
    }

    @Override
    public Observable<List<FeedItem>> getFeed() {
        return service.getFeedItems().doOnNext(new Consumer<List<FeedItem>>() {
            @Override
            public void accept(@NonNull List<FeedItem> feedItems) throws Exception {
                //Save data from API to DB!
                appLocalDataStore.saveFeedList(feedItems);
            }
        });
    }

    public Observable<Boolean> sendTweet(String text) {
        return service.sendTweet(text);
    }
}
