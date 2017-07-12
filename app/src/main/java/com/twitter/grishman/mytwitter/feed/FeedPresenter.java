package com.twitter.grishman.mytwitter.feed;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.twitter.grishman.mytwitter.R;
import com.twitter.grishman.mytwitter.TwitterApp;
import com.twitter.grishman.mytwitter.data.AppRepository;
import com.twitter.grishman.mytwitter.model.FeedItem;
import com.twitter.grishman.mytwitter.services.TwitterService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by grishman on 10/07/17.
 */

@InjectViewState
public class FeedPresenter extends MvpPresenter<FeedScreenContract.FeedView> implements FeedScreenContract.FeedPresenter {

    @Inject
    AppRepository repository;

    @Inject
    TwitterService service;
    private List<FeedItem> timelineItems;

    public FeedPresenter() {
        TwitterApp.getAppComponent().inject(this);
    }

    @Override
    public void fetchFeed() {
        repository.getFeed().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<FeedItem>>() {
                    @Override
                    public void onNext(@NonNull List<FeedItem> feedItems) {
                        timelineItems = feedItems;
                        if (timelineItems.size() > 0) {
                            getViewState().showFeedList(timelineItems);
                        } else {
                            getViewState().showNoTweetsError();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getViewState().showError(R.string.error_fetch_failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void tweet(String tweetText) {
//        if (timelineItems != null) {
//            timelineItems.add(0, new FeedItem("0s", tweetText, service.getCurrentUser()));
//            getViewState().showFeedList(timelineItems);
//        }
        repository.sendTweet(tweetText).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Boolean>() {

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        //just update feed
                        fetchFeed();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getViewState().showError(R.string.error_post_failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
