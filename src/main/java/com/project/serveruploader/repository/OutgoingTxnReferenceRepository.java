package com.project.serveruploader.repository;

import com.project.serveruploader.entity.OutgoingRec;
import com.project.serveruploader.entity.OutgoingTxnReferences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutgoingTxnReferenceRepository extends JpaRepository<OutgoingTxnReferences, Long> {

    List<OutgoingTxnReferences> findByOutgoingRec(OutgoingRec outgoingRec);
}
