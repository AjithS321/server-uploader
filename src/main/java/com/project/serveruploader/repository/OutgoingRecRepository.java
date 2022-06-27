package com.project.serveruploader.repository;

import com.project.serveruploader.entity.OutgoingRec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutgoingRecRepository extends JpaRepository<OutgoingRec, Long> {
}
