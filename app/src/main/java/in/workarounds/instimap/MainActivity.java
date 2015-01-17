package in.workarounds.instimap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.util.AsyncExecutor;
import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.fragments.NavigationDrawerFragment;
import in.workarounds.instimap.models.Locations;
import in.workarounds.instimap.models.Marker;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialSetUp();
    }

    private void initialSetUp() {
        setUpActionBar();
        setUpDrawer();
        in.workarounds.instimap.helpers.SyncHandler.setUp(this);
        setUpEventSubscription();
        initLocations(this);
        startSearchFragment();
    }

    private void startSearchFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment mapFragment = new in.workarounds.instimap.fragments.MapFragment();
        fragmentManager.beginTransaction().add(R.id.top_fragment, mapFragment).commit();
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
                EventBus.getDefault().postSticky(new StickyEvents.LocationLoadEvent(data));
            }
        });
    }

    public void onEvent(StickyEvents.CurrentMarkerEvent event) {
        Log.d("MainActivity", "marker selected: " + event.marker.getName());
    }
}
