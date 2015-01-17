package in.workarounds.instimap.bus;

import java.util.HashMap;

public class StickyEvents {

    public static class CurrentMarkerEvent {
        public in.workarounds.instimap.models.Marker marker;

        public CurrentMarkerEvent(in.workarounds.instimap.models.Marker m) {
            this.marker = m;
        }
    }

    public static class LocationLoadEvent {
        public HashMap<String, in.workarounds.instimap.models.Marker> markersHashMap;

        public LocationLoadEvent(HashMap<String, in.workarounds.instimap.models.Marker> markersHashMap) {
            this.markersHashMap = markersHashMap;
        }
    }
}
