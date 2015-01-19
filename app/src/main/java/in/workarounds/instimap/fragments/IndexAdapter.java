package in.workarounds.instimap.fragments;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import in.designlabs.instimap.R;
import in.workarounds.instimap.models.Marker;

public class IndexAdapter extends BaseExpandableListAdapter {

    private LayoutInflater layoutInflater;
    private List<String> groupNames;
    private HashMap<String, List<Marker>> markersByGroup;

    public IndexAdapter(Context context, List<Marker> markerList) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        populateData(markerList);
    }

    private void populateData(List<Marker> markerList) {
        groupNames = Arrays.asList(Marker.getGroupNames());
        markersByGroup = new HashMap<>();
        for (String groupName: groupNames) {
            markersByGroup.put(groupName, new ArrayList<Marker>());
        }
        for (Marker marker: markerList) {
            markersByGroup.get(marker.getGroupName()).add(marker);
        }
    }

    @Override
    public int getGroupCount() {
        return groupNames.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupName = groupNames.get(groupPosition);
        List<Marker> markerGroup = markersByGroup.get(groupName);
        return markerGroup.size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return groupNames.get(groupPosition);
    }

    @Override
    public Marker getChild(int groupPosition, int childPosition) {
        String groupName = groupNames.get(groupPosition);
        List<Marker> markerGroup = markersByGroup.get(groupName);
        Marker childMarker = markerGroup.get(childPosition);
        return childMarker;
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


        String groupName = groupNames.get(groupPosition);
        holder.lblListHeader.setText(groupName);
        int groupId = Marker.getGroupId(groupName);
        int color = Marker.getColor(groupId);
        holder.groupColor.setImageDrawable(new ColorDrawable(color));
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

        Marker marker = markersByGroup.get(groupNames.get(groupPosition)).get(childPosition);
        holder.txtListChild.setText(marker.getName());
        int color = marker.getColor();
        holder.itemGroupColor.setBackgroundColor(color);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setMarkers(List<Marker> markerList) {
        populateData(markerList);
        notifyDataSetChanged();
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
