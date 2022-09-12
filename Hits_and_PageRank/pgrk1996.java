//Aarjavi Dharaiya 1996

import java.util.*;
import java.io.*;

class pgrk1996 {

    
    double[] P;
    double[] Pprev; 
    int[] outdeg;
    int[][] Lt;
   
    double errRate = 0;
    int numOfIter = 0;
    int initVal = 0;
    int numOfVertices = 0;
    int numOfEdges = 0;
    int error_mode = 1; //1 - for error bound, 0 for iteration bound
    int iteration_count = 0;

    pgrk1996(String a1, String a2, String a3) throws FileNotFoundException {
        numOfIter = Integer.parseInt(a1);
        initVal = Integer.parseInt(a2);
        String fileName = a3;
        

        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            numOfVertices =  myReader.nextInt();
            numOfEdges =  myReader.nextInt();

            if(numOfVertices > 10 ) {
                initVal = -1;
                numOfIter = 0;    
            }

            if(numOfIter == 0) {
                error_mode = 1; //error bound
                errRate = 0.00001;
            }
            else if(numOfIter < 0){ //error bound
                error_mode = 1;
                errRate = Math.pow(10,numOfIter);
            }
            else{
                error_mode = 0; // iteration bound
            }

            P = new double[numOfVertices];
            Pprev = new double[numOfVertices];
            Lt = new int[numOfVertices][numOfVertices];
            outdeg = new int[numOfVertices];
            
            for(int i=0;i<numOfVertices;i++) {
                outdeg[i] = 0;
                for(int j=0;j<numOfVertices;j++){
                    Lt[i][j] = 0;
                }
            }
            while (myReader.hasNextInt()) { 
                int u = myReader.nextInt();
                int v = myReader.nextInt();
                Lt[v][u] = 1; //reading in transpose of L
            }
            for(int i=0;i<numOfVertices;i++){
                for(int j=0;j<numOfVertices;j++) {
                    outdeg[j] += Lt[i][j];
                }
            }

            myReader.close();

            switch (initVal) {
                case 0:
                    InitArray(0);
                    break;
                case 1:
                    InitArray(1);
                    break;
                case -1:
                    InitArray(1.0/numOfVertices);
                    break;
                case -2:
                    InitArray(1.0/Math.sqrt(numOfVertices));
                    break;
            }

            if(numOfVertices<=10) {
                printThisIteration(0,P,"Base");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        }
    }

    void InitArray(double val) {
        for(int i = 0; i<numOfVertices ; i++) {
            P[i] = val; 
        }
    }

    public static void main(String args[]) throws FileNotFoundException {
        if(args.length < 3) {
            System.out.println("Please provide required input arguments :- iterations, initialvalue, filename ");
            return;
        }
        pgrk1996 pgrkobj = new pgrk1996(args[0],args[1],args[2]);
        pgrkobj.Pgrk();
    }

    void Pgrk() {
    
        do{
            
            for(int i=0;i<numOfVertices;i++){
                Pprev[i] = P[i];
                P[i] = 0;
            }

            for(int i=0; i<numOfVertices;i++){
                for(int j=0;j<numOfVertices;j++) {
                    double temp = Lt[i][j] * Pprev[j];
                    if(outdeg[j] != 0 ) {
                        temp = temp/outdeg[j]; 
                    }
                    P[i] += temp;
                }
                P[i] = 0.15/numOfVertices + 0.85*P[i];  
            }

            iteration_count++;
            if(numOfVertices<=10) {
                printThisIteration(iteration_count,P,"Iter");
            }
            
        }while(isConverged()==false);
        if(numOfVertices > 10) {
            printFinalIteration(iteration_count,P);
        }

    }
    public void printFinalIteration(int iteration, double P[]) {
        
        System.out.print("Iter" + "    : ");
        System.out.printf("%3d\n",iteration);
        for (int i = 0; i < numOfVertices; i++) {
            System.out.printf("P[%2d]=%.7f\n", i,P[i]);
        }
    }
    public void printThisIteration(int iteration, double P[],String printcase) {
        
        System.out.print(printcase + "    : ");
        System.out.printf("%3d :",iteration);
        for (int i = 0; i < numOfVertices; i++) {
            System.out.printf("P[%2d]=%.7f ", i,P[i]);
        }
        System.out.println();
    }
    
    boolean isConverged(){
        if(error_mode==0){
            //iteration bound;
            if(iteration_count == numOfIter) {
                return true;
            }
            else {   
                return false;
            }
        }
        else{
            //error bound;
            for(int i =0;i<numOfVertices;i++){
                double err1 = Math.abs(P[i]-Pprev[i]);
                //System.out.printf("\ni=%d, errRate = %f\n",i,errRate);
                //System.out.printf("P=%f, Pprev=%f, err1=%f\n",P[i],Pprev[i],err1);
                if (err1>=errRate) {
                    return false;    
                } 
            }
            return true;            
        } 
    }
}
