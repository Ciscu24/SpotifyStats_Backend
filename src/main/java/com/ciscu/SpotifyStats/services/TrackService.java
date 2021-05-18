package com.ciscu.SpotifyStats.services;

import com.ciscu.SpotifyStats.exceptions.RecordNotFoundException;
import com.ciscu.SpotifyStats.models.Track;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ciscu.SpotifyStats.repositories.TrackRepository;

@Service
public class TrackService {
    @Autowired
    TrackRepository repository;
    
    public List<Track> getAllTracks(){
        List<Track> songList = repository.findAll();
        
        if(songList.size() > 0){
            return songList;
        }else{
            return new ArrayList<>();
        }
    }
    
    public Track getTrackById(String id) throws RecordNotFoundException{
        Optional<Track> song = repository.findById(id);
        
        if(song.isPresent()){
            return song.get();
        }else{
            throw new RecordNotFoundException("No song record exist for given id", id);
        }
    }
    
    public Track createTrack(Track entity){
        entity = repository.save(entity);
        return entity;
    }
    
    public Track updateTrack(Track entity) throws RecordNotFoundException{
        if(entity.getId() != null){
            Optional<Track> song = repository.findById(entity.getId());
            
            if(song.isPresent()){
                Track newEntity = song.get();
                newEntity.setTrackName(entity.getTrackName());
                newEntity.setTrackThumbnail(entity.getTrackThumbnail());
                newEntity.setSpotifyURL(entity.getSpotifyURL());
                newEntity.setUsersTracks(entity.getUsersTrack());
                
                newEntity = repository.save(newEntity);
                
                return newEntity;
            }else{
                throw new RecordNotFoundException("Song not found", entity.getId());
            }
        }else{
            throw new RecordNotFoundException("No id of song given", 0l);
        }
    }
    
    public void deleteTrackById(String id) throws RecordNotFoundException{
        Optional<Track> song = repository.findById(id);
        
        if(song.isPresent()){
            repository.deleteById(id);
        }else{
            throw new RecordNotFoundException("No song record exist for given id", id);
        }
    }
}