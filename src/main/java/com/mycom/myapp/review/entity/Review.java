package com.mycom.myapp.review.entity;

import com.mycom.myapp.region.entity.Region;
import com.mycom.myapp.review.dto.ReviewUpdateRequestDto;
import com.mycom.myapp.user.dto.UserInfo;
import com.mycom.myapp.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="reviews")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    private String title;
    private String content;

    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "likes", nullable = false)
    private int likesCount;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isWriter(UserInfo userInfo){
        return this.user.getId() == userInfo.getId();
    }

    public void updateTitleAndContent(ReviewUpdateRequestDto request) {
        request.validateForUpdate();

        if (request.getTitle() != null) {
            this.title = request.getTitle().trim();
        }

        if (request.getContent() != null) {
            this.content = request.getContent().trim();
        }
    }

    public void incrementLikes() {
        this.likesCount++;
    }

    public void decrementLikes() {
        if (this.likesCount > 0) {
            this.likesCount--;
        }
    }
}
