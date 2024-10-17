package com.socialmedia.repository;

import com.socialmedia.model.Like;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends CrudRepository<Like, Long> {

    boolean existsByPostAndUser(Post post, User user);

    Optional<Like> findByPostAndUser(Post post, User user);

    long countByPostId(Long postId);
}