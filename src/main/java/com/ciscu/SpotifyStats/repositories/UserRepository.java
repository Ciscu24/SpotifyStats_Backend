package com.ciscu.SpotifyStats.repositories;

import com.ciscu.SpotifyStats.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String>{
    @Query(value="SELECT * FROM user WHERE id != ?1", nativeQuery=true)
    public List<User> getUsersWithoutUser(String id);
    
    @Query(value="SELECT * FROM user WHERE id != ?1 AND id NOT IN (SELECT user_secondary FROM friend WHERE user_primary = ?1)", nativeQuery=true)
    public List<User> getAllUnfollowedUsers(String id);
    
    @Query(value="SELECT * FROM user WHERE id != ?1 AND id NOT IN (SELECT user_secondary FROM friend WHERE user_primary = ?1) AND display_name LIKE ?2", nativeQuery=true)
    public List<User> getUnfollowedUsersByFilter(String id, String id_filter);
}
