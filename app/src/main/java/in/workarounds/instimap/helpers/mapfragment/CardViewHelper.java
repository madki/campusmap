package in.workarounds.instimap.helpers.mapfragment;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.models.Locations;
import in.workarounds.instimap.models.Marker;
import in.workarounds.instimap.models.Room;
import in.workarounds.instimap.views.SubHeadingView;

public class CardViewHelper {
    private Context context;
    private View cardView;
    private CardSlideListener cardSlideListener;
    private Marker resultMarker;
    private ViewHolder cardViewHolder;

    public CardViewHelper(Context context, View cardView, CardSlideListener cardSlideListener) {
        this.context = context;
        this.cardView = cardView;
        this.cardSlideListener = cardSlideListener;

        setUp();
    }

    private class ViewHolder {
        TextView placeNameTextView;
        SubHeadingView placeSubHeading;
        ImageView placeColor;
        ImageButton addMarkerIcon;
    }

    private void setUp() {
        setUpEventBus();
        setUpViewHolder();
    }

    private void setUpEventBus() {
        EventBus eventBus = EventBus.getDefault();
        eventBus.registerSticky(this);
        StickyEvents.CurrentMarkerEvent event = eventBus.getStickyEvent(
                StickyEvents.CurrentMarkerEvent.class);
        if(event!=null) {
            this.resultMarker = event.marker;
            showResultMarker();
        }
    }

    private void setUpViewHolder() {
        cardViewHolder = new ViewHolder();
        cardViewHolder.placeNameTextView = (TextView) cardView.findViewById(R.id.place_name);
        cardViewHolder.placeSubHeading = (SubHeadingView) cardView.findViewById(R.id.place_sub_head);
        cardViewHolder.placeColor = (ImageView) cardView.findViewById(R.id.place_color);
        cardViewHolder.addMarkerIcon = (ImageButton) cardView.findViewById(R.id.add_marker_icon);
    }

    public void onEventMainThread(StickyEvents.CurrentMarkerEvent event) {
        this.resultMarker = event.marker;
        showResultMarker();
    }

    private void showResultMarker() {
        if(resultMarker != null) {
            setPlaceName();
            setPlaceSubHeading();
            cardSlideListener.showCard();
        } else {
            cardSlideListener.dismissCard();
        }
    }

    private void setPlaceName() {
        String name = resultMarker.getShortName();
        cardViewHolder.placeNameTextView.setText(name);
    }

    private void setPlaceSubHeading() {
        String name = resultMarker.getName();
        if(resultMarker instanceof Room) {
            Room room = (Room) resultMarker;
            String tag = room.tag;
            if(!tag.equals("Inside")) {
                tag += ",";
            } else {
                tag = "in";
            }
            final Marker parent = getParent(room);
            String normalText = name + " - " + tag + " ";
            cardViewHolder.placeSubHeading.setNormalText(normalText);
            cardViewHolder.placeSubHeading.setLinkedText(parent.getShortName());
            cardViewHolder.placeSubHeading.setLinkTarget(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(
                            new StickyEvents.CurrentMarkerEvent(parent));
                }
            });
        } else {
            cardViewHolder.placeSubHeading.setNormalText(name);
            cardViewHolder.placeSubHeading.setLinkedText("");
            cardViewHolder.placeSubHeading.setLinkTarget(null);
        }
    }

    private Marker getParent(Room room) {
        String parentName = room.parentKey;
        Locations locations = EventBus.getDefault().getStickyEvent(
                StickyEvents.LocationLoadEvent.class).locations;
        Marker parent = locations.data.get(parentName);
        return parent;
    }
}
