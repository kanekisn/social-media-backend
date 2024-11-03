package com.socialmedia.repository;

import com.socialmedia.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @EntityGraph(attributePaths = {"stack"})
    Optional<User> findUserById(Long userId);

    @Query("SELECT u FROM User u LEFT JOIN u.stack s " +
            "WHERE (:username IS NULL OR u.username LIKE %:username%) " +
            "OR (:stack IS NULL OR s IN :stack)")
    Page<User> searchUsers(@Param("username") String username,
                           @Param("stack") List<String> stack,
                           Pageable pageable);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.stack")
    List<User> findAllUsersWithStack();

    @Query("SELECT f FROM User u JOIN u.followers f WHERE u.id = :userId")
    Page<User> findByIdWithFollowers(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT f FROM User u JOIN u.following f WHERE u.id = :userId")
    Page<User> findByIdWithFollowing(@Param("userId") Long userId, Pageable pageable);
}