package in.workarounds.instimap.helpers.mapfragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.workarounds.instimap.fragments.map.CardDetailsFragment;
import in.workarounds.instimap.fragments.notice.NoticeListFragment;

public class CardPagerAdapter extends FragmentPagerAdapter {
    private String[] tabs = new String[] {"Details", "Notices"};

    public CardPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CardDetailsFragment();
            case 1:
                return NoticeListFragment.newInstance(NoticeListFragment.MARKER_EVENTS);
            default:
                return new CardDetailsFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
