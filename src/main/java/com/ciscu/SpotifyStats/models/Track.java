package com.ciscu.SpotifyStats.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="track")
public class Track {
    
    @Id
    @Column(name="id", nullable = false)
    private String id;
    
    @Column(name="trackName")
    private String trackName;
    
    @Column(name="trackThumbnail")
    private String trackThumbnail;
    
    @Column(name="spotifyURL")
    private String spotifyURL; 
    
    @JsonIgnoreProperties(value = {"tracks"}, allowSetters = true)
    @ManyToMany(mappedBy = "tracks", cascade = {CascadeType.MERGE})
    private List<User> usersTrack;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }
    
    public String getTrackThumbnail() {
        return trackThumbnail;
    }

    public void setTrackThumbnail(String trackThumbnail) {
        this.trackThumbnail = trackThumbnail;
    }
    
    public String getSpotifyURL() {
        return spotifyURL;
    }

    public void setSpotifyURL(String spotifyURL) {
        this.spotifyURL = spotifyURL;
    }

    public List<User> getUsersTrack() {
        return usersTrack;
    }

    public void setUsersTracks(List<User> users) {
        this.usersTrack = users;
        
        this.usersTrack = users;
        for(User u: users){
            List<Track> tracks = u.getTracks();
            if(tracks == null){
                tracks = new ArrayList<>();
            }
            if(!tracks.contains(this)){
                tracks.add(this);
            }
        }
    }
    
}
