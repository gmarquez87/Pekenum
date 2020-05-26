package org.msarecovery.msar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



public class Testing{
	public static void main (String [] args) throws FileNotFoundException{
		System.out.println(readCSV("/Users/gaston.marquez/Documents/RIT/MS recovery tool/Paper/Empirical study/P38-JHipster microservice analysis/tmp38-1/csvToWekaSemantic.csv"));
		//PrintWriter pw = new PrintWriter(new File("/Users/gaston.marquez/Desktop/csvTMP.csv"));
        //StringBuilder sb = new StringBuilder();
        //int [][] matrix = new int[2500][2500];
        //for (int[] row: matrix)
        //    Arrays.fill(row, 0);
        
        
        //for (int i = 0; i < matrix.length; i++) {
        //	for (int j = 0; j < matrix[i].length; j++) {
        //		pw.append(String.valueOf(matrix[i][j]) + ",");
        //	}
        //	pw.append('\n');
        //}
        //pw.write(sb.toString());
        //pw.close();
	}
	
	public static List<List<String>> readCSV(String path) {
		String fileName= path;
        File file= new File(fileName);

        // this gives you a 2-dimensional array of strings
        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);

            while(inputStream.hasNext()){
                String line= inputStream.next();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                lines.add(Arrays.asList(values));
            }

            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
	}
}