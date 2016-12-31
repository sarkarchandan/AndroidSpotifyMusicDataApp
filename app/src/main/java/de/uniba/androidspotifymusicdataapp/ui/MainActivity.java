package de.uniba.androidspotifymusicdataapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.uniba.androidspotifymusicdataapp.R;

public class MainActivity extends AppCompatActivity {

    //Enabling default logging at class level.
    private static final Logger logger = Logger.getLogger(MainActivity.class.getName());
    //Setting the Client ID for authentication.
    private static final String clientId = "973f03d1cf7b412eabf015fa6fa66b23";
    //Setting the Redirect URI for authentication.
    private static final String redirectUri = "spotify-meta-data-on-android://callback";
    //Setting the Request Code to verify if the result comes from the correct activity.
    private static final int request_Code = 1337;
    //Setting private variable for storing the Access Token
    private String accessToken;

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
                TextView textView = (TextView)findViewById(R.id.textView1);
                textView.setText(accessToken);
            }else{
                logger.log(Level.SEVERE,"Something went wrong!!!");
            }
        }

    }
}
