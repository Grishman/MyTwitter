package com.twitter.grishman.mytwitter.feed;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.twitter.grishman.mytwitter.model.FeedItem;

import java.util.List;

/**
 * Holds interface for mvp in the Feed screen
 */

public interface FeedScreenContract {
    //Views
    @StateStrategyType(AddToEndSingleStrategy.class)
    interface FeedView extends MvpView {
        void showFeedList(List<FeedItem> feedItems);

        void showNoTweetsError();

        void showError(@StringRes int messageResId);
    }

    //Presenters
    interface FeedPresenter {
        void fetchFeed();

        void tweet(String tweetText);
    }
}
