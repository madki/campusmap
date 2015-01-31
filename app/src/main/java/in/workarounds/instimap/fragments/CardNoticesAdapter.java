package in.workarounds.instimap.fragments;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.models.Marker;
import in.workarounds.instimap.models.Notice;

public class CardNoticesAdapter extends BaseNoticesListAdapter {
    private static String TAG = "CardNoticesAdapter";
    private Marker resultMarker;

    public CardNoticesAdapter(Context context) {
        super(context);
        setEventBus();
    }

    @Override
    protected List<Notice> getNoticesList() {
        return getEventsFromMarker(resultMarker);
    }

    private void setEventBus() {
        EventBus eventBus = EventBus.getDefault();
        eventBus.registerSticky(this);

        StickyEvents.CurrentMarkerEvent event = eventBus.getStickyEvent(StickyEvents.CurrentMarkerEvent.class);
        if(event!=null) {
            resultMarker = event.marker;
        }
    }

    public void onEventMainThread(StickyEvents.CurrentMarkerEvent event) {
        resultMarker = event.marker;
        populateData();
    }

    private List<Notice> getEventsFromMarker(Marker marker) {
        if(marker != null) {
            List<Notice> notices = Notice.find(Notice.class, "venue_id=? order by start_time", Long.toString(marker.getId()));
            return notices;
        }
        else{
            return new ArrayList<>();
        }
    }
}
