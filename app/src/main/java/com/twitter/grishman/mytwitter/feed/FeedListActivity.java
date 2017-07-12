package com.twitter.grishman.mytwitter.feed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.twitter.grishman.mytwitter.R;
import com.twitter.grishman.mytwitter.TwitterApp;
import com.twitter.grishman.mytwitter.feed.adapters.FeedAdapter;
import com.twitter.grishman.mytwitter.model.FeedItem;
import com.twitter.grishman.mytwitter.utils.NetChecker;

import java.util.List;

import javax.inject.Inject;

public class FeedListActivity extends MvpAppCompatActivity implements FeedScreenContract.FeedView {

    @InjectPresenter
    FeedPresenter presenter;

    @Inject
    NetChecker networkChecker;
    private FeedAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView tweetList;
    private TextView noTweetsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterApp.getAppComponent().inject(this);
        setContentView(R.layout.activity_feed_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.post_tweet);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewTweetDialog();
            }
        });
        presenter.fetchFeed();
    }

    private void initViews() {
        tweetList = (RecyclerView) findViewById(R.id.tweets);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        noTweetsView = (TextView) findViewById(R.id.error_state);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tweetList.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(tweetList.getContext(),
                linearLayoutManager.getOrientation());
        tweetList.addItemDecoration(dividerItemDecoration);
        adapter = new FeedAdapter(this);
        tweetList.setAdapter(adapter);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.fetchFeed();
            }
        });
    }

    private void showNewTweetDialog() {
        final EditText tweetText = new EditText(this);
        tweetText.setId(R.id.tweet_text);
        tweetText.setSingleLine();
        tweetText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        tweetText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(140)});
        tweetText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.label_what_is_happening);
        builder.setPositiveButton(R.string.action_tweet, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (networkChecker.isNetworkAvailable()) {
                    presenter.tweet(tweetText.getText().toString());
                } else {
                    showError(R.string.error_no_internet);
                }
            }
        });

        final AlertDialog alert = builder.create();
        alert.setView(tweetText, 64, 0, 64, 0);
        alert.show();

        tweetText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    alert.getButton(DialogInterface.BUTTON_POSITIVE).callOnClick();
                    return true;
                }
                return false;
            }
        });
    }

    //View methods
    @Override
    public void showFeedList(List<FeedItem> feedItems) {
        swipeRefresh.setVisibility(View.VISIBLE);
        tweetList.setVisibility(View.VISIBLE);
        noTweetsView.setVisibility(View.GONE);
        adapter.setItems(feedItems);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showNoTweetsError() {
        swipeRefresh.setVisibility(View.GONE);
        tweetList.setVisibility(View.GONE);
        noTweetsView.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showError(@StringRes int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show();
        swipeRefresh.setRefreshing(false);
    }
}
