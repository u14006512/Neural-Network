/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos_314_nn_project;

/**
 *
 * @author Warmaster
 */
public class DataRecord {

    double[] pattern;
    double[] target;

    DataRecord() {

    }

    DataRecord(int inputs, int targets,double[] pattern,double[] targ) {
        this.pattern = new double[inputs];
        this.target = new double[targets];
        setPattern(pattern);
        setTarget(targ);
    }

    private void setPattern(double[] a) {
        for (int x = 0; x < a.length; x++) {
            pattern[x] = a[x];
        }
    }

    private void setTarget(double[] a) {
        for (int x = 0; x < a.length; x++) {
            target[x] = a[x];
        }
    }
}
