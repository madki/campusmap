package in.workarounds.instimap.helpers.mapfragment;

import android.content.Context;
import android.view.View;
import android.view.animation.Interpolator;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import in.designlabs.instimap.R;
import in.workarounds.instimap.views.EndDetectScrollView;
import in.workarounds.instimap.views.SlidingUpPanelLayout;

public class CardSlideListener implements SlidingUpPanelLayout.PanelSlideListener,
		AnimatorUpdateListener {
	private Context context;
	private in.workarounds.instimap.views.SlidingUpPanelLayout slidingLayout;
	private in.workarounds.instimap.views.EndDetectScrollView scrollView;
	private ValueAnimator animator;

	private static final long TIME_ANIMATION_SHOW = 250;

	public CardSlideListener(Context context, SlidingUpPanelLayout slidingPaneLayout) {
		this.context = context;
		this.slidingLayout = slidingPaneLayout;
		scrollView = (EndDetectScrollView) slidingPaneLayout
				.findViewById(R.id.new_expanded_place_card_scroll);

		animator = new ValueAnimator();
		animator.addUpdateListener(this);
		Interpolator i = new Interpolator() {
	        public float getInterpolation(float t) {
	            t -= 1.0f;
	            return t * t * t * t * t + 1.0f;
	        }
	    };
		animator.setInterpolator(i);
	}

	@Override
	public void onPanelSlide(View panel, float slideOffset) {

	}

	@Override
	public void onPanelCollapsed(View panel) {
	}

	@Override
	public void onPanelExpanded(View panel) {
		scrollView.requestDisallowInterceptTouchEvent(false);
		scrollView.setScrollingEnabled(true);

    }

	@Override
	public void onPanelAnchored(View panel) {
		scrollView.requestDisallowInterceptTouchEvent(true);
		scrollView.setScrollingEnabled(false);
    }

	@Override
	public void onPanelHidden(View panel) {
	}

	public void dismissCard() {
		animator.cancel();
		int initialPanelHeight = slidingLayout.getPanelHeight();
		int finalPanelHeight = 0;
		animator.setIntValues(initialPanelHeight, finalPanelHeight);
		animator.setDuration(TIME_ANIMATION_SHOW);
		animator.start();
	}

	public void showCard() {
		animator.cancel();
		int initialPanelHeight = slidingLayout.getPanelHeight();
		int finalPanelHeight = context.getResources()
				.getDimensionPixelSize(R.dimen.hidden_card_height);
		animator.setIntValues(initialPanelHeight, finalPanelHeight);
		animator.setDuration(TIME_ANIMATION_SHOW);
		animator.start();
	}

	@Override
	public void onAnimationUpdate(ValueAnimator animator) {
		int panelHeight = (Integer) animator.getAnimatedValue();
		slidingLayout.setPanelHeight(panelHeight);
	}

    public boolean handleBackPress() {
        return slidingLayout.collapsePanel();
    }

}
