package in.workarounds.instimap.fragments.notice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.designlabs.instimap.R;
import in.workarounds.instimap.bus.EventFragment;
import in.workarounds.instimap.bus.StickyEvents;
import in.workarounds.instimap.helpers.CustomSpinnerAdapter;
import in.workarounds.instimap.models.Board;
import in.workarounds.instimap.models.Corner;
import in.workarounds.instimap.models.CornerRelation;
import in.workarounds.instimap.views.slidingtabs.SlidingTabLayout;

public class NoticesFragment extends EventFragment {
    private CustomSpinnerAdapter boardSpinnerAdapter;
    private CustomSpinnerAdapter cornerSpinnerAdapter;
    private Spinner cornerSpinner;
    private Spinner boardSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notices, container, false);
        setUp(rootView);
        return rootView;
    }

    @Override
    protected boolean shouldRegisterSticky() {
        return true;
    }

    private void setUp(View rootView) {
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        NoticesFragmentPagerAdapter noticesFragmentPagerAdapter = new NoticesFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(noticesFragmentPagerAdapter);

//        pagerTitleStrip = (PagerTitleStrip) expandCardView.findViewById(R.id.pager_title_strip);
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);

        setUpSpinners(rootView);
    }

    private void setUpSpinners(View rootView) {
        final EventBus eventBus = EventBus.getDefault();
        boardSpinner  = (Spinner) rootView.findViewById(R.id.board_spinner);
        cornerSpinner = (Spinner) rootView.findViewById(R.id.corner_spinner);
        cornerSpinner.setEnabled(false);

        boardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventBus.postSticky(new StickyEvents.BoardSpinnerEvent(boardSpinnerAdapter.getId(position)));
                eventBus.postSticky(new StickyEvents.CornerSpinnerEvent(0));
                Log.d("NoticesFragment", "selected: ");
                cornerSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cornerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventBus.postSticky(new StickyEvents.CornerSpinnerEvent(cornerSpinnerAdapter.getId(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        boardSpinnerAdapter = new CustomSpinnerAdapter(getActivity());
        cornerSpinnerAdapter = new CustomSpinnerAdapter(getActivity());
        setBoardSpinnerAdapterList();

        StickyEvents.BoardSpinnerEvent boardSpinnerEvent = eventBus.getStickyEvent(StickyEvents.BoardSpinnerEvent.class);
        if(boardSpinnerEvent != null) {
            long boardId = boardSpinnerEvent.boardId;
            int boardPosition = boardSpinnerAdapter.getItemPositionById(boardId);
            boardSpinner.setSelection(boardPosition);
            setCornerSpinnerAdapterList(boardId);
        } else {
            setCornerSpinnerAdapterList(0);
        }

        StickyEvents.CornerSpinnerEvent cornerSpinnerEvent = eventBus.getStickyEvent(StickyEvents.CornerSpinnerEvent.class);
        if(cornerSpinnerEvent!=null) {
            long cornerId = cornerSpinnerEvent.cornerId;
            cornerSpinner.setSelection(cornerSpinnerAdapter.getItemPositionById(cornerId));
        }

        boardSpinner.setAdapter(boardSpinnerAdapter);
        cornerSpinner.setAdapter(cornerSpinnerAdapter);
    }

    public void onEventMainThread(StickyEvents.BoardSpinnerEvent event) {
        long boardId = event.boardId;
        setCornerSpinnerAdapterList(boardId);
    }

    private void setBoardSpinnerAdapterList() {
        List<Board> boards = Board.listAll(Board.class);
        boardSpinnerAdapter.setSpinnerItems(boards);
    }

    private void setCornerSpinnerAdapterList(long boardId) {
        if(boardId > 0) {
            // List<Corner> corners = Corner.listAll(Corner.class);
            List<CornerRelation> cornerRelations = CornerRelation.find(CornerRelation.class, "board_id = ?", Long.toString(boardId));
            List<Corner> corners = new ArrayList<>();
            for(CornerRelation cornerRelation: cornerRelations) {
                if(cornerRelation.isChild()) {
                    List<Corner> cornerList = Corner.find(Corner.class, "db_id = ?", Long.toString(cornerRelation.getCornerId()));
                    corners.addAll(cornerList);
                }
            }
            cornerSpinnerAdapter.setSpinnerItems(corners);
            cornerSpinner.setEnabled(true);
        } else {
            cornerSpinnerAdapter.setSpinnerItems(new ArrayList<Corner>());
            cornerSpinner.setEnabled(false);
        }
    }

}
