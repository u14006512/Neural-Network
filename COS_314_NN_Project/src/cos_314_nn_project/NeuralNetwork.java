/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos_314_nn_project;

import static java.lang.Math.*;
import java.util.ArrayList;

/**
 *
 * @author Warmaster
 */
public class NeuralNetwork {

    final double LEARNING_RATE_DEFAULT = 0.001;
    final double MOMENTUM = 0.8;
    final long MAX_EPOCHS = 1500;
    final long PREFERED_ACCURACY = 100;

    ///LEARNING PARAMETERS
    double learningRate;
    double momentum;

    long epoch;
    long maxEpochs;

    double desiredAccuracy;

    double[][] deltaChangeWeightsInputToHidden;
    double[][] deltaChangeWeightsHiddenToOutput;

    double[] hiddenErrorGradients;
    double[] outputErrorGradients;

    ////Per Epoch Accuracy Stats
    double trainingSetAccuracy;
    double validationSetAccuracy;
    double generalisationSetAccuracy;
    double trainingSetMeanSqError;
    double validationSetMeanSqError;
    double generalisationSetMeanSqError;
    //VARIABLES
    boolean batchLearning;
    private int inputNNum;
    private int hiddenNNum;
    private int outputNNum;

    double[] inputNeurons;
    double[] hiddenNeurons;
    double[] outputNeurons;

    double[][] weightsInputToHidden;
    double[][] weightsHiddenToOutput;

    double getInN() {
        return inputNNum;
    }

    double getHiddenN() {
        return hiddenNNum;
    }

    double getOutN() {
        return outputNNum;
    }

    double getLearningRate() {
        return learningRate;
    }

    double getMomentum() {
        return momentum;
    }

    long getEpoch() {
        return epoch;
    }

    long getMaxEpochs() {
        return maxEpochs;
    }

    double getDesiredAccuracy() {
        return desiredAccuracy;
    }

    double getTrainingSetAccuracy() {
        return trainingSetAccuracy;
    }

    double getValidationSetAccuracy() {
        return validationSetAccuracy;
    }

    double getGeneralisationSetAccuracy() {
        return generalisationSetAccuracy;
    }

    double getTrainingsetMeanSqError() {
        return trainingSetMeanSqError;
    }

    double getGeneralisationSetMeanSqError() {
        return generalisationSetMeanSqError;
    }

    double getValidationSetMeanSqError() {
        return validationSetMeanSqError;
    }

    void setHiddenUnits(int in) {
        this.hiddenNNum = in;
    }

    void setLearningRate(double in) {
        this.learningRate = in;
    }

    void setMomentum(double in) {
        this.momentum = in;
    }

    void setEpoch(long in) {
        this.epoch = in;
    }

    void setMaxEpochs(long in) {
        this.maxEpochs = in;
    }

    void setDesiredAccuracy(double in) {
        this.desiredAccuracy = in;
    }

    void setTrainingSetAccuracy(double in) {
        this.trainingSetAccuracy = in;
    }

    void setValidationSetAccuracy(double in) {
        this.validationSetAccuracy = in;
    }

    void setGeneralisationSetAccuracy(double in) {
        this.generalisationSetAccuracy = in;
    }

    void setTrainingsetMeanSqError(double in) {
        this.trainingSetMeanSqError = in;
    }

    void setGeneralisationSetMeanSqError(double in) {
        this.generalisationSetMeanSqError = in;
    }

    void setValidationSetMeanSqError(double in) {
        this.validationSetMeanSqError = in;
    }

    ///FUNCTIONS
    NeuralNetwork() {

    }

