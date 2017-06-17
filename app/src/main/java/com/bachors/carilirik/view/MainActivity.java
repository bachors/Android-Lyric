package com.bachors.carilirik.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bachors.carilirik.BuildConfig;
import com.bachors.carilirik.R;
import com.bachors.carilirik.adapter.LirikAdapter;
import com.bachors.carilirik.api.LirikApi;
import com.bachors.carilirik.api.LirikInterface;
import com.bachors.carilirik.model.DataJudul;
import com.bachors.carilirik.model.Judul;
import com.bachors.carilirik.util.RecyclerTouchListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bachors on 6/16/2017.
 */

public class MainActivity extends AppCompatActivity {

    private final String ApiKey = "AKfycbzeSkwJ2sSzlMAQXy9hU_IrYTIKKVFPG8nighAUOFGHTIdJ-bU";
    private LinearLayoutManager linearLayoutManager;
    private LirikInterface apiService;
    private ProgressDialog pDialog;
    private LirikAdapter adapter;
    private EditText cariInput;
    private RecyclerView rv;
    private Button cariBtn;
    private String judul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fork = (FloatingActionButton) findViewById(R.id.fork);
        fork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(BuildConfig.FORK);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        adapter = new LirikAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv = (RecyclerView) findViewById(R.id.list_musik);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, LirikActivity.class);
                intent.putExtra("judul", adapter.getItem(position).getJudul());
                intent.putExtra("lirik", adapter.getItem(position).getLirik());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        apiService = LirikApi.getClient().create(LirikInterface.class);
        cariInput = (EditText) findViewById(R.id.input_cari);
        cariBtn = (Button) findViewById(R.id.btn_cari);
        cariBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
                judul = cariInput.getText().toString().trim();
                cariJudul();
            }
        });

    }

    private void cariJudul() {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        getParam().enqueue(new Callback<Judul>() {

            @Override
            public void onResponse(Call<Judul> call, Response<Judul> response) {
                String status = response.body().getStatus();
                if(status.equals("success")) {
                    List<DataJudul> data = response.body().getData();
                    adapter.addAll(data);
                } else {
                    Toast.makeText(getApplicationContext(), "Data tidak ditemukan.", Toast.LENGTH_LONG).show();
                }
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Judul> call, Throwable t) {
                t.printStackTrace();
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Cek koneksi internet anda.", Toast.LENGTH_LONG).show();
            }
        });

    }

    private Call<Judul> getParam() {
        return apiService.getJudul(
                ApiKey,
                judul
        );
    }

}
