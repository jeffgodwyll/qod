/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jeffgodwyll.android.qod.app.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Defines table and column names for the weather database.
 */
public class QuotesContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.jeffgodwyll.android.qod.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
//    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.jeffgodwyll.android.qod.app/weather/ is a valid path for
    // looking at weather data. content://com.jeffgodwyll.android.qod.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_QUOTES= "quotes";


    // Crude hack. Fix later. UriMatcher doesn't seem to function properly. Keeps returning root path
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + PATH_QUOTES);

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.setToNow();
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }


    /* Inner class that defines the table contents of the quotes table */
    public static final class QuotesEntry implements BaseColumns {

//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_QUOTES).build();
    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_QUOTES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_QUOTES;

        public static final String TABLE_NAME = "quotes";

        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_QUOTES_ID = "_id";

        // Short description and long description of the weather, as provided by API.
        // e.g "clear" vs "sky is clear".
//        public static final String COLUMN_SHORT_DESC = "short_desc";
        // the quote itself
        public static final String COLUMN_QUOTE_CONTENT = "quote";

        public static final String COLUMN_AUTHOR = "author";
       // public static final String COLUMN_QUOTE_LENGTH = "length";
        public static final String COLUMN_CATEGORY = "category";

        public static Uri buildQuotesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
