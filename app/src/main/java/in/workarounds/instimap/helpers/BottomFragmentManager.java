package in.workarounds.instimap.helpers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import in.designlabs.instimap.R;
import in.workarounds.instimap.fragments.MapFragment;

public class BottomFragmentManager {
    private Context context;
    private FragmentManager fragmentManager;

    private Fragment activeFragment = null;
    private MapFragment mapFragment;

    public BottomFragmentManager(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public void openMapFragment() {
        openFragment(new MapFragment());
    }

    private void openFragment(Fragment fragment) {
        if(activeFragment != fragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if(activeFragment == null) {
                transaction.add(R.id.bottom_fragment, fragment);
                transaction.commit();
                activeFragment = fragment;
            } else {
                transaction.replace(R.id.bottom_fragment, fragment);
                transaction.commit();
                activeFragment = fragment;
            }
        } else {
            Toast.makeText(context, "Same fragment", Toast.LENGTH_LONG);
        }
    }
}
