package com.project.serveruploader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.serveruploader.entity.IncomingRec;
import com.project.serveruploader.entity.IncomingTxnReferences;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IncomingRecDto {

    String processType;

    @JsonProperty("BatchNumber")
    String batchNumber;
    @JsonProperty("BatchAmount")
    String batchAmount;
    @JsonProperty("NoOfPayment")
    String noOfPayment;

    Long batchDate;
    @JsonProperty("txnReferences")
    List<IncomingTxnReferencesDto> incomingTxnReferencesDtos;

    public static IncomingRec toEntity(IncomingRecDto dto){
//        List<IncomingTxnReferences> incomeTxnRefs = dto.getIncomingTxnReferencesDtos().stream().map(IncomingTxnReferencesDto::toEntity).collect(Collectors.toList());
        return new IncomingRec(dto.getProcessType(),dto.getBatchNumber(),dto.getBatchAmount(),
                dto.getNoOfPayment(),dto.getBatchDate());
    }

    public static IncomingRecDto toDto(IncomingRec entity){
        List<IncomingTxnReferencesDto> incomeTxnRefDtos = entity.getIncomingTxnReferences().stream().map(IncomingTxnReferencesDto::toDto).collect(Collectors.toList());
        return new IncomingRecDto(entity.getProcessType(),entity.getBatchNumber(),entity.getBatchAmount(),
                entity.getNoOfPayment(),entity.getBatchDate(),incomeTxnRefDtos);
    }

}
