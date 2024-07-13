package com.example.classone.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.classone.R;
import com.example.classone.model.Player;
import com.example.classone.model.Records;
import com.example.classone.utils.MySPv;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap gMap;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;
        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(),R.raw.map_style));
        String impGson = MySPv.getInstance().getString(MySPv.getInstance().getMyKey(), "");
        Records recs = new Gson().fromJson(impGson,Records.class);
        if (recs == null){
            recs = new Records();
        }
        setOnMap(recs.getRecords());

    }

    public void zoom(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude,longitude);
        gMap.addMarker(new MarkerOptions().position(latLng));
        CameraPosition cPos = new CameraPosition.Builder().target(latLng).zoom(16).build();
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cPos));
    }

    public void setOnMap(ArrayList<Player> players){
        for (Player p :players) {
            LatLng latLng = new LatLng(p.getLatitude(),p.getLongitude());
            gMap.addMarker(new MarkerOptions().position(latLng));
        }
    }
}
