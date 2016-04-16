/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos_314_nn_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Warmaster
 */
public class Experiment1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        System.out.println("=====================================================================================");
        System.out.println("Experiment 1 - Differentiate between a letter and all the others of the alphabet");
        System.out.println("=====================================================================================");
        
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        
        String letter="A";
        System.out.println("Please enter the letter you wish to train the network to differentiate for");
        letter=br.readLine();
        letter=letter.toUpperCase();
        
        NetworkTrainer trainer1=new NetworkTrainer(16,1,1,false);
        trainer1.RunExperiment("letter-recognition.data", 16, 1, letter, 0);
        
        double test[]={2,1,3,1,1,8,6,6,6,6,5,9,1,7,5,10};
        
        for (int x = 0; x < 16; x++) {
            test[x] = test[x] - 7.0;
            test[x] = test[x] / 16.0;
        }
       
        int [] check=trainer1.pokemon.feedForwardPattern(test);
        int result=check[0];
        
        System.out.println("Classification of G: "+result);
        
        test[0]=4;
        test[1]=2;
        test[2]=5;
        test[3]=4;
        test[4]=4;
        test[5]=8;
        test[6]=7;
        test[7]=6;
        test[8]=6;
        test[9]=7;
        test[10]=6;
        test[11]=6;
        test[12]=2;
        test[13]=8;
        test[14]=7;
        test[15]=10;
        
        for (int x = 0; x < 16; x++) {
            test[x] = test[x] - 7.0;
            test[x] = test[x] / 16.0;
        }
       
        check=trainer1.pokemon.feedForwardPattern(test);
        result=check[0];
        
        System.out.println("Classification of B: "+result);
        
        test[0]=2;
        test[1]=8;
        test[2]=3;
        test[3]=5;
        test[4]=1;
        test[5]=8;
        test[6]=13;
        test[7]=0;
        test[8]=6;
        test[9]=6;
        test[10]=10;
        test[11]=8;
        test[12]=0;
        test[13]=8;
        test[14]=0;
        test[15]=8;
        
        for (int x = 0; x < 16; x++) {
            test[x] = test[x] - 7.0;
            test[x] = test[x] / 16.0;
        }
       
        check=trainer1.pokemon.feedForwardPattern(test);
        result=check[0];
        
        System.out.println("Classification of T: "+result);
        
        test[0]=1;
        test[1]=1;
        test[2]=3;
        test[3]=2;
        test[4]=1;
        test[5]=8;
        test[6]=2;
        test[7]=2;
        test[8]=2;
        test[9]=8;
        test[10]=2;
        test[11]=8;
        test[12]=1;
        test[13]=6;
        test[14]=2;
        test[15]=7;
        
        for (int x = 0; x < 16; x++) {
            test[x] = test[x] - 7.0;
            test[x] = test[x] / 16.0;
        }
       
        check=trainer1.pokemon.feedForwardPattern(test);
        result=check[0];
        
        System.out.println("Classification of A: "+result);
        
        test[0]=8;
        test[1]=12;
        test[2]=8;
        test[3]=6;
        test[4]=4;
        test[5]=3;
        test[6]=10;
        test[7]=4;
        test[8]=7;
        test[9]=12;
        test[10]=11;
        test[11]=9;
        test[12]=3;
        test[13]=7;
        test[14]=3;
        test[15]=4;
        
        for (int x = 0; x < 16; x++) {
            test[x] = test[x] - 7.0;
            test[x] = test[x] / 16.0;
        }
       
        check=trainer1.pokemon.feedForwardPattern(test);
        result=check[0];
        
        System.out.println("Classification of X: "+result);
        
        test[0]=3;
        test[1]=8;
        test[2]=5;
        test[3]=6;
        test[4]=3;
        test[5]=9;
        test[6]=2;
        test[7]=2;
        test[8]=3;
        test[9]=8;
        test[10]=2;
        test[11]=8;
        test[12]=2;
        test[13]=6;
        test[14]=3;
        test[15]=7;
        
        for (int x = 0; x < 16; x++) {
            test[x] = test[x] - 7.0;
            test[x] = test[x] / 16.0;
        }
       
        check=trainer1.pokemon.feedForwardPattern(test);
        result=check[0];
        
        System.out.println("Classification of A: "+result);
    }
    
}
