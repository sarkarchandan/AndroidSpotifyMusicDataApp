package de.uniba.androidspotifymusicdataapp.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import de.uniba.androidspotifymusicdataapp.R;
import de.uniba.androidspotifymusicdataapp.adapters.ArtistAlbumsAdapter;
import de.uniba.androidspotifymusicdataapp.model.AlbumArtist;
import de.uniba.androidspotifymusicdataapp.model.ArtistAlbum;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;

public class ArtistActivity extends AppCompatActivity implements ArtistAlbumsAdapter.ItemClickCallBack{

    private static final String BUNDLE_EXTRA = "BUNDLE_EXTRA";
    private static final String EXTRA_ALBUM_ID = "EXTRA_ALBUM_ID";
    private static final String EXTRA_ALBUM_NAME = "EXTRA_ALBUM_NAME";
    private static final String EXTRA_ALBUM_IMAGE = "EXTRA_ALBUM_IMAGE";
    private static final String EXTRA_ALBUM_ARTIST_NAME = "EXTRA_ALBUM_ARTIST_NAME";
    private static final String EXTRA_ALBUM_RELEASE_DATE = "EXTRA_ALBUM_RELEASE_DATE";
    private static final String EXTRA_SPOTIFY_ACCESS_TOKEN = "EXTRA_SPOTIFY_ACCESS_TOKEN";
    private static final String EXTRA_ALBUM_ARTIST_ID = "EXTRA_ARTIST_ID";

    private AlbumArtist albumArtistData;
    private List<ArtistAlbum> artistAlbumList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArtistAlbumsAdapter artistAlbumsAdapter;
    private String spotifyAccessToken;
    private Bundle extras;
    private ProgressBar load_artistactivity;
    private ImageView imageView_artist_detail_image;
    private TextView textView_artist_detail_artistName;
    private TextView textView_artist_detail_artist_genres;
    private RatingBar ratingBar_artist_detail_artistPopularity;

    /**
     * Getter method required for the onItemClick method
     * @return
     */
    public String getSpotifyAccessToken() {
        return spotifyAccessToken;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        extras = getIntent().getBundleExtra(BUNDLE_EXTRA);
        load_artistactivity = (ProgressBar) findViewById(R.id.load_artistactivity);
        imageView_artist_detail_image = (ImageView)findViewById(R.id.imageView_artist_detail_image);
        textView_artist_detail_artistName = (TextView)findViewById(R.id.textView_artist_detail_artistName);
        textView_artist_detail_artist_genres = (TextView) findViewById(R.id.textView_artist_detail_artist_genres);
        ratingBar_artist_detail_artistPopularity = (RatingBar)findViewById(R.id.ratingBar_artist_detail_artistPopularity);

        spotifyAccessToken = extras.getString(EXTRA_SPOTIFY_ACCESS_TOKEN);
        new SpotifyArtist(spotifyAccessToken, extras.getString(EXTRA_ALBUM_ARTIST_ID)).execute();
    }

