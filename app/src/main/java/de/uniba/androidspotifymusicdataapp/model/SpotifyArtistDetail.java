package de.uniba.androidspotifymusicdataapp.model;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;

/**
 * This class is the helper class for the ArtistActivity and responsible for fetching the artist data from the Spotify Api wrapper
 * Created by chandan on 03/01/2017.
 */
public class SpotifyArtistDetail extends AsyncTask<Void,Void,AlbumArtist>{

    //Enabling default logging at class level
    private static final Logger logger = Logger.getLogger(SpotifyArtistDetail.class.getName());
    private String spotifyAccessToken;
    private String spotifyArtistId;


    /**
     * Constructor for SpotifyArtistDetail
     * @param spotifyAccessToken
     * @param spotifyArtistId
     */
    public SpotifyArtistDetail(String spotifyAccessToken, String spotifyArtistId) {
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
    protected AlbumArtist doInBackground(Void... voids) {
        AlbumArtist albumArtist = getSpotifyArtistDetail();
        return albumArtist;
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
            logger.log(Level.WARNING,"We have not received a valid access token.");
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
        List<String> artistGenres = new ArrayList<>();
        float artistPopularity;

        SpotifyService spotifyService = getSpotifyService();
        ArtistSimple simpleArtist = spotifyService.getArtist(getSpotifyArtistId());
        logger.info("Android Spotify Artist Name: "+simpleArtist.name);
        artistName = simpleArtist.name;

        Artist artist = spotifyService.getArtist(getSpotifyArtistId());
        List<Image> artistImageList = artist.images;
        int maxWidth = 0;
        for(Image image:artistImageList){
            if(image.width > maxWidth)
                maxWidth = image.width;
        }
        for(Image image:artistImageList){
            if (image.width == maxWidth) {
                logger.info("Image Width: " + image.width);
                logger.info("Image Height" + image.height);
                logger.info("Album Image: " + image.url);
                artistImageURL = image.url;
            }
        }
        for(String genre: artist.genres){
            logger.info("Artist Genre: "+genre);
            artistGenres.add(genre);
        }
        logger.info("Artist Popularity: "+artist.popularity);
        artistPopularity = ((float) (artist.popularity/100.0)*5);
        return new AlbumArtist(getSpotifyArtistId(),artistName,artistImageURL,artistGenres,artistPopularity);
    }
}
