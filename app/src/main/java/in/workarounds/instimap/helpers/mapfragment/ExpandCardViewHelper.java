package in.workarounds.instimap.helpers.mapfragment;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.View;

import de.greenrobot.event.EventBus;
import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.models.Marker;

public class ExpandCardViewHelper {
    private Context context;
    private View expandCardView;
    private FragmentManager cardFragmentManager;
    private Marker resultMarker = null;
//    private SlidingTabLayout slidingTabLayout;
    private PagerTitleStrip pagerTitleStrip;

    public ExpandCardViewHelper(Context context, View expandCardView, FragmentManager cardFragmentManger) {
        this.context = context;
        this.expandCardView = expandCardView;
        this.cardFragmentManager = cardFragmentManger;

        setUp();
    }

    private void setUp() {
        ViewPager viewPager = (ViewPager) expandCardView.findViewById(R.id.card_view_pager);
        CardPagerAdapter cardPagerAdapter = new CardPagerAdapter(cardFragmentManager);
        viewPager.setAdapter(cardPagerAdapter);

        pagerTitleStrip = (PagerTitleStrip) expandCardView.findViewById(R.id.pager_title_strip);
//        slidingTabLayout = (SlidingTabLayout) expandCardView.findViewById(R.id.card_sliding_tabs);
//        slidingTabLayout.setDistributeEvenly(true);
//        slidingTabLayout.setViewPager(viewPager);

        setEventBus();
    }

    private void setEventBus() {
        EventBus eventBus = EventBus.getDefault();
        eventBus.registerSticky(this);

        StickyEvents.CurrentMarkerEvent event = eventBus.getStickyEvent(StickyEvents.CurrentMarkerEvent.class);
        if(event != null) {
            resultMarker = event.marker;
            setColor();
        }
    }

    public void onEventMainThread(StickyEvents.CurrentMarkerEvent event) {
        resultMarker = event.marker;
        setColor();
    }

    private void setColor() {
        if(resultMarker!=null) {
            final int color = resultMarker.getColor();
            pagerTitleStrip.setBackgroundColor(color);
//            slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//                @Override
//                public int getIndicatorColor(int position) {
//                    return color;
//                }
//            });
        }
    }
}
