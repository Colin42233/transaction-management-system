package com.home.work.trans.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {

    private String id;

    private BigDecimal amount;

    private String transferUser;

    private String receiveUsers;

    private String status;

    private Date transDate = new Date();

    private String description;

}
