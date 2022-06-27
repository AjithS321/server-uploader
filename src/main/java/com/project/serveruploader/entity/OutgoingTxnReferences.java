package com.project.serveruploader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.loader.plan.build.internal.LoadGraphLoadPlanBuildingStrategy;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "outgoing_txn_references")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutgoingTxnReferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "payment_seq")
    String paymentSeq;

    @Column(name = "txn_ref")
    String txnRef;

    @Column(name = "rcv_Status")
    String rcvStatus;

    @Column(name = "status_Code")
    String statusCode;

    @Column(name = "error_Msg")
    String errorMsg;

    @Column(name = "response_date")
    String responseDate;

    @Column(name = "response_time")
    String responseTime;

    @ManyToOne
    @JoinColumn(name="outgoing_id", nullable=false)
    OutgoingRec outgoingRec;

    public OutgoingTxnReferences(String paymentSeq, String txnRef, String rcvStatus, String statusCode, String errorMsg, String responseDate, String responseTime, OutgoingRec outgoingRec) {
        this.paymentSeq = paymentSeq;
        this.txnRef = txnRef;
        this.rcvStatus = rcvStatus;
        this.statusCode = statusCode;
        this.errorMsg = errorMsg;
        this.responseDate = responseDate;
        this.responseTime = responseTime;
        this.outgoingRec = outgoingRec;
    }
}
