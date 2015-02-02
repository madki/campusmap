package in.workarounds.instimap.fragments.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;
import com.diegocarloslima.fgelv.lib.WrapperExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.EventFragment;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.models.Marker;

public class IndexFragment extends EventFragment implements ExpandableListView.OnChildClickListener {
    List<Marker> markers;
    IndexAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        markers = getData();
        adapter = new IndexAdapter(getActivity(), markers);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_index, container, false);

        setExpandableListView(rootView);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem index = menu.findItem(R.id.index_icon);
        index.setVisible(false);
        MenuItem search = menu.findItem(R.id.search_icon);
        search.setVisible(false);
        MenuItem settings = menu.findItem(R.id.settings_icon);
        settings.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected boolean shouldRegisterSticky() {
        return true;
    }

    public void onEventMainThread(StickyEvents.LocationLoadEvent event) {
        List<Marker> newMarkerList = getSortedMarkers(event.locations.data);
        if(adapter!=null) {
            adapter.setMarkers(newMarkerList);
        } else {
        }
    }

    public void setExpandableListView(View rootView) {
        FloatingGroupExpandableListView expandableListView =
                (FloatingGroupExpandableListView) rootView.findViewById(R.id.list_index);
        final WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(adapter);
        expandableListView.setAdapter(wrapperAdapter);
        expandableListView.setDividerHeight(0);
        expandableListView.setOnChildClickListener(this);
    }

    private List<Marker> getData() {
        StickyEvents.LocationLoadEvent event = EventBus.getDefault().getStickyEvent(StickyEvents.LocationLoadEvent.class);
        if(event!=null) {
            return getSortedMarkers(event.locations.data);
        } else {
            return new ArrayList<>();
        }
    }

    private List<Marker> getSortedMarkers(HashMap<String, Marker> markersHashMap) {
        List<Marker> markers = new ArrayList<>(markersHashMap.values());
        return markers;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Marker marker = adapter.getChild(groupPosition, childPosition);
        EventBus.getDefault().postSticky(new StickyEvents.CurrentMarkerEvent(marker));
        return true;
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
}
