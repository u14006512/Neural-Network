/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos_314_nn_project;

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

    BufferedWriter bw;
    File logFile;
    NeuralNetwork pokemon;
    Long epochCount;

    NetworkTrainer() {

    }

    NetworkTrainer(int inInput, int inHidden, int inOutput, boolean useBatch) {
        pokemon = new NeuralNetwork(inInput, inHidden, inOutput, useBatch);
    }

    void openLogReport(int logNumber) throws IOException {

        logFile = new File("Logs\\"+Integer.toString(logNumber) + ".txt");

        logFile.createNewFile();

        FileWriter fw = new FileWriter(logFile.getCanonicalFile());
        bw = new BufferedWriter(fw);
        String heading="Epoch" + '\t' + "Training Set Accuracy" + '\t' + "Generalisation Set Accuracy";
        bw.write(heading);
        bw.newLine();
        bw.close();
    }

    void writeEpochLogData(String data) throws IOException {
        FileWriter fw = new FileWriter(logFile.getCanonicalFile());
        bw = new BufferedWriter(fw);
        bw.write(data);
        bw.newLine();
        bw.close();
    }

    void closeLogReport() throws IOException {
        if (bw != null) {
            bw.close();
        }
    }

    void RunExperiment(String fn, int inputs, int targets, String search, int experiment) throws IOException {
        DataReader dataInput = new DataReader();
        
        
        int hiddenUnits = 10;
        int i = 0;
        int logNumber=0;
        dataInput.loadFileExperiment(fn, inputs, targets, search, experiment);
        while (i < 1) {
            if (pokemon!=null) pokemon=null;
            pokemon=new NeuralNetwork(16, hiddenUnits, 1, false);
            dataInput.reshuffleAndGenerateSets();
            trainNetwork(dataInput.trainingSet, dataInput.generalisationSet, dataInput.validationSet, logNumber);

            if (hiddenUnits < 17) {
                hiddenUnits = hiddenUnits + 1;
            }
           // pokemon.resetNetwork();
            
            i++;
        }
    }

    void trainNetwork(ArrayList<DataRecord> trainingSet, ArrayList<DataRecord> generalisationSet, ArrayList<DataRecord> validationSet, int logNum) throws IOException {
        String reportData;
        openLogReport(logNum);

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

        while ((pokemon.getTrainingSetAccuracy() < pokemon.getDesiredAccuracy() && pokemon.getGeneralisationSetAccuracy() < pokemon.getDesiredAccuracy()) && pokemon.getEpoch() < pokemon.getMaxEpochs()) {

            double priorTrainingAcc = pokemon.getTrainingSetAccuracy();
            double priorGenAcc = pokemon.getGeneralisationSetAccuracy();

            pokemon.runEpoch(trainingSet);

            pokemon.setGeneralisationSetAccuracy(pokemon.getSetAccuracy(generalisationSet));
            pokemon.setGeneralisationSetMeanSqError(pokemon.getSetMeanSqError(generalisationSet));

            reportData = Long.toString(pokemon.getEpoch()) + '\t' + Double.toString(pokemon.getTrainingSetAccuracy()) + '\t' + Double.toString(pokemon.getGeneralisationSetAccuracy());

            writeEpochLogData(reportData);

            if (Math.ceil(priorTrainingAcc) != Math.ceil(pokemon.getTrainingSetAccuracy()) || Math.ceil(priorGenAcc) != Math.ceil(pokemon.getGeneralisationSetAccuracy())) {
                System.out.println("Epoch: " + pokemon.getEpoch());
                System.out.println("Training Set Accuracy: " + pokemon.getTrainingSetAccuracy());
                System.out.println("Training Set Mean Square Error: " + pokemon.getTrainingsetMeanSqError());
                System.out.println("Generalisation Set Accuracy: " + pokemon.getGeneralisationSetAccuracy());
                System.out.println("Generalisation Set Mean Square Error: " + pokemon.getGeneralisationSetMeanSqError());
            }

            pokemon.setEpoch(pokemon.getEpoch() + 1);

        }

        reportData = Long.toString(pokemon.getEpoch()) + '\t' + Double.toString(pokemon.getTrainingSetAccuracy()) + '\t' + Double.toString(pokemon.getGeneralisationSetAccuracy());
        writeEpochLogData(reportData);

        pokemon.setValidationSetAccuracy(pokemon.getSetAccuracy(validationSet));
        pokemon.setValidationSetMeanSqError(pokemon.getSetMeanSqError(validationSet));

        System.out.println("");
        System.out.println("");
        System.out.println("===============================================================");
        System.out.println("Training Complete");
        System.out.println("Validation Set Accuracy:" + pokemon.getValidationSetAccuracy());
        System.out.println("Validation Set Mean Square Error:" + pokemon.getValidationSetMeanSqError());
        closeLogReport();
    }
}
