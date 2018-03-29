/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dehealthlab.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author gielpa
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    MainProcessJobShopLP_2x2_Test1.class,
    MainProcessJobShopLP_3x3_42_Test1.class,
    MainProcessJobShopLP_TAI4x4_272_Test1.class,
    MainProcessJobShopLP_TAI5x5_333_Test1.class,
    MainProcessJobShopLP_TAI6x6_55_FT06_Test1.class,    
    MainProcessJobShopLP_TAI6x6_55_Test1.class,
    MainProcessJobShopLP_TAI7x7_558_Test1.class,
    MainProcessJobShopLP_TAI7x7_590_Test1.class,
    MainProcessJobShopLP_TAI7x7_605_Test1.class,
    MainProcessJobShopLP_TAI7x7_669_Test1.class,
    
})
public class MatrixFactoryTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
