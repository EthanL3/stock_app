package com.example.mockinvestor;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Stock implements Serializable {
    private String symbol;
    private double volume;
    private double T_gainLossDollars, T_gainLossPercent; //total gain/loss of all shares
    private double currentPrice, purchasePrice; //price per share
    private double currentValue, purchaseValue;//value of all shares
    private ArrayList<Double> valueHistory = new ArrayList<Double>(); //array of values of stock over time
    private int shares, count_day = 0;
    private Date purchaseDate, currentDate;
    private String date = "08/01/2023"; //placeholder

    public Stock(String symbol, int shares) { //temporary code
        this.symbol = symbol;
        this.shares = shares;
        this.currentPrice = 100;
        this.purchasePrice = 75;
        this.volume = 100000;
        this.T_gainLossDollars = 25;
        this.T_gainLossPercent = 33.33;
    }

    public Stock(String symbol, double price, double volume){
        this.symbol = symbol;
        try {
            this.currentPrice = price;
            this.purchasePrice = price;
            this.currentValue = currentPrice * shares;
            this.purchaseValue = purchasePrice * shares;
            this.volume = volume;
        } catch (NumberFormatException e){
            System.out.println("Error: initialization: string not in number format");
        }
        updateGainsLoss();
    }
    public String getSymbol(){
        return symbol;
    }
    public int getShares(){ //number of shares
        return shares;
    }
    public double getVolume() {
        return volume;
    }
    public double getCurrentPrice(){ //per share
        return currentPrice;
    } //per share
    public double getPurchasePrice() { //per share
        return purchasePrice;
    } //per share
    public double getCurrentValue() {
        this.currentValue = currentPrice * shares; //TEMPORARY
        return currentValue;
    }
    public double getPurchaseValue() {
        this.purchaseValue = purchasePrice * shares; //TEMPORARY
        return purchaseValue;
    }
    public double getGainLossDollars(){
        return T_gainLossDollars;
    }
    public double getGainLossPercent(){
        return T_gainLossPercent;
    }

    //update day function: to be used every day through clock-imitating loop
    public void updateDay(String price){
        try {
            this.currentPrice = Double.parseDouble(price);
        } catch (NumberFormatException e){
            System.out.println("Error: update string not in number format");
        }
        this.currentValue = currentPrice * shares;
        count_day++;
        valueHistory.set(count_day, currentValue);
        updateGainsLoss();
        //update date by one day in "currentDate"
        SimpleDateFormat date_format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            purchaseDate = date_format.parse(date);
            currentDate = date_format.parse(date);
        } catch (ParseException e) {
            System.out.println("Error: purchaseStock: string not in MM/dd/yyyy format" + e.getMessage());
        }
        Calendar cal_currentDate = Calendar.getInstance();
        cal_currentDate.setTime(currentDate);
        cal_currentDate.add(Calendar.DATE, 1);
        currentDate = cal_currentDate.getTime();
        count_day++;
    }

    public void buyShares(int numShares) {
        this.shares += numShares;
        this.currentValue = shares * currentPrice;
        this.purchaseValue = shares * purchasePrice;
    }

    public void sellShares(int numShares) {
        this.shares -= numShares;
        this.currentValue = shares * currentPrice;
        this.purchaseValue = shares * purchasePrice;
    }

    //private functions to be used within class (IGNORE)
    private void updateGainsLoss(){
        T_gainLossDollars = currentValue - purchaseValue;
        T_gainLossPercent = (T_gainLossDollars / purchaseValue) * 100; //in percent
    }

    //assumes symbol is unique for each stock
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(symbol, stock.symbol);
    }
    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}