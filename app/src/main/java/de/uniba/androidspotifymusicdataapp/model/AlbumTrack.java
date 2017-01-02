package de.uniba.androidspotifymusicdataapp.model;

/**
 * This class is the prototype of the Tracks that are going to be displayed as a part of the Album
 * Created by chandan on 02/01/2017.
 */
public class AlbumTrack {

    private String trackId;
    private String trackName;
    private String trackDuration;
    private int trackPopularity;

    /**
     * Constructor for the instance variables
     * @param trackId
     * @param trackName
     * @param trackDuration
     * @param trackPopularity
     */
    public AlbumTrack(String trackId, String trackName, String trackDuration, int trackPopularity) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackDuration = trackDuration;
        this.trackPopularity = trackPopularity;
    }

    /**
     * Getter and Setter methods for the instance variables
     */
    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackDuration() {
        return trackDuration;
    }

    public void setTrackDuration(String trackDuration) {
        this.trackDuration = trackDuration;
    }

    public int getTrackPopularity() {
        return trackPopularity;
    }

    public void setTrackPopularity(int trackPopularity) {
        this.trackPopularity = trackPopularity;
    }
}
