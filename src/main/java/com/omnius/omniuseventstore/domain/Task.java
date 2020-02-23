package com.omnius.omniuseventstore.domain;

import com.omnius.omniuseventstore.domain.converter.PriorityConverter;
import com.omnius.omniuseventstore.domain.converter.StatusConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(schema = "omnius_event_store", name = "task")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "status-type", typeClass = StatusConverter.class)
@TypeDef(name = "priority-type", typeClass = PriorityConverter.class)
@EntityListeners(AuditingEntityListener.class)
public class Task implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;
    private String title;
    private String description;
    @Type(type = "status-type")
    private Status status;
    @Type(type = "priority-type")
    private Priority priority;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dueDate;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime resolvedAt;

    @CreatedDate
    @Column(updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
