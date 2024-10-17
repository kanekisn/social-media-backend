package com.socialmedia.repository;

import com.socialmedia.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    @Query("SELECT p, COUNT(c) AS commentCount, COUNT(l) AS likeCount " +
            "FROM Post p " +
            "LEFT JOIN Comment c ON p.id = c.post.id " +
            "LEFT JOIN Like l ON p.id = l.post.id " +
            "WHERE p.author.id = :userId " +
            "GROUP BY p.id " +
            "ORDER BY p.createdAt DESC")
    Page<Object[]> findAllByUserId(@Param("userId") Long userId, Pageable pageable);
}