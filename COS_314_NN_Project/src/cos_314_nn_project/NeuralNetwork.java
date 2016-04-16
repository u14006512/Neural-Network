/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos_314_nn_project;

import static java.lang.Math.*;
import java.util.Random;


/**
 *
 * @author Warmaster
 */
public class NeuralNetwork {
    //VARIABLES
    private int inputNNum;
    private int hiddenNNum;
    private int outputNNum;
    
    double[] inputNeurons;
    double[] hiddenNeurons;
    double[] outputNeurons;
    
    double[][] weightsInputToHidden;
    double[][] weightsHiddenToOutput;
    
    ///FUNCTIONS
    NeuralNetwork()
    {
        
    }
    
    NeuralNetwork(int inInput,int inHidden,int inOutput)
    {
        //1 is added to account for the bias neuron
        //at both Hidden and Input phases
        inputNNum=inInput;
        hiddenNNum=inHidden;
        outputNNum=inOutput;
        
        inputNeurons=new double[inInput+1];
        hiddenNeurons=new double[inHidden+1];
        outputNeurons=new double[inOutput];
        
        for(int a=0;a<inInput;a++)
        {
            inputNeurons[a]=0;
        }
        inputNeurons[inInput]=-1;
        
        for(int a=0;a<inHidden;a++)
        {
            hiddenNeurons[a]=0;
        }
        hiddenNeurons[inHidden]=-1;
        
        for(int a=0;a<inOutput;a++)
        {
            outputNeurons[a]=0;
        }
       
         weightsInputToHidden=new double[inputNNum+1][hiddenNNum];
        weightsHiddenToOutput=new double[hiddenNNum+1][outputNNum];
        
        
        for(int a=0;a<inputNNum+1;a++)
        {
            for(int b=0;b<hiddenNNum;b++)
            {
                weightsInputToHidden[a][b]=0;
            }
        }
        
        for(int a=0;a<hiddenNNum+1;a++)
        {
            for(int b=0;b<outputNNum;b++)
            {
                weightsHiddenToOutput[a][b]=0;
            }
        }
        
        
        initWeights();
    }
    
    boolean loadWeights(String filename)
    {
        return false;
    }
    
    boolean saveWeights(String filename)
    {
        return false;
    }
    
    private void initWeights()
    {
       double fanin,fanin2;
       
       fanin=(inputNNum+1);
       fanin2=(hiddenNNum+1);
       
       double lBound,hBound;
       
       lBound=-1*(1/sqrt(fanin));
       hBound=(1/sqrt(fanin));
       
       for(int a=0;a<=inputNNum;a++)
       {
           for(int b=0;b<hiddenNNum;b++)
           {
               weightsInputToHidden[a][b]=lBound+(int)(Math.random()*(hBound-lBound));
           }
       }
       
       lBound=-1*(1/sqrt(fanin2));
       hBound=(1/sqrt(fanin2));
       
       for(int a=0;a<=hiddenNNum;a++)
       {
           for(int b=0;b<outputNNum;b++)
           {
               weightsHiddenToOutput[a][b]=lBound+(Math.random()*(hBound-lBound));
           }
       }
    };
    
    private double activationFunction(double x)
    {
        double value=1/(1+Math.exp(-x));
        return 0;
    }
    
    private int postProcessingOut(double x)
    {
        if (x>=0.7) return 1;
        if (x<0.3) return 0;
        return -1;
    }
    
    private void feedForward(double[] pattern)
    {
        
    }
}
