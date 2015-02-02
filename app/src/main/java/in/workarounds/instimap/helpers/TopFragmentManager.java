package in.workarounds.instimap.helpers;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import in.designlabs.instimap.R;
import in.workarounds.instimap.fragments.map.IndexFragment;
import in.workarounds.instimap.fragments.map.SearchFragment;

public class TopFragmentManager {
    private Context context;
    private FragmentManager fragmentManager;

    private Fragment activeFragment = null;
    private SearchFragment searchFragment;
    private IndexFragment indexFragment;

    private String FIRST_STACK_TAG = "first_tag";

    public TopFragmentManager(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;

        searchFragment = new SearchFragment();
        indexFragment = new IndexFragment();
    }

    public void openSearchFragment() {
        openFragment(searchFragment);
    }

    public void openIndexFragment() {
       openFragment(indexFragment);
    }

    private void openFragment(Fragment fragment) {
        if(activeFragment != fragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if(activeFragment == null) {
                transaction.add(R.id.top_fragment, fragment);
                transaction.addToBackStack(FIRST_STACK_TAG);
                transaction.commit();
                activeFragment = fragment;
            } else {
                transaction.replace(R.id.top_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                activeFragment = fragment;
            }
        } else {
            Toast.makeText(context, "Same fragment", Toast.LENGTH_LONG);
        }
    }

    public boolean closeTopFragments() {
        if(activeFragment!=null) {
            fragmentManager.popBackStack(FIRST_STACK_TAG,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
            activeFragment = null;
            return true;
        } else {
            return false;
        }
    }

    public boolean handleBackPress() {
        return closeTopFragments();
    }

}
