package org.project.minorproject1.controller;

import org.project.minorproject1.models.TransactionType;
import org.project.minorproject1.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/initiate")
    public String initiateTxn(@RequestParam("studentId") Integer studentId,
                              @RequestParam("bookId") Integer bookId,
                              @RequestParam("transactionType")TransactionType transactionType) throws Exception {
        return this.transactionService.initiateTransaction(studentId, bookId,transactionType);
    }
}
