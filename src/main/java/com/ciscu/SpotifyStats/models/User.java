package com.ciscu.SpotifyStats.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User{
    
    @Id
    @Column(name="id", nullable = false)
    private String id;
    
    @Column(name="displayName")
    private String displayName;
    
    @Column(name="followers")
    private int followers;
    
    @Column(name="image")
    private String image;
    
    @Column(name="spotifyURL")
    private String spotifyURL;
    
    @JsonIgnoreProperties(value = {"usersArtist"}, allowSetters = true)
    @ManyToMany()
    @JoinTable(name = "user_artist", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_artist"))
    private Set<Artist> artists;
    
    @JsonIgnoreProperties(value = {"usersTrack"}, allowSetters = true)
    @ManyToMany()
    @JoinTable(name = "user_track", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_track"))
    private Set<Track> tracks;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSpotifyURL() {
        return spotifyURL;
    }

    public void setSpotifyURL(String spotifyURL) {
        this.spotifyURL = spotifyURL;
    }
    
    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
        for(Artist a: artists){
            Set<User> users = a.getUsersArtist();
            if(users == null){
                users = new HashSet<>();
            }
            if(!users.contains(this)){
                users.add(this);
            }
        }
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Set<Track> songs) {
        this.tracks = songs;
        for(Track s: songs){
            Set<User> users = s.getUsersTrack();
            if(users == null){
                users = new HashSet<>();
            }
            if(!users.contains(this)){
                users.add(this);
            }
        }
    }
    
}
