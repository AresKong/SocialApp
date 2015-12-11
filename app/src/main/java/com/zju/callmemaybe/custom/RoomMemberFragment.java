package com.zju.callmemaybe.custom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zju.callmemaybe.R;
import com.zju.callmemaybe.adapter.RoomMemberAdapter;

public class RoomMemberFragment extends BaseFragment{
    private static final String TAG="RoomMemberFragment";
    protected SwipeRefreshLayout memberSwipeRefreshLayout;
    protected RoomMemberAdapter roomMemberAdapter;
    protected RecyclerView recyclerView;
    public RoomMemberFragment() {
        // Empty constructor required for fragment subclasses
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Created");
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_room_member, container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.fragment_member_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        roomMemberAdapter = new RoomMemberAdapter();
        recyclerView.setAdapter(roomMemberAdapter);
        memberSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_member_pullRefresh);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        memberSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                memberSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
