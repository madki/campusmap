package in.workarounds.instimap.helpers.mapfragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

        cardViewHolder.addMarkerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMarkerToAddedMarkers();
            }
        });
    }

    public void onEventMainThread(StickyEvents.CurrentMarkerEvent event) {
        this.resultMarker = event.marker;
        showResultMarker();
    }

    public void onEventMainThread(StickyEvents.AddedMarkersChangedEvent event) {
        setAddMarkerIcon();
    }

    private void addMarkerToAddedMarkers() {
        EventBus eventBus = EventBus.getDefault();
        StickyEvents.AddedMarkersChangedEvent event = eventBus.getStickyEvent(
                StickyEvents.AddedMarkersChangedEvent.class);
        List<Marker> addedMarkers;
        if(event==null) {
            addedMarkers = new ArrayList<>();
            addedMarkers.add(resultMarker);
        } else {
            addedMarkers = event.addedMarkers;
            if(addedMarkers.contains(resultMarker)) {
                addedMarkers.remove(resultMarker);
            } else {
                addedMarkers.add(resultMarker);
            }
        }
        eventBus.postSticky(new StickyEvents.AddedMarkersChangedEvent(addedMarkers));
    }

    private void showResultMarker() {
        if(resultMarker != null) {
            setPlaceName();
            setPlaceSubHeading();
            setAddMarkerIcon();
            setMarkerColors();
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

    private boolean isAddedMarker(Marker marker) {
        EventBus eventBus = EventBus.getDefault();
        StickyEvents.AddedMarkersChangedEvent event = eventBus.getStickyEvent(
                StickyEvents.AddedMarkersChangedEvent.class);
        if(event!=null) {
            return event.addedMarkers.contains(marker);
        }
        return false;
    }

    private Drawable getLockIcon(Marker marker) {
        int color = marker.getColor();
        int drawableId = R.drawable.lock_all_off;
        if (isAddedMarker(marker)) {
            if (color == Marker.COLOR_BLUE)
                drawableId = R.drawable.lock_blue_on;
            else if (color == Marker.COLOR_YELLOW)
                drawableId = R.drawable.lock_on_yellow;
            else if (color == Marker.COLOR_GREEN)
                drawableId = R.drawable.lock_green_on;
            else if (color == Marker.COLOR_GRAY)
                drawableId = R.drawable.lock_gray_on;
        }
        Drawable lock = context.getResources().getDrawable(drawableId);
        return lock;
    }

    private void setMarkerColors() {
        cardViewHolder.placeColor.setImageDrawable(new ColorDrawable(resultMarker.getColor()));
    }

    private void setAddMarkerIcon() {
        cardViewHolder.addMarkerIcon.setImageDrawable(getLockIcon(resultMarker));
    }

    public boolean handleBackPress() {
        if(cardSlideListener.handleBackPress()) {
            return true;
        } else {
            return removeCurrentMarker();
        }
    }

    private boolean removeCurrentMarker() {
        EventBus eventBus = EventBus.getDefault();
        StickyEvents.CurrentMarkerEvent event = eventBus.getStickyEvent(
                StickyEvents.CurrentMarkerEvent.class);
        if(event != null) {
            if(event.marker!=null) {
                eventBus.postSticky(new StickyEvents.CurrentMarkerEvent(null));
                return true;
            }
        }
        return false;
    }

}
