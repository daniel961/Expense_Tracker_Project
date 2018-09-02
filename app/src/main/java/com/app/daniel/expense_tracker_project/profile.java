package com.app.daniel.expense_tracker_project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class profile {

    //personal
    String name,SecurityCode,fileName="thisProfile";
    Context context;

    //finance
    int income; //TOTAL how much you earn per month
    int outcome; // TOTAL how much you spend this month //calculate from list AND from mothlyExpenses(Cars and more)

    //list of Expenses
    List<FinancialExpense> FixedMonthlyExpenseList = new ArrayList<FinancialExpense>(); //list of Fixed monthly expenditure    //NOT USED YET!!!
    List<FinancialExpense> CurrentMonthExpensesList; //= new ArrayList<FinancialExpense>(); //list of Expenses of the month






    //questions must answer
    boolean haveCar;
    boolean rentHouse;
    boolean gotLoan;


    public profile(String name, String securityCode, int income,Context context) {
        this.name = name;
        SecurityCode = securityCode;
        this.income = income;
        this.context = context;
        this.haveCar = false;
        this.rentHouse = false;
        this.gotLoan = false;
        CurrentMonthExpensesList = new ArrayList<FinancialExpense>();


    }

    public profile() { //emptyConstructor
        CurrentMonthExpensesList = new ArrayList<FinancialExpense>();


    }

    public void DeleteAllExpenses(){ //delete all expenses
        CurrentMonthExpensesList.clear();
        FixedMonthlyExpenseList.clear();
    }




    public int ExpensesCalculator() { //running on financialExpensesList AND mothlyExpenses(Cars and more) = calculate Expenses //for start calc onlu normal expenses
        int sum = 0;

        DateFormat df = new SimpleDateFormat("MM" + "YY");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);

        if(CurrentMonthExpensesList != null) {

            for (int i = 0; i < CurrentMonthExpensesList.size(); i++) {
                if (CurrentMonthExpensesList.get(i).getDateSignature().equals(reportDate) == true) {
                    sum += CurrentMonthExpensesList.get(i).getExpenseAmount();
                } else {
                    CurrentMonthExpensesList.remove(i); //if the DateSignature is Wrong the object delete from the list
                }
            }

        }



            this.outcome = sum;
            return sum;
        }




    public int getMoneyLeft(){
        int moneyLeft = 0;
        moneyLeft = income - ExpensesCalculator(); //calculate how much money left
        return moneyLeft;
    }




    public void AddNormalExpense(int amount,String productName){ //Add Expense to the list of NormalExpenses
        if(CurrentMonthExpensesList == null){
            CurrentMonthExpensesList = new ArrayList<FinancialExpense>();
        }
        CurrentMonthExpensesList.add(0,new FinancialExpense(amount, productName)); //add new Expnese
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecurityCode() {
        return SecurityCode;
    }

    public void setSecurityCode(String securityCode) {
        SecurityCode = securityCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public boolean isHaveCar() {
        return haveCar;
    }

    public void setHaveCar(boolean haveCar) {
        this.haveCar = haveCar;
    }

    public boolean isRentHouse() {
        return rentHouse;
    }

    public void setRentHouse(boolean rentHouse) {
        this.rentHouse = rentHouse;
    }

    public boolean isGotLoan() {
        return gotLoan;
    }

    public void setGotLoan(boolean gotLoan) {
        this.gotLoan = gotLoan;
    }


    public List<FinancialExpense> getCurrentMonthExpensesList() { //get Spesific month expenses
        return CurrentMonthExpensesList;
    }

    public void setCurrentMonthExpensesList(List<FinancialExpense> currentMonthExpensesList) {  //set Spesific month expenses
        CurrentMonthExpensesList = currentMonthExpensesList;
    }

    public List<FinancialExpense> getFixedMonthlyExpenseList() {
        return FixedMonthlyExpenseList;
    }

    public void setFixedMonthlyExpenseList(List<FinancialExpense> fixedMonthlyExpenseList) {
        FixedMonthlyExpenseList = fixedMonthlyExpenseList;
    }

    public void deleteExpenseByPosition(int position){
        CurrentMonthExpensesList.remove(position);
    }


}
