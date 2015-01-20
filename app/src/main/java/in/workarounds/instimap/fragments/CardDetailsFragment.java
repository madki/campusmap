package in.workarounds.instimap.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import de.greenrobot.event.EventBus;
import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.EventFragment;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.models.Marker;
import in.workarounds.instimap.views.TypeFacedTextView;

public class CardDetailsFragment extends EventFragment {
    private CardDetailsView cardDetailsView;
    private Marker resultMarker;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_details, container, false);
        setUp(rootView);
        return rootView;
    }

    @Override
    protected boolean shouldRegisterSticky() {
        return true;
    }

    private class CardDetailsView {
        ImageView cardDetailsImage;
        CardDetailsDescription cardDetailsDescription;

        public CardDetailsView(View rootView) {
            cardDetailsImage = (ImageView) rootView.findViewById(R.id.card_details_image);
            cardDetailsDescription = new CardDetailsDescription(rootView);
        }
    }

    private class CardDetailsDescription {
        TypeFacedTextView descHeader;
        TypeFacedTextView descContent;

        public CardDetailsDescription(View rootView) {
            descHeader = (TypeFacedTextView) rootView.findViewById(R.id.desc_header);
            descContent = (TypeFacedTextView) rootView.findViewById(R.id.desc_content);
        }
    }

    private void setUp(View rootView) {
        cardDetailsView = new CardDetailsView(rootView);

        EventBus eventBus = EventBus.getDefault();
        StickyEvents.CurrentMarkerEvent event = eventBus.getStickyEvent(StickyEvents.CurrentMarkerEvent.class);
        if(event!=null) {
            resultMarker = event.marker;
        } else {
            resultMarker = null;
        }
        populateData();
    }

    public void onEventMainThread(StickyEvents.CurrentMarkerEvent event) {
        resultMarker = event.marker;
        populateData();
    }

    private void populateData() {
        if(resultMarker!=null) {
            if(!resultMarker.getImageUri().isEmpty()) {
                int imageId = getActivity().getResources().getIdentifier(resultMarker.getImageUri(),
                        "drawable", getActivity().getPackageName());
                cardDetailsView.cardDetailsImage.setImageResource(imageId);
                cardDetailsView.cardDetailsImage.setVisibility(View.VISIBLE);
            } else {
                cardDetailsView.cardDetailsImage.setVisibility(View.GONE);
            }
            if(!resultMarker.getDescription().isEmpty()) {
                cardDetailsView.cardDetailsDescription.descHeader.setVisibility(View.VISIBLE);
                cardDetailsView.cardDetailsDescription.descContent.setVisibility(View.VISIBLE);
                cardDetailsView.cardDetailsDescription.descContent.setText(resultMarker.getDescription());
                Linkify.addLinks(cardDetailsView.cardDetailsDescription.descContent, Linkify.ALL);
                cardDetailsView.cardDetailsDescription.descContent.setLinkTextColor(Color.rgb(19, 140, 190));
            } else {
                cardDetailsView.cardDetailsDescription.descHeader.setVisibility(View.GONE);
                cardDetailsView.cardDetailsDescription.descContent.setVisibility(View.GONE);
            }
        } else {

        }
    }
}
