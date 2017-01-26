package de.uniba.androidspotifymusicdataapp.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import java.util.ArrayList;
import java.util.List;
import de.uniba.androidspotifymusicdataapp.R;
import de.uniba.androidspotifymusicdataapp.adapters.CardAdapter;
import de.uniba.androidspotifymusicdataapp.model.CardAlbum;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.NewReleases;
import kaaes.spotify.webapi.android.models.Pager;

public class MainActivity extends AppCompatActivity implements CardAdapter.CardClickCallBack{

    /*
     * The following two parameters are specific to the user' registered application with Spotify.
     * In order to execute this app, user needs to register with free account with Spotify and register one
     * application. Then the Android Authentication Guide must be followed to generate the Client Id and Redirect Uri.
     * These two values are required to generate the Authentication Token which is required to fetch data from the Spotify Web Api
     * android wrapper.
     */
    /*Your Client id goes here*/
    private static final String clientId = "973f03d1cf7b412eabf015fa6fa66b23";
    /*Your Redirect Uri goes here*/
    private static final String redirectUri = "spotify-meta-data-on-android://callback";


    //Setting the Request Code to verify if the result comes from the correct activity.
    private static final int request_Code = 1337;
    //Setting private variable for storing the Access Token
    private static String accessToken;

    //Constants which will be used as keys while passing data from MainActivity to other Activities.
    private static final String BUNDLE_EXTRA = "BUNDLE_EXTRA";
    private static final String EXTRA_ALBUM_ID = "EXTRA_ALBUM_ID";
    private static final String EXTRA_ALBUM_NAME = "EXTRA_ALBUM_NAME";
    private static final String EXTRA_ALBUM_IMAGE = "EXTRA_ALBUM_IMAGE";
    private static final String EXTRA_ALBUM_ARTIST_NAME = "EXTRA_ALBUM_ARTIST_NAME";
    private static final String EXTRA_ALBUM_RELEASE_DATE = "EXTRA_ALBUM_RELEASE_DATE";
    private static final String EXTRA_SPOTIFY_ACCESS_TOKEN = "EXTRA_SPOTIFY_ACCESS_TOKEN";
    private static final String EXTRA_ALBUM_ARTIST_ID = "EXTRA_ARTIST_ID";

    //Instance variables for the Main Activity.
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardAlbum> cardAlbumListData;
    private ProgressBar loadMainActivity;
    private Toolbar toolbar_main_activity;


    /**
     * Getter method for the Access Token
     * @return
     */
    public static String getAccessToken() {
        return accessToken;
    }

