/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos_314_nn_project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.ceil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Warmaster
 */
public class DataReader {

    int targets;
    int inputs;
    int experiment;
    String seek;

    ArrayList<DataRecord> sampleSet;
    ArrayList<DataRecord> trainingSet;
    ArrayList<DataRecord> generalisationSet;
    ArrayList<DataRecord> validationSet;

    DataReader() {

    }

    void processLine(String inline) {

        String[] attributes = inline.split(",");

        String[] tmpPattern = new String[inputs];
        double[] tmpTarget = new double[targets];

        for (int i = 0; i < inputs; i++) {
            tmpPattern[i] = attributes[i];
        }
        double[] tmpTransform = new double[inputs];

        for (int x = 0; x < inputs; x++) {
            tmpTransform[x] = Double.parseDouble(tmpPattern[x]);
            tmpTransform[x] = tmpTransform[x] - 7.0;
            tmpTransform[x] = tmpTransform[x] / 16.0;
        }

        switch (experiment) {
            case 0:
                if (tmpPattern[0].equals(seek) == true) {
                    tmpTarget[0] = 1.0;

                } else {
                    tmpTarget[0] = 0.0;
                }
                break;
            case 1:
                if ((tmpPattern[0].equals("U")) || (tmpPattern[0].equals("O")) || (tmpPattern[0].equals("I")) || (tmpPattern[0].equals("E")) || (tmpPattern[0].equals("A"))) {
                    tmpTarget[0] = 1.0;

                } else {
                    tmpTarget[0] = 0.0;
                }

                break;
            case 2:
                /*
                TO DO: ADD unique code 26 digit binary code for each Alphabet
                 */
                break;
        }
        sampleSet.add(new DataRecord(inputs, targets, tmpTransform, tmpTarget));
    }

    boolean loadFileExperiment(String filename, int inp, int targ, String seek, int experiment) throws FileNotFoundException, IOException {
        Random r = new Random(System.currentTimeMillis());
        inputs = inp;
        targets = targ;
        this.seek = seek;
        this.experiment = experiment;

        clearAllData();

        BufferedReader br = new BufferedReader(new FileReader("letter-recognition.data"));
        if (br.ready() == true) {
            String sline;
            while (((sline = br.readLine()) != null)) {
                processLine(sline);

            }

            Collections.shuffle(sampleSet, r);
            int trainingSize, genSize, valSize;

            trainingSize = (int) (sampleSet.size() * 0.6);
            genSize = (int) ceil(sampleSet.size() * 0.2);
            valSize = sampleSet.size() - trainingSize - genSize;

            for (int i = 0; i < trainingSize; i++) {
                double[] tmpP = new double[sampleSet.get(i).pattern.length];
                double[] tmpT = new double[sampleSet.get(i).target.length];
                for (int j = 0; j < tmpP.length; j++) {
                    tmpP[j] = sampleSet.get(i).pattern[j];
                }
                for (int j = 0; j < tmpT.length; j++) {
                    tmpP[j] = sampleSet.get(i).target[j];
                }
                trainingSet.add(new DataRecord(inputs, targets, tmpP, tmpT));
            }

            for (int i = trainingSize; i < (trainingSize + genSize); i++) {
                double[] tmpP = new double[sampleSet.get(i).pattern.length];
                double[] tmpT = new double[sampleSet.get(i).target.length];
                for (int j = 0; j < tmpP.length; j++) {
                    tmpP[j] = sampleSet.get(i).pattern[j];
                }
                for (int j = 0; j < tmpT.length; j++) {
                    tmpP[j] = sampleSet.get(i).target[j];
                }
                generalisationSet.add(new DataRecord(inputs, targets, tmpP, tmpT));
            }

            for (int i = trainingSize + genSize; i < sampleSet.size(); i++) {
                double[] tmpP = new double[sampleSet.get(i).pattern.length];
                double[] tmpT = new double[sampleSet.get(i).target.length];
                for (int j = 0; j < tmpP.length; j++) {
                    tmpP[j] = sampleSet.get(i).pattern[j];
                }
                for (int j = 0; j < tmpT.length; j++) {
                    tmpP[j] = sampleSet.get(i).target[j];
                }
                validationSet.add(new DataRecord(inputs, targets, tmpP, tmpT));
            }

            br.close();
            System.out.println("=========================================");
            System.out.println("Source File: " + filename);
            System.out.println("Data Records Loaded: " + sampleSet.size());
            System.out.println("Training Set Size: " + trainingSet.size());
            System.out.println("Generalisation Set Size: " + generalisationSet.size());
            System.out.println("Validation Set Size: " + validationSet.size());
            System.out.println("=========================================");
            return true;
        } else {
            System.out.println("=========================================");
            System.out.println("Source File: " + filename + " was not found!");
            System.out.println("=========================================");
            return false;
        }
    }

    void clearAllData() {
        trainingSet.clear();
        generalisationSet.clear();
        validationSet.clear();
        sampleSet.clear();
    }

    void clearTrainingData() {
        trainingSet.clear();
        generalisationSet.clear();
        validationSet.clear();
    }

    void setTrainingSet(ArrayList<DataRecord> set) {
        for (int x = 0; x < set.size(); x++) {
            trainingSet.clear();
            trainingSet.add(set.get(x));
        }
    }

    void setGenSet(ArrayList<DataRecord> set) {
        for (int x = 0; x < set.size(); x++) {
            generalisationSet.clear();
            generalisationSet.add(set.get(x));
        }
    }

    void setValSet(ArrayList<DataRecord> set) {
        for (int x = 0; x < set.size(); x++) {
            validationSet.clear();
            validationSet.add(set.get(x));
        }
    }

    ArrayList<DataRecord> getTrainingSet() {
        return trainingSet;
    }

    ArrayList<DataRecord> getGenSet() {
        return generalisationSet;
    }

    ArrayList<DataRecord> getValSet() {
        return validationSet;
    }
}
