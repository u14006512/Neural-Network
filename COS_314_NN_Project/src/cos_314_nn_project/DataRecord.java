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
    double [] pattern;
    double [] target;
    
    DataRecord()
    {
        
    }
    
    DataRecord(int inputs,int targets)
    {
        pattern=new double[inputs];
        target=new double[targets];
        
    }
    public void setPattern(double [] a)
    {
        for(int x=0;x<a.length;x++)
        {
            pattern[x]=a[x];
        }
    }
    public void setTarget(double [] a)
    {
        for(int x=0;x<a.length;x++)
        {
            target[x]=a[x];
        }
    }
}
