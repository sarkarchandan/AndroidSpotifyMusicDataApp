package de.uniba.androidspotifymusicdataapp.model;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.NewReleases;
import kaaes.spotify.webapi.android.models.Pager;

/**
 * SpotifyEngine class will connect the Spotify Api for Android and fetch the required data
 * Created by chandan on 01/01/2017.
 */
public class SpotifyEngine extends AsyncTask<Void,Void,List<CardAlbum>>{

    //Enabling logging at the class level
    private static final Logger logger = Logger.getLogger(SpotifyEngine.class.getName());

    private String accessToken;

    private static List<CardAlbum> cardAlbumList;

    /**
     * Constructor for SpotifyEngine
     * @param accessToken
     */
    public SpotifyEngine(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Getter method for the accessToken
     * @return
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * This methods will run on the back ground thread
     * @param voids
     * @return
     */
    @Override
    protected List<CardAlbum> doInBackground(Void... voids) {
        cardAlbumList = getNewReleasedAlbums();
        return cardAlbumList;
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
            logger.log(Level.WARNING,"We have not received a valid access token.");
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
     * Methods
     * @return
     */
    public List<CardAlbum> getNewReleasedAlbums(){
        List<CardAlbum> cardAlbumList = new ArrayList<>();
        String artistName = null;
        String albumImageURL=null;
        String albumName = null;

        SpotifyService spotifyService = getSpotifyService();
        if(spotifyService!=null) {
            NewReleases newReleases = spotifyService.getNewReleases();
            Pager<AlbumSimple> albumSimplePager = newReleases.albums;

            //Getting the Album Name for CardAlbum
            List<AlbumSimple> albumSimpleList = albumSimplePager.items;
            for (AlbumSimple simpleAlbum : albumSimpleList) {
                logger.info("Album Name: " + simpleAlbum.name);
                albumName = simpleAlbum.name;

                //Getting the list of Album Artists for CardAlbum
                Album album = getSpotifyAlbumById(simpleAlbum.id);
                List<ArtistSimple> simpleArtistList = album.artists;
                for (ArtistSimple simpleArtist : simpleArtistList) {
                    logger.info("Simple Artist: " + simpleArtist.name);
                    artistName = simpleArtist.name;
                }

                //Getting the Album Image for CardAlbum
                List<Image> albumImages = simpleAlbum.images;
                for (Image albumImage : albumImages) {
                    if (albumImage.width > 600) {
                        logger.info("Image Width: " + albumImage.width);
                        logger.info("Image Height" + albumImage.height);
                        logger.info("Album Image: " + albumImage.url);
                        albumImageURL = albumImage.url;
                    }
                }
                //Constructing the List of CardAlbumInstances
                if (simpleAlbum.name != null && albumImageURL != null && artistName !=null) {
                    cardAlbumList.add(new CardAlbum(albumImageURL, albumName, artistName));
                }
            }
        }else{
            logger.log(Level.WARNING,"We have not received valid instance of SpotifyService.");
        }
        return cardAlbumList;
    }
}
