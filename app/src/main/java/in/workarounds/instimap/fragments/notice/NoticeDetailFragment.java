package in.workarounds.instimap.fragments.notice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import in.designlabs.instimap.R;

/**
 * Created by manidesto on 02/02/15.
 */
public class NoticeDetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notice_detail, container, false);
        ListView updatesListView = (ListView)rootView.findViewById(R.id.lv_updates);
        NoticeDetailAdapter adapter = new NoticeDetailAdapter(getActivity());
        updatesListView.setAdapter(adapter);
        return rootView;
    }

}
