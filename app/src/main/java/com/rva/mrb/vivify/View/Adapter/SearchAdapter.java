package com.rva.mrb.vivify.View.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rva.mrb.vivify.Model.Data.Playlist;
import com.rva.mrb.vivify.Model.Data.SimpleTrack;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.View.Search.SearchInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Playlist playlists;
    private SimpleTrack simpleTrack;
    private SearchInterface searchInterface;

    public SearchAdapter(Playlist playlists) {
        this.playlists = playlists;
    }
    public SearchAdapter(SimpleTrack simpleTrack) {this.simpleTrack = simpleTrack; }

    public void setSearchInterface(SearchInterface searchInterface) {this.searchInterface = searchInterface; }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, final int position) {
        holder.playlistName.setText(simpleTrack.getTracks().getItems().get(position).getName()
                + "\n" + simpleTrack.getTracks().getItems().get(position).getArtists().get(0).getName());

        //holder.artistName.setText(simpleTrack.getTracks().getItems().get(position).getArtists().get(0).getName());
        //holder.playlistName.setText(playlists.getPlaylists().getItems().get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Search cardView", "Successful click");
                searchInterface.onTrackSelected(simpleTrack.getTracks().getItems().get(position));
//                Intent intent = new Intent();
//                intent.putExtra("track", myobj);
//                setResult(Activity.RESULT_OK, intent);
//                finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
        //return simpleTrack.getNumOfItems();
        //   return playlists.getPlaylists().getTotal();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.playlist_name) TextView playlistName;
        @BindView(R.id.artist_name) TextView artistName;
        @BindView(R.id.card_search) CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}