package com.socialmedia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Table(name = "comments")
@Entity
@BatchSize(size = 20)
public class Comment {
    @Id
    @SequenceGenerator(name = "comments_seq", sequenceName = "comments_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
}