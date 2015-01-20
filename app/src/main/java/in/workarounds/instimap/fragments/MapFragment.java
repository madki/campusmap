package in.workarounds.instimap.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.EventFragment;
import in.workarounds.instimap.bus.NormalEvents;
import in.workarounds.instimap.helpers.mapfragment.BarViewHelper;
import in.workarounds.instimap.helpers.mapfragment.CardSlideListener;
import in.workarounds.instimap.helpers.mapfragment.CardViewHelper;
import in.workarounds.instimap.helpers.mapfragment.ExpandCardViewHelper;
import in.workarounds.instimap.views.slidingpanel.SlidingUpPanelLayout;
import in.workarounds.instimap.views.mapview.CampusMapView;

public class MapFragment extends EventFragment {
    private CardViewHelper cardViewHelper;
    private ExpandCardViewHelper expandCardViewHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        setUp(rootView);
        return rootView;
    }

    private void setUp(View rootView) {
        SlidingUpPanelLayout slidingLayout = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);

        CardSlideListener cardSlideListener = new CardSlideListener(getActivity(), slidingLayout);
        slidingLayout.setPanelSlideListener(cardSlideListener);
        slidingLayout.post(setAnchor(slidingLayout));

        CampusMapView campusMapView = (CampusMapView) rootView.findViewById(R.id.campusMapView);
        campusMapView.setImageAsset("map.jpg");

        View barView = rootView.findViewById(R.id.bar_view);
        BarViewHelper barViewHelper = new BarViewHelper(getActivity(), barView);

        View cardView = rootView.findViewById(R.id.card_view);
        cardViewHelper = new CardViewHelper(getActivity(), cardView, cardSlideListener);

        View expandCardView = rootView.findViewById(R.id.expand_card_view);
        expandCardViewHelper = new ExpandCardViewHelper(getActivity(), expandCardView, getChildFragmentManager());
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

    public boolean handleBackPress() {
        return cardViewHelper.handleBackPress();
    }
}
