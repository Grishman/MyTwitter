package com.twitter.grishman.mytwitter.data;


import com.twitter.grishman.mytwitter.data.local.AppLocalDataStore;
import com.twitter.grishman.mytwitter.data.remote.AppRemoteDataStore;
import com.twitter.grishman.mytwitter.model.FeedItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * App Repo
 */

public class AppRepository implements AppDataStore {

    private AppLocalDataStore appLocalDataStore;
    private AppRemoteDataStore appRemoteDataStore;


    @Inject
    public AppRepository(AppLocalDataStore appLocalDataStore, AppRemoteDataStore appRemoteDataStore) {
        this.appLocalDataStore = appLocalDataStore;
        this.appRemoteDataStore = appRemoteDataStore;
    }

    @Override
    public Observable<List<FeedItem>> getFeed() {
//        Return feed list
        return Observable.merge(appRemoteDataStore.getFeed(), appLocalDataStore.getFeed());
//                .filter(new Predicate<List<FeedItem>>() {
//                    @Override
//                    public boolean test(@NonNull List<FeedItem> feedItems) throws Exception {
//                        return (feedItems != null && feedItems.size() > 0);
//                    }
//                }).firstElement();
        //TODO this shit should be removed
    }

    public Observable<Boolean> sendTweet(String tweetText) {
        return appRemoteDataStore.sendTweet(tweetText);
    }
}
