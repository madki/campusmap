package in.workarounds.instimap.fragments.notice;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NoticesFragmentPagerAdapter  extends FragmentPagerAdapter {
    private String[] tabs = new String[] {"Events", "Updates"};

    public NoticesFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NoticeListFragment.newInstance(NoticeListFragment.EVENTS);
            case 1:
                return NoticeListFragment.newInstance(NoticeListFragment.UPDATES);
            default:
                return new NoticeListFragment().newInstance(NoticeListFragment.DEFAULT_TYPE);
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
