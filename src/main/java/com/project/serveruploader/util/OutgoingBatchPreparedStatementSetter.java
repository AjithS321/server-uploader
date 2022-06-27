package com.project.serveruploader.util;

import com.project.serveruploader.entity.IncomingTxnReferences;
import com.project.serveruploader.entity.OutgoingTxnReferences;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class OutgoingBatchPreparedStatementSetter implements BatchPreparedStatementSetter{

    private List<OutgoingTxnReferences> outgIncomingTxnReferences;

    public OutgoingBatchPreparedStatementSetter(List<OutgoingTxnReferences> outgoingTxnReferences
    ) {
        super();
        this.outgIncomingTxnReferences = outgoingTxnReferences;
    }
    @Override
    public void setValues(PreparedStatement ps, int i) {
        try {
            OutgoingTxnReferences entity = outgIncomingTxnReferences.get(i);
            ps.setString(1, entity.getErrorMsg());
            ps.setString(2, entity.getPaymentSeq());
            ps.setString(3, entity.getRcvStatus());
            ps.setString(4, entity.getResponseDate());
            ps.setString(5, entity.getResponseTime());
            ps.setString(6, entity.getStatusCode());
            ps.setString(7, entity.getTxnRef());
            ps.setLong(8, entity.getOutgoingRec().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getBatchSize() {
        return outgIncomingTxnReferences.size();
    }
}