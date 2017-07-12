package com.twitter.grishman.mytwitter.model;


import com.twitter.grishman.mytwitter.db.models.FeedTable;

/**
 * Created by grishman on 10/07/17.
 */

public class FeedItem {
    private final String createdAt;
    private final String text;
    private final User user;

    public FeedItem(String createdAt, String text, User user) {
        this.createdAt = createdAt;
        this.text = text;
        this.user = user;
    }

    public FeedItem(FeedTable fromDB) {
        this.createdAt = fromDB.getCreatedAt();
        this.text = fromDB.getTweetText();
        User userDb = new User(fromDB.getUserName(), fromDB.getUserDisplayName(), fromDB.getUserProfileImageUrl());
        this.user = userDb;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getText() {
        return text;
    }
}
