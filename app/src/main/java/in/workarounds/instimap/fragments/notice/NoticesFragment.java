package in.workarounds.instimap.fragments.notice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.designlabs.instimap.R;
import in.workarounds.instimap.views.slidingtabs.SlidingTabLayout;

public class NoticesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_titled_view_pager, container, false);
        setUp(rootView);
        return rootView;
    }

    private void setUp(View rootView) {
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.card_view_pager);
        NoticesFragmentPagerAdapter noticesFragmentPagerAdapter = new NoticesFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(noticesFragmentPagerAdapter);

//        pagerTitleStrip = (PagerTitleStrip) expandCardView.findViewById(R.id.pager_title_strip);
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.card_sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
    }

}
