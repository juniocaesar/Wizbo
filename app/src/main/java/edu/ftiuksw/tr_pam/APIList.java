package edu.ftiuksw.tr_pam;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
public interface APIList {
    @GET("provinsi")
    Call<ArrayList<Provinsi>> getAllProvinsi();
}
