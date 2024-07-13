package com.Ex.CarGame_part2.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Ex.CarGame_part2.R;
import com.Ex.CarGame_part2.model.Player;
import com.Ex.CarGame_part2.model.Records;
import com.Ex.CarGame_part2.utils.MySPv;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private com.Ex.CarGame_part2.views.PlayerAdapter adapter;
    private final List<Player> playerList = new ArrayList<>();

    public void setCallback() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // Initialize the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new com.Ex.CarGame_part2.views.PlayerAdapter(playerList);
        recyclerView.setAdapter(adapter);

        // Load the player data from SharedPreferences
        loadPlayerData();

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadPlayerData() {
        String impGson = MySPv.getInstance().getString(MySPv.getInstance().getMyKey(), "");
        Records recs = new Gson().fromJson(impGson, Records.class);
        if (recs != null) {
            playerList.clear();
            playerList.addAll(recs.getRecords());
            adapter.notifyDataSetChanged();
        }
    }
}
