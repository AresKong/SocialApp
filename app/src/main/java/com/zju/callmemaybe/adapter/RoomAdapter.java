package com.zju.callmemaybe.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.zju.callmemaybe.model.MyRoom;
import com.zju.callmemaybe.viewholder.RoomItemHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomItemHolder> {
    private List<HashMap<String,String>> roomList;
    public RoomAdapter() {
        roomList = new ArrayList();
        roomList = MyRoom.getAllRooms();
    }

    @Override
    public RoomItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RoomItemHolder(parent);
    }

    @Override
    public void onBindViewHolder(RoomItemHolder holder, int position) {
        if (position >= 0 && position < roomList.size()){
            holder.bindData(roomList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }
}
