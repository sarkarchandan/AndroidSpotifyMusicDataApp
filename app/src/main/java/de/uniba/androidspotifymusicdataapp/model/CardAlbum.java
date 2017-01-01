package de.uniba.androidspotifymusicdataapp.model;

import java.util.List;

/**
 * This class is the template for the Albums that are to be displayed in the form of Cards
 * Created by chandan on 01/01/2017.
 */
public class CardAlbum {

    private String albumImageoURL;
    private String albumName;
    private String artistName;

    /**
     * Constructor for the instance variables.
     * @param albumImageoURL
     * @param albumName
     * @param artistName
     */
    public CardAlbum(String albumImageoURL, String albumName, String artistName) {
        this.albumImageoURL = albumImageoURL;
        this.albumName = albumName;
        this.artistName = artistName;
    }

    /**
     * Getter and Setter methods for the instance variables
     */
    public String getAlbumImageoURL() {
        return albumImageoURL;
    }

    public void setAlbumImageoURL(String albumImageoURL) {
        this.albumImageoURL = albumImageoURL;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumNam(String albumName) {
        this.albumName = albumName;
    }

    public String getArtistName() {
        return artistName;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
