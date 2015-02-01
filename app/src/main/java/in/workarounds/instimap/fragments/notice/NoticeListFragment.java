package in.workarounds.instimap.fragments.notice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;
import com.diegocarloslima.fgelv.lib.WrapperExpandableListAdapter;

import in.designlabs.instimap.R;

public class NoticeListFragment extends Fragment{
    public static int EVENTS = 0;
    public static int UPDATES = 1;
    public static int MARKER_EVENTS = 2;
    public static int DEFAULT_TYPE = EVENTS;
    public static String FRAGMENT_TYPE = "fragment_type";

    public static NoticeListFragment newInstance(int type) {
        NoticeListFragment noticeListFragment = new NoticeListFragment();

        Bundle args = new Bundle();
        args.putInt(FRAGMENT_TYPE, type);
        noticeListFragment.setArguments(args);

        return noticeListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notice_list, container, false);
        Bundle bundle = getArguments();
        int fragment_type = DEFAULT_TYPE;
        if(bundle!=null) {
            fragment_type = bundle.getInt(FRAGMENT_TYPE, DEFAULT_TYPE);
        }
        setExpandableListView(rootView, fragment_type);
        return rootView;
    }

    public void setExpandableListView(View rootView, int fragment_type) {
        NoticeListAdapter adapter = new NoticeListAdapter(getActivity(), fragment_type);
        FloatingGroupExpandableListView expandableListView =
                (FloatingGroupExpandableListView) rootView.findViewById(R.id.list_card_notices);
        final WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(adapter);
        expandableListView.setAdapter(wrapperAdapter);
        expandableListView.setDividerHeight(0);
        // expandableListView.setOnChildClickListener(this);
    }

}
