package in.workarounds.instimap.bus;

import java.util.List;

import in.workarounds.instimap.models.Locations;
import in.workarounds.instimap.models.Marker;

public class StickyEvents {

    public static class CurrentMarkerEvent {
        public Marker marker;

        public CurrentMarkerEvent(Marker m) {
            this.marker = m;
        }
    }

    public static class LocationLoadEvent {
        public Locations locations;

        public LocationLoadEvent(Locations locations) {
            this.locations = locations;
        }
    }

    public static class NoticeMarkersChangedEvent {
        public List<Marker> noticeMarkerList;

        public NoticeMarkersChangedEvent(List<Marker> noticeMarkerList) {
            this.noticeMarkerList = noticeMarkerList;
        }
    }

    public static class AddedMarkersChangedEvent {
        public List<Marker> addedMarkers;

        public AddedMarkersChangedEvent(List<Marker> addedMarkers) {
            this.addedMarkers = addedMarkers;
        }
    }

    public static class SyncStatusEvent {
        public static int DID_NOT_START = 0;
        public static int STARTED = 1;
        public static int FINISHED = 2;
        public int status = DID_NOT_START;

        public SyncStatusEvent(int status) {
            this.status = status;
        }
    }

    public static class BoardSpinnerEvent {
        public long boardId = 0;
        public BoardSpinnerEvent(long boardId) {
            this.boardId = boardId;
        }
    }

    public static class CornerSpinnerEvent {
        public long cornerId = 0;
        public CornerSpinnerEvent(long cornerId) {
            this.cornerId = cornerId;
        }
    }

}
