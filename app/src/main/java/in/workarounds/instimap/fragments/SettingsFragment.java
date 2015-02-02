package in.workarounds.instimap.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import in.designlabs.instimap.R;

public class SettingsFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem index = menu.findItem(R.id.index_icon);
        index.setVisible(false);
        MenuItem search = menu.findItem(R.id.search_icon);
        search.setVisible(false);
        MenuItem settings = menu.findItem(R.id.settings_icon);
        settings.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
