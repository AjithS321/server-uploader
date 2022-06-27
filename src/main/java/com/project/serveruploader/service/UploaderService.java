package com.project.serveruploader.service;

import com.google.gson.Gson;
import com.project.serveruploader.dto.IncomingRecDto;
import com.project.serveruploader.dto.IncomingTxnReferencesDto;
import com.project.serveruploader.dto.OutgoingRecDto;
import com.project.serveruploader.dto.OutgoingTxnReferencesDto;
import com.project.serveruploader.entity.IncomingRec;
import com.project.serveruploader.entity.IncomingTxnReferences;
import com.project.serveruploader.entity.OutgoingRec;
import com.project.serveruploader.entity.OutgoingTxnReferences;
import com.project.serveruploader.repository.IncomingRecRepository;
import com.project.serveruploader.repository.IncomingTxnReferencesRepository;
import com.project.serveruploader.repository.OutgoingRecRepository;
import com.project.serveruploader.repository.OutgoingTxnReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class UploaderService {

    @Autowired
    private IncomingRecRepository incomingRecRepository;
    @Autowired
    private IncomingTxnReferencesRepository incomingTxnReferencesRepository;
    @Autowired
    private OutgoingRecRepository outgoingRecRepository;
    @Autowired
    private OutgoingTxnReferenceRepository outgoingTxnReferenceRepository;
    @Autowired
    private UserBatchUpdateService service;

    @Autowired
    Gson gson;
//    public static <T> List<List<T>> createSubList(List<T> list, int subListSize){
//        List<List<T>> listOfSubList = new ArrayList<>();
//        for (int i = 0; i < list.size(); i+=subListSize) {
//            if(i + subListSize <= list.size()){
//                listOfSubList.add(list.subList(i, i + subListSize));
//            }else{
//                listOfSubList.add(list.subList(i, list.size()));
//            }
//        }
//        return listOfSubList;
//    }

//    @Transactional(rollbackOn = {DataIntegrityViolationException.class, Exception.class})
//    public OutgoingRecDto uploadData(IncomingRecDto incomingRecDto){
//        IncomingRec incomeEntty = IncomingRecDto.toEntity(incomingRecDto);
//        IncomingRec savedIncomingRec = incomingRecRepository.save(incomeEntty);
//        Optional<IncomingTxnReferencesDto> sample = incomingRecDto.getIncomingTxnReferencesDtos().stream().findFirst();
//        List<IncomingTxnReferencesDto> samples = new ArrayList<>();
//        if(sample.isPresent()){
//            for (int i = 0; i < 10000; i++) {
//                samples.add(sample.get());
//            }
//        }
//
//        List<IncomingTxnReferences> incomeTxnRefs = samples.stream().map(i-> IncomingTxnReferencesDto.toEntity(i,savedIncomingRec)).collect(Collectors.toList());
//
//        incomingTxnReferencesRepository.saveAll(incomeTxnRefs);
//        System.out.println("--res saved sucessfully");
//        return null;
//    }

    @Transactional(rollbackOn = {DataIntegrityViolationException.class, Exception.class})
    public OutgoingRecDto uploadDataAsyc(IncomingRecDto incomingRecDto){
        IncomingRec incomeEntty = IncomingRecDto.toEntity(incomingRecDto);
        IncomingRec savedIncomingRec = incomingRecRepository.save(incomeEntty);
        List<IncomingTxnReferencesDto> incomingTxnReferencesDtos = incomingRecDto.getIncomingTxnReferencesDtos();



        OutgoingRec outgoingRec = OutgoingRecDto.toEntityFromIncomeDto(incomingRecDto);
        OutgoingRec savedOutgoingRec = outgoingRecRepository.save(outgoingRec);

        List<OutgoingTxnReferences> OutgoingTxnReferences =  incomingTxnReferencesDtos.stream().map(i-> OutgoingTxnReferencesDto.toEntityFromIncomeRefsDto(i,savedOutgoingRec)).collect(Collectors.toList());

        List<IncomingTxnReferences> incomeTxnRefs = incomingTxnReferencesDtos.stream().map(i-> IncomingTxnReferencesDto.toEntity(i,savedIncomingRec)).collect(Collectors.toList());

        try {
            service.batchInsertAsync(incomeTxnRefs, OutgoingTxnReferences);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        OutgoingRec outgoingRecEntity = outgoingRecRepository.getReferenceById(savedOutgoingRec.getId());
        List<OutgoingTxnReferences> outgoingTxnrefs = outgoingTxnReferenceRepository.findByOutgoingRec(savedOutgoingRec);

        outgoingRecEntity.setOutgoingTxnReferences(outgoingTxnrefs);
        return OutgoingRecDto.toDto(outgoingRecEntity);
    }

//    public OutgoingRecDto uploaderWithExecutor(IncomingRecDto incomingRecDto){
//        IncomingRec incomeEntty = IncomingRecDto.toEntity(incomingRecDto);
//        IncomingRec savedIncomingRec = incomingRecRepository.save(incomeEntty);
//
//        Optional<IncomingTxnReferencesDto> sample = incomingRecDto.getIncomingTxnReferencesDtos().stream().findFirst();
//        List<IncomingTxnReferencesDto> samples = new ArrayList<>();
//        if(sample.isPresent()){
//            for (int i = 0; i < 10000; i++) {
//                samples.add(sample.get());
//            }
//        }
//        List<IncomingTxnReferences> incomeTxnRefs = samples.stream().map(i-> IncomingTxnReferencesDto.toEntity(i,savedIncomingRec)).collect(Collectors.toList());
//
//        List<List<IncomingTxnReferences>> listOfBookSub = createSubList(incomeTxnRefs, 30);
//        ExecutorService executorService = Executors.newFixedThreadPool(30);
//        List<Callable<Integer>> callables = listOfBookSub.stream().map(sublist ->
//                (Callable<Integer>) () -> {
//                System.out.println("Inserting " + sublist.size() + " using callable from thread" + Thread.currentThread().getName());
//                    incomingTxnReferencesRepository.saveAll(sublist);
//                    return sublist.size();
//                }).collect(Collectors.toList());
//        try {
//            List<Future<Integer>> futures = executorService.invokeAll(callables);
//            int count = 0;
//            for(Future<Integer> future: futures){
//                count += future.get();
//            }
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }




}
