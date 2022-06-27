package com.project.serveruploader.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.serveruploader.entity.IncomingRec;
import com.project.serveruploader.entity.IncomingTxnReferences;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomingTxnReferencesDto {


    @JsonProperty("PaymentSeq")
    String PaymentSeq;

    String txnRef;

    String txnCurr;

    String settlementDate;

    String ordCustAccount;

    String ordCusName;

    String benDepart;

    String txnPurpose;

    String benBankBIC;

    String benAccount;

    String benName;

    String benCrAmount;

    String batchInputter;

    String batchAuthoriser;

    Long processDate;

    Long processTime;

    public static IncomingTxnReferences toEntity(IncomingTxnReferencesDto dto, IncomingRec incomingRec){
        return new IncomingTxnReferences(dto.getPaymentSeq(),dto.getTxnRef(),dto.getTxnCurr(),
                dto.getSettlementDate(), dto.getOrdCustAccount(),dto.getOrdCusName(),dto.getBenDepart(),
                dto.getTxnPurpose(),dto.getBenBankBIC(),dto.getBenAccount(),dto.getBenName(),
                dto.getBenCrAmount(),dto.getBatchInputter(),dto.getBatchAuthoriser(),dto.getProcessDate(),
                dto.getProcessTime(), incomingRec);
    }

    public static IncomingTxnReferencesDto toDto(IncomingTxnReferences entity){
        return new IncomingTxnReferencesDto(entity.getPaymentSeq(),entity.getTxnRef(),entity.getTxnCurr(),
                entity.getSettlementDate(), entity.getOrdCustAccount(),entity.getOrdCusName(),entity.getBenDepart(),
                entity.getTxnPurpose(),entity.getBenBankBIC(),entity.getBenAccount(),entity.getBenName(),
                entity.getBenCrAmount(),entity.getBatchInputter(),entity.getBatchAuthoriser(),entity.getProcessDate(),
                entity.getProcessTime());
    }
}
