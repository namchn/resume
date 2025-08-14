package com.nc.resume._project.resume.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * ⏰ 현재 대화를 저장하는 Entity
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_log")
public class ChatLog {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Column(columnDefinition = "TEXT")
    private String requestMessage1;
    
    @Column(columnDefinition = "TEXT")
    private String requestMessage2;

    @Column(columnDefinition = "TEXT")
    private String responseMessage1;
    
    @Column(columnDefinition = "TEXT")
    private String responseMessage2;

    private String model; // ex: GPT-4, Claude 등

    private String language; // ex: ko, en 등

    private LocalDateTime createdAt;
    
    private String testYn;
}