    /**
     * Loads Spotify Artist Details.
     * @param albumArtist
     */
    public void loadSelectedArtist(AlbumArtist albumArtist){
        Picasso.with(this).setLoggingEnabled(true);
        Picasso.with(this).load(albumArtist.getArtistImageUrl()).into(imageView_artist_detail_image);

        textView_artist_detail_artistName.setText(albumArtist.getArtistName());
        for(String artistGenre: albumArtist.getArtistGenres()) {
            textView_artist_detail_artist_genres.append(artistGenre+" ");
        }

        ratingBar_artist_detail_artistPopularity.setNumStars(5);
        ratingBar_artist_detail_artistPopularity.setStepSize(1);
        ratingBar_artist_detail_artistPopularity.setRating(albumArtist.getArtistPopularity());

        try {
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView_artist_detail_artist_albums);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            artistAlbumList = albumArtist.getArtistAlbumList();
            if (artistAlbumList.size() > 0) {
                artistAlbumsAdapter = new ArtistAlbumsAdapter(artistAlbumList, this);
            }
            recyclerView.setAdapter(artistAlbumsAdapter);
            artistAlbumsAdapter.setItemClickCallBack(this);
        }catch (NullPointerException nP){
            Log.v("NullPointerException",nP.getMessage());
        }
    }

    /**
     * Implementation for the onItemClick method
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        ArtistAlbum eachSingleArtistAlbum = artistAlbumList.get(position);

        Intent intent = new Intent(this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ALBUM_ID,eachSingleArtistAlbum.getArtistAlbumId());
        bundle.putString(EXTRA_ALBUM_NAME,eachSingleArtistAlbum.getArtistAlbumName());
        bundle.putString(EXTRA_ALBUM_IMAGE,eachSingleArtistAlbum.getArtistAlbumImageURL());
        bundle.putString(EXTRA_ALBUM_ARTIST_NAME,albumArtistData.getArtistName());
        bundle.putString(EXTRA_ALBUM_RELEASE_DATE,eachSingleArtistAlbum.getArtistAlbumReleaseDate());
        bundle.putString(EXTRA_SPOTIFY_ACCESS_TOKEN,getSpotifyAccessToken());
        intent.putExtra(BUNDLE_EXTRA,bundle);
        startActivity(intent);
    }

    /**
     * Class SpotifyArtist is responsible for the background task of the ArtistActivity.
     * We instantiate this class with passing the Spotify Access Token and selected Spotify Artist id to the constructor.
     * Background task returns an AlbumArtist object.
     */
    public class SpotifyArtist extends AsyncTask<Void,Void,AlbumArtist>{


        private String spotifyAccessToken;
        private String spotifyArtistId;


        /**
         * Constructor for SpotifyArtistDetail
         * @param spotifyAccessToken
         * @param spotifyArtistId
         */
        public SpotifyArtist(String spotifyAccessToken, String spotifyArtistId) {
            this.spotifyAccessToken = spotifyAccessToken;
            this.spotifyArtistId = spotifyArtistId;
        }

        /**
         * Getter methods for the instance variables
         */
        public String getSpotifyAccessToken() {
            return spotifyAccessToken;
        }

        public String getSpotifyArtistId() {
            return spotifyArtistId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            load_artistactivity.setVisibility(View.VISIBLE);
        }

        @Override
        protected AlbumArtist doInBackground(Void... voids) {
            AlbumArtist albumArtist = getSpotifyArtistDetail();
            return albumArtist;
        }

        @Override
        protected void onPostExecute(AlbumArtist albumArtist) {
            super.onPostExecute(albumArtist);
            load_artistactivity.setVisibility(View.INVISIBLE);
            albumArtistData = albumArtist;
            loadSelectedArtist(albumArtist);
        }


        /**
         * Returns an instance of the Spotify Service
         * @return spotifyService
         */
        public SpotifyService getSpotifyService(){
            //Creates and configures a REST adapter for Spotify Web API.
            SpotifyApi wrapper = new SpotifyApi();
            if(!getSpotifyAccessToken().equals("") && getSpotifyAccessToken()!=null) {
                wrapper.setAccessToken(getSpotifyAccessToken());
            }else{
                Log.d("SpotifyArtist","Invalid Access Token");
            }
            SpotifyService spotifyService = wrapper.getService();
            return spotifyService;
        }

        /**
         * Returns Spotify Artist Detail
         */
        public AlbumArtist getSpotifyArtistDetail(){

            String artistName = null;
            String artistImageURL=null;
            List<String> listedArtistGenres = new ArrayList<>();
            List<String> finalArtistGenres = new ArrayList<>();
            float artistPopularity;
            float artistAlbumPopularity;
            List<ArtistAlbum> listofAlbumsOfArtist = new ArrayList<>();
            String individualAlbumImageURL=null;

            SpotifyService spotifyService = getSpotifyService();
            ArtistSimple simpleArtist = spotifyService.getArtist(getSpotifyArtistId());
            artistName = simpleArtist.name;

            Artist artist = spotifyService.getArtist(getSpotifyArtistId());
            List<Image> artistImageList = artist.images;
            int maxArtistImageWidth = 0;
            for(Image image:artistImageList){
                if(image.width > maxArtistImageWidth)
                    maxArtistImageWidth = image.width;
            }
            for(Image image:artistImageList){
                if (image.width == maxArtistImageWidth) {
                    artistImageURL = image.url;
                }
            }
            for(String genre: artist.genres){
                listedArtistGenres.add(genre);
            }
            //Reducing the Artist Genres is favor of the displaying in the UI
            if(listedArtistGenres.size() > 3){
                finalArtistGenres = listedArtistGenres.subList(0,2);
            }else{
                finalArtistGenres = listedArtistGenres;
            }
            artistPopularity = ((float) (artist.popularity/100.0)*5);

            Pager<Album> simpleAlbumPager = spotifyService.getArtistAlbums(getSpotifyArtistId());
            List<Album> artistAlbumList = simpleAlbumPager.items;
            for(Album artistAlbum: artistAlbumList){
                Album individualAlbum = spotifyService.getAlbum(artistAlbum.id);
                artistAlbumPopularity = ((float) (individualAlbum.popularity/100.0)*5);

                int maxAlbumImageWidth = 0;
                for(Image albumImage: individualAlbum.images){
                    if(albumImage.width > maxAlbumImageWidth)
                        maxAlbumImageWidth = albumImage.width;
                }
                for (Image albumImage: individualAlbum.images){
                    if(albumImage.width==maxAlbumImageWidth){
                        individualAlbumImageURL = albumImage.url;
                    }
                }
                listofAlbumsOfArtist.add(new
                        ArtistAlbum(artistAlbum.id,individualAlbum.name,individualAlbum.release_date,artistAlbumPopularity,individualAlbumImageURL));
            }
            return new AlbumArtist(getSpotifyArtistId(),artistName,artistImageURL,finalArtistGenres,artistPopularity,listofAlbumsOfArtist);
        }
    }

}
