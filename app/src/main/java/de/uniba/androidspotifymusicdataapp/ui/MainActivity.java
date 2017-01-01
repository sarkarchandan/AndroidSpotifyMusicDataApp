package de.uniba.androidspotifymusicdataapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.uniba.androidspotifymusicdataapp.R;
import de.uniba.androidspotifymusicdataapp.adapters.CardAdapter;
import de.uniba.androidspotifymusicdataapp.model.CardData;
import de.uniba.androidspotifymusicdataapp.model.CardItem;
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

    //Constants which will be used as keys while passing data from MainActivity to DetailActivity
    private static final String BUNDLE_EXTRA = "BUNDLE_EXTRA";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";

    //Instance variables for the Main Activity.
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardItem> cardItemList;

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
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder
                (clientId, AuthenticationResponse.Type.TOKEN, redirectUri);
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, request_Code, request);
        logger.info("Authentication Request sent");


        //Getting the Data for cards
        cardItemList = CardData.getDataForCards();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_for_main_activity);

        //Setting the LayoutManager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Instantiating CardAdapter class using the defined constructor
        cardAdapter = new CardAdapter(CardData.getDataForCards(),this);
        //Giving the reference of the Adapter class instance to the RecyclerView
        recyclerView.setAdapter(cardAdapter);
        //Giving the reference of the Callback interface to the Card Adapter so that the CallbackInterface methods can be called upon events.
        cardAdapter.setCardClickCallBack(this);
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
                new SpotifyEngine(response.getAccessToken()).execute();
                logger.info("Our Access Token: "+accessToken);
            }else{
                logger.log(Level.SEVERE,"Something went wrong!!!");
            }
        }

    }

    /**
     * Behavior of the Application upon clicking the Card
     * @param position
     */
    @Override
    public void onCardClick(int position) {
        CardItem cardItem = (CardItem) cardItemList.get(position);
        logger.info("Card Item Data on CardClick: "+cardItem.getPersonName());
        logger.info("Card Item Data on CardClick: "+cardItem.getPersonStatement());

        /**
         * Putting the Data inside a bundle nd sending to the DetailActivity.
         */
        Intent intent = new Intent(this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_QUOTE,cardItem.getPersonStatement());
        intent.putExtra(BUNDLE_EXTRA,bundle);
        startActivity(intent);
    }

    /**
     * Behavior of the Application upon clicking the Button
     * @param position
     */
    @Override
    public void onCardButtonClick(int position) {

        CardItem cardItem = (CardItem) cardItemList.get(position);
        logger.info("Card Item Data on ButtonClick: "+cardItem.getPersonName());
        logger.info("Card Item Data on ButtonClick: "+cardItem.getPersonStatement());

        /**
         * Putting the Data inside a bundle nd sending to the DetailActivity.
         */
        Intent intent = new Intent(this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_QUOTE,cardItem.getPersonStatement());
        intent.putExtra(BUNDLE_EXTRA,bundle);
        startActivity(intent);
    }
}
