package jpa.board.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class TimeEntity {
    @CreatedDate
    @DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;
}
