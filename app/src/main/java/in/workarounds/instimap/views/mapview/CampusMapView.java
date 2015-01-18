package in.workarounds.instimap.views.mapview;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import in.workarounds.instimap.bus.NormalEvents;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.helpers.SettingsManager;
import in.workarounds.instimap.helpers.mapview.BitmapHelper;
import in.workarounds.instimap.helpers.mapview.MarkerListHelper;
import in.workarounds.instimap.helpers.mapview.TextHelper;
import in.workarounds.instimap.models.Building;
import in.workarounds.instimap.models.Marker;
import in.workarounds.instimap.models.Room;

public class CampusMapView extends SubsamplingScaleImageView {
    public static final float pointerWidth = 12;
    private static long DURATION_MARKER_ANIMATION = 500;
    private static long DELAY_MARKER_ANIMATION = 675;
    private static int RATIO_SHOW_PIN = 10;
    private static int RATIO_SHOW_PIN_TEXT = 20;
    public static final PointF MAP_CENTER = new PointF(2971f, 1744f);
    public static final long DURATION_INIT_MAP_ANIM = 500;
    public static float highlightedMarkerScale = 1.0f;
    private boolean isFirstLoad;
    private BitmapHelper bitmapHelper;
    private TextHelper textHelper;
    private MarkerListHelper markerListHelper;
    private DisplayMetrics displayMetrics;
    private float density;
    private SettingsManager settingsManager;
    private HashMap<String, in.workarounds.instimap.models.Marker> data;

    public CampusMapView(Context context) {
        this(context, null);
    }

    public CampusMapView(Context context, AttributeSet attr) {
        super(context, attr);
        setUp(context);
    }

    private void setUp(Context context) {
        displayMetrics = context.getResources().getDisplayMetrics();
        density = displayMetrics.density;
        markerListHelper = new MarkerListHelper();
        bitmapHelper = new BitmapHelper(context, displayMetrics);
        textHelper = new TextHelper(context, displayMetrics);
        settingsManager = SettingsManager.getInstance(context);
        isFirstLoad = true;

        showResultMarker(markerListHelper.getResultMarker());
        setUpEventBus();
        setGestureDetector(context);
    }

    private void setUpEventBus() {
        EventBus eventBus = EventBus.getDefault();
        eventBus.registerSticky(this);

        StickyEvents.LocationLoadEvent event = eventBus.getStickyEvent(StickyEvents.LocationLoadEvent.class);
        if(event!=null) {
            data = event.locations.data;
        } else {
            data = new HashMap<>();
        }
    }

    public void onEventMainThread(StickyEvents.CurrentMarkerEvent event) {
        showResultMarker(event.marker);
    }

    public void onEventMainThread(StickyEvents.LocationLoadEvent event) {
        this.data = event.locations.data;
    }

    public void onEventMainThread(Object event) {
        invalidate();
    }

    @Override
    protected void onImageReady() {
        if (isFirstLoad) {
            Runnable runnable = new Runnable() {
                public void run() {
                    AnimationBuilder anim;
                    anim = animateScaleAndCenter(
                            getTargetMinScale(), MAP_CENTER);
                    anim.withDuration(DURATION_INIT_MAP_ANIM)
                            .start();
                    isFirstLoad = false;
                }
            };
            EventBus.getDefault().post(new NormalEvents.ImageReadyEvent(runnable));
        }
    }

