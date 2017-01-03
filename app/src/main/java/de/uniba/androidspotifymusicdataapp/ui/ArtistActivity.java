package de.uniba.androidspotifymusicdataapp.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.uniba.androidspotifymusicdataapp.R;
import de.uniba.androidspotifymusicdataapp.adapters.ArtistAlbumsAdapter;
import de.uniba.androidspotifymusicdataapp.model.AlbumArtist;
import de.uniba.androidspotifymusicdataapp.model.ArtistAlbum;
import de.uniba.androidspotifymusicdataapp.model.SpotifyArtistDetail;

public class ArtistActivity extends AppCompatActivity {

    //Enabligh default logging at class level
    private static final Logger logger = Logger.getLogger(ArtistActivity.class.getName());

    private static final String BUNDLE_EXTRA = "BUNDLE_EXTRA";
    private static final String EXTRA_ALBUM_ID = "EXTRA_ALBUM_ID";
    private static final String EXTRA_ALBUM_NAME = "EXTRA_ALBUM_NAME";
    private static final String EXTRA_ALBUM_IMAGE = "EXTRA_ALBUM_IMAGE";
    private static final String EXTRA_ALBUM_ARTIST_NAME = "EXTRA_ALBUM_ARTIST_NAME";
    private static final String EXTRA_ALBUM_RELEASE_DATE = "EXTRA_ALBUM_RELEASE_DATE";
    private static final String EXTRA_SPOTIFY_ACCESS_TOKEN = "EXTRA_SPOTIFY_ACCESS_TOKEN";
    private static final String EXTRA_ALBUM_ARTIST_ID = "EXTRA_ARTIST_ID";

    private AlbumArtist albumArtist;
    private List<ArtistAlbum> artistAlbumList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArtistAlbumsAdapter artistAlbumsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);


        Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRA);

        try {
            albumArtist = new SpotifyArtistDetail(extras.getString(EXTRA_SPOTIFY_ACCESS_TOKEN),
                    extras.getString(EXTRA_ALBUM_ARTIST_ID)).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ImageView artistImage = (ImageView)findViewById(R.id.imageView_artist_detail_image);
        Picasso.with(this).setLoggingEnabled(true);
        Picasso.with(this).load(albumArtist.getArtistImageUrl()).into(artistImage);
        ((TextView)findViewById(R.id.textView_artist_detail_artistName)).setText(albumArtist.getArtistName());
        for(String artistGenre: albumArtist.getArtistGenres()) {
            ((TextView) findViewById(R.id.textView_artist_detail_artist_genres)).append(artistGenre+" ");
        }
        RatingBar artistRating = (RatingBar)findViewById(R.id.ratingBar_artist_detail_artistPopularity);
        artistRating.setNumStars(5);
        artistRating.setStepSize(1);
        artistRating.setRating(albumArtist.getArtistPopularity());

        try {
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView_artist_detail_artist_albums);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            artistAlbumList = albumArtist.getArtistAlbumList();
            if (artistAlbumList.size() > 0) {
                artistAlbumsAdapter = new ArtistAlbumsAdapter(artistAlbumList, this);
            }
            recyclerView.setAdapter(artistAlbumsAdapter);
        }catch (NullPointerException nP){
            logger.log(Level.WARNING,"We have encountered NullPointerException"+nP.getMessage());
        }

    }
}
