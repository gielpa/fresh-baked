/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dehealthlab.commons;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author gielpa
 */
public class MatrixFactory {
    public MatrixFactory(){}
    
    public static int[][] getMatrix(String filepath) {
        File f = new File(filepath);
        int rowctr = 0;
        int colctr = 0;
        
        try {
            Scanner line =  new Scanner(f);


            while (line.hasNextLine()) {

                String row = line.nextLine();
                Scanner col = new Scanner(row);
                if(rowctr==0)
                    while (col.hasNextInt()) {
                            colctr++; 
                            col.next();
                    }
                rowctr++;
            }


        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
        System.out.println("[rowctr][colctr]:" + "[" + rowctr + "]"+"["+colctr+"]");
        int[][] data = new int[rowctr][colctr];
        
        try {
        Scanner s1 = new Scanner(f);
            int i=0;
            while (s1.hasNextLine()) {
                int j=0;
                String row = s1.nextLine();
                Scanner col = new Scanner(row);
                while (col.hasNext()) {
                        String token = col.next();
                        data[i][j] = Integer.valueOf(token);
                        j++;
                    }
                i++;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        
        }
        
  
        return data;
    
    }
}
