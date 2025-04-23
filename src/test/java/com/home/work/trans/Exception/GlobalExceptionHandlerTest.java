package com.home.work.trans.Exception;

import com.home.work.trans.exception.GlobalExceptionHandler;
import com.home.work.trans.exception.TransactionBusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @MockitoBean
    private TransactionBusinessException transactionBusinessException;

    @MockitoBean
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Test
    void handleTransactionBusinessException(){
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleTransactionError(transactionBusinessException);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void handleMethodArgumentNotValidException(){
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleValidationError(methodArgumentNotValidException);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}
