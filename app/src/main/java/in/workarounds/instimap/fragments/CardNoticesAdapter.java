package in.workarounds.instimap.fragments;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.models.Marker;
import in.workarounds.instimap.models.Notice;

public class CardNoticesAdapter extends BaseExpandableListAdapter {
    private static String TAG = "CardNoticesAdapter";
    private LayoutInflater layoutInflater;
    private List<String> dates;
    private HashMap<String, List<Notice>> noticesByDate;
    private Marker resultMarker;

    public CardNoticesAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setEventBus();
        populateData();
    }

    private void setEventBus() {
        EventBus eventBus = EventBus.getDefault();
        eventBus.registerSticky(this);

        StickyEvents.CurrentMarkerEvent event = eventBus.getStickyEvent(StickyEvents.CurrentMarkerEvent.class);
        if(event!=null) {
            resultMarker = event.marker;
        }
        notifyDataSetChanged();
    }

    public void onEventMainThread(StickyEvents.CurrentMarkerEvent event) {
        resultMarker = event.marker;
        populateData();
    }

    private void populateData() {
        noticesByDate = new HashMap<>();
        dates = new ArrayList<>();
        if(resultMarker!=null) {
            List<Notice> notices = getEventsFromMarker(resultMarker);
            for (Notice notice : notices) {
                addNoticeToHashMap(notice);
            }
        }
        notifyDataSetChanged();
    }

    private void addNoticeToHashMap(Notice notice) {
        Date date = notice.getStartTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
        String dateString = simpleDateFormat.format(date);
        if(!noticesByDate.containsKey(dateString)) {
            noticesByDate.put(dateString, new ArrayList<Notice>());
            dates.add(dateString);
        }
        noticesByDate.get(dateString).add(notice);
    }

    private List<Notice> getEventsFromMarker(Marker marker) {
//        List<Notice> notices = Notice.find(Notice.class, "venue_id=? order by start_time", Long.toString(marker.getId()));
        List<Notice> notices = Notice.find(Notice.class, null, null, null, "start_time DESC", "1");
        return notices;
    }

    @Override
    public int getGroupCount() {
        int count = dates.size();
        return count;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String date = getGroup(groupPosition);
        List<Notice> notices = noticesByDate.get(date);
        int count = notices.size();
        return count;
    }

    @Override
    public String getGroup(int groupPosition) {

        return dates.get(groupPosition);
    }

    @Override
    public Notice getChild(int groupPosition, int childPosition) {
        String date = getGroup(groupPosition);
        List<Notice> notices = noticesByDate.get(date);
        return notices.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_header_index, parent, false);
            holder.lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
            holder.iconExpand = (ImageView) convertView.findViewById(R.id.icon_expand);
            holder.groupColor = (ImageView) convertView.findViewById(R.id.group_color);

            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }


        String date = getGroup(groupPosition);
        holder.lblListHeader.setText(date);
        holder.groupColor.setVisibility(View.INVISIBLE);
        if(isExpanded) {
            holder.iconExpand.setImageResource(R.drawable.ic_action_expand);
        } else {
            holder.iconExpand.setImageResource(R.drawable.ic_action_next_item);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_item_index, parent, false);
            holder.txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
            holder.itemGroupColor = convertView.findViewById(R.id.item_group_color);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Notice notice = getChild(groupPosition, childPosition);
        holder.txtListChild.setText(notice.getTitle());
        holder.itemGroupColor.setVisibility(View.INVISIBLE);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class HeaderViewHolder {
        TextView lblListHeader;
        ImageView iconExpand;
        ImageView groupColor;
    }

    class ViewHolder {
        TextView txtListChild;
        View itemGroupColor;
    }
}
