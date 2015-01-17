package in.workarounds.instimap.fragments;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.designlabs.instimap.R;
import in.workarounds.instimap.models.Marker;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class IndexAdapter extends BaseAdapter implements StickyListHeadersAdapter, StickyListHeadersListView.OnHeaderClickListener {

    public List<Marker> getMarkers() {
        return markers;
    }

    public void setMarkers(List<Marker> markers) {
        this.markers = markers;
    }

    private List<Marker> markers;
    private LayoutInflater inflater;
    private long expandedHeader = 0;

    public IndexAdapter(Context context, List<Marker> markers) {
        inflater = LayoutInflater.from(context);
        this.markers = markers;
    }

    @Override
    public int getCount() {
        return markers.size();
    }

    @Override
    public Object getItem(int position) {
        return markers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_index, parent, false);
            holder.txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
            holder.itemGroupColor = convertView.findViewById(R.id.item_group_color);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Marker marker = markers.get(position);
        holder.txtListChild.setText(marker.getName());
        int color = marker.getColor();
        holder.itemGroupColor.setBackgroundColor(color);

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.list_header_index, parent, false);
            holder.lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
            holder.iconExpand = (ImageView) convertView.findViewById(R.id.icon_expand);
            holder.groupColor = (ImageView) convertView.findViewById(R.id.group_color);

            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        //set header text as first char in name
        Marker marker = markers.get(position);
        String headerText = marker.getGroupName();
        long headerIndex = marker.getGroupIndex();
        holder.lblListHeader.setText(headerText);
        int color = marker.getColor();
        holder.groupColor.setImageDrawable(new ColorDrawable(color));
        if(headerIndex == expandedHeader) {
            holder.iconExpand.setImageResource(R.drawable.ic_action_expand);
        } else {
            holder.iconExpand.setImageResource(R.drawable.ic_action_next_item);
        }

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return markers.get(position).getGroupIndex();
    }

    @Override
    public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
        ExpandableStickyListHeadersListView expandableStickyList = (ExpandableStickyListHeadersListView) l;
        ImageView iconExpand = (ImageView) header.findViewById(R.id.icon_expand);
        if(expandableStickyList.isHeaderCollapsed(headerId)){
            expandableStickyList.collapse(expandedHeader);
            expandableStickyList.expand(headerId);
            iconExpand.setImageResource(R.drawable.ic_action_expand);
            expandedHeader = headerId;
        } else {
            expandedHeader = 0;
            expandableStickyList.collapse(headerId);
            iconExpand.setImageResource(R.drawable.ic_action_next_item);
        }
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