package in.workarounds.instimap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
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
        for(int i = 0; i < Marker.NUMBER_OF_GROUPS+1; i++) {
            expandableListView.collapseGroup(i);
        }
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
}
