package in.workarounds.instimap.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import in.designlabs.instimap.R;

public class TypeFacedTextView extends TextView {
    private String FONT_BOLD;
    private String FONT_REGULAR;
    private String FONT_LIGHT;

    private static final int LIGHT = 0;
    private static final int REGULAR = 1;
    private static final int BOLD = 2;

    private int typeFace = REGULAR;

    public TypeFacedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getFontFaces(context);
        applyAttributes(context, attrs);
    }

    public TypeFacedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getFontFaces(context);
        applyAttributes(context, attrs);
    }

    public TypeFacedTextView(Context context) {
        super(context);
        getFontFaces(context);
        setType(context);
    }

    private void applyAttributes(Context context, AttributeSet attrs){
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TypeFacedTextView,
                0, 0);

        try {
            typeFace = a.getInt(R.styleable.TypeFacedTextView_typeFace, REGULAR);
        } finally {
            a.recycle();
        }
        setType(context);
    }

    private void getFontFaces(Context context) {
        FONT_BOLD = context.getString(R.string.font_bold);
        FONT_REGULAR = context.getString(R.string.font_regular);
        FONT_LIGHT = context.getString(R.string.font_light);
    }

    private void setType(Context context){
        if(typeFace == REGULAR) {
            this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                    FONT_REGULAR));
        } else if(typeFace == BOLD) {
            this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                    FONT_BOLD));
        } else if(typeFace == LIGHT) {
            this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                    FONT_LIGHT));
        }
    }
}
