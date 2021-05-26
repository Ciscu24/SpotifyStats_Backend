package com.ciscu.SpotifyStats.controllers;

import com.ciscu.SpotifyStats.exceptions.RecordNotFoundException;
import com.ciscu.SpotifyStats.exceptions.RecordUnauthorizedException;
import com.ciscu.SpotifyStats.models.Support;
import com.ciscu.SpotifyStats.services.SupportService;
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
@RequestMapping("/support")
public class SupportServiceController {
    @Autowired
    SupportService service;
    
    /**
     * Function that returns all messages
     * @param headers Header that should have the apikey
     * @return List of all nessages
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @GetMapping
    public ResponseEntity<List<Support>> getAllMessages(@RequestHeader Map<String, String> headers)
            throws RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
          List<Support> list = service.getAllMessages();
        
            return new ResponseEntity<List<Support>>(list, new HttpHeaders(), HttpStatus.OK);  
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function returned by a specific message
     * @param id message id
     * @param headers Header that should have the apikey
     * @return a specific message
     * @throws RecordNotFoundException Exception if not found message in database
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @GetMapping("/{id}")
    public ResponseEntity<Support> getMessageById(@PathVariable("id") Long id, @RequestHeader Map<String, String> headers)
            throws RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            Support entity = service.getMessageById(id);
        
            return new ResponseEntity<Support>(entity, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that creates a message 
     * @param myMessage the message to be created
     * @param headers Header that should have the apikey
     * @return the message created
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @PostMapping
    public ResponseEntity<Support> createMessage(@Valid @RequestBody Support myMessage, @RequestHeader Map<String, String> headers)
            throws RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
           Support entity = service.createMessage(myMessage);
        
            return new ResponseEntity<Support>(entity, new HttpHeaders(), HttpStatus.OK); 
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that update a message
     * @param myMessage message to update
     * @param headers Header that should have the apikey
     * @return message updated
     * @throws RecordNotFoundException Exception if not found message in database
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @PutMapping
    public ResponseEntity<Support> updateMessage(@Valid @RequestBody Support myMessage, @RequestHeader Map<String, String> headers)
            throws RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            Support entity = service.updateMessage(myMessage);
        
            return new ResponseEntity<Support>(entity, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that delete a message from the database
     * @param id message track to delete
     * @param headers Header that should have the apikey
     * @return Status Accepted
     * @throws RecordNotFoundException Exception if not found message in database
     * @throws RecordNotFoundException Exception if header does not contain a valid apikey
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteMessageById(@PathVariable("id") Long id, @RequestHeader Map<String, String> headers)
            throws  RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            service.deleteMessageById(id);
        
            return HttpStatus.ACCEPTED;
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
}
