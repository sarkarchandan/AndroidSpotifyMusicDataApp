package de.uniba.androidspotifymusicdataapp.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.uniba.androidspotifymusicdataapp.R;
import de.uniba.androidspotifymusicdataapp.adapters.AlbumTrackAdapter;
import de.uniba.androidspotifymusicdataapp.model.AlbumTrack;
import de.uniba.androidspotifymusicdataapp.model.SpotifyAlbumDetail;
import de.uniba.androidspotifymusicdataapp.model.SpotifyEngine;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import kaaes.spotify.webapi.android.models.Image;

public class DetailActivity extends AppCompatActivity {

    //Enabling default logging at class level.
    private static final Logger logger = Logger.getLogger(DetailActivity.class.getName());

    private static final String BUNDLE_EXTRA = "BUNDLE_EXTRA";
    private static final String EXTRA_ALBUM_ID = "EXTRA_ALBUM_ID";
    private static final String EXTRA_ALBUM_NAME = "EXTRA_ALBUM_NAME";
    private static final String EXTRA_ALBUM_IMAGE = "EXTRA_ALBUM_IMAGE";
    private static final String EXTRA_ALBUM_ARTIST_NAME = "EXTRA_ALBUM_ARTIST_NAME";
    private static final String EXTRA_ALBUM_RELEASE_DATE = "EXTRA_ALBUM_RELEASE_DATE";
    private static final String EXTRA_SPOTIFY_ACCESS_TOKEN = "EXTRA_SPOTIFY_ACCESS_TOKEN";

    private List<AlbumTrack> albumTrackList;
    private RecyclerView recyclerView;
    private AlbumTrackAdapter albumTrackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        try {
            Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRA);

            ImageView imageView_albumImage = (ImageView)findViewById(R.id.imageView_album_detail_image);

            //Picasso Experiment Begin
            Picasso.with(this).setLoggingEnabled(true);
            Picasso.with(this).load(extras.getString(EXTRA_ALBUM_IMAGE)).into(imageView_albumImage);
            //Experiment Experiment End

            ((TextView)findViewById(R.id.textView_album_detail_album_name)).setText(extras.getString(EXTRA_ALBUM_NAME));
            ((TextView)findViewById(R.id.textView_album_detail_artist_name)).setText(extras.getString(EXTRA_ALBUM_ARTIST_NAME));
            ((TextView)findViewById(R.id.textView_album_detail_album_release_date)).setText(extras.getString(EXTRA_ALBUM_RELEASE_DATE));
            //Getting the Data for AlbumTracks
            try {
                albumTrackList = new SpotifyAlbumDetail(extras.getString(EXTRA_ALBUM_ID),
                        extras.getString(EXTRA_SPOTIFY_ACCESS_TOKEN)).execute().get();
            } catch (InterruptedException e) {
                logger.log(Level.WARNING,"We have got Interrupted Exception"+e.getMessage());
            } catch (ExecutionException e) {
                logger.log(Level.WARNING,"We have got ExecutionException"+e.getMessage());
            } catch (NullPointerException e){
                logger.log(Level.WARNING,"We have received NUll List "+e.getMessage());
            }

            recyclerView = (RecyclerView)findViewById(R.id.recyclerView_for_detail_activity_track_details);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            if(albumTrackList.size() > 0){
                albumTrackAdapter = new AlbumTrackAdapter(albumTrackList,this);
            }
            recyclerView.setAdapter(albumTrackAdapter);

        }catch (NullPointerException nP){
            nP.getCause();
        }

    }
}
