package com.gamaza.examples.digimon.entity.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static com.gamaza.examples.digimon.constant.EntityConstants.FIELD_CREATED_DATE_STRING;
import static com.gamaza.examples.digimon.constant.EntityConstants.FIELD_MODIFIED_DATE_STRING;

@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class Auditable {

    @CreatedDate
    @Column(name = FIELD_CREATED_DATE_STRING, nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = FIELD_MODIFIED_DATE_STRING, nullable = false)
    private LocalDateTime modifiedDate;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}
