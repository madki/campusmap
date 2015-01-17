package in.workarounds.instimap.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.EventFragment;
import in.workarounds.instimap.bus.NormalEvents;
import in.workarounds.instimap.helpers.CardSlideListener;
import in.workarounds.instimap.views.SlidingUpPanelLayout;
import in.workarounds.instimap.views.mapview.CampusMapView;

public class MapFragment extends EventFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        setUp(rootView);
        return rootView;
    }

    private void setUp(View rootView) {
        View newSmallCard = rootView.findViewById(R.id.new_small_card);
        in.workarounds.instimap.views.SlidingUpPanelLayout slidingLayout = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);
        TextView placeNameTextView = (TextView) rootView.findViewById(R.id.place_name);
        ImageView placeColor = (ImageView) rootView.findViewById(R.id.place_color);
        TextView placeSubHeadTextView = (TextView) rootView.findViewById(R.id.place_sub_head);

        CardSlideListener cardSlideListener = new CardSlideListener(getActivity(), slidingLayout);
        slidingLayout.setPanelSlideListener(cardSlideListener);
        slidingLayout.post(setAnchor(slidingLayout));

        CampusMapView campusMapView = (CampusMapView) rootView.findViewById(R.id.campusMapView);
        campusMapView.setImageAsset("map.jpg");
    }

    private Runnable setAnchor(final SlidingUpPanelLayout slidingLayout) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int totalHeight = slidingLayout.getHeight();
                int expandedCardHeight = getResources().getDimensionPixelSize(
                        R.dimen.expanded_card_height);
                float anchorPoint = 0.5f;
                slidingLayout.setAnchorPoint(anchorPoint);
                Log.d("testing", "Anchor point = " + anchorPoint);
            }
        };
        return runnable;
    }

    public void onEvent(NormalEvents.ImageReadyEvent event) {
        getActivity().runOnUiThread(event.runnable);
    }

    @Override
    protected boolean shouldRegisterSticky() {
        return true;
    }
}
