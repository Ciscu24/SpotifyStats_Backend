package com.ciscu.SpotifyStats.controllers;

import com.ciscu.SpotifyStats.exceptions.RecordNotFoundException;
import com.ciscu.SpotifyStats.exceptions.RecordUnauthorizedException;
import com.ciscu.SpotifyStats.models.Track;
import com.ciscu.SpotifyStats.services.TrackService;
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
@RequestMapping("/song")
public class TrackServiceController {
    
    @Autowired
    TrackService service;
    
    /**
     * Function that returns all tracks
     * @param headers Header that should have the apikey
     * @return List of all tracks
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @GetMapping
    public ResponseEntity<List<Track>> getAllTracks(@RequestHeader Map<String, String> headers)
            throws RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
          List<Track> list = service.getAllTracks();
        
            return new ResponseEntity<List<Track>>(list, new HttpHeaders(), HttpStatus.OK);  
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function returned by a specific track
     * @param id track id
     * @param headers Header that should have the apikey
     * @return a specific track
     * @throws RecordNotFoundException Exception if not found track in database
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable("id") String id, @RequestHeader Map<String, String> headers)
            throws RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            Track entity = service.getTrackById(id);
        
            return new ResponseEntity<Track>(entity, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that creates a track 
     * @param mySong the track to be created
     * @param headers Header that should have the apikey
     * @return the track created
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @PostMapping
    public ResponseEntity<Track> createTrack(@Valid @RequestBody Track mySong, @RequestHeader Map<String, String> headers)
            throws RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
           Track entity = service.createTrack(mySong);
        
            return new ResponseEntity<Track>(entity, new HttpHeaders(), HttpStatus.OK); 
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that update a track
     * @param mySong track to update
     * @param headers Header that should have the apikey
     * @return track updated
     * @throws RecordNotFoundException Exception if not found track in database
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @PutMapping
    public ResponseEntity<Track> updateTrack(@Valid @RequestBody Track mySong, @RequestHeader Map<String, String> headers)
            throws RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            Track entity = service.updateTrack(mySong);
        
            return new ResponseEntity<Track>(entity, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that delete a track from the database
     * @param id id track to delete
     * @param headers Header that should have the apikey
     * @return Status Accepted
     * @throws RecordNotFoundException Exception if not found track in database
     * @throws RecordNotFoundException Exception if header does not contain a valid apikey
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteTrackById(@PathVariable("id") String id, @RequestHeader Map<String, String> headers)
            throws  RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            service.deleteTrackById(id);
        
            return HttpStatus.ACCEPTED;
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
}
