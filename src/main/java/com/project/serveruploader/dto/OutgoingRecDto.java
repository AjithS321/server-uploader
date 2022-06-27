package com.project.serveruploader.dto;

import com.project.serveruploader.entity.IncomingRec;
import com.project.serveruploader.entity.IncomingTxnReferences;
import com.project.serveruploader.entity.OutgoingRec;
import com.project.serveruploader.entity.OutgoingTxnReferences;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutgoingRecDto {

    String processType;

    String batchNumber;

    List<OutgoingTxnReferencesDto> outgoingTxnReferencesDtos;

    public OutgoingRecDto(String processType, String batchNumber) {
        this.processType = processType;
        this.batchNumber = batchNumber;
    }

//    public static OutgoingRec toEntity(OutgoingRecDto dto){
//        List<OutgoingTxnReferences> outgoingTxnRefs = dto.getOutgoingTxnReferencesDtos().stream().map(OutgoingTxnReferencesDto::toEntity).collect(Collectors.toList());
//        return new OutgoingRec(dto.getProcessType(), dto.getBatchNumber(), outgoingTxnRefs);
//    }

    public static OutgoingRecDto toDto(OutgoingRec entity){
        List<OutgoingTxnReferencesDto> outgoingTxnRefs = entity.getOutgoingTxnReferences().stream().map(OutgoingTxnReferencesDto::toDto).collect(Collectors.toList());
        return new OutgoingRecDto(entity.getProcessType(), entity.getBatchNumber(), outgoingTxnRefs);
    }

    public static OutgoingRec toEntityFromIncomeDto(IncomingRecDto dto){
//        List<OutgoingTxnReferences> outgoingTxnRefs = dto.getOutgoingTxnReferencesDtos().stream().map(OutgoingTxnReferencesDto::toEntity).collect(Collectors.toList());
        return new OutgoingRec(dto.getProcessType(), dto.getBatchNumber());
    }
}
