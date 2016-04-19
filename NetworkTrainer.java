/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos_314_nn_project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Warmaster
 */
public class NetworkTrainer {

    NeuralNetwork pokemon;
    Long epochCount;

    NetworkTrainer() {

    }

    NetworkTrainer(int inInput, int inHidden, int inOutput, boolean useBatch) {
        pokemon = new NeuralNetwork(inInput, inHidden, inOutput, useBatch);
    }
    
    void RunExperiment(String fn, int inputs, int targets, String search, int experiment, boolean batch) throws IOException {
        DataReader dataInput = new DataReader();

        int hiddenUnits = 10;
        int i = 0;
        int logNumber = 0;

        dataInput.loadFileExperiment(fn, inputs, targets, search, experiment);
        while (i < 30) {

            if (pokemon != null) {
                pokemon = null;
            }
            pokemon = new NeuralNetwork(16, hiddenUnits, 1, batch);
            dataInput.reshuffleAndGenerateSets();
            trainNetwork(dataInput.trainingSet, dataInput.generalisationSet, dataInput.validationSet, logNumber, experiment+1);

            if (hiddenUnits < 17) {
                hiddenUnits = hiddenUnits + 1;
            }
            pokemon.resetNetwork();

            i++;
            logNumber++;
        }
    }

    void trainNetwork(ArrayList<DataRecord> trainingSet, ArrayList<DataRecord> generalisationSet, ArrayList<DataRecord> validationSet, int logNum, int experiment) throws IOException {
        String reportData;

        File logFile = new File("Test" + logNum + ".txt");
        File dataFile = new File("DataFile" + logNum + ".txt");
        
        String header = "Experiment:" + Integer.toString(experiment) + " Test:" + logNum;
        logFile.createNewFile();
        FileWriter fw = new FileWriter(logFile.getCanonicalFile());
        FileWriter fdw=new FileWriter(dataFile.getCanonicalFile());
        
        BufferedWriter br = new BufferedWriter(fw);
        BufferedWriter brd=new BufferedWriter(fdw);
        
        header="Test: "+logNum;
        dataFile.createNewFile();
        brd.write(header);
        brd.newLine();
        //header = "Epoch" + '\t' + "Training Set Accuracy" + '\t' + "Generalisation Set Accuracy";
        brd.write(String.format("%s %20s %20s", "Epoch", "Training Set Accuracy","Generalisation Set Accuracy \r\n"));
        brd.newLine();
        
        br.write(header);
        br.newLine();
        
        System.out.println("NEURAL NETWORK TRAINING SESSION");
        System.out.println("==========================================================================");
        System.out.println("Network Paramaters");
        System.out.println("Learning Rate: " + pokemon.getLearningRate());
        System.out.println("Momentum: " + pokemon.getMomentum());
        System.out.println("Max Epochs: " + pokemon.getMaxEpochs());
        System.out.println("Input Neuron Count: " + pokemon.getInN());
        System.out.println("Hidden Neuron Count: " + pokemon.getHiddenN());
        System.out.println("Output Neuron Count: " + pokemon.getOutN());
        System.out.println("==========================================================================");

        pokemon.setEpoch(0);

        while ((pokemon.getSetMeanSqError(validationSet) > pokemon.getDesiredMSEAccuracy() || pokemon.getTrainingsetMeanSqError() > pokemon.getDesiredMSEAccuracy()) && (pokemon.getTrainingSetAccuracy() < pokemon.getDesiredAccuracy() || pokemon.getGeneralisationSetAccuracy() < pokemon.getDesiredAccuracy()) && pokemon.getEpoch() < pokemon.getMaxEpochs()) {

            br.write("NEURAL NETWORK TRAINING SESSION");
            br.newLine();
            br.write("==========================================================================");
            br.newLine();
            br.write("Network Paramaters");
            br.newLine();
            br.write("Learning Rate: " + pokemon.getLearningRate());
            br.newLine();
            br.write("Momentum: " + pokemon.getMomentum());
            br.newLine();
            br.write("Max Epochs: " + pokemon.getMaxEpochs());
            br.newLine();
            br.write("Input Neuron Count: " + pokemon.getInN());
            br.newLine();
            br.write("Hidden Neuron Count: " + pokemon.getHiddenN());
            br.newLine();
            br.write("Output Neuron Count: " + pokemon.getOutN());
            br.newLine();
            br.write("==========================================================================");
            br.newLine();
            br.write("==========================================================================");
            br.newLine();
            
            String heading = "Epoch" + '\t' + "Training Set Accuracy" + '\t' + "Generalisation Set Accuracy";
            br.write(heading);
            br.newLine();
            
            double priorTrainingAcc = pokemon.getTrainingSetAccuracy();
            double priorGenAcc = pokemon.getGeneralisationSetAccuracy();

            pokemon.runEpoch(trainingSet);

            pokemon.setGeneralisationSetAccuracy(pokemon.getSetAccuracy(generalisationSet));
            pokemon.setGeneralisationSetMeanSqError(pokemon.getSetMeanSqError(generalisationSet));

            reportData = Long.toString(pokemon.getEpoch()) + '\t' + Double.toString(pokemon.getTrainingSetAccuracy()) + '\t' + Double.toString(pokemon.getGeneralisationSetAccuracy());
            brd.write(String.format("%s %20s %20s", Long.toString(pokemon.getEpoch()),Double.toString(pokemon.getTrainingSetAccuracy()),Double.toString(pokemon.getGeneralisationSetAccuracy())+" \r\n"));
            //brd.write(reportData);
            brd.newLine();
            
            br.write(reportData);
            if (Math.ceil(priorTrainingAcc) != Math.ceil(pokemon.getTrainingSetAccuracy()) || Math.ceil(priorGenAcc) != Math.ceil(pokemon.getGeneralisationSetAccuracy())) {
                System.out.println("Epoch: " + pokemon.getEpoch());
                System.out.println("Training Set Accuracy: " + pokemon.getTrainingSetAccuracy());
                System.out.println("Training Set Mean Square Error: " + pokemon.getTrainingsetMeanSqError());
                System.out.println("Generalisation Set Accuracy: " + pokemon.getGeneralisationSetAccuracy());
                System.out.println("Generalisation Set Mean Square Error: " + pokemon.getGeneralisationSetMeanSqError());
            }

            pokemon.setEpoch(pokemon.getEpoch() + 1);
            br.newLine();
            br.write("==========================================================================");
            br.newLine();
            br.write("==========================================================================");
            br.newLine();
            br.write("Training Set Mean Square Error: " + pokemon.getTrainingsetMeanSqError());
            br.newLine();
            br.write("Generalisation Set Mean Square Error: " + pokemon.getGeneralisationSetMeanSqError());
            br.newLine();
        }

        pokemon.setValidationSetAccuracy(pokemon.getSetAccuracy(validationSet));
        pokemon.setValidationSetMeanSqError(pokemon.getSetMeanSqError(validationSet));

        br.newLine();
        br.write("==========================================================================");
        br.newLine();
        br.write("==========================================================================");
        br.newLine();
        br.write("Training Complete");
        br.newLine();
        br.write("Epoch Reached: " + (pokemon.getEpoch()-1));
        br.newLine();
        br.write("Validation Set Accuracy: " + pokemon.getValidationSetAccuracy());
        br.newLine();
        br.write("Validation Set Mean Square Error: " + pokemon.getValidationSetMeanSqError());
        br.newLine();
        br.write("Training Set Accuracy: " + pokemon.getTrainingSetAccuracy());
        br.newLine();
        br.write("Generalisation Set Accuracy: " + pokemon.getGeneralisationSetAccuracy());
        br.newLine();
        br.write("==========================================================================");
        
        br.close();
        
        brd.write("==========================================================================");
        brd.newLine();
        brd.write("Training Complete");
        brd.newLine();
        brd.write("Epoch Reached: " + (pokemon.getEpoch()-1));
        brd.newLine();
        brd.write("Validation Set Accuracy: " + pokemon.getValidationSetAccuracy());
        brd.newLine();
        brd.write("Validation Set Mean Square Error: " + pokemon.getValidationSetMeanSqError());
        brd.newLine();
        brd.write("Training Set Accuracy: " + pokemon.getTrainingSetAccuracy());
        brd.newLine();
        brd.write("Generalisation Set Accuracy: " + pokemon.getGeneralisationSetAccuracy());
        brd.newLine();
        brd.write("==========================================================================");
        brd.close();
        
        System.out.println("");
        System.out.println("");
        System.out.println("==========================================================================");
        System.out.println("Training Complete");
        System.out.println("Validation Set Accuracy:" + pokemon.getValidationSetAccuracy());
        System.out.println("Validation Set Mean Square Error:" + pokemon.getValidationSetMeanSqError());
        
    }
}
