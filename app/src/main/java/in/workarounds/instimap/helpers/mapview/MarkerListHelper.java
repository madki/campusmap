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
    public static final int ADDED_RESULT_MARKER = 5;
    private Marker resultMarker;
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
        setResultMarker(eventBus);
        setMarkerList(eventBus);
        setNoticeMarkerList(eventBus);
    }

    public List<Marker> getMarkerList() {
        return markerList;
    }

    public int getMarkerType(Marker marker) {
        if(addedMarkerList.contains(marker)) {
            if(isResultMarker(marker)) {
                return ADDED_RESULT_MARKER;
            }
            return ADDED_MARKER;
        } else if(specialMarkerList.contains(marker)) {
            return SPECIAL_MARKER;
        } else if(noticeMarkerList.contains(marker)) {
            return NOTICE_MARKER;
        } else if(isResultMarker(marker)) {
            return RESULT_MARKER;
        } else {
            return NORMAL_MARKER;
        }
    }

    public void onEventMainThread(StickyEvents.CurrentMarkerEvent event) {
        resultMarker = event.marker;
    }

    public void onEventMainThread(StickyEvents.LocationLoadEvent event) {
        markerList = new ArrayList<>(event.locations.data.values());
    }

    public void onEventMainThread(StickyEvents.NoticeMarkersChangedEvent event) {
        noticeMarkerList = event.noticeMarkerList;
    }

    public void onEventMainThread(StickyEvents.AddedMarkersChangedEvent event) {
        addedMarkerList = event.addedMarkers;
    }

    public boolean isAddedMarker(Marker marker) {
        int markerType = getMarkerType(marker);
        return  isAddedMarker(markerType);
    }

    public Marker getResultMarker() {
        return resultMarker;
    }

    public boolean isResultMarker(Marker marker) {
        if(resultMarker == null) {
            return false;
        }
        return resultMarker == marker;
    }

    private void setResultMarker(EventBus eventBus) {
        StickyEvents.CurrentMarkerEvent event = eventBus.getStickyEvent(
                StickyEvents.CurrentMarkerEvent.class);
        if(event!=null) {
            resultMarker = event.marker;
        } else {
            resultMarker = null;
        }
    }

    private void setMarkerList(EventBus eventBus) {
        StickyEvents.LocationLoadEvent event = eventBus.getStickyEvent(
                StickyEvents.LocationLoadEvent.class);
        if(event!=null) {
            markerList = new ArrayList<>(event.locations.data.values());
        } else {
            markerList = new ArrayList<>();
        }
    }

    private void setNoticeMarkerList(EventBus eventBus) {
        StickyEvents.NoticeMarkersChangedEvent event = eventBus.getStickyEvent(
                StickyEvents.NoticeMarkersChangedEvent.class);
        if(event != null) {
            this.noticeMarkerList = event.noticeMarkerList;
        }
    }

    public static boolean isAddedMarker(int markerType) {
        return (markerType == ADDED_MARKER) || (markerType == ADDED_RESULT_MARKER);
    }

    public static boolean isSpecialMarker(int markerType) {
        return markerType == SPECIAL_MARKER;
    }

    public static boolean isNoticeMarker(int markerType) {
        return markerType == NOTICE_MARKER;
    }

    public static boolean isResultMarker(int markerType) {
        return (markerType == RESULT_MARKER) || (markerType == ADDED_RESULT_MARKER);
    }

    public static boolean isAddedResultMarker(int markerType) {
        return markerType == ADDED_RESULT_MARKER;
    }

}
