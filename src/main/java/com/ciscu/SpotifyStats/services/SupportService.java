package com.ciscu.SpotifyStats.services;

import com.ciscu.SpotifyStats.exceptions.RecordNotFoundException;
import com.ciscu.SpotifyStats.models.Support;
import com.ciscu.SpotifyStats.repositories.SupportRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupportService {
    @Autowired
    SupportRepository repository;
    
    public List<Support> getAllMessages(){
        List<Support> messageList = repository.findAll();
        
        if(messageList.size() > 0){
            return messageList;
        }else{
            return new ArrayList<>();
        }
    }
    
    public Support getMessageById(Long id) throws RecordNotFoundException{
        Optional<Support> message = repository.findById(id);
        
        if(message.isPresent()){
            return message.get();
        }else{
            throw new RecordNotFoundException("No message record exist for given id", id);
        }
    }
    
    public Support createMessage(Support entity){
        entity = repository.save(entity);
        return entity;
    }
    
    public Support updateMessage(Support entity) throws RecordNotFoundException{
        if(entity.getId() != null){
            Optional<Support> message = repository.findById(entity.getId());
            
            if(message.isPresent()){
                Support newEntity = message.get();
                newEntity.setId_user(entity.getId_user());
                newEntity.setIssue(entity.getIssue());
                newEntity.setDescription(entity.getDescription());
                
                newEntity = repository.save(newEntity);
                
                return newEntity;
            }else{
                throw new RecordNotFoundException("Message not found", entity.getId());
            }
        }else{
            throw new RecordNotFoundException("No id of message given", 0l);
        }
    }
    
    public void deleteMessageById(Long id) throws RecordNotFoundException{
        Optional<Support> message = repository.findById(id);
        
        if(message.isPresent()){
            repository.deleteById(id);
        }else{
            throw new RecordNotFoundException("No message record exist for given id", id);
        }
    }
}
