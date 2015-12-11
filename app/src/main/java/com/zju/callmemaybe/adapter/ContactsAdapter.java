package com.zju.callmemaybe.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.zju.callmemaybe.model.MyContactList;
import com.zju.callmemaybe.viewholder.ContactsItemHolder;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsItemHolder> {
    private List<String> dataList;

    public ContactsAdapter() {
        dataList = new ArrayList();
        dataList = MyContactList.getContactsName();
    }

    @Override
    public ContactsItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactsItemHolder(parent);
    }

    @Override
    public void onBindViewHolder(ContactsItemHolder holder, int position) {
        if (position >= 0 && position < dataList.size()){
            holder.bindData(dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void sync() {
        dataList = MyContactList.getContactsName();
    }
}
