package com.nc.resume._project.resume.jpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nc.resume._project.resume.entity.ChatLog;


public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
    //List<ChatLog> findByUserId(String userId);
}

