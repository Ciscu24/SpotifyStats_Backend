package com.ciscu.SpotifyStats.repositories;

import com.ciscu.SpotifyStats.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, String>{
    
}
