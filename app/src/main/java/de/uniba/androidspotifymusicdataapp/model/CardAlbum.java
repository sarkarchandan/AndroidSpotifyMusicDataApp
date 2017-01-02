package de.uniba.androidspotifymusicdataapp.model;

import java.util.List;

/**
 * This class is the template for the Albums that are to be displayed in the form of Cards
 * Created by chandan on 01/01/2017.
 */
public class CardAlbum {

    private String albumId;
    private String albumImageURL;
    private String albumName;
    private String artistName;
    private int albumPopularity;
    private String albumReleaseDate;

    /**
     * Constructor for the instance variables.
     * @param albumId
     * @param albumImageURL
     * @param albumName
     * @param artistName
     */
    public CardAlbum(String albumId, String albumImageURL, String albumName, String artistName, int albumPopularity, String albumReleaseDate) {
        this.albumId = albumId;
        this.albumImageURL = albumImageURL;
        this.albumName = albumName;
        this.artistName = artistName;
        this.albumPopularity = albumPopularity;
        this.albumReleaseDate = albumReleaseDate;
    }

    /**
     * Getter and Setter methods for the instance variables
     */
    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumImageURL() {
        return albumImageURL;
    }

    public void setAlbumImageURL(String albumImageURL) {
        this.albumImageURL = albumImageURL;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getAlbumPopularity() {
        return albumPopularity;
    }

    public void setAlbumPopularity(int albumPopularity) {
        this.albumPopularity = albumPopularity;
    }

    public String getAlbumReleaseDate() {
        return albumReleaseDate;
    }

    public void setAlbumReleaseDate(String albumReleaseDate) {
        this.albumReleaseDate = albumReleaseDate;
    }
}
