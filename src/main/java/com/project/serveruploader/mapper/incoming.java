package com.project.serveruploader.mapper;

import com.project.serveruploader.dto.IncomingRecDto;
import com.project.serveruploader.dto.IncomingTxnReferencesDto;
import com.project.serveruploader.dto.OutgoingRecDto;
import com.project.serveruploader.entity.IncomingRec;
import com.project.serveruploader.entity.IncomingTxnReferences;
import com.project.serveruploader.entity.OutgoingRec;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class incoming {

//    public IncomingRec toIncomingEntity(IncomingRecDto incomingRecDto){
//        return IncomingRecDto.toEntity(incomingRecDto);
//    }
//
//    public IncomingRecDto toIncomingDto(IncomingRec incomingRec){
//        return IncomingRecDto.toDto(incomingRec);
//    }
//
//    public OutgoingRec toIncomingEntity(IncomingRecDto incomingRecDto){
//        return IncomingRecDto.toEntity(incomingRecDto);
//    }
//
//    public OutgoingRecDto toIncomingDto(IncomingRec incomingRec){
//        return IncomingRecDto.toDto(incomingRec);
//    }
}
