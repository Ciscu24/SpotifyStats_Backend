package com.ciscu.SpotifyStats.services;

import com.ciscu.SpotifyStats.exceptions.RecordNotFoundException;
import com.ciscu.SpotifyStats.models.Artist;
import com.ciscu.SpotifyStats.repositories.ArtistRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {
    @Autowired
    ArtistRepository repository;
    
    public List<Artist> getAllArtists(){
        List<Artist> artistList = repository.findAll();
        
        if(artistList.size() > 0){
            return artistList;
        }else{
            return new ArrayList<>();
        }
    }
    
    public Artist getArtistById(String name) throws RecordNotFoundException{
        Optional<Artist> artist = repository.findById(name);
        
        if(artist.isPresent()){
            return artist.get();
        }else{
            throw new RecordNotFoundException("No artist record exist for given id", name);
        }
    }
    
    public Artist createArtist(Artist entity){
        entity = repository.save(entity);
        return entity;
    }
    
    public Artist updateArtist(Artist entity) throws RecordNotFoundException{
        if(entity.getName() != null){
            Optional<Artist> artist = repository.findById(entity.getName());
            
            if(artist.isPresent()){
                Artist newEntity = artist.get();
                newEntity.setName(entity.getName());
                newEntity.setImage(entity.getImage());
                newEntity.setSpotifyURL(entity.getSpotifyURL());
                newEntity.setUsersArtist(entity.getUsersArtist());
                
                newEntity = repository.save(newEntity);
                
                return newEntity;
            }else{
                throw new RecordNotFoundException("Artist not found", entity.getName());
            }
        }else{
            throw new RecordNotFoundException("No id of artist given", 0l);
        }
    }
    
    public void deleteArtistById(String name) throws RecordNotFoundException{
        Optional<Artist> artist = repository.findById(name);
        
        if(artist.isPresent()){
            repository.deleteById(name);
        }else{
            throw new RecordNotFoundException("No artist record exist for given id", name);
        }
    }
}