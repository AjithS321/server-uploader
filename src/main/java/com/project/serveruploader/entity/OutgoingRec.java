package com.project.serveruploader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "outgoing_rec")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutgoingRec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "process_type")
    String processType;

    @Column(name = "batch_number")
    String batchNumber;

    @OneToMany(mappedBy="outgoingRec",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<OutgoingTxnReferences> outgoingTxnReferences;

    public OutgoingRec(String processType, String batchNumber) {
        this.processType = processType;
        this.batchNumber = batchNumber;
    }

    public OutgoingRec(String processType, String batchNumber, List<OutgoingTxnReferences> outgoingTxnReferences) {
        this.processType = processType;
        this.batchNumber = batchNumber;
        this.outgoingTxnReferences = outgoingTxnReferences;
    }
}
