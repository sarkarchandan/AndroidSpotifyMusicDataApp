package de.uniba.androidspotifymusicdataapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.uniba.androidspotifymusicdataapp.R;
import de.uniba.androidspotifymusicdataapp.adapters.CardAdapter;
import de.uniba.androidspotifymusicdataapp.model.CardAlbum;
import de.uniba.androidspotifymusicdataapp.model.SpotifyEngine;

public class MainActivity extends AppCompatActivity implements CardAdapter.CardClickCallBack{

    //Enabling default logging at class level.
    private static final Logger logger = Logger.getLogger(MainActivity.class.getName());

    //Setting the Client ID for authentication.
    private static final String clientId = "973f03d1cf7b412eabf015fa6fa66b23";
    //Setting the Redirect URI for authentication.
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
    private List<CardAlbum> cardAlbumList;
    ProgressDialog progressDialog;

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
        logger.setLevel(Level.ALL);
        logger.info("Executing onCreate() method");
        //Progress Dialogue
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setInverseBackgroundForced(false);
        progressDialog.show();
        logger.info("ProgressBar.show() method triggered !!!");

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder
                (clientId, AuthenticationResponse.Type.TOKEN, redirectUri);
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, request_Code, request);
        logger.info("Authentication Request is sent to Spotify");
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
        logger.setLevel(Level.ALL);
        logger.info("Executing onActivityResult() method");
        if(requestCode==request_Code){
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode,data);
            logger.info("AuthenticationClient.getResponse() method called for the token");
            if(response.getType()==AuthenticationResponse.Type.TOKEN){
                accessToken = response.getAccessToken();
                logger.info("Our Access Token: "+accessToken);
            }else{
                logger.log(Level.SEVERE,"Something went wrong!!!");
            }
        }

        //Getting the Data for cards
        try {
           cardAlbumList = new SpotifyEngine(accessToken).execute().get();
            logger.info("Main Activity has triggered AsychTask to fetch the music data.");
        } catch (InterruptedException e) {
            logger.log(Level.WARNING,"We have got Interrupted Exception"+e.getMessage());
        } catch (ExecutionException e) {
            logger.log(Level.WARNING,"We have got ExecutionException"+e.getMessage());
        } catch (NullPointerException e){
            logger.log(Level.WARNING,"We have received NUll List "+e.getMessage());
        }

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_for_main_activity);
        //Setting the LayoutManager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Instantiating CardAdapter class using the defined constructor
        if(cardAlbumList.size() > 0) {
            cardAdapter = new CardAdapter(cardAlbumList, this);
        }else{
            logger.info("We are getting a Null List from the background thread !!!");
        }
        //Giving the reference of the Adapter class instance to the RecyclerView
        recyclerView.setAdapter(cardAdapter);
        logger.info("Adapter has been set to the Main Activity RecyclerView");
        //Giving the reference of the Callback interface to the Card Adapter so that the CallbackInterface methods can be called upon events.
        cardAdapter.setCardClickCallBack(this);
        logger.info("setCardClickCallBack method called to pass on the reference of the main activity.");
        progressDialog.hide();
        logger.info("ProgressBar.hide() method triggered !!!");
    }

    /**
     * Behavior of the Application upon clicking the Card
     * @param position
     */
    @Override
    public void onCardClick(int position) {
        CardAlbum cardAlbum = (CardAlbum) cardAlbumList.get(position);
        logger.info("Album Id"+cardAlbum.getAlbumId());
        logger.info("Album Name: "+cardAlbum.getAlbumName());
        logger.info("Album Artist Id: "+cardAlbum.getArtistId());
        logger.info("Album Artist Name: "+cardAlbum.getArtistName());
        logger.info("Album ImageUrl"+cardAlbum.getAlbumImageURL());
        logger.info("Album Popularity: "+cardAlbum.getAlbumPopularity());
        logger.info("Album Release Date :"+cardAlbum.getAlbumReleaseDate());
        logger.info("Spotify Access Token: "+getAccessToken());
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

        CardAlbum cardAlbum = (CardAlbum) cardAlbumList.get(position);
        logger.info("Artist Id: "+cardAlbum.getArtistId());
        logger.info("Artist Name: "+cardAlbum.getArtistName());

        Intent intent = new Intent(this,ArtistActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ALBUM_ARTIST_ID,cardAlbum.getArtistId());
        bundle.putString(EXTRA_ALBUM_ARTIST_NAME,cardAlbum.getArtistName());
        bundle.putString(EXTRA_SPOTIFY_ACCESS_TOKEN,getAccessToken());
        intent.putExtra(BUNDLE_EXTRA,bundle);
        startActivity(intent);
    }
}
