package com.ciscu.SpotifyStats.controllers;

import com.ciscu.SpotifyStats.exceptions.RecordNotFoundException;
import com.ciscu.SpotifyStats.exceptions.RecordUnauthorizedException;
import com.ciscu.SpotifyStats.models.AUX_User_Filter;
import com.ciscu.SpotifyStats.models.User;
import com.ciscu.SpotifyStats.services.UserService;
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
@RequestMapping("/user")
public class UserServiceController {
    
    @Autowired
    UserService service;
    
    /**
     * Function that returns all users
     * @param headers Header that should have the apikey
     * @return List of all users
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader Map<String, String> headers)
            throws RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            List<User> list = service.getAllUsers();
        
            return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function returned by a specific user
     * @param id user id
     * @param headers Header that should have the apikey
     * @return a specific user
     * @throws RecordNotFoundException Exception if not found user in database
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id, @RequestHeader Map<String, String> headers)
            throws RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            User entity = service.getUserById(id);
        
            return new ResponseEntity<User>(entity, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that returns all users from the database without current user
     * @param id id of the client that is not listed
     * @param headers Header that should have the apikey
     * @return all users from the database without current user
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @GetMapping("/without/{id}")
    public ResponseEntity<List<User>> getUsersWithoutUser(@PathVariable("id") String id, @RequestHeader Map<String, String> headers)
            throws RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            List<User> list = service.getUsersWithoutUser(id);
        
            return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that returns all unfollowed users from the database
     * @param id user id
     * @param headers Header that should have the apikey
     * @return all unfollowed users
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @GetMapping("unfollowed/{id}")
    public ResponseEntity<List<User>> getAllUnfollowedUsers(@PathVariable("id") String id, @RequestHeader Map<String, String> headers)
            throws RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            List<User> list = service.getAllUnfollowedUsers(id);
        
            return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that allows the user to find a friend from the database (if not followed)
     * @param aux_user contains the id of the current client just to make sure he does not appear in the list
     *          and the id_filter is by the name that is filtered
     * @param headers Header that should have the apikey
     * @return user list
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @PutMapping("filter")
    public ResponseEntity<List<User>> getUnfollowedUsersByFilter(@RequestBody AUX_User_Filter aux_user, @RequestHeader Map<String, String> headers)
            throws RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            String id = aux_user.getId();
            String id_filter = aux_user.getId_filter()+ "%";
            List<User> list = service.getUnfollowedUsersByFilter(id, id_filter);

            return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that creates a user 
     * @param myUser the user to be created
     * @param headers Header that should have the apikey
     * @return the user created
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User myUser, @RequestHeader Map<String, String> headers)
            throws RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            User entity = service.createUser(myUser);
        
            return new ResponseEntity<User>(entity, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that update a user
     * @param myUser user to update
     * @param headers Header that should have the apikey
     * @return user updated
     * @throws RecordNotFoundException Exception if not found user in database
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User myUser, @RequestHeader Map<String, String> headers)
            throws RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            User entity = service.updateUser(myUser);
        
            return new ResponseEntity<User>(entity, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that delete a user from the database
     * @param id id user to delete
     * @param headers Header that should have the apikey
     * @return Status Accepted
     * @throws RecordNotFoundException Exception if not found user in database
     * @throws RecordNotFoundException Exception if header does not contain a valid apikey
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteUserById(@PathVariable("id") String id, @RequestHeader Map<String, String> headers)
            throws  RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            service.deleteUserById(id);
        
            return HttpStatus.ACCEPTED;
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that erases all the information related with the user
     * @param id id user to delete
     * @param headers Header that should have the apikey
     * @return Status Accepted
     * @throws RecordNotFoundException Exception if not found user in database
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @DeleteMapping("/all/{id}")
    public HttpStatus deleteAllUserById(@PathVariable("id") String id, @RequestHeader Map<String, String> headers)
            throws  RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            service.deleteAllUserById(id);
        
            return HttpStatus.ACCEPTED;
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
}
