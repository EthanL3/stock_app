package com.example.mockinvestor;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class portfolioCSVReader {

    public static ArrayList<Stock> loadSavedStocks(){
        ArrayList<Stock> savedStocks = new ArrayList<>();
        String fileP ="/data/user/0/com.example.mockinvestor/files/CSVFiles/" + "Portfolio.csv";

        try(BufferedReader br = new BufferedReader(new FileReader(fileP))){
            br.readLine();
            String l;
            Stock currentStock;
            while ((l = br.readLine()) != null){
                String[] dat = l.split(",");
                currentStock = new Stock(dat[0], Float.parseFloat(dat[1]), Integer.parseInt(dat[2]), dat[3]);
                currentStock.buyShares(Integer.parseInt(dat[4]));
                currentStock.buyShares(Integer.parseInt(dat[4]));
                currentStock.updateDay(Float.parseFloat(dat[5]));
                savedStocks.add(currentStock);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedStocks;
    }

}
