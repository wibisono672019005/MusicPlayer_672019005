package com.example.tugas2musicplayer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    ArrayList<ModelAudio> musicList;
    Context context;

    public MyAdapter(ArrayList<ModelAudio> musicList, Context context) {
        this.musicList = musicList;
        this.context = context;
    }

    //Implement methods

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview, parent, false);
        return new MyAdapter.ViewHolder(view);
        //Mendaftarkan recyclerview.xml ke class ViewHolder
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        //Bind semua data music list kepada View/tampilan
        ModelAudio musicData = musicList.get(position);
        holder.titleTextView.setText(musicData.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Menuju ke Activity PlayerActivity

                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                Intent intentToPlayerActivity = new Intent(context, PlayerActivity.class);
                intentToPlayerActivity.putExtra("LIST", musicList);
                intentToPlayerActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentToPlayerActivity);

            }
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        ImageView iconImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.judulLagu);
            iconImageView = itemView.findViewById(R.id.iconLagu);
        }
    }
}