    private void setGestureDetector(Context context) {
        final GestureDetector gestureDetector = new GestureDetector(
                context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (isImageReady()) {
                    PointF sCoord = viewToSourceCoord(e.getX(),
                            e.getY());
                    Marker marker = getNearestMarker(sCoord);
                    if (isMarkerInTouchRegion(marker, sCoord)) {
                        EventBus.getDefault().postSticky(new StickyEvents.CurrentMarkerEvent(marker));
                    }
                } else {

                }
                return true;
            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final float targetMinScale = getTargetMinScale();
                int action = motionEvent.getAction();
                if(action== MotionEvent.ACTION_DOWN){
                    if(motionEvent.getX()<20*density){
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                    }
                    else{
						// CampusMapView.this.setPanEnabled(true);
                    }
                }
                else if(action == MotionEvent.ACTION_UP){
                    CampusMapView.this.setPanEnabled(true);
                }
                if (targetMinScale > getScale()) {
                    callSuperOnTouch(motionEvent);

                    if (action == MotionEvent.ACTION_UP) {

                        Runnable anim = getScaleAnim(targetMinScale);
                        if(isImageReady())	anim.run();
                    }
                    return true;
                }
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    private void callSuperOnTouch(MotionEvent me) {
        super.onTouchEvent(me);
    }

    private boolean isMarkerInTouchRegion(Marker marker, PointF o) {
        if (marker != null) {
            PointF point = sourceToViewCoord(marker.getPoint());
            PointF origin = sourceToViewCoord(o);
            float dist = (float) calculateDistance(point, origin);
            if (dist < pointerWidth * density * 2 && isMarkerVisible(marker)) {
                return true;
            }
        }
        return false;
    }

    private double calculateDistance(PointF point1, PointF point2) {
        float xDiff = point1.x - point2.x;
        float yDiff = point1.y - point2.y;

        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    private Marker getNearestMarker(PointF touchPoint) {
        Marker resultMarker = null;
        float minDist = 100000000f;
        for (Marker marker : markerListHelper.getMarkerList()) {
            PointF point = marker.getPoint();
            float dist = (float) calculateDistance(point, touchPoint);

            if (dist < minDist && isMarkerVisible(marker)) {
                minDist = dist;
                resultMarker = marker;
            }
        }
        return resultMarker;
    }

    private boolean isMarkerVisible(Marker marker) {
        if(markerListHelper.getMarkerType(marker) > MarkerListHelper.NORMAL_MARKER) {
            return true;
        }
        if (isShowPinScale(marker) && shouldShowUp(marker)) {
            return true;
        }
        return false;
    }

    private boolean isShowPinScale(Marker m) {
        PointF left = viewToSourceCoord(0, 0);
        PointF right = viewToSourceCoord(getWidth(), 0);
        float xDpi = displayMetrics.xdpi;
        if ((right.x - left.x) * xDpi / getWidth() < getSWidth()
                / RATIO_SHOW_PIN) {
            return true;
        }
        return false;
    }

    private boolean shouldShowUp(Marker marker) {
        boolean result = true;
        if(marker.getGroupIndex() == Marker.RESIDENCES){
            result = settingsManager.showResidences();
        }
        if (marker instanceof Building) {
            String[] childKeys = ((Building) marker).children;
            for (String childKey : childKeys) {
                Marker child = data.get(childKey);
                if (markerListHelper.isAddedMarker(child) || markerListHelper.isResultMarker(child)) {
                    result = false;
                    break;
                }
            }
        }
        if (marker instanceof Room)
            result = false;
        return result;
    }

    private void setMarkerAnimation(boolean noDelay, int _sound_index) {
        final int sound_index = _sound_index;
        long delay = 0;
        if (!noDelay) {
            delay = DELAY_MARKER_ANIMATION;
        }

        if (android.os.Build.VERSION.SDK_INT >= 11) {
            playAnim(delay);
        } else {
            highlightedMarkerScale = 1.0f;
        }
        // TODO mainActivity.playAnimSoundDelayed(sound_index, delay);
        if (isImageReady())
            invalidate();
    }

    @SuppressLint("NewApi")
    private void playAnim(long delay) {
        highlightedMarkerScale = 0.1f;
        ValueAnimator valAnim = new ValueAnimator();
        valAnim.setFloatValues(0.1f, 1.0f);
        valAnim.setDuration(DURATION_MARKER_ANIMATION);
        valAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                highlightedMarkerScale = (Float) animation.getAnimatedValue();
                if (isImageReady())
                    invalidate();
            }
        });
        TimeInterpolator i = new BounceInterpolator();
        valAnim.setInterpolator(i);
        valAnim.setStartDelay(delay);
        valAnim.start();
    }

    public Runnable getScaleAnim(final float scale){
        Runnable anim = new Runnable() {
            public void run() {
                AnimationBuilder animation = animateScale(scale);
                animation
                        .withDuration(200)
                        .withEasing(
                                SubsamplingScaleImageView.EASE_OUT_QUAD)
                        .start();
            }
        };
        return anim;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(getTargetMinScale() > getScale()){
            setScaleAndCenter(getTargetMinScale(), getCenter());
        }
        super.onSizeChanged(w, h, oldw, oldh);

    }

    public float getTargetMinScale() {
        return Math.max(getWidth() / (float) getSWidth(), (getHeight())
                / (float) getSHeight());
    }

    private boolean isInView(PointF point) {
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        int viewX = (int) sourceToViewCoord(point).x;
        int viewY = (int) sourceToViewCoord(point).y;

        if (viewX > -displayWidth / 3 && viewX < displayWidth && viewY > 0
                && viewY < displayHeight)
            return true;

        return false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pin before image is ready so it doesn't move around during
        // setup.
        if (!isImageReady()) {
            return;
        }

        for (Marker marker : markerListHelper.getMarkerList()) {
            PointF point = marker.getPoint();
            int markerType = markerListHelper.getMarkerType(marker);
            if (isInView(point)) {
                if (isShowPinScale(marker)
                        && !(markerListHelper.isResultMarker(marker))
                        && shouldShowUp(marker)) {
                    Bitmap pin = bitmapHelper.drawMarkerBitmap(canvas,
                        marker, markerType, sourceToViewCoord(point));
                    textHelper.drawText(canvas, marker,
                            markerType, sourceToViewCoord(point), pin);
                }
            }
        }

        Marker marker = markerListHelper.getResultMarker();
        if (marker != null) {
            PointF point = marker.getPoint();
            int markerType = markerListHelper.getMarkerType(marker);
            if (isInView(point)) {
                Bitmap pin = bitmapHelper.drawMarkerBitmap(canvas,
                        marker, markerType, sourceToViewCoord(point));
                textHelper.drawText(canvas, marker,
                        markerType, sourceToViewCoord(point), pin);
            }
        }

    }

    public void showResultMarker(Marker resultMarker) {
        if (resultMarker != null) {
            boolean noDelay = false;
            if (isInView(resultMarker.getPoint()))
                noDelay = true;
            AnimationBuilder anim = animateScaleAndCenter(getShowTextScale(),
                    resultMarker.getPoint());
            anim.withDuration(750).start();
            // TODO change sound index
            setMarkerAnimation(noDelay, 1);
        }
    }

    private float getShowTextScale() {
        float xDpi = displayMetrics.xdpi;
        float scale = (RATIO_SHOW_PIN_TEXT * xDpi * 2 / density + 20)
                / getSWidth();
        if (scale > getMaxScale()) {
            scale = 0.7f * getMaxScale();
        }
        return scale;
    }

}