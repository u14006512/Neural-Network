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

        logFile = new File(Integer.toString(logNumber) + ".txt");

        logFile.createNewFile();

        FileWriter fw = new FileWriter(logFile.getAbsoluteFile());
        bw = new BufferedWriter(fw);

        bw.write("Epoch" + '\t' + "Training Set Accuracy" + '\t' + "Generalisation Set Accuracy");
        bw.newLine();

    }

    void writeEpochLogData(String data) throws IOException {
        bw.write(data);
        bw.newLine();
    }

    void closeLogReport() throws IOException {
        if (bw != null) {
            bw.close();
        }
    }

    void RunExperiment() {
        int hiddenUnits=1;
        
        
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

        while ((pokemon.getTrainingSetAccuracy() < pokemon.getDesiredAccuracy() || pokemon.getGeneralisationSetAccuracy() < pokemon.getDesiredAccuracy()) && pokemon.getEpoch() < pokemon.getMaxEpochs()) {

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

        System.out.println("===============================================================");
        System.out.println("Training Complete");
        System.out.println("Validation Set Accuracy:" + pokemon.getValidationSetAccuracy());
        System.out.println("Validation Set Mean Square Error:" + pokemon.getValidationSetMeanSqError());
        closeLogReport();
    }
}
