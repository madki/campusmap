package in.workarounds.instimap.helpers.mapview;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.DisplayMetrics;

import in.designlabs.instimap.R;
import in.workarounds.instimap.models.Marker;
import in.workarounds.instimap.views.mapview.CampusMapView;

public class BitmapHelper {
    private Paint paint;
    private Bitmap bluePointer;
    private Bitmap yellowPointer;
    private Bitmap greenPointer;
    private Bitmap grayPointer;
    private Bitmap blueMarker;
    private Bitmap yellowMarker;
    private Bitmap greenMarker;
    private Bitmap grayMarker;
    private Bitmap blueLockedMarker;
    private Bitmap blueNoticeMarker;
    private Bitmap yellowLockedMarker;
    private Bitmap greenLockedMarker;
    private Bitmap grayLockedMarker;

    public BitmapHelper(Context context, DisplayMetrics displayMetrics) {
        setUp();
        initMarkers(context, displayMetrics);
    }

    private void setUp() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public Bitmap drawMarkerBitmap(Canvas canvas, Marker marker, int markerType, PointF point) {
        Bitmap pin;
        if(markerType > MarkerListHelper.NORMAL_MARKER) {
            pin = drawMarkerPin(canvas, marker, markerType, point);
        } else {
            pin = drawMarkerPointer(canvas, marker, point);
        }
        return pin;
    }

    private Bitmap drawMarkerPin(Canvas canvas, Marker marker, int markerType, PointF point) {
        Bitmap highlightedPin = getMarkerBitmap(marker, markerType);
        float vX = point.x - (highlightedPin.getWidth() / 2);
        float vY = point.y - highlightedPin.getHeight();
        canvas.drawBitmap(highlightedPin, vX, vY, paint);
        return highlightedPin;
    }

    private Bitmap drawMarkerPointer(Canvas canvas, Marker marker, PointF point) {
        Bitmap pin = getPointerBitmap(marker);
        float vX = point.x - (pin.getWidth() / 2);
        float vY = point.y - (pin.getHeight() / 2);
        canvas.drawBitmap(pin, vX, vY, paint);
        return pin;
    }

    private Bitmap getPointerBitmap(Marker marker) {
        int color = marker.getColor();

        if (color == Marker.COLOR_BLUE) {
            return bluePointer;
        } else if (color == Marker.COLOR_YELLOW) {
            return yellowPointer;
        } else if (color == Marker.COLOR_GREEN) {
            return greenPointer;
        } else if (color == Marker.COLOR_GRAY) {
            return grayPointer;
        }

        return bluePointer;
    }

    private Bitmap getMarkerBitmap(Marker marker, int markerType) {
        int color = marker.getColor();

        Bitmap markerBitmap = null;

        if (color == Marker.COLOR_BLUE) {
            markerBitmap = blueMarker;
            if (MarkerListHelper.isAddedMarker(markerType)){
                markerBitmap = blueLockedMarker;
            }
            if(MarkerListHelper.isNoticeMarker(markerType)) {
                markerBitmap = blueNoticeMarker;
            }
        } else if (color == Marker.COLOR_YELLOW) {
            markerBitmap = yellowMarker;
            if (MarkerListHelper.isAddedMarker(markerType)) {
                markerBitmap = yellowLockedMarker;
            }
            if(MarkerListHelper.isNoticeMarker(markerType)) {
                markerBitmap = blueNoticeMarker;
            }
        } else if (color == Marker.COLOR_GREEN) {
            markerBitmap = greenMarker;
            if (MarkerListHelper.isAddedMarker(markerType)) {
                markerBitmap = greenLockedMarker;
            }
            if(MarkerListHelper.isNoticeMarker(markerType)) {
                markerBitmap = blueNoticeMarker;
            }
        } else if (color == Marker.COLOR_GRAY) {
            markerBitmap = grayMarker;
            if (MarkerListHelper.isAddedMarker(markerType)) {
                markerBitmap = grayLockedMarker;
            }
            if(MarkerListHelper.isNoticeMarker(markerType)) {
                markerBitmap = blueNoticeMarker;
            }
        }

        if (CampusMapView.highlightedMarkerScale != 1.0f && MarkerListHelper.isResultMarker(markerType)) {
            float w = markerBitmap.getWidth() * CampusMapView.highlightedMarkerScale;
            float h = markerBitmap.getHeight() * CampusMapView.highlightedMarkerScale;
            markerBitmap = Bitmap.createScaledBitmap(markerBitmap, (int) w,
                    (int) h, true);
        }

        if (MarkerListHelper.isResultMarker(markerType)) {
            float w = markerBitmap.getWidth() * 1.2f;
            float h = markerBitmap.getHeight() * 1.2f;
            markerBitmap = Bitmap.createScaledBitmap(markerBitmap, (int) w,
                    (int) h, true);
        }

        return markerBitmap;
    }

    private void initMarkers(Context context, DisplayMetrics displayMetrics) {
        float w = 0;
        float h = 0;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;

        bluePointer = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker_dot_blue, options);
        blueMarker = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker_blue_s, options);
        blueLockedMarker = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker_blue_h, options);
        blueNoticeMarker = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker_blue_h_notice, options);

        yellowPointer = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker_dot_yellow, options);
        yellowMarker = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker_yellow_s, options);
        yellowLockedMarker = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker_yellow_h, options);

        greenPointer = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker_dot_green, options);
        greenMarker = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker_green_s, options);
        greenLockedMarker = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker_green_h, options);

        grayPointer = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker_dot_gray, options);
        grayMarker = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker_gray_s, options);
        grayLockedMarker = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker_gray_h, options);
        w = in.workarounds.instimap.views.mapview.CampusMapView.pointerWidth*displayMetrics.density;
        h = bluePointer.getScaledHeight(displayMetrics) * (w / bluePointer.getScaledWidth(displayMetrics));

        bluePointer = Bitmap.createScaledBitmap(bluePointer, (int) w, (int) h,
                true);
        bluePointer = Bitmap.createScaledBitmap(bluePointer, (int) w, (int) h,
                true);
        yellowPointer = Bitmap.createScaledBitmap(yellowPointer, (int) w,
                (int) h, true);
        greenPointer = Bitmap.createScaledBitmap(greenPointer, (int) w,
                (int) h, true);
        grayPointer = Bitmap.createScaledBitmap(grayPointer, (int) w, (int) h,
                true);
        w = 4f * w;
        h = blueMarker.getScaledHeight(displayMetrics) * (w / blueMarker.getScaledWidth(displayMetrics));
        blueMarker = Bitmap.createScaledBitmap(blueMarker, (int) w, (int) h,
                true);
        yellowMarker = Bitmap.createScaledBitmap(yellowMarker, (int) w,
                (int) h, true);
        greenMarker = Bitmap.createScaledBitmap(greenMarker, (int) w, (int) h,
                true);
        grayMarker = Bitmap.createScaledBitmap(grayMarker, (int) w, (int) h,
                true);
        blueLockedMarker = Bitmap.createScaledBitmap(blueLockedMarker, (int) w,
                (int) h, true);
        blueNoticeMarker = Bitmap.createScaledBitmap(blueNoticeMarker, (int) w,
                (int) h, true);
        yellowLockedMarker = Bitmap.createScaledBitmap(yellowLockedMarker,
                (int) w, (int) h, true);
        greenLockedMarker = Bitmap.createScaledBitmap(greenLockedMarker,
                (int) w, (int) h, true);
        grayLockedMarker = Bitmap.createScaledBitmap(grayLockedMarker, (int) w,
                (int) h, true);
    }
}
