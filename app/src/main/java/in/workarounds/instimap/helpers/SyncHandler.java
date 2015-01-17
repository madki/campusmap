package in.workarounds.instimap.helpers;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

public class SyncHandler {

    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 24*60L;
    public static final long SYNC_INTERVAL =
            SYNC_INTERVAL_IN_MINUTES *
                    SECONDS_PER_MINUTE;

    public static final String AUTHORITY = "com.mrane.provider";
    public static final String ACCOUNT = "dummyaccount";
    private static Account mAccount = null;

    private SyncHandler(Context context) {

    }

    public static void setUp(Context context) {
        mAccount = getmAccount(context);
        setPeriodicSync(context);
        refreshData(context);
    }

    private static Account getmAccount(Context context) {
        if(mAccount==null) {
            mAccount = createSyncAccount(context);
        }
        return mAccount;
    }

    private static Account createSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, "com.mrane");
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        Context.ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        accountManager.addAccountExplicitly(newAccount, null, null);
        return newAccount;
    }

    public static void refreshData(Context context){
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getmAccount(context), AUTHORITY, bundle);
    }

    private static void setPeriodicSync(Context context){
        ContentResolver resolver = context.getContentResolver();
        Account account = getmAccount(context);
        resolver.setIsSyncable(account, AUTHORITY, 1);
        resolver.setSyncAutomatically(account, AUTHORITY, true);
        resolver.addPeriodicSync(account, AUTHORITY, Bundle.EMPTY, SYNC_INTERVAL);
    }

}
