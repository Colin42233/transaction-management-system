package com.home.work.trans.service;

import com.home.work.trans.model.Transaction;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class DataRepositoryService {

    private final Map<String, Transaction> transactionMap = new ConcurrentHashMap<>();

    public Transaction upsertData(String id, Transaction transaction){
        transactionMap.put(id, transaction);
        return transaction;
    }

    public void deleteData(String id){
        transactionMap.remove(id);
    }

    @Cacheable("transactions")
    public List<Transaction> getDataList(int page, int size){
        return transactionMap.values().stream().skip((long) page * size).limit(size).collect(Collectors.toList());
    }

    public boolean checkTransactionExist(String id){
        return transactionMap.containsKey(id);
    }

}
