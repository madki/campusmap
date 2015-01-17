package in.workarounds.instimap.helpers.mapview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;

import in.designlabs.instimap.R;
import in.workarounds.instimap.models.Marker;

public class TextHelper {
    private Paint textPaint;
    private Paint strokePaint;
    private Rect bounds = new Rect();
    private float density;

    public TextHelper(Context context, DisplayMetrics displayMetrics) {
        this.density = displayMetrics.density;
        initPaints(context);
    }

    private void initPaints(Context context) {
        setTextPaint(context);
        setStrokePaint();
    }

    private void setTextPaint(Context context) {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setShadowLayer(8.0f * density, -1 * density, 1 * density,
                Color.BLACK);
        textPaint.setTextSize(16 * density);
        Typeface boldCn = Typeface.createFromAsset(context.getAssets(),
                context.getString(R.string.font_bold));
        textPaint.setTypeface(boldCn);
    }

    private void setStrokePaint() {
        strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setTypeface(Typeface.DEFAULT_BOLD);
        strokePaint.setTextSize(14 * density);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeJoin(Paint.Join.ROUND);
        strokePaint.setStrokeWidth(0.2f * density);
    }

    public void drawText(Canvas canvas, Marker marker, int markerType, PointF point, Bitmap pin) {
        if(markerType > MarkerListHelper.NORMAL_MARKER) {
            drawPinText(canvas, marker, point);
        } else {
            drawPointerText(canvas, marker, point, pin);
        }
    }

    private void drawPinText(Canvas canvas, Marker marker, PointF point) {
        String name;
        if (marker.getShortName().equals("0") || marker.getShortName().isEmpty())
            name = marker.getName();
        else
            name = marker.getShortName();
        textPaint.getTextBounds(name, 0, name.length() - 1, bounds);
        float tX = point.x - bounds.width() / 2;
        float tY = point.y + bounds.height();
        canvas.drawText(name, tX, tY, textPaint);
    }

    private void drawPointerText(Canvas canvas, Marker marker, PointF point, Bitmap pin) {
        String name;
        if (marker.getShortName().equals("0") || marker.getShortName().isEmpty())
            name = marker.getName();
        else
            name = marker.getShortName();
        Paint temp = new Paint(textPaint);
        if(marker.getGroupIndex() == Marker.RESIDENCES) temp.setTextSize(12*density);
        textPaint.getTextBounds(name, 0, name.length() - 1, bounds);
        float tX = point.x + pin.getWidth();
        float tY = point.y + bounds.height() / 2;
        canvas.drawText(name, tX, tY, temp);
    }
}
