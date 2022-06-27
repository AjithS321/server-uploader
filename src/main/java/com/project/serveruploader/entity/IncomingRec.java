package com.project.serveruploader.entity;

import com.project.serveruploader.dto.IncomingRecDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "incoming_rec")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomingRec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "process_type")
    String processType;

    @Column(name = "batch_number")
    String batchNumber;

    @Column(name = "batch_amount")
    String batchAmount;

    @Column(name = "no_of_payment")
    String noOfPayment;

    @Column(name = "batch_date")
    Long batchDate;

    @OneToMany(mappedBy="incomingRec")
    List<IncomingTxnReferences> incomingTxnReferences;

    public IncomingRec(String processType, String batchNumber, String batchAmount, String noOfPayment, Long batchDate) {
        this.processType = processType;
        this.batchNumber = batchNumber;
        this.batchAmount = batchAmount;
        this.noOfPayment = noOfPayment;
        this.batchDate = batchDate;
//        this.incomingTxnReferences = incomingTxnReferences;
    }
}
