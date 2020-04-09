package edu.ftiuksw.tr_pam;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TourListActivity extends AppCompatActivity {

    private static final String TAG = "TourListActivity";
    private String ImageName;
    private String ImageUrl;
    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mDate = new ArrayList<>();
    private ArrayList<String> mLoc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_list);
        getIncomingIntent();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference(ImageName);

        Ref.child("tour").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TourList tl = snapshot.getValue(TourList.class);
                    mTitle.add(tl.getTitle());
                    mDate.add(tl.getDate());
                    mLoc.add(getCompleteAddressString(tl.getLatitude(), tl.getLongitude()));
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void getIncomingIntent() {

        if (getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")) {

            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");
            this.ImageName = imageName;
            this.ImageUrl = imageUrl;
            setImage(imageUrl, imageName);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    public void addTour(View view) {
        Intent intent = new Intent(this, TourInputActivity.class);
        intent.putExtra("image_name", ImageName);
        intent.putExtra("image_url", ImageUrl);
        startActivity(intent);
    }

    private void setImage(String imageUrl, String imageName) {
        TextView name = findViewById(R.id.tourListProvinceText);
        name.setText(imageName);

        ImageView image = findViewById(R.id.tourListProvinceImage);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.tourListRecyclerView);
        TourListAdapter adapter = new TourListAdapter(this, ImageUrl, ImageName, mTitle, mDate, mLoc);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }
}
