package de.uniba.androidspotifymusicdataapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import de.uniba.androidspotifymusicdataapp.R;
import de.uniba.androidspotifymusicdataapp.model.ArtistAlbum;

/**
 * This class serves as the adapter for displaying all albums published by an artist
 * Created by chandan on 03/01/2017.
 */
public class ArtistAlbumsAdapter extends RecyclerView.Adapter<ArtistAlbumsAdapter.ArtistAlbumViewHolder>{

    //Instance variables
    private List<ArtistAlbum> artistAlbumList;
    private LayoutInflater layoutInflater;
    private Context context;

    /**
     * Constructor for the ArtistAlbumsAdapter
     * @param artistAlbumList
     * @param context
     */
    public ArtistAlbumsAdapter(List<ArtistAlbum> artistAlbumList, Context context) {
        this.artistAlbumList = artistAlbumList;
        this.context=context;
        this.layoutInflater = layoutInflater.from(context);
    }

    /**
     * Methods to be implemented to bind the data to the recyclerView and inflate
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ArtistAlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.album_item,parent,false);
        return new ArtistAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistAlbumViewHolder holder, int position) {

        ArtistAlbum artistAlbum = artistAlbumList.get(position);
        //Picasso Experiment
        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(artistAlbum.getArtistAlbumImageURL()).into(holder.imageView_artistAlbumImage);
        //Picasso Experiment
        holder.textView_artistAlbumName.setText(artistAlbum.getArtistAlbumName());
        holder.textView_artistAlbumreleaseDate.setText(artistAlbum.getArtistAlbumReleaseDate());
        holder.ratingBar_artistAlbumPopularity.setNumStars(5);
        holder.ratingBar_artistAlbumPopularity.setStepSize(1);
        holder.ratingBar_artistAlbumPopularity.setRating(artistAlbum.getArtistAlbumPopularity());
    }

    @Override
    public int getItemCount() {
        return artistAlbumList.size();
    }


    class ArtistAlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView_artistAlbumImage;
        private TextView textView_artistAlbumName;
        private TextView textView_artistAlbumreleaseDate;
        private RatingBar ratingBar_artistAlbumPopularity;
        private View viewContainer;

        /**
         * Constructor for the ArtistAlbumViewHolder
         * @param itemView
         */
        public ArtistAlbumViewHolder(View itemView) {
            super(itemView);

            imageView_artistAlbumImage = (ImageView)itemView.findViewById(R.id.albums_imageView_artist_album_image);
            textView_artistAlbumName = (TextView)itemView.findViewById(R.id.albums_textView_artist_album_name);
            textView_artistAlbumreleaseDate = (TextView)itemView.findViewById(R.id.albums_textView_artist_album_release_date);
            ratingBar_artistAlbumPopularity = (RatingBar)itemView.findViewById(R.id.albums_textView_artist_album_popularity);
            viewContainer = itemView.findViewById(R.id.albums_linearLayout_outermost_container);
            //TODO viewContainer.setOnClickListener(this);
        }

        /**
         * Overridden onClick method for the ArtistActivity RecyclerView
         * @param view
         */
        @Override
        public void onClick(View view) {
            //TODO Implement the method
        }
    }
}
