package de.uniba.androidspotifymusicdataapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import de.uniba.androidspotifymusicdataapp.R;
import de.uniba.androidspotifymusicdataapp.model.AlbumTrack;

/**
 * Created by chandan on 02/01/2017.
 */
public class AlbumTrackAdapter extends RecyclerView.Adapter<AlbumTrackAdapter.AlbumTracksViewHolder> {

    private List<AlbumTrack> albumTrackList;
    private LayoutInflater layoutInflater;

    /**
     * Constructor for AlbumTrackAdapter
     * @param albumTrackList
     * @param context
     */
    public AlbumTrackAdapter(List<AlbumTrack> albumTrackList, Context context) {
        this.albumTrackList = albumTrackList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public AlbumTracksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.albumtrack_item,parent,false);
        return new AlbumTracksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumTracksViewHolder holder, int position) {
        AlbumTrack albumTrack = albumTrackList.get(position);
        holder.textView_trackName.setText(albumTrack.getTrackName());
        holder.textView_trackDuration.setText(albumTrack.getTrackDuration());
        holder.textView_trackPopularity.setNumStars(5);
        holder.textView_trackPopularity.setStepSize(1);
        holder.textView_trackPopularity.setRating(albumTrack.getTrackPopularity());
    }

    @Override
    public int getItemCount() {
        return albumTrackList.size();
    }

    /**
     * Class AlbumTracksViewHolder is the ViewHolder class for AlbumTrackAdapter
     */
    class AlbumTracksViewHolder extends RecyclerView.ViewHolder{

        private TextView textView_trackName;
        private TextView textView_trackDuration;
        private RatingBar textView_trackPopularity;
        private View container;

        public AlbumTracksViewHolder(View itemView) {
            super(itemView);
            textView_trackName = (TextView)itemView.findViewById(R.id.tracks_textView_track_name);
            textView_trackDuration = (TextView)itemView.findViewById(R.id.tracks_textView_track_duration);
            textView_trackPopularity = (RatingBar) itemView.findViewById(R.id.tracks_textView_track_popularity);
            container = itemView.findViewById(R.id.tracks_linearLayout_innerContainer);
        }
    }


}
