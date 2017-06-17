package com.bachors.carilirik.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bachors.carilirik.R;
import com.bachors.carilirik.model.DataJudul;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bachors on 6/16/2017.
 */

public class LirikAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataJudul> dataJudul;
    private Context context;

    public LirikAdapter(Context context) {
        this.context = context;
        dataJudul = new ArrayList<>();
    }

    public List<DataJudul> getData() {
        return dataJudul;
    }

    public void setData(List<DataJudul> dataJudul) {
        this.dataJudul = dataJudul;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        viewHolder = getViewHolder(parent, inflater);
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.list_item_judul, parent, false);
        viewHolder = new JudulVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DataJudul data = dataJudul.get(position);

        final JudulVH judulVH = (JudulVH) holder;

        String[] judul = data.getJudul().split(" by ");

        judulVH.judul.setText(judul[0]);
        judulVH.by.setText(judul[1]);

    }

    @Override
    public int getItemCount() {
        return dataJudul == null ? 0 : dataJudul.size();
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(DataJudul r) {
        dataJudul.add(r);
        notifyItemInserted(dataJudul.size() - 1);
    }

    public void addAll(List<DataJudul> dataJudul) {
        for (DataJudul data : dataJudul) {
            add(data);
        }
    }

    public void remove(DataJudul r) {
        int position = dataJudul.indexOf(r);
        if (position > -1) {
            dataJudul.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public DataJudul getItem(int position) {
        return dataJudul.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class JudulVH extends RecyclerView.ViewHolder {
        private TextView judul;
        private TextView by;

        public JudulVH(View itemView) {
            super(itemView);

            judul = (TextView) itemView.findViewById(R.id.judul);
            by = (TextView) itemView.findViewById(R.id.by);

        }
    }

}