package com.mrane.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mrane.campusmap.MapActivity;
import com.mrane.data.Marker;
import com.mrane.models.Notice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import in.designlabs.instimap.R;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class VenueEventsAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private LayoutInflater inflater;
    private MapActivity mapActivity;
    private List<Notice> notices;

    public VenueEventsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        mapActivity = MapActivity.getMainActivity();

        notices = this.getEventsFromMarker(mapActivity.campusMapView.getResultMarker());
    }

    private class HeaderViewHolder {
        TextView dateHeader;
    }

    @Override
    public View getHeaderView(int i, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.venue_events_header, parent, false);
            holder.dateHeader = (TextView) convertView.findViewById(R.id.date_header);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        holder.dateHeader.setText(sdf.format(notices.get(i).getStartTime()));

        return convertView;
    }

    @Override
    public long getHeaderId(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        long dateAsLong = 0;
        String date = sdf.format(notices.get(i).getStartTime());
        try {
            dateAsLong = sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateAsLong;
    }

    @Override
    public int getCount() {
        return notices.size();
    }

    @Override
    public Object getItem(int position) {
        return notices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView eventTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.venue_events_list_item, parent, false);
            holder.eventTitle = (TextView) convertView.findViewById(R.id.event_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.eventTitle.setText(notices.get(position).getTitle());

        return convertView;
    }

    private List<Notice> getEventsFromMarker(Marker marker) {
        List<Notice> notices = Notice.find(Notice.class, "venue_id=?", Long.toString(marker.getId()));
        return notices;
    }
}
