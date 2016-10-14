package com.rva.mrb.vivify.View.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rva.mrb.vivify.Model.Data.Album;
import com.rva.mrb.vivify.Model.Data.Playlist;
import com.rva.mrb.vivify.Model.Data.Search;
import com.rva.mrb.vivify.Model.Data.SimpleTrack;
import com.rva.mrb.vivify.Model.Data.Track;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.View.Search.SearchInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private static final String TAG = SearchAdapter.class.getSimpleName();

    private List<Object> items;
    private Search results;
    private Playlist playlists;
    private SimpleTrack simpleTrack;
    private SearchInterface searchInterface;

    public static final int PLAYLIST = 0;
    public static final int ALBUM = 1;
    public static final int TRACK = 2;

    public SearchAdapter(List<Object> item) { this.items = item; }
    public SearchAdapter(Search results) { this.results = results; }
    public SearchAdapter(Playlist playlists) {
        this.playlists = playlists;
    }
    public SearchAdapter(SimpleTrack simpleTrack) {this.simpleTrack = simpleTrack; }

    public void setSearchInterface(SearchInterface searchInterface) {this.searchInterface = searchInterface; }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == PLAYLIST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_card, parent, false);
            return new PlaylistViewHolder(view);
        }
        else if (viewType == ALBUM){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card, parent, false);
            return new AlbumViewHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_card, parent, false);
            return new TrackViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, final int position) {

        Track t = new Track();
        Album a = new Album();
        if (items.get(position) instanceof Track) {
           t  = (Track) items.get(position);
        }
        else {
           a = (Album) items.get(position);
        }

        if (holder.getItemViewType() == PLAYLIST) {
            PlaylistViewHolder viewHolder = (PlaylistViewHolder) holder;
            viewHolder.playlistName.setText(results.getTracks().getItems().get(position).getName()
                    + "\n" + results.getTracks().getItems().get(position).getArtists().get(0).getName());
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Search cardView", "Successful click");
                    searchInterface.onTrackSelected(results.getTracks().getItems().get(position));
                }
            });
        }
        else if (holder.getItemViewType() == ALBUM && a != null) {
            final Album b = a;
            AlbumViewHolder viewHolder = (AlbumViewHolder) holder;
            viewHolder.albumName.setText(a.getName()
                    + "\n") ;
//            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("Search cardView", "Successful click");
//                    searchInterface.onTrackSelected(b);
//                }
//            });
        }
        else {
            final Track s = t;
            TrackViewHolder viewHolder = (TrackViewHolder) holder;
            viewHolder.trackName.setText(t.getName()
                    + "\n" + t.getArtists().get(0).getName());
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Search cardView", "Successful click");
                    searchInterface.onTrackSelected(s);
                }
            });
        }

//        holder.playlistName.setText(simpleTrack.getTracks().getItems().get(position).getName()
//                + "\n" + simpleTrack.getTracks().getItems().get(position).getArtists().get(0).getName());
//        holder.playlistName.setText(results.getTracks().getItems().get(position).getName()
//                + "\n" + results.getTracks().getItems().get(position).getArtists().get(0).getName());
//
        //holder.artistName.setText(simpleTrack.getTracks().getItems().get(position).getArtists().get(0).getName());
        //holder.playlistName.setText(playlists.getPlaylists().getItems().get(position).getName());
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Search cardView", "Successful click");
//                searchInterface.onTrackSelected(results.getTracks().getItems().get(position));
//                Intent intent = new Intent();
//                intent.putExtra("track", myobj);
//                setResult(Activity.RESULT_OK, intent);
//                finish();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return items.size();
        //return simpleTrack.getNumOfItems();
        //   return playlists.getPlaylists().getTotal();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.playlist_name) TextView playlistName;
//        @BindView(R.id.artist_name) TextView artistName;
//        @BindView(R.id.card_search) CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
        }
    }

    public class AlbumViewHolder extends SearchAdapter.ViewHolder {
        @BindView(R.id.album_name) TextView albumName;
        @BindView(R.id.artist_name) TextView artistName;
        @BindView(R.id.card_search) CardView cardView;
        public AlbumViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class PlaylistViewHolder extends SearchAdapter.ViewHolder {
        @BindView(R.id.playlist_name) TextView playlistName;
        @BindView(R.id.artist_name) TextView artistName;
        @BindView(R.id.card_search) CardView cardView;
        public PlaylistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class TrackViewHolder extends SearchAdapter.ViewHolder {
        @BindView(R.id.track_name) TextView trackName;
        @BindView(R.id.artist_name) TextView artistName;
        @BindView(R.id.card_search) CardView cardView;
        public TrackViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTrackName() {
            return trackName;
        }
    }

    @Override
    public int getItemViewType(int position) {
//        List<Object> result = new ArrayList<Object>();
//        for (Track t : results.getTracks().getItems())
//                result.add(t);
//        for (Album a : results.getAlbums().getItems())
//                result.add(a);
//        result.add((Result) results.getTracks().getItems());
//        result.add((Result) results.getAlbums().getItems());
        if (items.get(position) instanceof Track)
            return 2;
        else if (items.get(position) instanceof Album)
            return 1;
        else
            return 0;
    }

    public class Result {
        Track track;
        Album album;
    }
}