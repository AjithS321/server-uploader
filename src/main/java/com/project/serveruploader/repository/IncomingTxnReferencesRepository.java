package com.project.serveruploader.repository;

import com.project.serveruploader.entity.IncomingTxnReferences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IncomingTxnReferencesRepository extends JpaRepository<IncomingTxnReferences, Long> {
}
