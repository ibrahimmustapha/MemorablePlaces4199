package com.example.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    static ArrayList<String> places = new ArrayList<>();
    static  ArrayList<LatLng> locations = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        places.add("Add new place ...");
        locations.add(new LatLng(0, 0));

        SharedPreferences sharedPreferences = this.getSharedPreferences("package com.example.memorableplaces", Context.MODE_PRIVATE);

        ArrayList<String> longitude = new ArrayList<>();
        ArrayList<String> latitude = new ArrayList<>();

        try {
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<String>())));
            longitude = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lons", ObjectSerializer.serialize(new ArrayList<String>())));
            latitude = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lats", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (places.size() > 0 && latitude.size() > 0 && longitude.size() > 0) {
            if (places.size() == latitude.size() && places.size() == longitude.size()) {
                for (int i=0; i <latitude.size(); i++) {
                    locations.add(new LatLng(Double.parseDouble(latitude.get(i)), Double.parseDouble(longitude.get(i))));
                }
            } else {
                places.add("Add new place ...");
                locations.add(new LatLng(0, 0));
            }
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, places);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("places",i);
                startActivity(intent);
            }
        });

    }
}