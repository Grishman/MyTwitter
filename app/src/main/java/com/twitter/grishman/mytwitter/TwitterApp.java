package com.twitter.grishman.mytwitter;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.twitter.grishman.mytwitter.di.AppComponent;
import com.twitter.grishman.mytwitter.di.DaggerAppComponent;
import com.twitter.grishman.mytwitter.di.module.AppModule;
import com.twitter.grishman.mytwitter.di.module.DataModule;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Application class
 */

public class TwitterApp extends Application {

    //No encryption here ;)
    @SuppressWarnings("SpellCheckingInspection")
    public static final String TWITTER_KEY = "yJspIgaP0ofJTJNAZmwIzwu12";
    @SuppressWarnings("SpellCheckingInspection")
    public static final String TWITTER_SECRET = "fhNsNp6uqR2RCFNYoNHg4GxnydLWr7rvGUSQkxcSZh2y2ghK2S";

    @Override
    public void onCreate() {
        super.onCreate();
        //Init Dagger
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule())
                .build();
        //Init twitter config
        Twitter.initialize(new TwitterConfig.Builder(this).twitterAuthConfig(new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET)).build());
        //Init DBFlow database
        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    private static AppComponent mAppComponent;

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}
