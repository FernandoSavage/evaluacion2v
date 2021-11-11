package com.example.evaluacion2;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.evaluacion2.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String recibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recibido = getIntent().getStringExtra("ciudad");

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if ("arica".equals(recibido)){
            LatLng arica = new LatLng(-18.4746, -70.29792);
            mMap.addMarker(new MarkerOptions().position(arica)
                    .title("Ciudad de la eterna primavera")
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_arica))
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arica, 15));
        }

        if ("iquique".equals(recibido)){
            LatLng iquique = new LatLng(-20.22036, -70.13913);
            mMap.addMarker(new MarkerOptions().position(iquique)
                .title("Tierra de campeones")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_iquique))
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iquique, 15));
        }

        if("santiago".equals(recibido)){
            LatLng santiago = new LatLng(-33.45694,-70.64827);
            mMap.addMarker(new MarkerOptions().position(santiago)
                .title("Capital de Chile")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_santiago))
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santiago, 15));
        }
    }
}