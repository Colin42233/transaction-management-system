package com.home.work.trans.controller;

import com.home.work.trans.model.Transaction;
import com.home.work.trans.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("ut")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionControllerMock;

    @MockitoBean
    private TransactionService transactionService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);;
    }


    @Test
    void createTransaction_returnSuccess(){
        Transaction transaction = new Transaction();
        when(transactionService.create(any())).thenReturn(transaction);
        transactionControllerMock.create(transaction);
        verify(transactionService, times(1)).create(any());
    }

    @Test
    void modifyTransaction_returnSuccess(){
        Transaction transaction = new Transaction();
        when(transactionService.modify(anyString(), any())).thenReturn(transaction);
        transactionControllerMock.modify("test_id", transaction);
        verify(transactionService, times(1)).modify(anyString(), any());
    }

    @Test
    void deleteTransaction_returnSuccess() {
        doNothing().when(transactionService).delete(anyString());
        transactionControllerMock.delete("test_id");
        verify(transactionService, times(1)).delete(anyString());
    }

    @Test
    void getTransaction_returnSuccess(){
        when(transactionService.getTransactionList(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        transactionControllerMock.getListAll(0, 10);
        verify(transactionService, times(1)).getTransactionList(anyInt(), anyInt());
    }

}
