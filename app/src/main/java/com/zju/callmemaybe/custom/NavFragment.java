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
import android.widget.TextView;

import com.zju.callmemaybe.R;
import com.zju.callmemaybe.adapter.ContactsAdapter;
import com.zju.callmemaybe.adapter.RoomAdapter;
import com.zju.callmemaybe.model.MyUser;

public class NavFragment extends BaseFragment {
    public static final String ARG_POSITION = "layout_id";
    private static final String TAG="NavFragment";
    protected SwipeRefreshLayout contactsSwipeRefreshLayout;
    protected SwipeRefreshLayout roomSwipeRefreshLayout;
    protected ContactsAdapter contactsAdapter;
    protected RoomAdapter roomAdapter;

    public NavFragment() {
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
        int position = getArguments().getInt(ARG_POSITION);
        View view;
        switch (position){
            case R.id.contacts_layout:
                Log.i(TAG, "CreateView: contacts");
                view = inflater.inflate(R.layout.fragment_contacts, container,false);
                RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.fragment_contact_list);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                contactsAdapter = new ContactsAdapter();
                recyclerView.setAdapter(contactsAdapter);
                contactsSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_contact_pullRefresh);
                break;

            case R.id.aboutme_layout:
                Log.i(TAG, "CreateView: aboutMe");
                view = inflater.inflate(R.layout.fragment_aboutme, container,false);
                ((TextView)view.findViewById(R.id.userInfoName)).setText(MyUser.getCurrentUser().getUsername());
                break;

            case R.id.room_layout:
                Log.i(TAG, "CreateView: room");
                view = inflater.inflate(R.layout.fragment_room, container,false);
                RecyclerView roomRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_room_list);
                LinearLayoutManager roomLayoutManager = new LinearLayoutManager(getActivity());
                roomRecyclerView.setLayoutManager(roomLayoutManager);
                roomAdapter = new RoomAdapter();
                roomRecyclerView.setAdapter(roomAdapter);
                roomSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_room_pullRefresh);
                break;
            default:
                view = inflater.inflate(R.layout.fragment_aboutme, container,false);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        int position = getArguments().getInt(ARG_POSITION);
        switch (position) {
            case R.id.contacts_layout:
                contactsSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    contactsAdapter.sync();
                    contactsSwipeRefreshLayout.setRefreshing(false);
                }
            });
                break;
            case R.id.aboutme_layout:
                break;

            case R.id.room_layout:
                break;
            default:
                ;
        }
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
