package com.twitter.grishman.mytwitter.db.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.twitter.grishman.mytwitter.db.TwitterDatabase;
import com.twitter.grishman.mytwitter.model.FeedItem;

/**
 * Created by grishman on 12/07/17.
 */
@Table(database = TwitterDatabase.class)
public class FeedTable extends BaseModel {

    public FeedTable(FeedItem item) {
        this.createdAt = item.getCreatedAt();
        this.tweetText = item.getText();
        this.userName = item.getUser().getName();
        this.userDisplayName = item.getUser().getScreenName();
        this.userProfileImageUrl = item.getUser().getProfileImageUrl();
    }

    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String tweetText;

    @Column
    String createdAt;

    //Use fields from User here as fields, to make it easy )
    @Column
    String userName;

    @Column
    String userDisplayName;

    @Column
    String userProfileImageUrl;

    public FeedTable() {

    }

    public String getTweetText() {
        return tweetText;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public String getUserProfileImageUrl() {
        return userProfileImageUrl;
    }

    @Override
    public String toString() {
        return "FeedTable{" +
                "id=" + id +
                ", tweetText='" + tweetText + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", userName='" + userName + '\'' +
                ", userDisplayName='" + userDisplayName + '\'' +
                ", userProfileImageUrl='" + userProfileImageUrl + '\'' +
                '}';
    }
}
