package com.ciscu.SpotifyStats.services;

import com.ciscu.SpotifyStats.exceptions.RecordNotFoundException;
import com.ciscu.SpotifyStats.models.Artist;
import com.ciscu.SpotifyStats.models.Track;
import com.ciscu.SpotifyStats.models.User;
import com.ciscu.SpotifyStats.repositories.ArtistRepository;
import com.ciscu.SpotifyStats.repositories.TrackRepository;
import com.ciscu.SpotifyStats.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository repository;
    
    @Autowired
    ArtistRepository artistRepository;
    
    @Autowired
    TrackRepository trackRepository;
    
    public List<User> getAllUsers(){
        List<User> userList = repository.findAll();
        
        if(userList.size() > 0){
            return userList;
        }else{
            return new ArrayList<>();
        }
    }
    
    public User getUserById(String id) throws RecordNotFoundException{
        Optional<User> user = repository.findById(id);
        
        if(user.isPresent()){
            return user.get();
        }else{
            throw new RecordNotFoundException("No user record exist for given id", id);
        }
    }
    
    public List<User> getUsersWithoutUser(String id){
        List<User> userList = repository.getUsersWithoutUser(id);
        
        if(userList.size() > 0){
            return userList;
        }else{
            return new ArrayList<>();
        }
    }
    
    public List<User> getAllUnfollowedUsers(String id){
        List<User> userList = repository.getAllUnfollowedUsers(id);
        
        if(userList.size() > 0){
            return userList;
        }else{
            return new ArrayList<>();
        }
    }
    
    public List<User> getUnfollowedUsersByFilter(String id, String id_filter){
        List<User> userList = repository.getUnfollowedUsersByFilter(id, id_filter);
        
        if(userList.size() > 0){
            return userList;
        }else{
            return new ArrayList<>();
        }
    }
    
    public User createUser(User entity){
        entity = repository.save(entity);
        return entity;
    }
    
    public User updateUser(User entity) throws RecordNotFoundException{
        if(entity.getId() != null){
            Optional<User> user = repository.findById(entity.getId());
            
            if(user.isPresent()){
                User newEntity = user.get();
                newEntity.setDisplayName(entity.getDisplayName());
                newEntity.setFollowers(entity.getFollowers());
                newEntity.setImage(entity.getImage());
                newEntity.setSpotifyURL(entity.getSpotifyURL());
                newEntity.setArtists(entity.getArtists());
                newEntity.setTracks(entity.getTracks());
                
                newEntity = repository.save(newEntity);
                
                return newEntity;
            }else{
                throw new RecordNotFoundException("User not found", entity.getId());
            }
        }else{
            throw new RecordNotFoundException("No id of user given", 0l);
        }
    }
    
    public void deleteUserById(String id) throws RecordNotFoundException{
        Optional<User> user = repository.findById(id);
        
        if(user.isPresent()){
            repository.deleteById(id);
        }else{
            throw new RecordNotFoundException("No user record exist for given id", id);
        }
    }
    
    public void deleteAllUserById(String id) throws RecordNotFoundException{
        Optional<User> user = repository.findById(id);
        
        if(user.isPresent()){
            if(!user.get().getArtists().isEmpty()){
                List<Artist> artists = user.get().getArtists();
                for(Artist a: artists){
                    artistRepository.deleteById(a.getName());
                }
            }
            if(!user.get().getTracks().isEmpty()){
                List<Track> tracks = user.get().getTracks();
                for(Track t: tracks){
                    trackRepository.deleteById(t.getId());
                }
            }
            repository.deleteById(id);
        }else{
            throw new RecordNotFoundException("No user record exist for given id", id);
        }
    }
}
