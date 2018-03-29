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
public class LPCommand extends JobShopLP implements Command {
    
    private Input I;
    
    public LPCommand(Input I) {this.I = I;}

    @Override
    public void execute() {
        super.process(I);
    }
    
}
