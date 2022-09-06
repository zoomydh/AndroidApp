package com.example.part8_23;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.lab1_map)).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (map != null)
        {
            LatLng latLng = new LatLng(37.56610, 126.978403);
            CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(16f).build();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(position));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
            markerOptions.position(latLng);
            markerOptions.title("서울시청");
            markerOptions.snippet("Tel:01-120");
            map.addMarker(markerOptions);

            String address = "서울특별시 중구 서소문동 37-9";
            MyReverseGeocodingThread reverseThread = new MyReverseGeocodingThread(address);

            reverseThread.start();
        }
    }

    class MyReverseGeocodingThread extends Thread {
        String address;
        public MyReverseGeocodingThread(String address) {
            this.address = address;
        }

        @Override
        public void run() {
            Geocoder geocoder = new Geocoder(MainActivity.this);
            try {
                List<Address> results = geocoder.getFromLocationName(address, 1);
                Address resultAddress = results.get(0);
                LatLng latLng = new LatLng(resultAddress.getLatitude(), resultAddress.getLongitude());
                Message msg = new Message();
                msg.what = 200;
                msg.obj = latLng;
                handler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what)
            {
                case 200:
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                    markerOptions.position((LatLng)message.obj);
                    markerOptions.title("서울시립미술관");
                    map.addMarker(markerOptions);
            }
            return true;
        }
    });
}