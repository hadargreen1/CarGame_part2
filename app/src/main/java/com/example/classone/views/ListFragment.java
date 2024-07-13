package com.example.classone.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classone.R;
import com.example.classone.interfaces.UserProtocolCallBack;
import com.example.classone.model.Records;
import com.example.classone.utils.MySPv;
import com.example.classone.utils.PlayerAdapter;
import com.google.gson.Gson;

public class ListFragment extends Fragment {

    private RecyclerView scoreRecycler;

    private UserProtocolCallBack callback;

    public void setCallback(UserProtocolCallBack callback){
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        scoreRecycler = view.findViewById(R.id.main_LST_players);
        String impGson = MySPv.getInstance().getString(MySPv.getInstance().getMyKey(), "");
        Records recs = new Gson().fromJson(impGson,Records.class);
        if (recs == null){
            recs = new Records();
        }
        Context context = getContext();
        PlayerAdapter playerAdapter = new PlayerAdapter(context,recs.getRecords());
        playerAdapter.setRecordCallBack((player, position) -> callback.sendLocation(player.getLatitude(),player.getLongitude()));
        scoreRecycler.setLayoutManager(new LinearLayoutManager(context));
        scoreRecycler.setAdapter(playerAdapter);
        return view;
    }

}
