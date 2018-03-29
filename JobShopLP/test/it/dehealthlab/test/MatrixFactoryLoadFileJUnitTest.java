/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dehealthlab.test;

import it.dehealthlab.commons.MatrixFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gielpa
 */
public class MatrixFactoryLoadFileJUnitTest {
    
    public MatrixFactoryLoadFileJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void load() {
        int[][] a = MatrixFactory.getMatrix("C:\\Users\\gielpa\\Documents\\NetBeansProjects\\JobShopLP\\test\\it\\dehealthlab\\test\\file1.txt");
        System.out.println(a.length);
        for(int i=0; i<a.length; i++) {
            for(int j=0; j<a.length; j++) 
                System.out.print("a[rowctr][colctr]:" + "[" + i + "]" + "[" +j + "]="+a[i][j] + " ");
            System.out.println();
            }

    }
}
