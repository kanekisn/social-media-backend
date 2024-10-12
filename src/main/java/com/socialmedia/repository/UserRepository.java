package com.socialmedia.repository;

import com.socialmedia.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @EntityGraph(attributePaths = {"stack"})
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.stack")
    List<User> findAllUsersWithStack();

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.followers WHERE u.id = :userId")
    Optional<User> findByIdWithFollowers(@Param("userId") Long userId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.following LEFT JOIN FETCH u.stack WHERE u.id = :userId")
    Optional<User> findByIdWithFollowing(@Param("userId") Long userId);
}