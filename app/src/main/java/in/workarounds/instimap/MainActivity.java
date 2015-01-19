package in.workarounds.instimap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.util.AsyncExecutor;
import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.fragments.NavigationDrawerFragment;
import in.workarounds.instimap.helpers.BottomFragmentManager;
import in.workarounds.instimap.helpers.SyncHelper;
import in.workarounds.instimap.helpers.TopFragmentManager;
import in.workarounds.instimap.models.Locations;
import in.workarounds.instimap.models.Marker;


public class MainActivity extends ActionBarActivity {
    private TopFragmentManager topFragmentManager;
    private BottomFragmentManager bottomFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialSetUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_icon:
                topFragmentManager.openSearchFragment();
                return true;
            case R.id.index_icon:
                topFragmentManager.openIndexFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if(!topFragmentManager.handleBackPress()) {
            if(!bottomFragmentManager.handleBackPress()) {
                super.onBackPressed();
            }
        }
    }

    private void initialSetUp() {
        setUpActionBar();
        setUpDrawer();
        setUpFragmentManagers();
        SyncHelper.setUp(this);
        setUpEventSubscription();
        initLocations(this);
        startFragments();
    }

    private void startFragments() {
        bottomFragmentManager.openMapFragment();
    }

    private void setUpActionBar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void setUpDrawer() {
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    private void setUpFragmentManagers() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        topFragmentManager = new TopFragmentManager(this, fragmentManager);
        bottomFragmentManager = new BottomFragmentManager(this, fragmentManager);
    }

    private void setUpEventSubscription() {
        EventBus eventBus = EventBus.getDefault();
        eventBus.registerSticky(this);
    }

    private void initLocations(final Context context) {
        AsyncExecutor.create().execute(new AsyncExecutor.RunnableEx() {
            @Override
            public void run() throws Exception {
                Locations locations = Locations.getInstance(context);
                HashMap<String, Marker> data = locations.data;
                EventBus.getDefault().postSticky(new StickyEvents.LocationLoadEvent(locations));
            }
        });
    }

    public void onEvent(StickyEvents.CurrentMarkerEvent event) {
        topFragmentManager.closeTopFragments();
    }
}
