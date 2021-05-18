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
@Table(name="artist")
public class Artist{
    
    @Id
    @Column(name="name", nullable = false)
    private String name;
    
    @Column(name="image")
    private String image;
    
    @Column(name="spotifyURL")
    private String spotifyURL;
    
    @JsonIgnoreProperties(value = {"artists"}, allowSetters = true)
    @ManyToMany(mappedBy = "artists", cascade = {CascadeType.MERGE})
    private List<User> usersArtist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    public List<User> getUsersArtist() {
        return usersArtist;
    }

    public void setUsersArtist(List<User> users) {
        this.usersArtist = users;
        for(User u: users){
            List<Artist> artists = u.getArtists();
            if(artists == null){
                artists = new ArrayList<>();
            }
            if(!artists.contains(this)){
                artists.add(this);
            }
        }
    }
    
}

