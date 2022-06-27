package com.project.serveruploader.dto;

import com.project.serveruploader.entity.IncomingTxnReferences;
import com.project.serveruploader.entity.OutgoingRec;
import com.project.serveruploader.entity.OutgoingTxnReferences;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutgoingTxnReferencesDto {
    String paymentSeq;

    String txnRef;

    String rcvStatus;

    String statusCode;

    String errorMsg;

    public static OutgoingTxnReferencesDto toDto(OutgoingTxnReferences entity){
        return new OutgoingTxnReferencesDto(entity.getPaymentSeq(), entity.getTxnRef(), entity.getRcvStatus(),
                entity.getStatusCode(), entity.getErrorMsg());
    }

    public static OutgoingTxnReferences toEntityFromIncomeRefsDto(IncomingTxnReferencesDto dto, OutgoingRec outgoingRec){
        return new OutgoingTxnReferences(dto.getPaymentSeq(),dto.getTxnRef(),"ACKED",
                "00","Success", LocalDate.now().toString(),LocalTime.now().toString(),outgoingRec);
    }

}
