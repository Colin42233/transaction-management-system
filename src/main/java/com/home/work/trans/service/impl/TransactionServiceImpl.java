package com.home.work.trans.service.impl;

import com.home.work.trans.exception.TransactionBusinessException;
import com.home.work.trans.model.Transaction;
import com.home.work.trans.service.DataRepositoryService;
import com.home.work.trans.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private DataRepositoryService dataRepositoryService;


    @Override
    public Transaction create(Transaction transaction) {
        String id = transaction.getId();
        logger.info("Create new transaction, id: {}", id);
        //check duplicate transaction
        if(dataRepositoryService.checkTransactionExist(id)){
            logger.error("Transaction-{} is exist, can't be created again!", id);
            throw new TransactionBusinessException("Transaction id is exist!");
        }
        dataRepositoryService.upsertData(id, transaction);
        return transaction;
    }

    @Override
    public Transaction modify(String id, Transaction transaction) {
        logger.info("Modify new transaction, id: {}", id);
        //check transaction exist or not
        if(!dataRepositoryService.checkTransactionExist(id)){
            logger.error("Transaction-{} is not exist, can't be modified!", id);
            throw new TransactionBusinessException("Transaction is not exist, can't be modified!");
        }
        dataRepositoryService.upsertData(id, transaction);
        return transaction;
    }

    @Override
    public void delete(String id) {
        logger.info("delete new transaction, id: {}", id);
        if(!dataRepositoryService.checkTransactionExist(id)){
            logger.error("Transaction-{} is not exist, can't be deleted!", id);
            throw new TransactionBusinessException("Transaction is not exist, can't be deleted!");
        }
        dataRepositoryService.deleteData(id);
    }

    @Override
    public List<Transaction> getTransactionList(int page, int size) {
        logger.info("Get transaction list, page: {}, size:{} ", page, size);
        return dataRepositoryService.getDataList(page, size);
    }



}
