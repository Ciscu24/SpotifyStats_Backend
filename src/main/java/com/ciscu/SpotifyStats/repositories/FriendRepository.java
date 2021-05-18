package com.ciscu.SpotifyStats.repositories;

import com.ciscu.SpotifyStats.models.Friend;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendRepository extends JpaRepository<Friend, Long>{
    @Modifying
    @Transactional
    @Query(value="DELETE FROM friend WHERE user_primary = ?1 AND user_secondary = ?2", nativeQuery=true)
    public void deleteByUsersPS(String user_primary, String user_secondary);
    
    @Query(value="SELECT * FROM friend WHERE user_primary = ?1 AND user_secondary = ?2", nativeQuery=true)
    public Friend getFriendByUsersPS(String user_primary, String user_secondary);
}
