package com.ciscu.SpotifyStats.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="friend")
public class Friend{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="user_primary")
    private String user_primary;
    
    @Column(name="user_secondary")
    private String user_secondary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_primary() {
        return user_primary;
    }

    public void setUser_primary(String user_primary) {
        this.user_primary = user_primary;
    }

    public String getUser_secondary() {
        return user_secondary;
    }

    public void setUser_secondary(String user_secondary) {
        this.user_secondary = user_secondary;
    }
    
}
