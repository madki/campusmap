package com.mrane.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.designlabs.instimap.R;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class VenueEventsFragment extends Fragment {

    public VenueEventsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.venue_events_layout, container, false);
        StickyListHeadersListView stickyList = (StickyListHeadersListView) rootView.findViewById(R.id.list);
        VenueEventsAdapter adapter = new VenueEventsAdapter(this.getActivity());
        stickyList.setAdapter(adapter);

        return rootView;
    }
}
