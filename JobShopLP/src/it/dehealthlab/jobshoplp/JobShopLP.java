/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dehealthlab.jobshoplp;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.cplex.IloCplex;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gielpa
 */
public class JobShopLP implements JobShopChainOfResponsibility {
    
    public JobShopLP(){}
    
    private JobShopChainOfResponsibility chain;
    
    private IloCplex cplex;
    // n jobs.
    private int n;
    // m machines.
    private int m;
    // V = \sum p_{ij}, i \in Ops, j \in Job
    private int V;
    
    private IloIntVar CMax;
    private IloIntVar[][] x;
    private IloIntVar[][][] Z;
 
    
    /**
     * This method specifies the chain of responsibility in processing Job Shop.
     * Set up problem parameters wrt Input I;
     * Instantiate the problem (vars, obj function, constraints);
     * Find solutions.
     * Comment. Passing I from one method to another might look useless, but it 
     * is required by the pattern.
     * @param I
     */
    @Override
    public void process(Input I) {
        setup(I);
        instantiate(I);
        solve(I);
    }

    @Override
    public void init(JobShopChainOfResponsibility chain) {
        this.chain = chain;
    }

    @Override
    public void setup(Input I) {
        try {
            cplex = new IloCplex();
        } catch(IloException e){}
        // Assert I is already initialized.
        int P[][] = I.getP();
        int Ops[][] = I.getOps(-1);
        
        //JobShop is nxm problem where n are jobs and m are machines, and n=m.
        m = P.length;
        n = P.length; 
        
        // Instantiate V.
        // for each i in processing time, for each j in Jobs 
        for(int i=0;i<m;i++)
            for(int j=0;j<n;j++)
                V += P[i][j];
        
        // Matrix Z has m rows and (nC2) columns, where nC2=n!/2*(n−2)!, n>2 
        int zcol = n;

        x = new IloIntVar[n][m];
        Z = new IloIntVar[n][m][zcol];
        
        try {
            CMax = cplex.intVar(0,Integer.MAX_VALUE);
            /* UNDOFIX
            for(int i=0;i<m;i++)
                for(int j=0;j<n;j++) {
                    x[i][j] = cplex.intVar(0,Integer.MAX_VALUE);
                    // Build binary Z_{ijk} true 
                    // if job j precedes job k on operation i.
                    for(int k=j+1; k<n; k++)
                        Z[Ops[i][j]][j][k] = cplex.boolVar();

                }     
            */
            for(int j=0;j<n;j++)
                for(int i=0;i<m;i++)
                 {
                    x[j][i] = cplex.intVar(0,Integer.MAX_VALUE);
                    // Build binary Z_{ijk} true 
                    // if job j precedes job k on operation i.
                    for(int k=j+1; k<n; k++) {
                        // System.out.println("Z["+Ops[j][i]+"]["+j+"]["+k+"]");
                        Z[Ops[j][i]][j][k] = cplex.boolVar();
                    }
                }
        } catch (IloException ex) {
            Logger.getLogger(JobShopLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void instantiate(Input I) {
        int P[][] = I.getP();
        int Ops[][] = I.getOps(-1);
        
        // Instantiate the objective function.     
        try {
            cplex.addMinimize(CMax);
            
        } catch (IloException ex) {
            Logger.getLogger(JobShopLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Instantiate constraints.     
        try {
            for(int j=0;j<n;j++) {
                // Add EndBeforeStart constraints.
                // This constraint states that the end of a given starting time x_{i,j}
                // for operation i on job j, modified by processing time p_{i,j} 
                // is less than or equal to the starting time of the the next 
                // interval variable x_{i,j} + p_{i,j} <= x_{i+1,j}.
                for(int i=0;i<m-1;i++)
                    cplex.addLe(cplex.sum(cplex.prod(1, x[Ops[j][i]][j]), P[j][i]), 
                            cplex.prod(1, x[Ops[j][i+1]][j]));
                    
                // Add LargestCompletion constraints.
                // C_max >= x_{m,j} + p_{m,j}
                cplex.addGe(CMax, cplex.sum(cplex.prod(1, x[Ops[j][m-1]][j]), P[j][m-1]));
            }
            
            // Add NoOverlap constraints.
            /*UNDOFIX
for(int j=0;j<n;j++)
    for(int i=0;i<m;i++)
        for(int k=0; j<k && k<n; k++) {
            // x_{i,j} >= x_{i,k} + p_{i,k} -V * z_{i,j,k}   
            cplex.addGe(
                    cplex.prod(1, x[Ops[j][i]][j]), 
                    cplex.sum(
                            cplex.sum(
                                    cplex.prod(1, x[Ops[j][i]][k]),P[k][i]), 
                            cplex.prod(-V, Z[Ops[j][i]][j][k])
                    )
            );
            // x_{i,k} >= x_{i,j} + p_{i,j} -V * (1 - z_{i,j,k})                          
            cplex.addGe(
                    cplex.prod(1, x[Ops[j][i]][k]), 
                    cplex.sum(
                            cplex.sum(
                                    cplex.prod(1, x[Ops[j][i]][j]),P[j][i]), 
                            cplex.prod(-V,  
                                    cplex.sum(1, 
                                            cplex.prod(-1,Z[Ops[j][i]][j][k])
                                    )
                            )
                    )
            );
        }
            */
            for(int j=0;j<n;j++)
                for(int i=0;i<m;i++)
                    for(int k=j+1; k<n; k++)
                        for(int l=0; l<m; l++) {
                            // x_{i,j} >= x_{i,k} + p_{i,k} -V * z_{i,j,k}
                            if (Ops[j][i] == Ops[k][l]) {
                                cplex.addGe(
                                cplex.prod(1, x[Ops[j][i]][j]), 
                                cplex.sum(
                                        cplex.sum(
                                                cplex.prod(1, x[Ops[k][l]][k]),P[k][l]), 
                                        cplex.prod(-V, Z[Ops[j][i]][j][k])
                                    )
                                );
                                // x_{i,k} >= x_{i,j} + p_{i,j} -V * (1 - z_{i,j,k})                          
                                cplex.addGe(
                                        cplex.prod(1, x[Ops[j][i]][k]), 
                                        cplex.sum(
                                                cplex.sum(
                                                        cplex.prod(1, x[Ops[j][i]][j]),P[j][i]), 
                                                cplex.prod(-V,  
                                                        cplex.sum(1, 
                                                                cplex.prod(-1,Z[Ops[j][i]][j][k])
                                                        )
                                                )
                                        )
                                );                                

                            
                            }

                    }
        } catch (IloException ex) {
            Logger.getLogger(JobShopLP.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    @Override
    public void solve(Input I) {
        try {
         if (cplex.solve()) {
             cplex.output().println("Solution status = " + cplex.getStatus());
             cplex.output().println("Solution value = " + cplex.getObjValue());
             /*
             for (int i=0;i<x.length;i++) {
                double[] val = cplex.getValues(x[i]);

                for (int j = 0; j<val.length ; j++) {
                    cplex.output().println("Column: " + j + " xsValue = " + val[j]);
                }
             }
            */
             
             double Cval = cplex.getValue(CMax);
             cplex.output().println("Column: " + " CMax = " + Cval);

         }
         cplex.end();
        } catch (IloException ex) {
            Logger.getLogger(JobShopLP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // FIXME! Now working only with positive integers!
    private int comb(int n, int r) {
        if(r>=0 && n>=r)
            return fact(n) / ( fact(r)* fact(n-r) );
        
        throw new RuntimeException();

    }
    // FIXME! Now working only with positive integers!
    private int fact(int n) {
        if(n>=0)
            switch(n) {
                case 0: return 1;
                case 1: return 1;
                default: return n*fact(n-1);
            }
        throw new RuntimeException();
    }
    
}
