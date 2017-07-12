package com.twitter.grishman.mytwitter.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by grishman on 12/07/17.
 */
@Database(name = TwitterDatabase.NAME, version = TwitterDatabase.VERSION)
public class TwitterDatabase {
    public static final String NAME = "TwatBase";
    public static final int VERSION = 1;
}
