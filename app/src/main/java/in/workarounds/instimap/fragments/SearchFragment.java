package in.workarounds.instimap.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;
import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.EventFragment;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.models.Locations;
import in.workarounds.instimap.models.Marker;

public class SearchFragment extends EventFragment implements AdapterView.OnItemClickListener, View.OnTouchListener, TextWatcher {
    private SearchAdapter adapter;
    private String MESSAGE = "Sorry, no such place in our data.";
    private EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(R.layout.view_custom_actionbar);
        View actionBarView = actionBar.getCustomView();

        editText = (EditText) actionBarView.findViewById(R.id.search);
        editText.addTextChangedListener(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ListView list = (ListView) rootView.findViewById(R.id.suggestion_list);

        adapter = new SearchAdapter(getActivity(), getMarkerList());
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        list.setOnTouchListener(this);
        list.setFastScrollEnabled(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.remove_icon:
                editText.setText("");
                break;
        }
        return true;
    }

    @Override
    public void onResume() {
        getActionBar().setDisplayShowCustomEnabled(true);
        super.onResume();
    }

    @Override
    public void onPause() {
        getActionBar().setDisplayShowCustomEnabled(false);
        super.onPause();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (adapter.getResultSize() == 0) {
            Toast toast = Toast.makeText(getActivity(), MESSAGE, Toast.LENGTH_LONG);
            toast.show();
        } else {
            String selection = editText.getText().toString();
            if (id < adapter.getCount()) {
                selection = adapter.getItem(position).getName();
            }
            this.hideKeyboard();
            this.triggerCurrentMarker(selection);
        }
    }

    private void triggerCurrentMarker(String selection) {
        Locations mLocations = Locations.getInstance(getActivity());
        HashMap<String, Marker> data = mLocations.data;
        Marker m = data.get(selection);
        EventBus.getDefault().postSticky(new StickyEvents.CurrentMarkerEvent(m));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.hideKeyboard();
        editText.clearFocus();
        return false;
    }

    public void onEventMainThread(StickyEvents.LocationLoadEvent event) {
        List<Marker> newMarkerList = new ArrayList<in.workarounds.instimap.models.Marker>(event.markersHashMap.values());
        if(adapter!=null) {
            adapter.setMarkerLists(newMarkerList);
            adapter.notifyDataSetInvalidated();
        } else {
            Log.d("SearchFragment", "adapter is null");
        }
    }

    private List<Marker> getMarkerList() {
        StickyEvents.LocationLoadEvent event = EventBus.getDefault().getStickyEvent(StickyEvents.LocationLoadEvent.class);
        if(event!=null) {
            return new ArrayList<>(event.markersHashMap.values());
        } else {
            Log.d("SearchFragment", "event is null");
            return new ArrayList<>();
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    private String refineText(String text) {
        String refinedText = text.replaceAll(Pattern.quote("("), "@")
                .replaceAll(Pattern.quote(")"), "@")
                .replaceAll(Pattern.quote("."), "@")
                .replaceAll(Pattern.quote("+"), "@")
                .replaceAll(Pattern.quote("{"), "@")
                .replaceAll(Pattern.quote("?"), "@")
                .replaceAll(Pattern.quote("\\"), "@")
                .replaceAll(Pattern.quote("["), "@")
                .replaceAll(Pattern.quote("^"), "@")
                .replaceAll(Pattern.quote("$"), "@");

        return refinedText;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String text = editText.getText().toString()
                .toLowerCase(Locale.getDefault());
        adapter.filter(refineText(text));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
