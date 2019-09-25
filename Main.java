/*
 * Name: Justin Senia
 * E-Number: E00851822
 * Date: 2/9/2017
 * Class: COSC 314
 * Project: #1, Part 3
 */
import java.util.*;
import java.io.*;

public class Main {
    
    //Declaring random variable outside of main so it can be used by all methods
    public static Random randGen;
    
    //main method for Project 1 
    public static void main(String[] args) throws IOException{
        
        //declaring variable "n" to be used as a universal "size" variable
        int n;
        
		
        //FOR COSC 314 PROJECT #1 PART 3
        //Main Code for querying the user for information, getiing the matrix dimensions,
        //seedvalue for the random number generator and the probability used to determine
        //chance of communicating information, this information is used to generate a matrix,
        //find the transitive closure of it, and make note of the average number of links
        //between data fields as a result of varying connection probability.
        
        //creating buffered reader to get user input
        BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));
        String userInStr = ""; //variable used to store initial user string input
        double p;              //variable used to store user inputted probability
        int s;                 //variable used to store user inputted seed value
        double pcntAvg;        //variable used to store the % average of connections per trans closure 
        
        //file output location & creating printwriter to write data to it
        File outFileThree = new File("Output.txt");
        PrintWriter pWriter = new PrintWriter(outFileThree);
        
        //declaring a matrix to hold data
        int[][] partThreeMatrix;
        
        //Informing user of program's intent
        System.out.println("\nCOSC 314 Project #1, Part 3: ");
        System.out.println("This is a program for testing Transitive closure with weighted relations.");
        System.out.println("You will be asked for three values (Enter Q as a response to quit)\n");
        System.out.println("n = population size,  p = connection probability,  s = random # seed value");
		
		//printing heading to output file
		pWriter.println("\nCOSC 314 Project #1, Part 3 (User Input Example): \n");
        
        //while loop for repeating user input/calculations
        //repeats as long as user doesn't enter a "q" to quit
        while (!userInStr.equalsIgnoreCase("q")){
            
            //asking user for population size parameter (int value)
            System.out.println("\nPlease Enter a population size and hit Enter...");
            System.out.print("(Enter data as an \"Integer\" value please)  n = ");
            userInStr = buffRead.readLine();       //read user input as string
            if (userInStr.equalsIgnoreCase("q"))   //if "q" end loop
                break;
            n = Integer.parseInt(userInStr);       //else parse string for int value
            
            //asking user for probability parameter (double value)
            System.out.println("\nPlease Enter the connection probability and hit Enter...");
            System.out.print("(Enter data as a \"Double\" value please)  p = ");
            userInStr = buffRead.readLine();       //read user input as string
            if (userInStr.equalsIgnoreCase("q"))   //if "q" end loop
                break;
            p = Double.parseDouble(userInStr);     //else parse string for double value
            
            //asking user for seed value parameter (int value)
            System.out.println("\nPlease Enter a seed value and hit Enter...");
            System.out.print("(Enter data as an \"Integer\" value please)  s = ");
            userInStr = buffRead.readLine();       //read user input as string
            if (userInStr.equalsIgnoreCase("q"))   //if "q end loop
                break;
            s = Integer.parseInt(userInStr); //else parse string for int value
            
            //populating matrix with user supplied values, then assigning data to original pointer
            //(passing: n= size parameter, s= seed value, p= probability value)
            partThreeMatrix = randPopMatrix(n, s, p);
            //Calculating transitive closure with warshall's algorithm, storing back in original loc.
            //(passing: previously populated matrix, and n= size parameter)
            partThreeMatrix = transClosureMatrix(partThreeMatrix, n);
            //Calculating Percent average of information dispersal via conections
            //(passing: previously calculated trans closure matrix, and n = size parameter)
            pcntAvg = infoSpreadPercent(partThreeMatrix, n);
            
            //Writing resulting information to local file in readable format
            pWriter.println("\nTransitive closure for " + n + "x" + n + " Matrix");
            pWriter.println("(p = " + String.format("%.2f", p*100) + "% , " + " s = " + s + ")");
            pWriter.println("Percentage of population to get Info (on Average): " + 
                    String.format("%.2f", pcntAvg * 100) + "%\n");
			
			//Writing resulting information to local file in readable format
            System.out.println("\nTransitive closure for " + n + "x" + n + " Matrix");
            System.out.println("(p = " + String.format("%.2f", p*100) + "% , " + " s = " + s + ")");
            System.out.println("Percentage of population to get Info (on Average): " + 
                    String.format("%.2f", pcntAvg * 100) + "%\n");
            
            //giving user prompt so they know writing was successful
            System.out.println("\nResults saved to \"Output.txt\"\n");  
			
		}
        
		//prompt to let user know program has stopped
		System.out.println("Exiting Program...");
		
        //closing printWriter for data protection
        pWriter.close();
        
		
		
        /*
        
        //for multiple test runs with increasing variable % value and different seed values

        //Creating File location and PrintWriter in order to be able to write data to a file
        File outFileFour = new File("Output2.txt");
        PrintWriter pWriter = new PrintWriter(outFileFour);
        int[][] partThreeMatrix;
        double pcntAvg;
        
        n = 100; //initializing n to 100 for tests
        int sd = 13;
		pWriter.println("COSC 314 Project #1, Part 3: ");
		pWriter.println("");
		pWriter.println("---------------------------------------------------------------------------------");
		pWriter.println("|                  100x100 transitive closure matrix analysis                   |");
		pWriter.println("---------------------------------------------------------------------------------");
		pWriter.println("| Probability  |  Avg  Spread  |                    Seed Values                 |");
		pWriter.println("---------------------------------------------------------------------------------");
		
        //For loop to increment probability by .005 and seed value by 2 each iteration
        //used to discover how quickly probability sharing increases % of 
        //connected population
        for (double w = 0; w < 0.105; w = w + .005){
        
            //resetting variable for incremental re-use
            pcntAvg = 0;
            
            //calculating average of averages
            for (sd = 13; sd < 33; sd = sd + 2){
            //creating new matrix using incremental parameters
            //(passing: n= size, sd= seed value, w = probability of connections)
            partThreeMatrix = randPopMatrix(n, sd, w);
            //doing transitive closure with warshalls algorithm on current matrix, putting back in original
            //(passing: previously created matrix, and n= size value)
            partThreeMatrix = transClosureMatrix(partThreeMatrix, n);
            //calculating percentage of average spread based on current transitive closure matrix
            //(passing: current trans closure matrix, n= size value)
            pcntAvg = pcntAvg + infoSpreadPercent(partThreeMatrix, n);
            }
            
            //dividing by number of different seed values used (10) to get average of averages
            pcntAvg = pcntAvg / 10;
            
            //outputting properly formatted data to file
            pWriter.printf("|   %-6.2f%%    |   %-8.2f%%   |    %-40s    |", w*100, pcntAvg*100, "13, 15, 17, 19, 21, 23, 25, 27, 29, 31");
            pWriter.println(" ");
        
            //printing prompt so user knows output has been successful
            System.out.println("Results saved to \"Output2.txt\"\n\n");
        }
		pWriter.println("---------------------------------------------------------------------------------");
      
        //closing print writer for data protection
        pWriter.close();
        
        System.out.println("");
    */
    }
    
	
    //Transitive closure algorithm
    //Uses warshalls algorithm to calculate transitive closure and reurns a
    //matrix containing the results
    //(passed: R= matrix, nElements= length of one side of matrix)
    public static int[][] transClosureMatrix(int[][] R, int nElements){
        
        //creating matrix pointer, assigning passed matrix to new pointer 
        int[][] A = R;
        //creating & initializing a matrix of equal size to matrix A,
        //(passed: nElements= length of one side of matrix)
        int[][] B = popMatrix(nElements);
        
        //3 nested for loops that use warshall's algorithm to bitwise Or and And
        //the proper elements of a single matrix in order to find the transitive closure
        //over multiple iterations
        for (int k = 0; k < nElements; k++){
            for (int i = 0; i < nElements; i++)
                for (int j = 0; j < nElements; j++)
                    B[i][j] = bitwiseOr(A[i][j], bitwiseAnd(A[i][k], A[k][j]));
            
            //copying the values of matrix B to matrix  A so loop can reset and 
            //go onto next iteration of calculations (if it hasn't reached the last iteration yet
            for (int i = 0; i < nElements; i++)
                for (int j = 0; j < nElements; j++)
                    A[i][j] = B[i][j];
        }
        
        //return matrix A with completed transitive closure
        return A;
    }
    
	
    //bitwise AND
    //takes two values, if they are both 1, returns a 1, otherwise returns 0
    //(passed: d= 1st value to be compared, m= 2nd value to be compared)
    public static int bitwiseAnd(int d, int m){
        
        if (d == 1 && m == 1){
            return 1;
        } 
        else 
            return 0;
    }
    
	
    //bitwise OR
    //takes two values, if they are both 0, returns a 0, otherwise returns 1
    //(passed: v= 1st value to be compared, b= 2nd value to be compared)
    public static int bitwiseOr(int v, int b){
        
        if (v == 0 && b == 0){
            return 0;
        } 
        else 
            return 1;
    }
    
    //creates a matrix populated by all zero's based on input parameter value, returns created matrix
    //(passed: nPop= size of on side of desired matrix to be created)
    public static int[][] popMatrix(int nPop){
        
        //create new matrix based on input param size
        int[][] zeroMatrix = new int[nPop][nPop];
        
        //populate new matrix with all zero's
        for (int t = 0; t < nPop; t++){
            for (int y = 0; y < nPop; y++){
                zeroMatrix[t][y] = 0;
            }
        }
        
        //returns new matrix filled with all zero's
        return zeroMatrix;
    }
    
	
    //Creates a matrix based off of supplied matrix dimension size,
    //seed value and the user supplied probability of getting a connection
    //returns the random matrix
    //(passed: n= matrix size dimension, seedVal= seedvalue, pcnt= probability)
    public static int[][] randPopMatrix(int n, int seedVal, double pcnt){
        
        //create new random  generator based on suplied seed value
        randGen = new Random(seedVal);
        
        //creating matrix based on supplied size value
        int[][] randMatrix = new int[n][n];
        
        //populates matrix with approprate values at appropriate places based on probability parameter
        for (int t = 0; t < n; t++){
            for (int y = 0; y < n; y++){
                if (randGen.nextDouble() < 1 - pcnt)
                    randMatrix[t][y] = 0;
                else
                    randMatrix[t][y] = 1; 
            }
        }
        
        //return the populated matrix
        return randMatrix;
    }
    
	
    //Calculates the percent of the population that information has reached
    //based on number of supplied transitive closure connections and the size of the matrix
    //(passed: prcntMtrx= trans closure matrix, n= matrix dimension size)
    public static double infoSpreadPercent(int[][] prcntMtrx, int n){
        
        //declaring and initializing variable to hold final calculated percent value
        double cSum = 0;
        
        //nested for loops sums up all "1"'s in trans closure matrix
        for (int u = 0; u < n; u ++){
            for (int i = 0; i < n; i ++){
                if (prcntMtrx[u][i] == 1)
                    cSum = cSum + 1;
            }
        }
        
        //divides trans closure matrix "1"'s sum and divides by total number of possibilities
        return cSum / (n*n);
    }
    

    //prints the supplied matrix to an external file
    //(passed: toBePrinted= matrix to be printed, nPrint= length of matrix side,
    //(passed(cont): pW= printwriter with preconfigured file location attached.
    public static void printMatrix(int[][] toBePrinted, int nPrnt, PrintWriter pW){
        
        //iterates through matrix, and prints matrix in proper form
        for (int d = 0; d < nPrnt; d++){
            for (int f = 0; f < nPrnt; f++){
                pW.print(toBePrinted[d][f]);
            }
            pW.println("");
        }
        pW.println("\n");
    }
}
