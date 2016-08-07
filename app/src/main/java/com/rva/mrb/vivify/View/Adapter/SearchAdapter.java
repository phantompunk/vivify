package com.rva.mrb.vivify.View.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rva.mrb.vivify.Model.Playlist;
import com.rva.mrb.vivify.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Playlist playlists;

    public SearchAdapter(Playlist playlists) {
        this.playlists = playlists;
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        holder.playlistName.setText(playlists.getPlaylists().getItems().get(position).getName());
    }

    @Override
    public int getItemCount() {
        return playlists.getPlaylists().getTotal();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.playlist_name) TextView playlistName;
        @BindView(R.id.card_search) CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}