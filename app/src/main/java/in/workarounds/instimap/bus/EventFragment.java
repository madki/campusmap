package in.workarounds.instimap.bus;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.greenrobot.event.EventBus;

public class EventFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(shouldRegisterSticky()) {
            EventBus.getDefault().registerSticky(this);
        } else {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * override this to listen to sticky events
     * @return
     */
    protected boolean shouldRegisterSticky() {
        return false;
    }
}
