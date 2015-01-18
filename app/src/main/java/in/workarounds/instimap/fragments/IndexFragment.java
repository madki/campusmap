package in.workarounds.instimap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.EventFragment;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.models.Marker;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;

public class IndexFragment extends EventFragment implements AdapterView.OnItemClickListener {
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
        final ExpandableStickyListHeadersListView expandableStickyList = (ExpandableStickyListHeadersListView) rootView.findViewById(R.id.list_index);
        expandableStickyList.setAdapter(adapter);
        for (int i = 0; i < Marker.NUMBER_OF_GROUPS+1; i++) {
            expandableStickyList.collapse(i);
        }
        expandableStickyList.setOnHeaderClickListener(adapter);
        expandableStickyList.setOnItemClickListener(this);
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
            adapter.notifyDataSetInvalidated();
        } else {
        }
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
        Collections.sort(markers, new MarkerGroupComparator());
        return markers;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EventBus.getDefault().postSticky(new StickyEvents.CurrentMarkerEvent(markers.get(position)));
    }

    private class MarkerGroupComparator implements Comparator<Marker> {
        public int compare(Marker m1, Marker m2) {
            return (m1.getGroupIndex() - m2.getGroupIndex());
        }
    }
}
