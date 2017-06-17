package com.bachors.carilirik.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bachors.carilirik.R;
import com.bachors.carilirik.api.LirikApi;
import com.bachors.carilirik.api.LirikInterface;
import com.bachors.carilirik.model.Lirik;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bachors on 6/17/2017.
 */

public class LirikActivity extends AppCompatActivity {

    private final String ApiKey = "AKfycbzeSkwJ2sSzlMAQXy9hU_IrYTIKKVFPG8nighAUOFGHTIdJ-bU";
    private LirikInterface apiService;
    private ProgressDialog pDialog;
    private String judul, lirik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lirik);

        Bundle b = getIntent().getExtras();
        judul = b.getString("judul");
        lirik = b.getString("lirik");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(judul);

        apiService = LirikApi.getClient().create(LirikInterface.class);
        cariLirik();

    }

    private void cariLirik() {
        pDialog = new ProgressDialog(LirikActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        getParam().enqueue(new Callback<Lirik>() {

            @Override
            public void onResponse(Call<Lirik> call, Response<Lirik> response) {
                String status = response.body().getStatus();
                if(status.equals("success")) {
                    TextView vLirik = (TextView) findViewById(R.id.lirik);
                    vLirik.setText(response.body().getData());
                } else {
                    Toast.makeText(getApplicationContext(), "Data tidak ditemukan.", Toast.LENGTH_LONG).show();
                }
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Lirik> call, Throwable t) {
                t.printStackTrace();
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Cek koneksi internet anda.", Toast.LENGTH_LONG).show();
            }
        });

    }

    private Call<Lirik> getParam() {
        return apiService.getLirik(
                ApiKey,
                lirik
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
