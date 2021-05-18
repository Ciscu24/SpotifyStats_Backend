package com.ciscu.SpotifyStats.controllers;

import com.ciscu.SpotifyStats.exceptions.RecordNotFoundException;
import com.ciscu.SpotifyStats.exceptions.RecordUnauthorizedException;
import com.ciscu.SpotifyStats.models.Friend;
import com.ciscu.SpotifyStats.models.User;
import com.ciscu.SpotifyStats.services.FriendService;
import com.ciscu.SpotifyStats.utils.Utils;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/friend")
public class FriendServiceController {
    
    @Autowired
    FriendService service;
    
    /**
     * Function that returns all friends
     * @param headers Header that should have the apikey
     * @return List of all friends
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @GetMapping
    public ResponseEntity<List<Friend>> getAllFriends(@RequestHeader Map<String, String> headers)
            throws RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
           List<Friend> list = service.getAllFriends();

            return new ResponseEntity<List<Friend>>(list, new HttpHeaders(), HttpStatus.OK); 
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function returned by a specific friend
     * @param id friend id
     * @param headers Header that should have the apikey
     * @return a specific friend
     * @throws RecordNotFoundException Exception if not found friend in database
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @GetMapping("/{id}")
    public ResponseEntity<Friend> getFriendById(@PathVariable("id") Long id, @RequestHeader Map<String, String> headers)
            throws  RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
           Friend entity = service.getFriendById(id);
        
         return new ResponseEntity<Friend>(entity, new HttpHeaders(), HttpStatus.OK); 
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that return all friends by user id
     * @param id user id
     * @param headers Header that should have the apikey
     * @return all friends by user id
     * @throws RecordNotFoundException RecordNotFoundException Exception if not found user in database
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @GetMapping("user/{id}")
    public ResponseEntity<List<User>> getFriendsByUser(@PathVariable("id") String id, @RequestHeader Map<String, String> headers)
            throws  RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            List<User> list = service.getFriendsByUser(id);
        
            return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that creates a friend 
     * @param myFriend the friend to be created
     * @param headers Header that should have the apikey
     * @return the friend created
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @PostMapping
    public ResponseEntity<Friend> createFriend(@Valid @RequestBody Friend myFriend, @RequestHeader Map<String, String> headers)
            throws RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            Friend entity = service.createFriend(myFriend);
        
            return new ResponseEntity<Friend>(entity, new HttpHeaders(), HttpStatus.OK); 
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that update a friend
     * @param myFriend friend to update
     * @param headers Header that should have the apikey
     * @return friend updated
     * @throws RecordNotFoundException Exception if not found friend in database
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @PutMapping
    public ResponseEntity<Friend> updateFriend(@Valid @RequestBody Friend myFriend, @RequestHeader Map<String, String> headers)
            throws RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            Friend entity = service.updateFriend(myFriend);
        
            return new ResponseEntity<Friend>(entity, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that delete a friend from the database
     * @param id id friend to delete
     * @param headers Header that should have the apikey
     * @return Status Accepted
     * @throws RecordNotFoundException Exception if not found friend in database
     * @throws RecordNotFoundException Exception if header does not contain a valid apikey
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteFriendById(@PathVariable("id") Long id, @RequestHeader Map<String, String> headers)
            throws  RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            service.deleteFriendById(id);
        
            return HttpStatus.ACCEPTED;
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that delete a friend by user_primary and user_secondary from the database
     * @param myFriend friend to delete
     * @param headers Header that should have the apikey
     * @return Status Accepted
     * @throws RecordNotFoundException Exception if not found friend in database
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @PutMapping("/delete_friend")
    public HttpStatus deleteByUsersPS(@RequestBody Friend myFriend, @RequestHeader Map<String, String> headers)
            throws  RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            service.deleteByUsersPS(myFriend.getUser_primary(), myFriend.getUser_secondary());
        
            return HttpStatus.ACCEPTED;
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
}
