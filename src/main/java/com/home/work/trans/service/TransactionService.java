package com.home.work.trans.service;

import com.home.work.trans.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction create(Transaction transaction);

    Transaction modify(String id, Transaction transaction);

    void delete(String id);

    List<Transaction> getTransactionList(int page, int size);
}