    NeuralNetwork(int inInput, int inHidden, int inOutput, boolean useBatch) {
        //1 is added to account for the bias neuron
        //at both Hidden and Input phases
        epoch = 0;
        trainingSetAccuracy = 0;
        validationSetAccuracy = 0;
        generalisationSetAccuracy = 0;

        trainingSetMeanSqError = 0;
        generalisationSetMeanSqError = 0;
        validationSetMeanSqError = 0;

        batchLearning = useBatch;
        inputNNum = inInput;
        hiddenNNum = inHidden;
        outputNNum = inOutput;

        inputNeurons = new double[inInput + 1];
        hiddenNeurons = new double[inHidden + 1];
        outputNeurons = new double[inOutput];

        for (int a = 0; a < inInput; a++) {
            inputNeurons[a] = 0;
        }
        inputNeurons[inInput] = -1;

        for (int a = 0; a < inHidden; a++) {
            hiddenNeurons[a] = 0;
        }
        hiddenNeurons[inHidden] = -1;

        for (int a = 0; a < inOutput; a++) {
            outputNeurons[a] = 0;
        }

        weightsInputToHidden = new double[inputNNum + 1][hiddenNNum];
        weightsHiddenToOutput = new double[hiddenNNum + 1][outputNNum];

        for (int a = 0; a <=inputNNum; a++) {
            for (int b = 0; b < hiddenNNum; b++) {
                weightsInputToHidden[a][b] = 0;
            }
        }

        for (int a = 0; a <=hiddenNNum; a++) {
            for (int b = 0; b < outputNNum; b++) {
                weightsHiddenToOutput[a][b] = 0;
            }
        }

        deltaChangeWeightsInputToHidden = new double[inInput + 1][inHidden];
        for (int i = 0; i <= inInput; i++) {
            for (int j = 0; j < inHidden; j++) {
                deltaChangeWeightsInputToHidden[i][j] = 0;
            }
        }

        deltaChangeWeightsHiddenToOutput = new double[inHidden + 1][inOutput];
        for (int i = 0; i <= inHidden; i++) {
            for (int j = 0; j < inOutput; j++) {
                deltaChangeWeightsHiddenToOutput[i][j] = 0;
            }
        }

        hiddenErrorGradients = new double[inHidden + 1];
        outputErrorGradients = new double[inOutput + 1];

        for (int i = 0; i <= inHidden; i++) {
            hiddenErrorGradients[i] = 0;
        }
        for (int i = 0; i <= inOutput; i++) {
            outputErrorGradients[i] = 0;
        }

        learningRate = LEARNING_RATE_DEFAULT;
        momentum = MOMENTUM;
        maxEpochs = MAX_EPOCHS;
        desiredAccuracy = PREFERED_ACCURACY;
        initWeights();
    }

    void setLearningParams(double lr, double mo) {
        learningRate = lr;
        momentum = mo;
    }

    void setMaxEpochs(int max) {
        maxEpochs = max;
    }

    void setAcc(double d) {
        desiredAccuracy = d;
    }

    void switchToBatch() {
        batchLearning = true;
    }

    void switchToStochastic() {
        batchLearning = false;
    }

    boolean loadWeights(String filename) {
        return false;
    }

    boolean saveWeights(String filename) {
        return false;
    }

    int[] feedForwardPattern(double[] pattern) {
        feedForward(pattern);

        int[] resultSet = new int[outputNNum];

        for (int i = 0; i < outputNNum; i++) {
            resultSet[i] = postProcessingOut(outputNeurons[i]);
        }
        return resultSet;
    }

    double getSetAccuracy(ArrayList<DataRecord> set) {
        double errorTally = 0;
        boolean correctClassification = true;

        for (int i = 0; i < set.size(); i++) {
            feedForward(set.get(i).pattern);

            correctClassification = true;

            for (int j = 0; j < outputNNum; j++) {
                if (postProcessingOut(outputNeurons[j]) != set.get(i).target[j]) {
                    correctClassification = false;
                }
            }

            if (correctClassification == false) {
                errorTally = errorTally + 1;
            }
        }

        return 100 - ((errorTally / set.size()) * 100);
    }

    double getSetMeanSqError(ArrayList<DataRecord> set) {
        double meanSqError = 0;

        for (int i = 0; i < set.size(); i++) {
            feedForward(set.get(i).pattern);

            for (int j = 0; j < outputNNum; j++) {
                meanSqError = meanSqError + Math.pow((outputNeurons[j] - set.get(i).target[j]), 2);
            }
        }

        return meanSqError / (outputNNum * set.size());
    }

    void resetNetwork() {
        initWeights();
    }

    private void initWeights() {
        double fanin, fanin2;

        fanin = (inputNNum + 1);
        fanin2 = (hiddenNNum + 1);

        double lBound, hBound;

        lBound = -1 * (1 / sqrt(fanin));
        hBound = (1 / sqrt(fanin));

        for (int a = 0; a <= inputNNum; a++) {
            for (int b = 0; b < hiddenNNum; b++) {
                weightsInputToHidden[a][b] = lBound + (int) (Math.random() * (hBound - lBound));
            }
        }

        lBound = -1 * (1 / sqrt(fanin2));
        hBound = (1 / sqrt(fanin2));

        for (int a = 0; a <= hiddenNNum; a++) {
            for (int b = 0; b < outputNNum; b++) {
                weightsHiddenToOutput[a][b] = lBound + (Math.random() * (hBound - lBound));
            }
        }
    }

    private double activationFunction(double x) {
        double value = 1 / (1 + Math.exp(-x));
        return value;
    }

    private int postProcessingOut(double x) {
        if (x >= 0.7) {
            return 1;
        }
        if (x <= 0.3) {
            return 0;
        }else
        return -1;
    }

