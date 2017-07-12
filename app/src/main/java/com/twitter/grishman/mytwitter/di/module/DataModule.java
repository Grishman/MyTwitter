package com.twitter.grishman.mytwitter.di.module;

import android.app.Application;

import com.twitter.grishman.mytwitter.data.local.AppLocalDataStore;
import com.twitter.grishman.mytwitter.data.remote.AppRemoteDataStore;
import com.twitter.grishman.mytwitter.services.TwitterService;
import com.twitter.grishman.mytwitter.services.TwitterServiceImpl;
import com.twitter.grishman.mytwitter.utils.NetChecker;
import com.twitter.grishman.mytwitter.utils.NetworkChecker;
import com.twitter.sdk.android.core.TwitterCore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Data module
 */

@Module
public class DataModule {

    @Provides
    @Singleton
    TwitterService provideTwitterService() {
        return new TwitterServiceImpl(TwitterCore.getInstance().getSessionManager().getActiveSession());
    }

    @Provides
    @Singleton
    AppLocalDataStore porvidesAppLocalDataStore(Application context) {
        return new AppLocalDataStore(context);
    }

    @Provides
    @Singleton
    NetChecker porvideNetworkChecker(Application context) {
        return new NetworkChecker(context);
    }

    @Provides
    @Singleton
    AppRemoteDataStore providesRepository() {
        return new AppRemoteDataStore();
    }

}
