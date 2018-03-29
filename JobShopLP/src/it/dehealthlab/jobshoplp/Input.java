/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dehealthlab.jobshoplp;

import it.dehealthlab.commons.*;

/**
 *
 * @author gielpa
 */
public class Input {
    // Operations sequence in job.
    private int[][] Ops;
    // Operationn processing time.
    private int[][] P;
    
    public Input(){}
    public Input(String opsFile, String pFile) {
        this.Ops = MatrixFactory.getMatrix(opsFile);
        this.P   = MatrixFactory.getMatrix(pFile);
    }

    public int[][] getP() {
        return this.P;
    }   
    
    public int[][] getOps() {
        return this.Ops;
    }

    // Special getter to change offset of Ops machines.
    public int[][] getOps(int offset) {
        int[][] opsOffset = new int[getOps().length][getOps().length];
        
        for(int i=0;i<getOps().length;i++)
            for(int j=0;j<opsOffset.length;j++)
                opsOffset[i][j] = getOps()[i][j] + offset;
        return opsOffset;
    }
    
 
}
