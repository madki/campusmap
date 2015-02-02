package in.workarounds.instimap.fragments.notice;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
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
import in.workarounds.instimap.models.Filter;
import in.workarounds.instimap.models.Marker;
import in.workarounds.instimap.models.Notice;
import in.workarounds.instimap.util.TimeUtil;


public class NoticeListAdapter extends BaseExpandableListAdapter {
    private static String TAG = "BaseNoticesListAdapter";
    private LayoutInflater layoutInflater;
    private List<String> dates;
    private HashMap<String, List<Notice>> noticesByDate;
    private int fragment_type = NoticeListFragment.DEFAULT_TYPE;
    private Marker resultMarker;

    public NoticeListAdapter(Context context, int fragment_type) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fragment_type = fragment_type;
        EventBus.getDefault().registerSticky(this);
        populateData();
    }

    public void populateData() {
        noticesByDate = new HashMap<>();
        dates = new ArrayList<>();
        List<Notice> notices = getNoticesList();
        for (Notice notice : notices) {
            addNoticeToHashMap(notice);
        }
        notifyDataSetChanged();
    }

    private List<Notice> getNoticesList() {
        List<Notice> notices = new ArrayList<>();
        if(fragment_type == NoticeListFragment.EVENTS) {
            notices = Notice.find(Notice.class, "is_event = 1 order by start_time");
        } else if(fragment_type == NoticeListFragment.UPDATES) {
            notices = Notice.find(Notice.class, "1=1 order by modified DESC");
        } else if(fragment_type == NoticeListFragment.MARKER_EVENTS) {
            setResultMarker();
            if(resultMarker != null) {
                notices = getEventsFromMarker(resultMarker);
            }
        }
        return notices;
    }

    private List<Notice> getNoticeUpdates(long boardId, long cornerId) {
        List<Notice> notices = new ArrayList<>();
        if(cornerId!=0) {
            List<Filter> filters = Filter.find(Filter.class, "corner_id = ?", Long.toString(cornerId));

            for (Filter filter: filters) {
                List<Notice> noticeList = Notice.find(Notice.class, "db_id = ?", Long.toString(filter.getNoticeId()));
                notices.addAll(noticeList);
            }
        } else if(boardId!=0) {

        } else {
        }
        return notices;
    }

    private List<Notice> getNoticeEvents(long boardId, long cornerId) {
        return null;
    }

    private void addNoticeToHashMap(Notice notice) {
        Date date;
        if(fragment_type == NoticeListFragment.UPDATES) {
            date = notice.getModified();
        } else {
            date = notice.getStartTime();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = simpleDateFormat.format(date);
        if(!noticesByDate.containsKey(dateString)) {
            noticesByDate.put(dateString, new ArrayList<Notice>());
            dates.add(dateString);
        }
        noticesByDate.get(dateString).add(notice);
    }

    public void onEventMainThread(StickyEvents.CurrentMarkerEvent event) {
        resultMarker = event.marker;
        if(fragment_type == NoticeListFragment.MARKER_EVENTS) {
            populateData();
        }
    }

    public void onEventMainThread(StickyEvents.SyncStatusEvent syncStatusEvent) {
        Log.d("NoticeListAdapter", "syn status: " + syncStatusEvent.status);
        if(syncStatusEvent.status == StickyEvents.SyncStatusEvent.FINISHED) {
            populateData();
        }
    }

    private void setResultMarker() {
        StickyEvents.CurrentMarkerEvent event = EventBus.getDefault()
                .getStickyEvent(StickyEvents.CurrentMarkerEvent.class);
        if(event != null) {
            resultMarker = event.marker;
        }
    }

    private List<Notice> getEventsFromMarker(Marker marker) {
        if(marker != null) {
            List<Notice> notices = Notice.find(Notice.class,
                    "venue_id=? order by start_time", Long.toString(marker.getId()));
            return notices;
        }
        else{
            return new ArrayList<>();
        }
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
            convertView = layoutInflater.inflate(R.layout.list_header_notices, parent, false);
            holder.lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
            holder.iconExpand = (ImageView) convertView.findViewById(R.id.icon_expand);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }


        String date = getGroup(groupPosition);
        holder.lblListHeader.setText(date);
        if(isExpanded) {
            holder.iconExpand.setImageResource(R.drawable.ic_action_expand);
        } else {
            holder.iconExpand.setImageResource(R.drawable.ic_action_next_item);
        }

        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_item_notices, parent, false);
            holder.timeTextView = (TextView) convertView.findViewById(R.id.notice_start_time);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.notice_title);
            holder.durationTextView = (TextView) convertView.findViewById(R.id.notice_duration);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Notice notice = getChild(groupPosition, childPosition);
        holder.titleTextView.setText(notice.getTitle());
        holder.timeTextView.setText(TimeUtil.getAmPmString(notice.getStartTime()));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class HeaderViewHolder {
        TextView lblListHeader;
        ImageView iconExpand;
    }

    class ViewHolder {
        TextView timeTextView;
        TextView titleTextView;
        TextView durationTextView;
    }
}
