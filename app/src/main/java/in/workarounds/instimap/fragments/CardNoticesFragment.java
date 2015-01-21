package in.workarounds.instimap.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;
import com.diegocarloslima.fgelv.lib.WrapperExpandableListAdapter;

import in.designlabs.instimap.R;

public class CardNoticesFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_notices, container, false);
        setExpandableListView(rootView);
        return rootView;
    }

    public void setExpandableListView(View rootView) {
        CardNoticesAdapter adapter = new CardNoticesAdapter(getActivity());
        FloatingGroupExpandableListView expandableListView =
                (FloatingGroupExpandableListView) rootView.findViewById(R.id.list_card_notices);
        final WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(adapter);
        expandableListView.setAdapter(wrapperAdapter);
        expandableListView.setDividerHeight(0);
        // expandableListView.setOnChildClickListener(this);
    }
}
