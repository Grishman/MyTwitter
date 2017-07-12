package com.twitter.grishman.mytwitter.services;

import android.util.Log;

import com.twitter.grishman.mytwitter.model.FeedItem;
import com.twitter.grishman.mytwitter.utils.TimelineConverter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

@Singleton
public class TwitterServiceImpl extends TwitterApiClient implements TwitterService {

    private static final String TAG = TwitterServiceImpl.class.getSimpleName();

    private TwitterSession twitterSession;

    public TwitterServiceImpl(TwitterSession session) {
        super(session);
        twitterSession = session;
    }

    @Override
    public Observable<List<FeedItem>> getFeedItems() {
        return Observable.create(new ObservableOnSubscribe<List<FeedItem>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<FeedItem>> emitter) throws Exception {
                Callback<List<Tweet>> callback = new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        Log.i(TAG, "Got the tweets, buddy!");
                        emitter.onNext(TimelineConverter.fromTweets(result.data, System.currentTimeMillis()));
                    }

                    @Override
                    public void failure(TwitterException e) {
                        Log.e(TAG, e.getMessage(), e);
                        emitter.onError(e);
                    }
                };

                getStatusesService().homeTimeline(null, null, null, null, null, null, null).enqueue(callback);
            }
        });
    }

    public Observable<Boolean> sendTweet(final String tweetText) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    Callback<Tweet> callback = new Callback<Tweet>() {
                        @Override
                        public void success(Result<Tweet> result) {
                            Log.i(TAG, "Tweet tweeted");
                            emitter.onNext(true);
                        }

                        @Override
                        public void failure(TwitterException e) {
                            Log.e(TAG, e.getMessage(), e);
                            emitter.onError(e);
                        }
                    };
                    getStatusesService().update(tweetText, null, null, null, null, null, null, null, null).enqueue(callback);
                } catch (Exception e1) {
                }
            }
        });
    }

}
