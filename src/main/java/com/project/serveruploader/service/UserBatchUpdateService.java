package com.project.serveruploader.service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.project.serveruploader.dto.IncomingTxnReferencesDto;
import com.project.serveruploader.entity.IncomingTxnReferences;
import com.project.serveruploader.entity.OutgoingTxnReferences;
import com.project.serveruploader.util.IncomeBatchPreparedStatementSetter;
import com.project.serveruploader.util.OutgoingBatchPreparedStatementSetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;


@Service
public class UserBatchUpdateService {

    private static final Logger log = LoggerFactory.getLogger(UserBatchUpdateService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Value("${jdbc.batch_insert_size}")
    private int batchInsertSize;

    @Value("${jdbc.batch_update_size}")
    private int batchUpdateSize;

    private static final ExecutorService executor = Executors.newFixedThreadPool(55);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchInsertAsync(List<IncomingTxnReferences> incomingTxnReferences,List<OutgoingTxnReferences> outgoingTxnReferences) throws InterruptedException, ExecutionException {
        StopWatch timer = new StopWatch();
        String incomeSql = "INSERT INTO `bupload`.`incoming_txn_references` (`payment_seq`, `batch_authoriser`, `batch_inputter`, `ben_account`, `ben_bank_bic`, `ben_cr_amount`, `ben_depart`, `ben_name`, `ord_cus_name`, `ord_cust_account`, `process_date`, `process_time`, `settlement_date`, `txn_curr`, `txn_purpose`, `txn_ref`, `income_id`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? ,?, ?, ?, ?, ?)";

        String outgoingSql = "INSERT INTO `bupload`.`outgoing_txn_references` (`error_msg`, `payment_seq`, `rcv_status`, `response_date`, `response_time`, `status_code`, `txn_ref`, `outgoing_id`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        final AtomicInteger sublists = new AtomicInteger();


        Collection<List<IncomingTxnReferences>> incomeTxnGrouped = incomingTxnReferences.stream()
                .collect(Collectors.groupingBy(t -> sublists.getAndIncrement() / batchInsertSize))
                .values();
        Collection<List<OutgoingTxnReferences>> outgoingTxnGrouped = outgoingTxnReferences.stream()
                .collect(Collectors.groupingBy(t -> sublists.getAndIncrement() / batchInsertSize))
                .values();
        Stream<CompletableFuture<Void>> incomeFutureStream = incomeTxnGrouped.stream()
                .map(ul -> runIncomeBatchInsert(ul, incomeSql));

        Stream<CompletableFuture<Void>> outgoingFutureStream = outgoingTxnGrouped.stream()
                .map(ul -> runOutgoingBatchInsert(ul, outgoingSql));

        CompletableFuture[] futures = Stream.concat(incomeFutureStream,outgoingFutureStream)
                .toArray(CompletableFuture[]::new);
        
        CompletableFuture<Void> run = CompletableFuture.allOf(futures);

        timer.start();

        run.get();

        timer.stop();

        log.info("batchInsertAsync -> Total inserted"+ (incomingTxnReferences.size() + outgoingTxnReferences.size()) +" records in seconds: " + timer.getTotalTimeSeconds());
    }


    public CompletableFuture<Void> runIncomeBatchInsert(List<IncomingTxnReferences> incomingTxnReferences, String sql) {
        return CompletableFuture.runAsync(() -> {
            jdbcTemplate.batchUpdate(sql, new IncomeBatchPreparedStatementSetter(incomingTxnReferences));
        }, executor);
    }

    public CompletableFuture<Void> runOutgoingBatchInsert(List<OutgoingTxnReferences> outgoingTxnReferences, String sql) {
        return CompletableFuture.runAsync(() -> {
            jdbcTemplate.batchUpdate(sql, new OutgoingBatchPreparedStatementSetter(outgoingTxnReferences));
        }, executor);
    }

}
