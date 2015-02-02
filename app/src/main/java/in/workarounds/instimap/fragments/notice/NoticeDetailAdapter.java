package in.workarounds.instimap.fragments.notice;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.Inflater;

import de.greenrobot.event.EventBus;
import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.models.Block;
import in.workarounds.instimap.models.ImageContent;
import in.workarounds.instimap.models.Notice;
import in.workarounds.instimap.models.TableContent;
import in.workarounds.instimap.models.TextContent;
import in.workarounds.instimap.util.ImageDownloader;
import in.workarounds.instimap.util.TimeUtil;

/**
 * Created by manidesto on 03/02/15.
 */
public class NoticeDetailAdapter extends BaseAdapter {
    private static String TAG;
    private Context context;
    private Notice mainNotice;
    private List<Notice> updates;

    public NoticeDetailAdapter(Context context){
        this.context = context;
        getMainNotice();
        getUpdates();
    }

    private void getMainNotice() {
        StickyEvents.ShowNoticeEvent event = EventBus.getDefault().getStickyEvent(StickyEvents.ShowNoticeEvent.class);
        this.mainNotice = event.notice;
    }

    private void getUpdates() {
        this.updates = mainNotice.getUpdates();
    }

    @Override
    public int getCount() {
        return updates.size();
    }

    @Override
    public Notice getItem(int position) {
        return updates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d(TAG, "position: " + position + " , updatesAbove: " + mainNotice.getUpdatesAbove());
        if(mainNotice.getUpdatesAbove() == position){
            return getMainView(parent, inflater);
        } else{
            return getUpdateView(parent, inflater, position);
        }
    }

    private View getUpdateView(ViewGroup parent, LayoutInflater inflater, int position) {
        View rootView = inflater.inflate(R.layout.list_item_update, parent, false);
        ((TextView)rootView.findViewById(R.id.tv_update_title)).setText(updates.get(position).getNoticeData().getTitle());
        return rootView;
    }

    private View getMainView(ViewGroup parent, LayoutInflater inflater) {
        View rootView = inflater.inflate(R.layout.layout_notice_detail, parent, false);
        populateDetails(rootView, inflater);
        return rootView;
    }

    private void populateDetails(View rootView, LayoutInflater inflater) {
        populateHeaderDetails(rootView);
        ViewGroup container = (ViewGroup) rootView.findViewById(R.id.notice_detail_container);
        if(mainNotice.isEvent()){
            addEventDetails(container, inflater);
        }
        for(Block block: mainNotice.getNoticeData().getBlocks()){
            if(block.getType().equals(Block.CONTENT_TYPE_TEXT)){
                addTextBlock(container, inflater, (TextContent)block.getContentObject());
            }
            else if(block.getType().equals(Block.CONTENT_TYPE_IMAGE)){
                addImageBlock(container, inflater, (ImageContent)block.getContentObject());
            }
            else if(block.getType().equals(Block.CONTENT_TYPE_TABLE)){
                addTableBlock(container, inflater, (TableContent)block.getContentObject());
            }
        }
    }

    private void populateHeaderDetails(View rootView) {
        ((TextView)rootView.findViewById(R.id.tv_title)).setText(mainNotice.getNoticeData().getTitle());
        ((TextView)rootView.findViewById(R.id.tv_position)).setText(mainNotice.getNoticeData().getPositionName());
        ((TextView)rootView.findViewById(R.id.tv_ago)).setText(TimeUtil.getAgoString(mainNotice.getCreated()));
    }

    private void addEventDetails(ViewGroup container, LayoutInflater inflater) {
        View eventView = inflater.inflate(R.layout.layout_notice_event, container, false);
        container.addView(eventView);
        populateEventDetails(eventView);
    }

    private void populateEventDetails(View eventView) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        ((TextView)eventView.findViewById(R.id.tv_month)).setText(monthFormat.format(mainNotice.getStartTime()).toUpperCase());
        ((TextView)eventView.findViewById(R.id.tv_day)).setText(dayFormat.format(mainNotice.getStartTime()));
        String startTime = timeFormat.format(mainNotice.getStartTime());
        String endTime = timeFormat.format(mainNotice.getEndTime());
        ((TextView)eventView.findViewById(R.id.tv_time)).setText(startTime + " - " + endTime);

        //TODO: add Venue details
    }

    private void addTextBlock(ViewGroup container, LayoutInflater inflater, TextContent textContent) {
        View textBlockView = inflater.inflate(R.layout.layout_notice_text, container, false);
        container.addView(textBlockView);

        ((TextView)textBlockView).setText(textContent.getText());
    }

    private void addImageBlock(ViewGroup container, LayoutInflater inflater, ImageContent imageContent) {
        View imageBlockView = inflater.inflate(R.layout.layout_notice_image, container, false);
        container.addView(imageBlockView);

        new ImageDownloader((ImageView)imageBlockView).execute(imageContent.getLink());
    }

    private void addTableBlock(ViewGroup container, LayoutInflater inflater, TableContent contentObject) {
        //TODO: add table block and populate the cells
    }
}
