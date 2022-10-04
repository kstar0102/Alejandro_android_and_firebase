package com.usa.tripjungle.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.usa.tripjungle.DetailActivity;
import com.usa.tripjungle.R;
import com.usa.tripjungle.model.MainModel;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
    private final List<MainModel> mainModelList;

    static class MainHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, description;

        MainHolder(View view) {
            super(view);
            image = view.findViewById(R.id.list_image);
            title = view.findViewById(R.id.list_title);
            description = view.findViewById(R.id.list_des);
        }
    }

    public MainAdapter(List<MainModel> mainModelList) {
        this.mainModelList = mainModelList;
    }

    @NonNull
    @Override
    public MainAdapter.MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new MainHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainAdapter.MainHolder holder, int position) {
        Context context = holder.itemView.getContext();
        MainModel model = mainModelList.get(position);
        holder.title.setText(model.getTitle());
        if (model.getDescription().length() > 50) {
            holder.description.setText(model.getDescription().substring(0, 50) + "...");
        } else {
            holder.description.setText(model.getDescription().substring(0, model.getDescription().length()));
        }

        StrictMode.ThreadPolicy gfgPolicy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);
        Picasso.get().load(model.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra("id", mainModelList.get(position).getId());
            i.putExtra("image", model.getImage());
            i.putExtra("title", model.getTitle());
            i.putExtra("description", model.getDescription());
            context.startActivity(i);
        });
    }
    @Override
    public int getItemCount() {
        return mainModelList.size();
    }
}
