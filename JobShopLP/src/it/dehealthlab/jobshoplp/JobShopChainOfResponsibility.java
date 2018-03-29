/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dehealthlab.jobshoplp;

/**
 *
 * @author gielpa
 */
public interface JobShopChainOfResponsibility {
    public void init(JobShopChainOfResponsibility chain);
    public void setup(Input I);
    public void instantiate(Input I);
    public void solve(Input I);
    public void process(Input I);  
}
