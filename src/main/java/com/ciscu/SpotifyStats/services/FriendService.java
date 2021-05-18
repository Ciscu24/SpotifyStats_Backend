package com.ciscu.SpotifyStats.services;

import com.ciscu.SpotifyStats.exceptions.RecordNotFoundException;
import com.ciscu.SpotifyStats.models.Friend;
import com.ciscu.SpotifyStats.models.User;
import com.ciscu.SpotifyStats.repositories.FriendRepository;
import com.ciscu.SpotifyStats.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
    @Autowired
    FriendRepository repository;
    @Autowired
    UserRepository repositoryUser;
    
    public List<Friend> getAllFriends(){
        List<Friend> friendList = repository.findAll();
        
        if(friendList.size() > 0){
            return friendList;
        }else{
            return new ArrayList<>();
        }
    }
    
    public Friend getFriendById(Long id) throws RecordNotFoundException{
        Optional<Friend> friend = repository.findById(id);
        
        if(friend.isPresent()){
            return friend.get();
        }else{
            throw new RecordNotFoundException("No friend record exist for given id", id);
        }
    }
    
    public List<User> getFriendsByUser(String id) throws RecordNotFoundException{
        List<User> result = new ArrayList<>();
        List<Friend> friendList = repository.findAll();
        List<User> userList = repositoryUser.findAll();
        
        if(friendList.size() > 0 && userList.size() > 0){
            for(Friend f: friendList){
                if(f.getUser_primary().equals(id)){
                    boolean aux = false;
                    for(int i=0; i<userList.size() && !aux; i++){
                        if(userList.get(i).getId().equals(f.getUser_secondary())){
                            result.add(userList.get(i));
                            aux = true;
                        }
                    }
                }
            }
            return result;
        }else{
            return new ArrayList<>();
        }
    }
    
    public Friend createFriend(Friend entity){
        entity = repository.save(entity);
        return entity;
    }
    
    public Friend updateFriend(Friend entity) throws RecordNotFoundException{
        if(entity.getId() != null){
            Optional<Friend> friend = repository.findById(entity.getId());
            
            if(friend.isPresent()){
                Friend newEntity = friend.get();
                newEntity.setUser_primary(entity.getUser_primary());
                newEntity.setUser_secondary(entity.getUser_secondary());
                
                newEntity = repository.save(newEntity);
                
                return newEntity;
            }else{
                throw new RecordNotFoundException("Friend not found", entity.getId());
            }
        }else{
            throw new RecordNotFoundException("No id of frien given", 0l);
        }
    }
    
    public void deleteFriendById(Long id) throws RecordNotFoundException{
        Optional<Friend> friend = repository.findById(id);
        
        if(friend.isPresent()){
            repository.deleteById(id);
        }else{
            throw new RecordNotFoundException("No friend record exist for given id", id);
        }
    }
    
    public void deleteByUsersPS(String user_primary, String user_secondary) throws RecordNotFoundException{
        Friend friend = repository.getFriendByUsersPS(user_primary, user_secondary);
        
        if(friend != null){
            repository.deleteByUsersPS(user_primary, user_secondary);
        }else{
            throw new RecordNotFoundException("No friend exists", user_primary + " / " + user_secondary);
        }
    }
}
