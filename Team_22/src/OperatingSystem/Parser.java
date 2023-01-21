package OperatingSystem;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;




public class Parser {
	  
	public Parser() {
		
	}
	

	public  ArrayList<String> Parse(String filepath){
		try {		  
	   FileReader fr=new FileReader(filepath);    
        BufferedReader br=new BufferedReader(fr);    
        
        ArrayList<String> Instructions = new ArrayList<String>();
            String i;        
			while((i=br.readLine())!=null){ 
				  Instructions.add(i);
				  

				
			  }
			fr.close();
			br.close();
			return Instructions;
			}catch(Exception e) {
				return null;
			}

		
          } 
	
	
  
          
	

	
	public static void main(String[] args) {
		Parser x=new Parser();
		int i =0;
		int j =0;






		



	
			
		
	
		
		

	}

}
