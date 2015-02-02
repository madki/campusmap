package in.workarounds.instimap.sync;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.models.Board;
import in.workarounds.instimap.models.Corner;
import in.workarounds.instimap.models.CornerRelation;
import in.workarounds.instimap.models.Filter;
import in.workarounds.instimap.models.Notice;
import in.workarounds.instimap.models.Venue;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    @SuppressLint("NewApi")
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }

    /*
         * Specify the code you want to run in the sync adapter. The entire
         * sync adapter runs in a background thread, so you don't have to set
         * up your own background processing.
         */
    @Override
    public void onPerformSync(
            Account account,
            Bundle extras,
            String authority,
            ContentProviderClient provider,
            SyncResult syncResult) {
    /*
     * Put the data transfer code here.
     */
        Log.d("SyncAdapter", "Yo sync running");
        EventBus eventBus = EventBus.getDefault();
        eventBus.postSticky(new StickyEvents.SyncStatusEvent(StickyEvents.SyncStatusEvent.STARTED));
        syncData();
        eventBus.postSticky(new StickyEvents.SyncStatusEvent(StickyEvents.SyncStatusEvent.FINISHED));
    }

    private void syncData() {
        Type venueListType = new TypeToken<ArrayList<Venue>>() {}.getType();
        SyncTableHelper.syncTable(Venue.class, venueListType);

        Type cornerListType = new TypeToken<ArrayList<Corner>>() {}.getType();
        SyncTableHelper.syncTable(Corner.class, cornerListType);

        Type boardListType = new TypeToken<ArrayList<Board>>() {}.getType();
        SyncTableHelper.syncTable(Board.class, boardListType);

        Type cornerRelationListType = new TypeToken<ArrayList<CornerRelation>>() {}.getType();
        SyncTableHelper.syncTable(CornerRelation.class, cornerRelationListType);

        Type noticeListType = new TypeToken<ArrayList<Notice>>() {}.getType();
        SyncTableHelper.syncTable(Notice.class, noticeListType);

        Type filterListType = new TypeToken<ArrayList<Filter>>() {}.getType();
        SyncTableHelper.syncTable(Filter.class, filterListType);

    }

}
