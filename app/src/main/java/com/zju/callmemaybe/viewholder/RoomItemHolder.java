package com.zju.callmemaybe.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zju.callmemaybe.R;

import java.util.HashMap;

import butterknife.Bind;

public class RoomItemHolder extends AVCommonViewHolder {
    @Bind(R.id.hostName)
    protected TextView nameView;
    @Bind(R.id.roomNote)
    protected TextView noteView;
    @Bind(R.id.roomTopic)
    protected TextView topicView;

    public RoomItemHolder(ViewGroup root) {
        super(root.getContext(), root, R.layout.room_item);
    }

    @Override
    public void bindData(Object o) {
        HashMap<String, String> room = (HashMap<String, String>) o;
        String host = room.get("host");
        String note = room.get("note");
        String topic = room.get("topic");
//        final String objectId = room.get("objectId");
        nameView.setText("房主："+host);
        noteView.setText(note);
        topicView.setText("主题："+topic);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), Room.class);
//                intent.putExtra("objectId", objectId);
                Toast.makeText(getContext(), "进入房间", Toast.LENGTH_SHORT).show();
//                getContext().startActivity(intent);
            }
    });
    }
}
