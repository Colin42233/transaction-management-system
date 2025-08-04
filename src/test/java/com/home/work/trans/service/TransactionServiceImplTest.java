package com.home.work.trans.service;

import com.home.work.trans.exception.TransactionBusinessException;
import com.home.work.trans.model.Transaction;
import com.home.work.trans.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = TransactionServiceImpl.class)
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@EnableCaching
public class TransactionServiceImplTest {

    @Autowired
    private TransactionService transactionService;

    @MockitoBean
    private CacheManager cacheManager;

    @MockitoBean
    private DataRepositoryService dataRepositoryService;

    @Test
    void createTransaction_duplicatedId_throwsException(){
        Transaction transaction = new Transaction();
        transaction.setId("1");
        Mockito.when(dataRepositoryService.checkTransactionExist(anyString())).thenReturn(Boolean.TRUE);
        assertThrows(TransactionBusinessException.class, () -> transactionService.create(transaction));
    }

    @Test
    void createTransaction_success(){
        Transaction transaction = new Transaction("test_id", BigDecimal.TEN, "transferUser",
                "receiveUsers", "Success", new Date(), "for created");
        Mockito.when(dataRepositoryService.checkTransactionExist(anyString())).thenReturn(Boolean.FALSE);
        transactionService.create(transaction);
        verify(dataRepositoryService, times(1)).upsertData(anyString(), any());
    }

    @Test
    void modifyTransaction_failed_withNotExistId_throwsException(){
        Transaction transaction = new Transaction();
        transaction.setId("1");
        Mockito.when(dataRepositoryService.checkTransactionExist(anyString())).thenReturn(Boolean.FALSE);
        assertThrows(TransactionBusinessException.class, () -> transactionService.modify(transaction.getId(), transaction));
    }

    @Test
    void modifyTransaction_success(){
        Transaction transaction = new Transaction("test_id", BigDecimal.TEN, "transferUser",
                "receiveUsers", "Success", new Date(), "for modified");
        Mockito.when(dataRepositoryService.checkTransactionExist(anyString())).thenReturn(Boolean.TRUE);
        transactionService.modify(transaction.getId(), transaction);
        verify(dataRepositoryService, times(1)).upsertData(anyString(), any());
    }

    @Test
    void deleteTransaction_failed_withNotExistId_throwsException(){
        String id = "delete_test_id";
        Mockito.when(dataRepositoryService.checkTransactionExist(anyString())).thenReturn(Boolean.FALSE);
        assertThrows(TransactionBusinessException.class, () -> transactionService.delete(id));
    }

    @Test
    void deleteTransaction_success(){
        String id = "delete_test_id";
        Mockito.when(dataRepositoryService.checkTransactionExist(anyString())).thenReturn(Boolean.TRUE);
        transactionService.delete(id);
        verify(dataRepositoryService, times(1)).deleteData(anyString());
    }

}

