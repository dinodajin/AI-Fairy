package com.LGDXSCHOOL._dx.entity;

import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "MODULE_CONNECT_TB")
public class ModuleConnect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long no;

    @Column(name = "MODULE_ID")
    private String moduleId;

    @Column(name = "RFID_ID")
    private String rfidId;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "CONNECT_ONOFF")
    private String connectOnOff;

    // Getters and Setters
    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getRfidId() {
        return rfidId;
    }

    public void setRfidId(String rfidId) {
        this.rfidId = rfidId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getConnectOnOff() {
        return connectOnOff;
    }

    public void setConnectOnOff(String connectOnOff) {
        this.connectOnOff = connectOnOff;
    }
}