    private void feedForward(double[] pattern) {

        for (int x = 0; x < inputNNum; x++) {
            inputNeurons[x] = pattern[x];
        }

        for (int y = 0; y < hiddenNNum; y++) {
            hiddenNeurons[y] = 0;

            for (int z = 0; z <= inputNNum; z++) {
                hiddenNeurons[y] = hiddenNeurons[y] + inputNeurons[z] * weightsInputToHidden[z][y];
            }

            hiddenNeurons[y] = activationFunction(hiddenNeurons[y]);
        }

        for (int a = 0; a < outputNNum; a++) {

            outputNeurons[a] = 0;

            for (int b = 0; b <= hiddenNNum; b++) {
                outputNeurons[a] = outputNeurons[a] + hiddenNeurons[b] * weightsHiddenToOutput[b][a];
            }

            outputNeurons[a] = activationFunction(outputNeurons[a]);
        }

    }

    public void runEpoch(ArrayList<DataRecord> trainingSet) {
        double errorClassifications = 0.0;
        double meanSqError = 0.0;
       

        for (int x = 0; x < trainingSet.size(); x++) {
            feedForward(trainingSet.get(x).pattern);
            backProgagation(trainingSet.get(x).target);

            boolean correctClassification = true;

            for (int y = 0; y < outputNNum; y++) {
                if (postProcessingOut(outputNeurons[y]) != trainingSet.get(x).target[y]) {
                    correctClassification = false;
                }

                meanSqError = meanSqError + Math.pow(outputNeurons[y] - trainingSet.get(x).target[y], 2);
            }

            if (correctClassification == false) {
                errorClassifications = errorClassifications + 1;
            }
        }

        if (batchLearning == true) {
            updateWeights();
        }

        trainingSetAccuracy = 100 - ((errorClassifications / trainingSet.size() * 100));
        trainingSetMeanSqError = meanSqError / (outputNNum * trainingSet.size());
    }

    private void updateWeights() {
        for (int a = 0; a <= inputNNum; a++) {
            for (int b = 0; b < hiddenNNum; b++) {
                weightsInputToHidden[a][b] = weightsInputToHidden[a][b] + deltaChangeWeightsInputToHidden[a][b];

                if (batchLearning == true) {
                    deltaChangeWeightsInputToHidden[a][b] = 0;
                }
            }
        }

        for (int x = 0; x <= hiddenNNum; x++) {
            for (int y = 0; y < outputNNum; y++) {
                weightsHiddenToOutput[x][y] = weightsHiddenToOutput[x][y] + deltaChangeWeightsHiddenToOutput[x][y];

                if (batchLearning == true) {
                    deltaChangeWeightsHiddenToOutput[x][y] = 0;
                }
            }
        }
    }

    private void backProgagation(double[] targetPattern) {

        for (int i = 0; i < outputNNum; i++) {
            outputErrorGradients[i] = getOutputErrorGradient(targetPattern[i],outputNeurons[i]);

            for (int j = 0; j <= hiddenNNum; j++) {
                if (batchLearning == false) {
                    deltaChangeWeightsHiddenToOutput[j][i] = learningRate * hiddenNeurons[j] * outputErrorGradients[i] + momentum * deltaChangeWeightsHiddenToOutput[j][i];
                } else {
                    deltaChangeWeightsHiddenToOutput[j][i] = deltaChangeWeightsHiddenToOutput[j][i] + learningRate * hiddenNeurons[j] * outputErrorGradients[i];
                }
            }
        }

        for (int x = 0; x < hiddenNNum; x++) {
            hiddenErrorGradients[x] = getHiddenErrorGradient(x);
            for (int y = 0; y <= inputNNum; y++) {
                if (batchLearning == false) {
                    deltaChangeWeightsInputToHidden[y][x] = learningRate * inputNeurons[y] * hiddenErrorGradients[x] + momentum * deltaChangeWeightsInputToHidden[y][x];
                } else {
                    deltaChangeWeightsInputToHidden[y][x] = deltaChangeWeightsInputToHidden[y][x] + learningRate * inputNeurons[y] * hiddenErrorGradients[x];
                }
            }
        }

        if (batchLearning == false) {
            updateWeights();
        }
    }

    double getOutputErrorGradient(double target, double actual) {
        return actual * (1 - actual) * (target - actual);
    }

    double getHiddenErrorGradient(int x) {
        double weightedSum = 0;
        for (int i = 0; i < outputNNum; i++) {
            weightedSum = weightedSum + weightsHiddenToOutput[x][i] * outputErrorGradients[i];
        }

        return hiddenNeurons[x] * (1 - hiddenNeurons[x]) * weightedSum;
    }

}
