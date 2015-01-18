package in.workarounds.instimap.helpers.mapfragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.util.AsyncExecutor;
import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.helpers.SettingsManager;
import in.workarounds.instimap.models.Locations;
import in.workarounds.instimap.models.Marker;
import in.workarounds.instimap.models.Notice;

public class BarViewHelper implements SharedPreferences.OnSharedPreferenceChangeListener {
    Context context;
    View barView;
    SettingsManager settingsManager;
    Date date;

    public BarViewHelper(Context context, View barView) {
        this.context = context;
        this.barView = barView;
        setUp();
    }

    private void setUp(){
        setEventBus();
        settingsManager = SettingsManager.getInstance(context);
        SharedPreferences prefs = settingsManager.getSharedPrefs();
        prefs.registerOnSharedPreferenceChangeListener(this);
        date = new Date();
        setDate();

        ImageButton next = (ImageButton) barView.findViewById(R.id.bar_next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext();
            }
        });

        ImageButton prev = (ImageButton) barView.findViewById(R.id.bar_prev_button);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPrev();
            }
        });

        setBarViewVisibility();
    }

    private void setEventBus() {
        EventBus eventBus = EventBus.getDefault();
        eventBus.register(this);
    }

    public void onEventMainThread(StickyEvents.CurrentMarkerEvent event) {
        if(event != null) {
            barView.setVisibility(View.GONE);
        } else {
            setBarViewVisibility();
        }
    }

    public void onEventMainThread(StickyEvents.LocationLoadEvent event) {
        setDate();
    }

    private Locations getLocations() {
        StickyEvents.LocationLoadEvent event = EventBus.getDefault().getStickyEvent(
                StickyEvents.LocationLoadEvent.class);
        if(event != null) {
            return event.locations;
        } else {
            return null;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String eventsKey = context.getResources().getString(R.string.setting_events_key);
        if(key.equals(eventsKey)){
            setBarViewVisibility();
        }
    }

    private void setBarViewVisibility() {
        if(settingsManager.isInEventsMode()) {
            barView.setVisibility(View.VISIBLE);
        } else {
            barView.setVisibility(View.GONE);
        }
    }

    private void setDate(){
        if(settingsManager.isInEventsMode()) {
            DateFormat dateFormat = new SimpleDateFormat("MMM dd");
            String dateString = dateFormat.format(date);
            dateString = dateString.toUpperCase();

            TextView dateView = (TextView) barView.findViewById(R.id.date_text_view);
            dateView.setText(dateString);

            getVenues();
        }
    }

    private void goToNext(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        setDate();
    }

    private void goToPrev(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        setDate();
    }

    private void getVenues() {
        AsyncExecutor.create().execute(new AsyncExecutor.RunnableEx() {
            @Override
            public void run() throws Exception {
                List<Marker> eventList = getVenueMarkersList(context, date);
                EventBus.getDefault().postSticky(new StickyEvents.NoticeMarkersChangedEvent(eventList));
            }
        });
    }

    private ArrayList<Marker> getVenueMarkersList(Context context, Date date){
        ArrayList<Marker> result = new ArrayList<>();

        Locations locations = getLocations();
        if(locations != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date startDate = calendar.getTime();

            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate = calendar.getTime();

            List<Notice> notices = Notice.find(Notice.class, "start_time BETWEEN ? AND ?", Long.toString(startDate.getTime()), Long.toString(endDate.getTime()));
            ArrayList<Long> venueIds = new ArrayList<>();
            for (Notice n : notices) {
                if (venueIds.indexOf(n.getVenueId()) == -1) {
                    if (n.getVenueId() != 0) {
                        venueIds.add(n.getVenueId());
                    }
                }
            }

            for (Long venueId : venueIds) {
                Marker marker = locations.getMarkerById(venueId);
                result.add(marker);
            }
        }
        return result;
    }
}
