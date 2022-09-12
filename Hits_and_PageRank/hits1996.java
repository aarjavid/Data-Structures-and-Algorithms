//Aarjavi Dharaiya 1996

import java.util.*;
import java.io.*;

class hits1996 {

    
    double[] hub, aut;
    double[] hubprev, autprev; 
    int[][] L;
    double errRate = 0;
    int numOfIter = 0;
    int initVal = 0;
    int numOfVertices = 0;
    int numOfEdges = 0;
    int error_mode = 1; //1 - for error bound, 0 for iteration bound
    int iteration_count = 0;

    hits1996(String a1, String a2, String a3) throws FileNotFoundException {
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

            hub = new double[numOfVertices];
            aut = new double[numOfVertices];
            L = new int[numOfVertices][numOfVertices];
            hubprev = new double[numOfVertices];
            autprev = new double[numOfVertices];
            
            for(int i=0;i<numOfVertices;i++) {
                for(int j=0;j<numOfVertices;j++){
                    L[i][j] = 0;
                }
            }

            while (myReader.hasNextInt()) { 
                int u = myReader.nextInt();
                int v = myReader.nextInt();
                L[u][v] = 1;
            }
            myReader.close();

            switch (initVal) {
                case 0:
                    InitArrays(0);
                    break;
                case 1:
                    InitArrays(1);
                    break;
                case -1:
                    InitArrays(1.0/numOfVertices);
                    break;
                case -2:
                    InitArrays(1.0/Math.sqrt(numOfVertices));
                    break;
            }
            
            if(numOfVertices<=10) {
                printThisIteration(0,aut,hub,"Base");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        }
    }

    void InitArrays(double val) {
        for(int i = 0; i<numOfVertices ; i++) {
            hub[i] = val;
            aut[i] = val;
           
        }

    }

    public static void main(String args[]) throws FileNotFoundException {
        if(args.length < 3) {
            System.out.println("Please provide required input arguments :- iterations, initialvalue, filename ");
            return;
        }
        hits1996 hitsobj = new hits1996(args[0],args[1],args[2]);
        hitsobj.Hits();
    }

    void Hits() {
    
        do{
            for(int i=0;i<numOfVertices;i++){
                hubprev[i] = hub[i];
                autprev[i] = aut[i];
                hub[i] = 0;
                aut[i] = 0;
            }

            int k = 0;
            for(int i=0; i<numOfVertices;i++){
                for(int j=0;j<numOfVertices;j++) {
                    aut[k] += L[j][i] * hubprev[j];
                }
                k++;
            }
            k = 0;
            for(int i=0; i<numOfVertices;i++){
                for(int j=0;j<numOfVertices;j++) {
                    hub[k] += L[i][j] * aut[j];
                }
                k++;
            }

    
            double sum_a = 0;
            double sum_h = 0;
            for(int i=0;i<numOfVertices;i++){
                sum_a += aut[i]*aut[i];
                sum_h += hub[i]*hub[i]; 
            }
            sum_a = Math.sqrt(sum_a);
            sum_h = Math.sqrt(sum_h);

            for(int i=0;i<numOfVertices;i++){
                aut[i] = aut[i]/sum_a;
                hub[i] = hub[i]/sum_h; 
            }
    
            iteration_count++;
            if(numOfVertices<=10) {
                printThisIteration(iteration_count,aut,hub,"Iter");
            }
            
        }while(isConverged()==false);

        if(numOfVertices > 10) {
            printFinalIteration(iteration_count,aut,hub);
        }
    }
    public void printFinalIteration(int iteration, double a[], double h[]) {
        
        System.out.print("Iter" + "    : ");
        System.out.printf("%3d\n",iteration);
        for (int i = 0; i < numOfVertices; i++) {
            System.out.printf("A/H[%2d]=%.7f/%.7f\n", i,a[i],h[i]);
        }
    }
    public void printThisIteration(int iteration, double a[], double h[],String printcase) {
        
        System.out.print(printcase + "    : ");
        System.out.printf("%3d :",iteration);
        for (int i = 0; i < numOfVertices; i++) {
            System.out.printf("A/H[%2d]=%.7f/%.7f ", i,a[i],h[i]);
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
                double err1 = Math.abs(aut[i]-autprev[i]);
                double err2 = Math.abs(hub[i]-hubprev[i]);
               // System.out.printf("\ni=%d, errRate = %f\n",i,errRate);
               // System.out.printf("a=%f, aprev=%f, err1=%f\n",aut[i],autprev[i],err1);
               // System.out.printf("h=%f, hprev=%f, err2=%f\n",hub[i],hubprev[i],err2);
                if (err1>=errRate || err2>=errRate) {
                    return false;    
                } 
            }
            return true;            
        } 
    }
}
