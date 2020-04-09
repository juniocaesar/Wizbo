package edu.ftiuksw.tr_pam;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private static final int INTERNET_LOCATION_PERMISSION = 1;

    private static final String TAG = "HomeActivity";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String[] PERMISSIONS = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION};
        if(!Function.hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, INTERNET_LOCATION_PERMISSION);
        }
        getDataFromServer();
    }

    public void getDataFromServer() {
        APIList apis = RetrofitClient.getRetrofitclient().create(APIList.class);
        Call<ArrayList<Provinsi>> call = apis.getAllProvinsi();
        call.enqueue(new Callback<ArrayList<Provinsi>>() {
            @Override
            public void onResponse(Call<ArrayList<Provinsi>> call, Response<ArrayList<Provinsi>> response) {
                ArrayList<Provinsi> data = response.body();

                for (int i = 0; i < data.size(); i++) {
                    Provinsi provinsi = data.get(i);
                    mNames.add(provinsi.getNama());
                    mImageUrls.add(provinsi.getImageurl());
                }
                initRecyclerView();
            }

            @Override
            public void onFailure(Call<ArrayList<Provinsi>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Ambil API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.homeRecyclerView);
        ProvinceAdapter adapter = new ProvinceAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
