package in.workarounds.instimap.helpers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.designlabs.instimap.R;
import in.workarounds.instimap.models.Board;
import in.workarounds.instimap.models.Corner;
import in.workarounds.instimap.models.ExtendedSugarRecord;

public class CustomSpinnerAdapter extends BaseAdapter {
    private class SimpleSpinnerItem {
        boolean isHeader;
        String title;
        long dbId;

        SimpleSpinnerItem(boolean isHeader, String title, long dbId) {
            this.isHeader = isHeader;
            this.title = title;
            this.dbId = dbId;
        }
    }

    private LayoutInflater layoutInflater;
    private List<SimpleSpinnerItem> spinnerItems;

    public CustomSpinnerAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public <T extends ExtendedSugarRecord> void setSpinnerItems(List<T> elements) {
        spinnerItems = new ArrayList<>();
        spinnerItems.add(new SimpleSpinnerItem(false, "All Items", 0));
        for (T element : elements) {
            long id = element.getDbId();
            String title = "";
            if (element instanceof Board) {
                Board board = (Board) element;
                title = board.getTitle();
            } else if (element instanceof Corner) {
                Corner corner = (Corner) element;
                title = corner.getName();
            }
            spinnerItems.add(new SimpleSpinnerItem(false, title, id));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return spinnerItems.size();
    }

    @Override
    public SimpleSpinnerItem getItem(int position) {
        return spinnerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        if (view == null || !view.getTag().toString().equals("DROPDOWN")) {
            view = layoutInflater.inflate(R.layout.spinner_item_dropdown,
                    parent, false);
            view.setTag("DROPDOWN");
        }

        TextView headerTextView = (TextView) view.findViewById(R.id.header_text);
        View dividerView = view.findViewById(R.id.divider_view);
        TextView normalTextView = (TextView) view.findViewById(android.R.id.text1);

        if (isHeader(position)) {
            headerTextView.setText(getTitle(position));
            headerTextView.setVisibility(View.VISIBLE);
            normalTextView.setVisibility(View.GONE);
            dividerView.setVisibility(View.VISIBLE);
        } else {
            headerTextView.setVisibility(View.GONE);
            normalTextView.setVisibility(View.VISIBLE);
            dividerView.setVisibility(View.GONE);

            normalTextView.setText(getTitle(position));
        }

        return view;
    }

    private boolean isHeader(int position) {
        return position >= 0 && position < spinnerItems.size()
                && spinnerItems.get(position).isHeader;
    }

    public long getId(int position) {
        return spinnerItems.get(position).dbId;
    }

    private String getTitle(int position) {
        return position >= 0 && position < spinnerItems.size() ? spinnerItems.get(position).title : "";
    }

    public int getItemPositionById(long id) {
        int position = 0;
        for(int i=0; i < spinnerItems.size(); i++) {
            if(spinnerItems.get(i).dbId == id) {
                return i;
            }
        }
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null || !view.getTag().toString().equals("NON_DROPDOWN")) {
            view = layoutInflater.inflate(R.layout.spinner_item, parent, false);
            view.setTag("NON_DROPDOWN");
        }
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getTitle(position));
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return !isHeader(position);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

}
