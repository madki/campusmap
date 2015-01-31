package in.workarounds.instimap.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import in.designlabs.instimap.R;
import in.workarounds.instimap.models.Notice;
import in.workarounds.instimap.util.TimeUtil;

/**
 * Created by manidesto on 31/01/15.
 */
public abstract class BaseNoticesListAdapter extends BaseExpandableListAdapter {
    private static String TAG = "BaseNoticesListAdapter";
    private LayoutInflater layoutInflater;
    private List<String> dates;
    private HashMap<String, List<Notice>> noticesByDate;

    public BaseNoticesListAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    private void addNoticeToHashMap(Notice notice) {
        Date date = notice.getStartTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = simpleDateFormat.format(date);
        if(!noticesByDate.containsKey(dateString)) {
            noticesByDate.put(dateString, new ArrayList<Notice>());
            dates.add(dateString);
        }
        noticesByDate.get(dateString).add(notice);
    }

    protected abstract List<Notice> getNoticesList();

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
        return false;
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
