package com.ciscu.SpotifyStats.repositories;

import com.ciscu.SpotifyStats.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrackRepository extends JpaRepository<Track, String>{
    
}
