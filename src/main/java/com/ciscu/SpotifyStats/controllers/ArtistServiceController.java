package com.ciscu.SpotifyStats.controllers;

import com.ciscu.SpotifyStats.exceptions.RecordNotFoundException;
import com.ciscu.SpotifyStats.exceptions.RecordUnauthorizedException;
import com.ciscu.SpotifyStats.models.Artist;
import com.ciscu.SpotifyStats.services.ArtistService;
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
@RequestMapping("/artist")
public class ArtistServiceController {
    
    @Autowired
    ArtistService service;
    
    /**
     * Function that returns all artists
     * @param headers Header that should have the apikey
     * @return List of all artists
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists(@RequestHeader Map<String, String> headers)
            throws RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            List<Artist> list = service.getAllArtists();
        
            return new ResponseEntity<List<Artist>>(list, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
   /**
     * Function returned by a specific artist
     * @param name artist name
     * @param headers Header that should have the apikey
     * @return a specific artist
     * @throws RecordNotFoundException Exception if not found artist in database
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @GetMapping("/{name}")
    public ResponseEntity<Artist> getUserById(@PathVariable("name") String name, @RequestHeader Map<String, String> headers)
            throws RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            Artist entity = service.getArtistById(name);
        
            return new ResponseEntity<Artist>(entity, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that creates a artist 
     * @param myArtist the artist to be created
     * @param headers Header that should have the apikey
     * @return the artist created
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @PostMapping
    public ResponseEntity<Artist> createArtist(@Valid @RequestBody Artist myArtist, @RequestHeader Map<String, String> headers)
            throws RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            Artist entity = service.createArtist(myArtist);
        
            return new ResponseEntity<Artist>(entity, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that update a artist
     * @param myArtist artist to update
     * @param headers Header that should have the apikey
     * @return artist updated
     * @throws RecordNotFoundException Exception if not found artist in database
     * @throws RecordUnauthorizedException Exception if header does not contain a valid apikey
     */
    @PutMapping
    public ResponseEntity<Artist> updateArtist(@Valid @RequestBody Artist myArtist, @RequestHeader Map<String, String> headers)
            throws RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            Artist entity = service.updateArtist(myArtist);
        
            return new ResponseEntity<Artist>(entity, new HttpHeaders(), HttpStatus.OK);
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
    /**
     * Function that delete a artist from the database
     * @param name name aritst to delete
     * @param headers Header that should have the apikey
     * @return Status Accepted
     * @throws RecordNotFoundException Exception if not found artist in database
     * @throws RecordNotFoundException Exception if header does not contain a valid apikey
     */
    @DeleteMapping("/{name}")
    public HttpStatus deleteArtistById(@PathVariable("name") String name, @RequestHeader Map<String, String> headers)
            throws  RecordNotFoundException, RecordUnauthorizedException{
        if(Utils.apiKey(headers)){
            service.deleteArtistById(name);
        
            return HttpStatus.ACCEPTED;
        }else{
            throw new RecordUnauthorizedException("API Key ERROR");
        }
    }
    
}

