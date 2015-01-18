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

}
