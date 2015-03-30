package com.jeffgodwyll.android.qod.app.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class QuotesSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static QuotesSyncAdapter sQuotesSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("QuotesSyncService", "onCreate - QuotesSyncService");
        synchronized (sSyncAdapterLock) {
            if (sQuotesSyncAdapter == null) {
                sQuotesSyncAdapter = new QuotesSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sQuotesSyncAdapter.getSyncAdapterBinder();
    }
}