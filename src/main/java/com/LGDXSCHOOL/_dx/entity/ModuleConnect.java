package com.LGDXSCHOOL._dx.entity;

import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public class ModuleConnect {

    @Id
    @Column(name = "RFID_ID")
    private String rfidId;

    @Column(name = "MODULE_ID")
    private String moduleId;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

}
