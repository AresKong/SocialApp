package com.zju.callmemaybe.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.zju.callmemaybe.model.RoomMember;
import com.zju.callmemaybe.viewholder.RoomMemberHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomMemberAdapter extends RecyclerView.Adapter<RoomMemberHolder>{
    private List<HashMap<String,String>> infoList;

    public RoomMemberAdapter() {
        infoList = new ArrayList();
        infoList = RoomMember.getMemberInfo();
    }

    @Override
    public RoomMemberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RoomMemberHolder(parent);
    }

    @Override
    public void onBindViewHolder(RoomMemberHolder holder, int position) {
        if (position >= 0 && position < infoList.size())
            infoList.get(position);
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }
}
