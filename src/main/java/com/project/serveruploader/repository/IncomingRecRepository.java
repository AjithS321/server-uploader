package com.project.serveruploader.repository;

import com.project.serveruploader.entity.IncomingRec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IncomingRecRepository extends JpaRepository<IncomingRec, Long> {
}
