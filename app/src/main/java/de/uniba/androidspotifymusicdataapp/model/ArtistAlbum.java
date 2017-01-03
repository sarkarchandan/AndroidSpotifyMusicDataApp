package de.uniba.androidspotifymusicdataapp.model;

/**
 * This class is the template for the each individual albums of an artist
 * Created by chandan on 03/01/2017.
 */
public class ArtistAlbum {

    private String artistAlbumId;
    private String artistAlbumName;
    private String artistAlbumReleaseDate;
    private float artistAlbumPopularity;
    private String artistAlbumImageURL;


    /**
     * Constructor for the ArtistAlbum
     * @param artistAlbumId
     * @param artistAlbumName
     * @param artistAlbumReleaseDate
     * @param artistAlbumPopularity
     * @param artistAlbumImageURL
     */
    public ArtistAlbum(String artistAlbumId, String artistAlbumName, String artistAlbumReleaseDate, float artistAlbumPopularity, String artistAlbumImageURL) {
        this.artistAlbumId = artistAlbumId;
        this.artistAlbumName = artistAlbumName;
        this.artistAlbumReleaseDate = artistAlbumReleaseDate;
        this.artistAlbumPopularity = artistAlbumPopularity;
        this.artistAlbumImageURL = artistAlbumImageURL;
    }

    /**
     * Getter and Setter methdos for the instance variables
     */
    public String getArtistAlbumId() {
        return artistAlbumId;
    }

    public void setArtistAlbumId(String artistAlbumId) {
        this.artistAlbumId = artistAlbumId;
    }

    public String getArtistAlbumName() {
        return artistAlbumName;
    }

    public void setArtistAlbumName(String artistAlbumName) {
        this.artistAlbumName = artistAlbumName;
    }

    public String getArtistAlbumReleaseDate() {
        return artistAlbumReleaseDate;
    }

    public void setArtistAlbumReleaseDate(String artistAlbumReleaseDate) {
        this.artistAlbumReleaseDate = artistAlbumReleaseDate;
    }

    public float getArtistAlbumPopularity() {
        return artistAlbumPopularity;
    }

    public void setArtistAlbumPopularity(float artistAlbumPopularity) {
        this.artistAlbumPopularity = artistAlbumPopularity;
    }

    public String getArtistAlbumImageURL() {
        return artistAlbumImageURL;
    }

    public void setArtistAlbumImageURL(String artistAlbumImageURL) {
        this.artistAlbumImageURL = artistAlbumImageURL;
    }
}
