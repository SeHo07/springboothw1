package com.likelion.springsession.post.entity;

import com.likelion.springsession.comment.entity.Comment;
import com.likelion.springsession.user.entity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.likelion.springsession.comment.entity.Comment;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // post/entity/Post.java

    @ManyToOne(fetch = FetchType.LAZY)              // ← 추가
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    public Post(User author, String title, String content) {   // ← 생성자 수정
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public Post(@NotBlank(message = "제목은 필수!") @Size(max=100, message = "제목은 100자 이하!") String title, @NotBlank(message = "본문은 필수!") @Size(max = 2000, message = "본문은 2000자 이하!") String content) {
    }

    public boolean isAuthor(Long userId) {          // ← 추가
        return this.author.getId().equals(userId);
    }


    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}