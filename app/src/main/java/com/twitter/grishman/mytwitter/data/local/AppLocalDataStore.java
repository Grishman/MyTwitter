package com.twitter.grishman.mytwitter.data.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.twitter.grishman.mytwitter.data.AppDataStore;
import com.twitter.grishman.mytwitter.db.TwitterDatabase;
import com.twitter.grishman.mytwitter.db.models.FeedTable;
import com.twitter.grishman.mytwitter.model.FeedItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


/**
 * Local data store
 */

public class AppLocalDataStore implements AppDataStore {

    @Inject
    public AppLocalDataStore(@NonNull Context context) {

    }

    @Override
    public Observable<List<FeedItem>> getFeed() {
        final List<FeedItem> feedItems = new ArrayList<>();
        List<FeedTable> feedTableList = new Select().from(FeedTable.class).queryList();
        for (FeedTable table : feedTableList) {
            feedItems.add(new FeedItem(table));
        }
        return Observable.create(new ObservableOnSubscribe<List<FeedItem>>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<List<FeedItem>> emitter) throws Exception {
                emitter.onNext(feedItems);
            }
        });
    }

    public void saveFeedList(List<FeedItem> feedItems) {
        List<FeedTable> saveToDB = new ArrayList<>();
        FeedTable model;
        //FIXME use some kind of a mapper
        for (FeedItem item : feedItems) {
            model = new FeedTable(item);
            saveToDB.add(model);
        }
        ProcessModelTransaction<FeedTable> processModelTransaction =
                new ProcessModelTransaction.Builder<>(new ProcessModelTransaction.ProcessModel<FeedTable>() {
                    @Override
                    public void processModel(FeedTable feedTable, DatabaseWrapper wrapper) {
                        Log.d("DB","save: "+feedTable.toString());
                        feedTable.save();
                    }

//                    @Override
//                    public void processModel(FeedTable model) {
//                        // call some operation on model here
//                        model.save();
////                        model.insert(); // or
////                        model.delete(); // or
//                    }
                }).processListener(new ProcessModelTransaction.OnModelProcessListener<FeedTable>() {
                    @Override
                    public void onModelProcessed(long current, long total, FeedTable modifiedModel) {
                        //modelProcessedCount.incrementAndGet();
                    }
                }).addAll(saveToDB).build();
        DatabaseDefinition database= FlowManager.getDatabase(TwitterDatabase.class);
        Transaction transaction = database.beginTransactionAsync(processModelTransaction).build();
        transaction.execute();
    }

    public void saveTweet(String tweet) {
        //TODO save tweet to DB, if we have no internet connection
    }
}
