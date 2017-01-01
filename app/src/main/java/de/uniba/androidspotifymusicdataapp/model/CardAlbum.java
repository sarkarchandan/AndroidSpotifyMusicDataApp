package de.uniba.androidspotifymusicdataapp.model;

import java.util.List;

/**
 * This class is the template for the Albums that are to be displayed in the form of Cards
 * Created by chandan on 01/01/2017.
 */
public class CardAlbum {

    private String albumImageoURL;
    private String albumName;
    private List<String> artistNames;

    /**
     * Constructor for the instance variables.
     * @param albumImageoURL
     * @param albumName
     * @param artistNames
     */
    public CardAlbum(String albumImageoURL, String albumName, List<String> artistNames) {
        this.albumImageoURL = albumImageoURL;
        this.albumName = albumName;
        this.artistNames = artistNames;
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

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<String> getArtistNames() {
        return artistNames;
    }

    public void setArtistNames(List<String> artistNames) {
        this.artistNames = artistNames;
    }
}
