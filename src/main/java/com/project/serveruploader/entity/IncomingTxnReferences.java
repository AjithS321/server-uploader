package com.project.serveruploader.entity;

import com.project.serveruploader.dto.IncomingTxnReferencesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "incoming_txn_references")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IncomingTxnReferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "payment_seq")
    String PaymentSeq;

    @Column(name = "txn_ref")
    String txnRef;

    @Column(name = "txn_curr")
    String txnCurr;

    @Column(name = "settlement_date")
    String settlementDate;

    @Column(name = "ord_cust_account")
    String ordCustAccount;

    @Column(name = "ord_cus_name")
    String ordCusName;

    @Column(name = "ben_depart")
    String benDepart;

    @Column(name = "txn_purpose")
    String txnPurpose;

    @Column(name = "ben_bank_bic")
    String benBankBIC;

    @Column(name = "ben_account")
    String benAccount;

    @Column(name = "ben_name")
    String benName;

    @Column(name = "ben_cr_amount")
    String benCrAmount;

    @Column(name = "batch_inputter")
    String batchInputter;

    @Column(name = "batch_authoriser")
    String batchAuthoriser;

    @Column(name = "process_date")
    Long processDate;

    @Column(name = "process_time")
    Long processTime;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="income_id", nullable=false)
    IncomingRec incomingRec;

    public IncomingTxnReferences(String paymentSeq, String txnRef, String txnCurr, String settlementDate, String ordCustAccount, String ordCusName, String benDepart, String txnPurpose, String benBankBIC, String benAccount, String benName, String benCrAmount, String batchInputter, String batchAuthoriser, Long processDate, Long processTime, IncomingRec incomingRec) {
        PaymentSeq = paymentSeq;
        this.txnRef = txnRef;
        this.txnCurr = txnCurr;
        this.settlementDate = settlementDate;
        this.ordCustAccount = ordCustAccount;
        this.ordCusName = ordCusName;
        this.benDepart = benDepart;
        this.txnPurpose = txnPurpose;
        this.benBankBIC = benBankBIC;
        this.benAccount = benAccount;
        this.benName = benName;
        this.benCrAmount = benCrAmount;
        this.batchInputter = batchInputter;
        this.batchAuthoriser = batchAuthoriser;
        this.processDate = processDate;
        this.processTime = processTime;
        this.incomingRec = incomingRec;
    }

}
