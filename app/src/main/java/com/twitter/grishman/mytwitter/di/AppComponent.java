package com.twitter.grishman.mytwitter.di;

import com.twitter.grishman.mytwitter.data.remote.AppRemoteDataStore;
import com.twitter.grishman.mytwitter.di.module.AppModule;
import com.twitter.grishman.mytwitter.di.module.DataModule;
import com.twitter.grishman.mytwitter.feed.FeedListActivity;
import com.twitter.grishman.mytwitter.feed.FeedPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component for the app
 */

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {
    void inject(FeedListActivity activity);

    void inject(FeedPresenter feedPresenter);

    void inject(AppRemoteDataStore appRemoteDataStore);
}
