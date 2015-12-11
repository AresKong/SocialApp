package com.zju.callmemaybe.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.zju.callmemaybe.R;

import java.util.HashMap;

import butterknife.Bind;

public class RoomMemberHolder extends AVCommonViewHolder {

    @Bind(R.id.memberName)
    protected TextView nameView;
    @Bind(R.id.memberPhone)
    protected TextView phoneView;
    @Bind(R.id.memberDist)
    protected TextView distView;

    public RoomMemberHolder(ViewGroup root) {
        super(root.getContext(), root, R.layout.member_item);
    }

    @Override
    public void bindData(Object o) {
        HashMap<String, String> info = (HashMap<String, String>) o;
        String name = info.get("name");
        String phone = info.get("phone");
//        String dist = info.get("dis");
        nameView.setText(name);
        phoneView.setText(phone);
//        distView.setText(dist);
    }

}
