package de.uniba.androidspotifymusicdataapp.model;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TrackSimple;

/**
 * This class is the helper class for the DetailActivity and responsible for fetching the TrackDetails from Spotify Api Wrapper
 * Created by chandan on 02/01/2017.
 */
public class SpotifyAlbumDetail extends AsyncTask<Void,Void,List<AlbumTrack>>{

    //Enabling default logging at class level
    private static final Logger logger = Logger.getLogger(SpotifyAlbumDetail.class.getName());

    private String spotifyAlbumId;
    private String spotifyAccessToken;

    /**
     * Constructor for SpotifyAlbumDetail
     * @param spotifyAlbumId
     * @param spotifyAccessToken
     */
    public SpotifyAlbumDetail(String spotifyAlbumId, String spotifyAccessToken) {
        this.spotifyAlbumId = spotifyAlbumId;
        this.spotifyAccessToken = spotifyAccessToken;
    }

    /**
     * Getter methods for the instance instance variables
     */
    public String getSpotifyAlbumId() {
        return spotifyAlbumId;
    }

    public String getSpotifyAccessToken() {
        return spotifyAccessToken;
    }

    /**
     * This method will run on the Background Thread
     * @param voids
     * @return
     */
    @Override
    protected List<AlbumTrack> doInBackground(Void... voids) {
        List<AlbumTrack> albumTrackList = new ArrayList<>();
        albumTrackList = getAlbumTracks();
        return albumTrackList;
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
     * Returns a list of AlbumTracks
     * @return
     */
    public List<AlbumTrack> getAlbumTracks(){
        List<AlbumTrack> albumTrackList = new ArrayList<>();
        String trackId=null;
        String trackName = null;
        String trackDuration;
        int trackPopularity;

        try {
            SpotifyService spotifyService = getSpotifyService();
            Album spotifyAlbum = spotifyService.getAlbum(getSpotifyAlbumId());
            Pager<TrackSimple> trackSimplePager = spotifyAlbum.tracks;
            List<TrackSimple> simpleTrackList = trackSimplePager.items;
            for (TrackSimple simpleTrack : simpleTrackList) {
                logger.info("Track Id: " + simpleTrack.id);
                trackId = simpleTrack.id;
                logger.info("Track Name: " + simpleTrack.name);
                trackName = simpleTrack.name;
                int seconds = (int) ((simpleTrack.duration_ms / 1000) % 60);
                int minutes = (int) ((simpleTrack.duration_ms / 1000) / 60);
                logger.info("Track Duration: " + minutes+"."+seconds);
                trackDuration = minutes+"."+seconds;
                Track spotifyTrack = spotifyService.getTrack(trackId);
                logger.info("Track Popularity: " + spotifyTrack.popularity);
                trackPopularity = spotifyTrack.popularity;
                albumTrackList.add(new AlbumTrack(trackId,trackName,trackDuration,trackPopularity));
            }
        }catch (NullPointerException nP){
            logger.log(Level.WARNING,"We have got null pointer exception: "+nP.getMessage());
        }
        return albumTrackList;
    }
}
