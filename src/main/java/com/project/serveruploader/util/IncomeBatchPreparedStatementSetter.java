package com.project.serveruploader.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.project.serveruploader.dto.IncomingTxnReferencesDto;
import com.project.serveruploader.entity.IncomingTxnReferences;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;



public class IncomeBatchPreparedStatementSetter implements BatchPreparedStatementSetter{

    private List<IncomingTxnReferences> incomingTxnReferences;

    public IncomeBatchPreparedStatementSetter(List<IncomingTxnReferences> incomingTxnReferences) {
        super();
        this.incomingTxnReferences = incomingTxnReferences;
    }

    @Override
    public void setValues(PreparedStatement ps, int i) {
        try {
            IncomingTxnReferences entity = incomingTxnReferences.get(i);
            ps.setString(1, entity.getPaymentSeq());
            ps.setString(2, entity.getBatchAuthoriser());
            ps.setString(3, entity.getBatchInputter());
            ps.setString(4, entity.getBenAccount());
            ps.setString(5, entity.getBenBankBIC());
            ps.setString(6, entity.getBenCrAmount());
            ps.setString(7, entity.getBenDepart());
            ps.setString(8, entity.getBenName());
            ps.setString(9, entity.getOrdCusName());
            ps.setString(10, entity.getOrdCustAccount());
            ps.setLong(11, entity.getProcessDate());
            ps.setLong(12, entity.getProcessTime());
            ps.setString(13, entity.getSettlementDate());
            ps.setString(14, entity.getTxnCurr());
            ps.setString(15, entity.getTxnPurpose());
            ps.setString(16, entity.getTxnRef());
            ps.setLong(17, entity.getIncomingRec().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getBatchSize() {
        return incomingTxnReferences.size();
    }
}