    /**
     * Kickoff the application and request for access token using Spotify Android SDK
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMainActivity = (ProgressBar) findViewById(R.id.load_mainactivity);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_for_main_activity);
        toolbar_main_activity = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar_main_activity);
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder
                (clientId, AuthenticationResponse.Type.TOKEN, redirectUri);
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, request_Code, request);
    }

    /**
     * Fetch the authentication token using the Spotify Android SDK
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==request_Code){
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode,data);
            if(response.getType()==AuthenticationResponse.Type.TOKEN){
                accessToken = response.getAccessToken();
            }else{
            }
        }
        new SpotifyNewRelease(accessToken).execute();
    }

    /**
     * Hides all View elements until the page is loaded and shows the progress bar.
     */
    public void showProgress(){
        loadMainActivity.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    /**
     * Once the values of all View elements are loaded hides the progress bar and shows the data.
     */
    public void showData(){
        loadMainActivity.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Set the adapter for the MainActivity RecyclerView with data.
     * @param cardAlbumList
     */
    public void loadSpotifyNewReleaseData(List<CardAlbum> cardAlbumList){
        //Setting the LayoutManager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Instantiating CardAdapter class using the defined constructor
        if(cardAlbumList.size() > 0) {
            cardAdapter = new CardAdapter(cardAlbumList, this);
        }else{
            Log.d("NULLList","We are getting a Null List from the background thread !!!");
        }
        //Giving the reference of the Adapter class instance to the RecyclerView
        recyclerView.setAdapter(cardAdapter);
        //Giving the reference of the Callback interface to the Card Adapter so that the CallbackInterface methods can be called upon events.
        cardAdapter.setCardClickCallBack(this);
        Toast.makeText(MainActivity.this,"New Releases from Spotify",Toast.LENGTH_SHORT).show();
    }

    /**
     * Inflates the overflow menu at the toolbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity_toolbar_overflowmenu,menu);
        return true;
    }

    /**
     * Provides dummy implementation for the MainActivity overflow menu at the toolbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItmId = item.getItemId();

        switch (selectedItmId){
            case (R.id.item_action_refresh):
                new SpotifyNewRelease(accessToken).execute();
                return true;
            case (R.id.find_web):
                Toast.makeText(MainActivity.this,"Find In Web Selected",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Behavior of the Application upon clicking the Card
     * @param position
     */
    @Override
    public void onCardClick(int position) {
        CardAlbum cardAlbum = cardAlbumListData.get(position);
        /**
         * Putting the Data inside a bundle nd sending to the DetailActivity.
         */

        Intent intent = new Intent(this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ALBUM_ID,cardAlbum.getAlbumId());
        bundle.putString(EXTRA_ALBUM_NAME,cardAlbum.getAlbumName());
        bundle.putString(EXTRA_ALBUM_ARTIST_NAME,cardAlbum.getArtistName());
        bundle.putString(EXTRA_ALBUM_IMAGE,cardAlbum.getAlbumImageURL());
        bundle.putString(EXTRA_ALBUM_RELEASE_DATE,cardAlbum.getAlbumReleaseDate());
        bundle.putString(EXTRA_SPOTIFY_ACCESS_TOKEN,getAccessToken());
        intent.putExtra(BUNDLE_EXTRA,bundle);
        startActivity(intent);
    }

    /**
     * Behavior of the Application upon clicking the Button
     * @param position
     */
    @Override
    public void onCardButtonClick(int position) {

        CardAlbum cardAlbum = cardAlbumListData.get(position);
        Intent intent = new Intent(this,ArtistActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ALBUM_ARTIST_ID,cardAlbum.getArtistId());
        bundle.putString(EXTRA_ALBUM_ARTIST_NAME,cardAlbum.getArtistName());
        bundle.putString(EXTRA_SPOTIFY_ACCESS_TOKEN,getAccessToken());
        intent.putExtra(BUNDLE_EXTRA,bundle);
        startActivity(intent);
    }

    /**
     * Class SpotifyNewRelease is responsible for performing the background task for the MainActivity.
     * We instantiate this class passing the Spotify Access Token to the constructor.
     * The access token than is used by the background task to generate the new release album list from spotify.
     */
    public class SpotifyNewRelease extends AsyncTask<Void,Void,List<CardAlbum>>{
        private String accessToken;
        private List<CardAlbum> cardAlbumList;

        /**
         * Constructor for SpotifyNewRelease
         * @param accessToken
         */
        public SpotifyNewRelease(String accessToken) {
            this.accessToken = accessToken;
        }

        /**
         * Getter method for the accessToken
         * @return
         */
        public String getAccessToken() {
            return accessToken;
        }


        @Override
        protected void onPreExecute() {
            showProgress();
        }

        @Override
        protected List<CardAlbum> doInBackground(Void... voids) {
            cardAlbumList = getNewReleasedAlbums();
            return cardAlbumList;
        }

        @Override
        protected void onPostExecute(List<CardAlbum> cardAlbumList) {
            cardAlbumListData = cardAlbumList;
            loadSpotifyNewReleaseData(cardAlbumList);
            showData();
        }

        /**
         * Returns an instance of the Spotify Service
         * @return spotifyService
         */
        public SpotifyService getSpotifyService(){
            //Creates and configures a REST adapter for Spotify Web API.
            SpotifyApi wrapper = new SpotifyApi();
            if(!getAccessToken().equals("") && getAccessToken()!=null) {
                wrapper.setAccessToken(getAccessToken());
            }else{
                Log.d("SpotifyNewRelease","Invalid Access Token");
            }
            SpotifyService spotifyService = wrapper.getService();
            return spotifyService;
        }

        /**
         * Returns a specific Spotify Album Instance by Id
         * @param albumId
         * @return spotifyAlbum
         */
        public Album getSpotifyAlbumById(String albumId){
            SpotifyService spotifyService = getSpotifyService();
            Album spotifyAlbum = spotifyService.getAlbum(albumId);
            return spotifyAlbum;
        }

        /**
         * Returns list of CardAlbum instances.
         * @return
         */
        public List<CardAlbum> getNewReleasedAlbums(){
            List<CardAlbum> cardAlbumList = new ArrayList<>();
            String albumId = null;
            String artistId = null;
            String artistName = null;
            String albumImageURL=null;
            String albumName = null;
            int albumPopularity;
            String albumReleaseDate;

            SpotifyService spotifyService = getSpotifyService();
            if(spotifyService!=null) {
                NewReleases newReleases = spotifyService.getNewReleases();
                Pager<AlbumSimple> albumSimplePager = newReleases.albums;

                //Getting the Album Name for CardAlbum
                List<AlbumSimple> albumSimpleList = albumSimplePager.items;
                for (AlbumSimple simpleAlbum : albumSimpleList) {
                    albumId = simpleAlbum.id;
                    albumName = simpleAlbum.name;

                    //Getting the list of Album Artists for CardAlbum
                    Album album = getSpotifyAlbumById(albumId);
                    albumPopularity = album.popularity;
                    albumReleaseDate = album.release_date;
                    List<ArtistSimple> simpleArtistList = album.artists;
                    for (ArtistSimple simpleArtist : simpleArtistList) {
                        artistId = simpleArtist.id;
                        artistName = simpleArtist.name;
                    }
                    //Getting the Album Image for CardAlbum
                    //We want to fetch the url for the image with largest dimension.
                    List<Image> albumImages = simpleAlbum.images;
                    int maxWidth = 0;
                    for (Image albumImage : albumImages) {
                        if(albumImage.width > maxWidth)
                            maxWidth = albumImage.width;
                    }
                    for(Image albumImage : albumImages){
                        if (albumImage.width == maxWidth) {
                            albumImageURL = albumImage.url;
                        }
                    }
                    //Constructing the List of CardAlbumInstances
                    if (simpleAlbum.name != null && albumImageURL != null && artistName !=null) {
                        cardAlbumList.add(new CardAlbum(albumId,albumName,artistId,artistName,albumImageURL,albumPopularity,albumReleaseDate));
                    }
                }
            }else{
                Log.d("SpotifyNewRelease","Invalid Instance Of the SpotifyService");
            }
            return cardAlbumList;
        }
    }
}
