package com.twitter.grishman.mytwitter.data;

import com.twitter.grishman.mytwitter.model.FeedItem;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;


/**
 * Created by Aditya on 23-Oct-16.
 */

public interface AppDataStore {
    Observable<List<FeedItem>> getFeed();
}
