/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dehealthlab.test;

import it.dehealthlab.commons.*;
import it.dehealthlab.jobshoplp.*;
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
public class MainProcessJobShopLP_TAI7x7_605_Test1 {
    
    public MainProcessJobShopLP_TAI7x7_605_Test1() {
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
        Input I = new Input("C:\\Users\\gielpa\\Documents\\NetBeansProjects\\JobShopLP\\test\\it\\dehealthlab\\test\\opsFile_7x7_605.txt",
                "C:\\Users\\gielpa\\Documents\\NetBeansProjects\\JobShopLP\\test\\it\\dehealthlab\\test\\pFile_7x7_605.txt");
        
        LPCommand jslp = new LPCommand(I);
        jslp.execute();
        
        

        

    }
}
