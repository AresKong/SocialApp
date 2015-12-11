package com.zju.callmemaybe.custom;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import de.greenrobot.event.EventBus;

public class BaseFragment extends Fragment {
    protected Context ctx;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ctx = getActivity();
    }
    protected void toast(String str) {
        Toast.makeText(this.getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    protected void toast(int id) {
        Toast.makeText(this.getActivity(), id, Toast.LENGTH_SHORT).show();
    }

    protected boolean filterException(Exception e) {
        if (e != null) {
            toast(e.getMessage());
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
