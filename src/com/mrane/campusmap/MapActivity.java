package com.mrane.campusmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.mrane.data.Building;
import com.mrane.data.Locations;
import com.mrane.data.Marker;
import com.mrane.data.Room;
import com.mrane.zoomview.CampusMapView;
import com.mrane.zoomview.SubsamplingScaleImageView;

public class MapActivity extends ActionBarActivity implements TextWatcher,
		OnEditorActionListener, OnItemClickListener, OnFocusChangeListener,
		OnTouchListener, OnChildClickListener {
	private static MapActivity mainActivity;
	boolean isOpened = false;
	private FuzzySearchAdapter adapter;
	private ExpandableListAdapter expAdapter;
	private FragmentManager fragmentManager;
	private ListFragment listFragment;
	private IndexFragment indexFragment;
	private Fragment fragment;
	public RelativeLayout placeCard;
	public ImageView placeColor;
	private CardTouchListener cardTouchListener;
	private RelativeLayout fragmentContainer;
	public RelativeLayout bottomLayout;
	public TextView placeNameTextView;
	public TextView placeSubHeadTextView;
	public EditText editText;
	public HashMap<String, Marker> data;
	private List<Marker> markerlist;
	public FragmentTransaction transaction;
	public CampusMapView campusMapView;
	public ImageButton searchIcon;
	public ImageButton removeIcon;
	public ImageButton indexIcon;
	public ImageButton mapIcon;
	public ImageButton addMarkerIcon;
	public LocationManager locationManager;
	public LocationListener locationListener;
	public int expandedGroup = -1;
	private boolean noFragments = true;
	private boolean editTextFocused = false;
	private final String firstStackTag = "FIRST_TAG";
	private final int MSG_ANIMATE = 1;
	private final int MSG_INIT_LAYOUT = 2;
	private final int MSG_PLAY_SOUND = 3;
	private final int MSG_DISPLAY_MAP = 4;
	private final long DELAY_ANIMATE = 150;
	private final long DELAY_INIT_LAYOUT = 250;
	private Toast toast;
	private String message = "Sorry, no such place in our data.";
	public static final PointF MAP_CENTER = new PointF(2971f, 1744f);
	public static final long DURATION_INIT_MAP_ANIM = 500;
	public static final int KEY_SOUND_ADD_MARKER = 1;
	public static final String FONT_BOLD = "myriadpro_bold_cn.otf";
	public static final String FONT_SEMIBOLD = "myriadpro_semibold.otf";
	public static final String FONT_REGULAR = "myriadpro_regular.otf";
	public static final int SOUND_ID_RESULT = 0;
	public static final int SOUND_ID_ADD = 1;
	public static final int SOUND_ID_REMOVE = 2;
	public SoundPool soundPool;
	public int[] soundPoolIds;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_ANIMATE:
				showReslutOnMap((String) msg.obj);
				break;
			case MSG_INIT_LAYOUT:
				initLayout();
				break;
			case MSG_PLAY_SOUND:
				playAnimSound(msg.arg1);
				break;
			case MSG_DISPLAY_MAP:
				displayMap();
				break;
			}
		}
	};

	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMainActivity(this);

		getSupportActionBar().hide();

		setContentView(R.layout.activity_main);

		bottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
		placeCard = (RelativeLayout) findViewById(R.id.place_card);
		placeNameTextView = (TextView) findViewById(R.id.place_name);
		placeColor = (ImageView) findViewById(R.id.place_color);
		placeSubHeadTextView = (TextView) findViewById(R.id.place_sub_head);

		Locations mLocations = new Locations(this);
		data = mLocations.data;
		markerlist = new ArrayList<Marker>(data.values());

		fragmentContainer = (RelativeLayout) findViewById(R.id.fragment_container);

		adapter = new FuzzySearchAdapter(this, markerlist);
		editText = (EditText) findViewById(R.id.search);
		editText.addTextChangedListener(this);
		editText.setOnEditorActionListener(this);
		editText.setOnFocusChangeListener(this);

		campusMapView = (CampusMapView) findViewById(R.id.campusMapView);
		campusMapView.setImageAsset("map.jpg");
		campusMapView.setData(data);

		searchIcon = (ImageButton) findViewById(R.id.search_icon);
		removeIcon = (ImageButton) findViewById(R.id.remove_icon);
		indexIcon = (ImageButton) findViewById(R.id.index_icon);
		mapIcon = (ImageButton) findViewById(R.id.map_icon);
		addMarkerIcon = (ImageButton) findViewById(R.id.add_marker_icon);

		cardTouchListener = new CardTouchListener(this);
		placeCard.setOnTouchListener(cardTouchListener);

		fragmentManager = getSupportFragmentManager();
		listFragment = new ListFragment();
		indexFragment = new IndexFragment();
		
		initSoundPool();
		setFonts();

		Message msg = mHandler.obtainMessage(MSG_INIT_LAYOUT);
		mHandler.sendMessageDelayed(msg, DELAY_INIT_LAYOUT);
		toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
	}
	
	private void setFonts(){
		Typeface regular = Typeface.createFromAsset(getAssets(), FONT_REGULAR);
		//Typeface semibold = Typeface.createFromAsset(getAssets(), FONT_SEMIBOLD);
		
		placeNameTextView.setTypeface(regular, Typeface.BOLD);;
		placeSubHeadTextView.setTypeface(regular);
		editText.setTypeface(regular);
	}

	private void initLayout() {
		if(!campusMapView.isImageReady()){
			Message msg = mHandler.obtainMessage(MSG_INIT_LAYOUT);
			mHandler.sendMessageDelayed(msg, DELAY_INIT_LAYOUT);
		}
		else{
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			FrameLayout.LayoutParams p = (FrameLayout.LayoutParams)campusMapView.getLayoutParams();
			int topMargin = campusMapView.getHeight() + p.topMargin;
			// float density = getResources().getDisplayMetrics().density;
			params.setMargins(0, topMargin, 0, 0);
			bottomLayout.setLayoutParams(params);
			bottomLayout.setVisibility(View.INVISIBLE);
			placeCard.setVisibility(View.INVISIBLE);
			cardTouchListener.initTopMargin(topMargin);
		}
	}
	
	private void initSoundPool(){
		soundPool = new SoundPool(2,AudioManager.STREAM_MUSIC, 100);
		soundPoolIds = new int[3];
		soundPoolIds[SOUND_ID_RESULT] = soundPool.load(this, R.raw.result_marker, 1);
		soundPoolIds[SOUND_ID_ADD] = soundPool.load(this, R.raw.add_marker, 2);
		soundPoolIds[SOUND_ID_REMOVE] = soundPool.load(this, R.raw.remove_marker, 3);
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		if (editTextFocused) {
			String text = editText.getText().toString()
					.toLowerCase(Locale.getDefault());
			adapter.filter(text);
		}

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		this.setCorrectIcons();

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		switch (actionId) {
		case EditorInfo.IME_ACTION_SEARCH:
			onItemClick(null, v, 0, 0);
		}
		return true;
	}

	public static MapActivity getMainActivity() {
		return mainActivity;
	}

	public static void setMainActivity(MapActivity mainActivity) {
		MapActivity.mainActivity = mainActivity;
	}

	private void putFragment(Fragment tempFragment) {
		this.dismissCard();
		transaction = fragmentManager.beginTransaction();
		// transaction.setCustomAnimations(R.anim.fragment_slide_in,
		// R.anim.fragment_slide_out);
		fragment = tempFragment;
		if (noFragments) {
			transaction.add(R.id.fragment_container, tempFragment);
			transaction.addToBackStack(firstStackTag);
			transaction.commit();
		} else {
			transaction.replace(R.id.fragment_container, tempFragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}
		noFragments = false;
	}

	private void backToMap() {
		noFragments = true;
		this.hideKeyboard();
		fragmentManager.popBackStack(firstStackTag,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
		this.removeEditTextFocus(null);
		this.setCorrectIcons();
		this.displayMap();
	}

	@Override
	public void onBackPressed() {
		if (noFragments) {
			if (!this.removeMarker()) {
				super.onBackPressed();
			} else {
				if (editText.length() > 0) {
				}
			}
		} else {
			backToMap();
			this.removeEditTextFocus("");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int id, long arg3) {
		if (adapter.getResultSize() == 0) {
			toast.setText(message);;
			toast.show();
		} else {
			String selection = editText.getText().toString();
			if (id < adapter.getCount()) {
				selection = adapter.getItem(id).name;
			}
			this.hideKeyboard();
			this.removeEditTextFocus(selection);
			this.backToMap();
		}
	}

	public void displayMap() {
		//check if is Image ready
		if(!campusMapView.isImageReady()){
			Message msg = mHandler.obtainMessage(MSG_DISPLAY_MAP);
			mHandler.sendMessageDelayed(msg, DELAY_INIT_LAYOUT);
		}
		else{
			// locateIcon.setVisibility(View.VISIBLE);
			// get text from auto complete text box
			String key = editText.getText().toString();
	
			// get Marker object if exists
			Marker marker = data.get(key);
	
			// display and zoom to marker if exists
			if (marker != null) {
				Message msg = mHandler.obtainMessage(MSG_ANIMATE, key);
				mHandler.sendMessageDelayed(msg, DELAY_ANIMATE);
			} else {
				removeMarker();
			}
		}
	}

	private void showReslutOnMap(String key) {
		Marker marker = data.get(key);
		showCard(marker);
		campusMapView.setAndShowResultMarker(marker);
	}

	public void showCard() {
		Marker marker = campusMapView.getResultMarker();
		showCard(marker);
	}

	public void showCard(Marker marker) {
		String name = marker.name;
		if(!marker.shortName.equals("0")) name = marker.shortName;
		placeNameTextView.setText(name);
		placeSubHeadTextView.setText(getSubHeading(marker));
		campusMapView.setPanLimit(SubsamplingScaleImageView.PAN_LIMIT_CUSTOM);
		setAddMarkerIcon(marker);
		bottomLayout.setVisibility(View.VISIBLE);
		placeCard.setVisibility(View.VISIBLE);
		placeColor.setImageDrawable(new ColorDrawable(marker.getColor()));;
		Runnable anim = cardTouchListener.showCardAnimation();
		anim.run();
	}
	
	private String getSubHeading(Marker marker){
		String result = "";
		result += marker.name;
		if(marker instanceof Room){
			Room room = (Room) marker;
			String tag = room.tag;
			if(!tag.equals("in")) tag += ",";
			Building parent = (Building)data.get(room.parentKey);
			String parentName = parent.name;
			if(!parent.shortName.equals("0")) parentName = parent.shortName;
			result += " - " + tag + " " + parentName;
		}
		
		return result;
	}
	
	private Drawable getLockIcon(Marker marker){
		int color = marker.getColor();
		int drawableId = R.drawable.lock_all_off;
		if(campusMapView.isAddedMarker(marker)){
			if(color == Marker.COLOR_BLUE) drawableId = R.drawable.lock_blue_on;
			else if(color == Marker.COLOR_YELLOW) drawableId = R.drawable.lock_on_yellow;
			else if(color == Marker.COLOR_GREEN) drawableId = R.drawable.lock_green_on;
			else if(color == Marker.COLOR_GRAY) drawableId = R.drawable.lock_gray_on;
		}
		Drawable lock = getResources().getDrawable(drawableId);
		return lock;
	}

	public void expandCard() {
		Runnable anim = cardTouchListener.expandCardAnimation();
		anim.run();
	}

	public void dismissCard() {
		campusMapView.setPanLimit(SubsamplingScaleImageView.PAN_LIMIT_INSIDE);
		Runnable anim = cardTouchListener.dismissCardAnimation();
		anim.run();
	}

	public boolean removeMarker() {
		if (campusMapView.getResultMarker() == null) {
			return false;
		} else {
			final int state = cardTouchListener.getCardState();
			switch (state) {
			case CardTouchListener.STATE_DISMISSED:
			case CardTouchListener.STATE_HIDDEN:
				editText.getText().clear();
				campusMapView.setResultMarker(null);
				this.dismissCard();
				campusMapView.invalidate();
				break;
			case CardTouchListener.STATE_EXPANDED:
			case CardTouchListener.STATE_UNKNOWN:
				showCard();
				break;
			default:
				break;
			}
			return true;
		}
	}

	public void searchClick(View v) {
		putFragment(listFragment);
		editText.requestFocus();
		editText.setText("");
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
	}

	public void removeClick(View v) {
		this.editText.setText("");
		displayMap();
	}

	public void indexClick(View v) {
		this.putFragment(indexFragment);
		this.removeEditTextFocus(null);
		this.setCorrectIcons();
	}

	public void mapClick(View v) {
		this.backToMap();
		this.removeEditTextFocus("");
	}

	private void removeEditTextFocus(String text) {
		if (this.editTextFocused) {
			this.hideKeyboard();
			editText.clearFocus();
		}

		if (text == null) {

		} else if (text.equals("")) {
			this.setOldText();
		} else {
			editText.setText(text);
		}

	}

	public FuzzySearchAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(FuzzySearchAdapter adapter) {
		this.adapter = adapter;
	}

	private void setOldText() {
		Marker oldMarker = campusMapView.getResultMarker();
		if (oldMarker == null) {
			if (editText.length() > 0) {
				editText.getText().clear();
			}
		} else {
			editText.setText(oldMarker.name);
		}
	}

	private void setCorrectIcons() {
		if (noFragments) {
			this.setVisibleButton(indexIcon);
		} else {
			if (fragment instanceof ListFragment) {
				
			} else {
				setVisibleButton(mapIcon);
			}
		}
		this.handleRemoveIcon();
	}

	private void handleRemoveIcon() {
			String text = editText.getText().toString();
			if (text.isEmpty() || text.equals(null)) {
				removeIcon.setVisibility(View.GONE);
			} else {
				removeIcon.setVisibility(View.VISIBLE);
			}
	}

	private void setVisibleButton(ImageButton icon) {
		indexIcon.setVisibility(View.GONE);
		mapIcon.setVisibility(View.GONE);

		icon.setVisibility(View.VISIBLE);
	}

	@Override
	public void onFocusChange(View v, boolean focus) {
		this.editTextFocused = focus;
		if (focus) {
			searchIcon.setVisibility(View.GONE);
			this.putFragment(listFragment);
			fragmentContainer.setOnTouchListener(this);
			String text = editText.getText().toString()
					.toLowerCase(Locale.getDefault());
			adapter.filter(text);
		} else {
			searchIcon.setVisibility(View.VISIBLE);
			fragmentContainer.setOnTouchListener(null);
		}
		this.setCorrectIcons();
	}

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
			fragmentContainer.setOnTouchListener(null);
		}

	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		if (adapter.getResultSize() != 0) {
			removeEditTextFocus(null);
		}
		return false;
	}

//	public void locateClick(View v) {
//
//	}

	public void addMarkerClick(View v) {
		campusMapView.toggleMarker();
		setAddMarkerIcon();
	}
	
	public void playAnimSound(int sound_index){
		if(sound_index >= 0 && sound_index < soundPoolIds.length){
		soundPool.play(soundPoolIds[sound_index], 1.0f, 1.0f, 1, 0, 1f);
		}
	}
	
	public void playAnimSoundDelayed(int sound_index,long delay ){
		Message msg = mHandler.obtainMessage(MSG_PLAY_SOUND, sound_index, 0);
		mHandler.sendMessageDelayed(msg, delay);
	}

	private void setAddMarkerIcon() {
		setAddMarkerIcon(campusMapView.getResultMarker());
	}

	private void setAddMarkerIcon(Marker m) {
		addMarkerIcon.setImageDrawable(getLockIcon(m));
	}

	public void removeCardClick(View v) {
		editText.getText().clear();
		displayMap();
		dismissCard();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		String selection = (String) expAdapter.getChild(groupPosition,
				childPosition);
		this.hideKeyboard();
		this.removeEditTextFocus(selection);
		this.backToMap();
		return true;
	}

	public ExpandableListAdapter getExpAdapter() {
		return expAdapter;
	}

	public void setExpAdapter(ExpandableListAdapter expAdapter) {
		this.expAdapter = expAdapter;
	}
	
	private static final String INSTANCE_CARD_STATE = "instanceCardState";
	private static final String INSTANCE_VISIBILITY_INDEX = "instanceVisibilityIndex";
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(INSTANCE_CARD_STATE, cardTouchListener.getCardState());
		outState.putBoolean(INSTANCE_VISIBILITY_INDEX, indexIcon.isShown());
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		int cardState = savedInstanceState.getInt(INSTANCE_CARD_STATE);
		boolean isIndexIconVisible = savedInstanceState.getBoolean(INSTANCE_VISIBILITY_INDEX);
		if(isIndexIconVisible){
			indexIcon.setVisibility(View.VISIBLE);
			mapIcon.setVisibility(View.GONE);
		}
		else{
			indexIcon.setVisibility(View.GONE);
			mapIcon.setVisibility(View.VISIBLE);
		}
		if(cardState != CardTouchListener.STATE_DISMISSED){ 
			displayMap();
		}
	}

}
