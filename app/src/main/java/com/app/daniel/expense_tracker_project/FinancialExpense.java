package com.app.daniel.expense_tracker_project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class FinancialExpense {

    int ExpenseAmount;
    String ProductName;
    String Information;
    String DateSignature;
    String fullDateSignature;
    UUID ExpenseId;
    








    public FinancialExpense(int expenseAmount, String product) {
        ExpenseAmount = expenseAmount;
        ProductName = product;
        DateSignature = DateToConstruct();
        ExpenseId = UUID.randomUUID();
    }


    public String DateToConstruct(){
        DateFormat df = new SimpleDateFormat("MM"+"YY");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);

        return reportDate;

    }



    public UUID getExpenseId() {
        return ExpenseId;
    }

    public void setExpenseId(UUID expenseId) {
        ExpenseId = expenseId;
    }

    public int getExpenseAmount() {
        return ExpenseAmount;
    }

    public void setExpenseAmount(int expenseAmount) {
        ExpenseAmount = expenseAmount;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String product) {
        ProductName = product;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public String getDateSignature() {
        return DateSignature;
    }

    public String getFullDateSignature() {

        DateFormat df = new SimpleDateFormat("YY"+"MM"+"dd");
        Date today = Calendar.getInstance().getTime();
        String fullDateSignature = df.format(today);

        return fullDateSignature;



    }




}
