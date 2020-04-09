package edu.ftiuksw.tr_pam;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TourInputActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private static final String TAG = "TourInputActivity";

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView title, displayDate, displayAddress;
    private FirebaseDatabase database;
    private DatabaseReference Ref;
    private EditText tourEdit;
    private String Name;
    private String Url;
    private Button insertDate;
    private GoogleMap mMap;
    private String strAdd = "";
    private double LAT;
    private double LON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_input);

        Intent intent = getIntent();
        Name = intent.getStringExtra("image_name");
        Url = intent.getStringExtra("image_url");

        title = findViewById(R.id.tourInputProvince);
        title.setText(Name);
        displayAddress = findViewById(R.id.tourInputLokasiTextEdit);
        tourEdit = findViewById(R.id.tourInputJudulEdit);
        database = FirebaseDatabase.getInstance();

        if (intent.getStringExtra("image_name") != null) {
            Ref = database.getReference(Name);
        }
        displayDate = (TextView) findViewById(R.id.tourInputTanggalTextEdit);
        insertDate = (Button) findViewById(R.id.tourInputTanggalButton);
        insertDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(
                        TourInputActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        mDateSetListener,
                        year,
                        month,
                        day);
                dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateDialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                displayDate.setText((month + 1) + "/" + dayOfMonth + "/" + year);
            }
        };
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void addProvinceTour(View view) {
        String tourCache = tourEdit.getText().toString();
        String dateCache = displayDate.getText().toString();
        TourList tl = new TourList(tourCache, dateCache, LAT, LON);
        Ref.child("tour").push().setValue(tl);
        Intent intent = new Intent(this, TourListActivity.class);
        intent.putExtra("image_name", Name);
        intent.putExtra("image_url", Url);
        startActivity(intent);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.clear();
        MarkerOptions markerOptions =
                new MarkerOptions().position(latLng).title(latLng.toString());
        mMap.addMarker(markerOptions);
        LAT = latLng.latitude;
        LON = latLng.longitude;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                displayAddress.setText(strAdd);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);
    }
}
