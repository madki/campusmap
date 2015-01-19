package in.workarounds.instimap.views;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.designlabs.instimap.R;

public class SubHeadingView extends LinearLayout {
    private TextView normalText;
    private TextView linkedText;

    public SubHeadingView(Context context) {
        super(context);
    }

    public SubHeadingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_sub_heading, this, true);

        normalText = (TextView) getChildAt(0);
        linkedText = (TextView) getChildAt(1);

    }

    public void setNormalText(String text) {
        normalText.setText(text);
    }

    public void setLinkedText(String text) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        linkedText.setText(content);
    }

    public void setLinkTarget(OnClickListener listener) {
        linkedText.setOnClickListener(listener);
    }
}
