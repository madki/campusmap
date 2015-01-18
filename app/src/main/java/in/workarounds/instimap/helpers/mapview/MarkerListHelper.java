package in.workarounds.instimap.helpers.mapview;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.models.Marker;

public class MarkerListHelper {
    public static final int NORMAL_MARKER = 0;
    public static final int ADDED_MARKER = 1;
    public static final int SPECIAL_MARKER = 2;
    public static final int NOTICE_MARKER = 3;
    public static final int RESULT_MARKER = 4;
    private List<Marker> markerList;
    private List<Marker> addedMarkerList = new ArrayList<>();
    private List<Marker> specialMarkerList = new ArrayList<>();
    private List<Marker> noticeMarkerList = new ArrayList<>();

    public MarkerListHelper() {
        setUp();
    }

    private void setUp() {
        EventBus eventBus = EventBus.getDefault();
        eventBus.registerSticky(this);
        setMarkerList();
        setNoticeMarkerList();
    }

    public List<Marker> getMarkerList() {
        return markerList;
    }

    public int getMarkerType(Marker marker) {
        if(addedMarkerList.contains(marker)) {
            return ADDED_MARKER;
        } else if(specialMarkerList.contains(marker)) {
            return SPECIAL_MARKER;
        } else if(noticeMarkerList.contains(marker)) {
            return NOTICE_MARKER;
        } else {
            return NORMAL_MARKER;
        }
    }

    public void onEventMainThread(StickyEvents.LocationLoadEvent event) {
        markerList = new ArrayList<>(event.markersHashMap.values());
    }

    public void onEventMainThread(StickyEvents.NoticeMarkersChangedEvent event) {
        noticeMarkerList = event.noticeMarkerList;
    }

    public boolean isAddedMarker(Marker marker) {
        int markerType = getMarkerType(marker);
        return  isAddedMarker(markerType);
    }


    private void setMarkerList() {
        StickyEvents.LocationLoadEvent event = EventBus.getDefault().getStickyEvent(
                StickyEvents.LocationLoadEvent.class);
        if(event!=null) {
            markerList = new ArrayList<>(event.markersHashMap.values());
        } else {
            markerList = new ArrayList<>();
        }
    }

    private void setNoticeMarkerList() {
        StickyEvents.NoticeMarkersChangedEvent event = EventBus.getDefault().getStickyEvent(
                StickyEvents.NoticeMarkersChangedEvent.class);
        if(event != null) {
            this.noticeMarkerList = event.noticeMarkerList;
        }
    }

    public static boolean isAddedMarker(int markerType) {
        return markerType == ADDED_MARKER;
    }

    public static boolean isSpecialMarker(int markerType) {
        return markerType == SPECIAL_MARKER;
    }

    public static boolean isNoticeMarker(int markerType) {
        return markerType == NOTICE_MARKER;
    }

}
