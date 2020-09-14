package com.depromeet.qr.base;

import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@Where(clause = "deleted = false")
public abstract class BaseEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @PrePersist
    public void create() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void update() {
        this.modifiedAt = LocalDateTime.now();
    }

    public void delete() {
        this.deleted = true;
    }
}
