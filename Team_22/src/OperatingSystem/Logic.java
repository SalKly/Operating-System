package OperatingSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Logic {
	 String[][] Memory; // the memory is an [[variable,Pid,Value],[],[]]
	 String [][] Temp;
	 Mutex x;
	 static int counter=0;
	 
	 
	 public Logic() {
			this.Memory=new String [100][3];
			this.Temp=new String [3][2];

			this.x=new Mutex();
	 }
	 
	
	  public  String readFile(String Variable,Process P){
		  String Path="";
	        String Instructions = "";
		  int i=0;
			while (i<this.Memory.length) {
				if(this.Memory[i][0].equals(Variable)) {
					if(this.Memory[i][1].equals(P.pid)) {
						Path=this.Memory[i][2];
						break;
					}
				}

				i++;
			
			}
		  
			try {		  
		   FileReader fr=new FileReader(Path);    
	        BufferedReader br=new BufferedReader(fr);    
	        
	        String j="";
				while((j=br.readLine())!=null){ 
					Instructions=Instructions+j;
					
				  }

				fr.close();
				br.close();
				return Instructions;

				}catch(Exception e) {

					return "File path not found";
				}
		  
		  

			
	          } 
		
		
		
		public void assign(String Variable,Process P,String Value) {
			int i=0;
			
			while (i<this.Memory.length) {
				if(this.Memory[i][0]==null) {
					this.Memory[i][0]=Variable;
					this.Memory[i][1]=P.pid;
					this.Memory[i][2]=Value;
					break;
					
					 
				}
				else {

					if(this.Memory[i][0].equals(Variable)) {
					if(this.Memory[i][1].equals(P.pid)) {
						this.Memory[i][2]=Value;
					break;
				}
				}
					
				}
				i++;	


				}
		
			
			}
		public void AddTemp(Process P,String Value) {
			int i=0;
			
			while (i<this.Temp.length) {
				if(this.Temp[i][0]==null) {
					this.Temp[i][0]=P.pid;
					this.Temp[i][1]=Value;
					break;
					
					 
				}
				else {
					if(this.Temp[i][0].equals(P.pid)) {
						this.Temp[i][1]=Value;
					break;
				}
				
					
				}
				i++;	


				}
		}
		
		


		
			
			
		
		public void print(String Variable,Process P) {
			int i=0;
			while (i<this.Memory.length) {
				if(this.Memory[i][0].equals(Variable)) {
					if(this.Memory[i][1].equals(P.pid)) {
						System.out.print(this.Memory[i][2]);
						break;
					}
				}
				else {
				
						System.out.print("Not found");
						break;
						
				}
				i++;
			
			}
			
			
		}
		public void printFromTo(String Variable1,String Variable2,Process P) {
			int firstNumb=0;
			int SecondNumb=0;
			int i=0;
			while (i<this.Memory.length) {
				if(this.Memory[i][0].equals(Variable1)) {
					if(this.Memory[i][1].equals(P.pid)) {
						firstNumb=Integer.parseInt(this.Memory[i][2]);
						break;
					}
				}

				i++;
			
			}
			i=0;
			while (i<this.Memory.length) {
				if(this.Memory[i][0].equals(Variable2)) {
					if(this.Memory[i][1].equals(P.pid)) {
						SecondNumb=Integer.parseInt(this.Memory[i][2]);
						break;
					}
				}

				i++;
			
			}
			
			int j=firstNumb;

			while(j<SecondNumb) {
				System.out.println(j);
				j++;
			}
			
			
			
			
			
			
			
			
		}
		public  void writeFile(String Variable,String data,Process P) {
			  String Path="";
			  int i=0;
				while (i<this.Memory.length) {
					if(this.Memory[i][0].equals(Variable)) {
						if(this.Memory[i][1].equals(P.pid)) {
							Path=this.Memory[i][2];
							break;
						}
					}

					i++;
				
				}
				try {
					FileWriter FW=new FileWriter(Path);
					BufferedWriter BW=new BufferedWriter(FW);
					BW.write(data);
					BW.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			
			
			
			
			
		}
		
		public  void RunInstruction(String instruction,Process P) {

			String[] Divider=instruction.split("\\s");
			Stack<String> St=new Stack<>();
			String op1,op2;
			int i =Divider.length-1;
			boolean Flag=true;
			ArrayList<String> instructionAv= new ArrayList<String>(Arrays.asList("print","assign","writeFile","readFile","printFromTo","semWait","semSignal","input"));
			while(i>=0&&Flag) {

				if (instructionAv.contains(Divider[i])) {
		            switch (Divider[i]) {
		                case "print":
		                	if(St.empty()){
		                		System.out.print("Wrong Syntax");
		                	}
		                	else {
		                     	 op1=St.pop();
		 	                    print(op1, P);
								 System.out.println("Now Process with id "+P.pid+" Has just finished the instruction Print "+ op1);

		                	}
		                	P.instructions.remove(0);
	 	                    counter++;

		          
		                    break;
		                case "assign":
		                 	if(St.size()<2&&St.size()>0){
		                 		int k=0;
		            			while (k<this.Temp.length) {
			                 		 System.out.println("We are at the assign");

		            				if(this.Temp[k][0].equals(P.pid)) {
		            					op1=St.pop();
		       	                 	     assign(op1,P,this.Temp[k][1]);
										 System.out.println("Now Process with id "+P.pid+" Has just finished the instruction Assign "+ op1 +" " +this.Temp[k][1]);
		            						break;
		            					
		            				}

		            				k++;
		            			
		            			}
		                 		
		                	}
		                	else {
	                 	     op1=St.pop();
	                	     op2=St.pop();

			                 assign(op1, P,op2);
							 System.out.println("Now Process with id "+P.pid+" Has just finished the instruction Assign "+ op1 +" "+ op2);

			                 

		                	}
		                	P.instructions.remove(0);
	 	                    counter++;


		                    break;
		                case "input":
		                    Scanner sc = new Scanner(System.in);
		                    System.out.println("Please Enter a value:");
		                    String TempV2=sc.nextLine();
							 System.out.println("Now Process with id "+P.pid+" Has just finished the instruction Input ");

		                    if(i==0) {
		                    	   P.instructions.remove(0);
		                       }
		                       else {
		                    	   AddTemp(P,TempV2);
		                    	   P.instructions.set(0,P.instructions.get(0).replace("input",""));

		                       }
		                    
	 	                    Flag=false;
	 	                    counter++;

		                    break;
		                case "readFile":
		                	if(St.empty()){
		                		System.out.print("Wrong Syntax");
		                	}
		                	else {
		                        op1=St.pop();
		                       String TempV=readFile(op1,P);
								 System.out.println("Now Process with id "+P.pid+" Has just finished the instruction ReadFile "+op1);

		                       if(i==0) {
		                    	   P.instructions.remove(0);

		                       }
		                       else {
		                    	   AddTemp(P,TempV);
		                    	   P.instructions.set(0,P.instructions.get(0).replace("readFile "+op1,""));

		                       }
		                  

		                	}
		                    Flag=false;
	 	                    counter++;

		                       
		                    break;
		                case "writeFile":
		                	P.instructions.remove(0);

		                	if(St.size()<2){
		                		System.out.print("Wrong Syntax");
		                	}
		                	else {
			                	 op1=St.pop();
			                	 op2=St.pop();
			                    writeFile(op1, op2,P);
								 System.out.println("Now Process with id "+P.pid+" Has just finished the instruction WriteFile "+op1+" "+op2);


		                	}
	 	                    counter++;



		                    break;
		                case "semWait":
		                	P.instructions.remove(0);

		                	if(St.empty()){
		                		
		                		System.out.print("Wrong Syntax");
		                	}
		                	else {
			                	 op1=St.pop();
			                    x.semWait(op1, P);
								 System.out.println("Now Process with id "+P.pid+" Has just finished the instruction SemWait "+op1);


		                	}
	 	                    counter++;


		                    break;
		           
		                case "semSignal":
		                 	P.instructions.remove(0);
		                	if(St.empty()){
		                		System.out.print("Wrong Syntax");
		                	}
		                	else {
			                	 op1=St.pop();
			                	 
			                    x.semSignal(op1, P);
								 System.out.println("Now Process with id "+P.pid+" Has just finished the instruction SemSignal "+op1);


		                	}
	 	                    counter++;

		                    break;
		                case "printFromTo":

		                	if(St.size()<2){
		                		System.out.print("Wrong Syntax");
		                	}
		                	else {
			                	 op1=St.pop();
			                	 op2=St.pop();
			                    printFromTo(op1, op2,P);
								 System.out.println("Now Process with id "+P.pid+" Has just finished the instruction PrintFromTo "+op1+" "+op2);


		                	}
		                 	P.instructions.remove(0);
	 	                    counter++;

		                    break;
		                    
		           
		           

		            }
		          
				
				
			}else {
				St.push(Divider[i]);
			}
			
				
			
				i--;

			}
			

		}
		
		
		public void Schedule(int InstructionsPerTS,int time1,int time2,int time3) {
			int min=Math.min(time1, time2);
			int minMain=Math.min(time3, min);
			int i=0;
			System.out.println("now the Timer is "+i);
			while(i<minMain) {
				System.out.println("now the Timer is "+i);
				i++;
			}
			
			
			Process p1=new Process("src/Program_1.txt","1");
			Process p2=new Process("src/Program_2.txt","2");
			Process p3=new Process("src/Program_3.txt","3");
			int timer=i;
			Process running=null;
			System.out.println("Start");
			while(true) {
				System.out.println("The counter is =  "+counter);
				System.out.println("The timer is =  "+timer);

				if (timer==time1) {
					if(!x.ReadyQ.contains(p1)) {
						x.ReadyQ.add(p1);
						System.out.println("Now Process with id "+p1.pid+" arrived");
					}
			
					


					
					
				}else {
					if(timer==time2) {
						if(!x.ReadyQ.contains(p2)) {
							x.ReadyQ.add(p2);
							System.out.println("Now Process with id "+p2.pid+" arrived");
						}

						
					}else {
						if (timer==time3){
							if(!x.ReadyQ.contains(p3)) {
								x.ReadyQ.add(p3);
								System.out.println("Now Process with id "+p3.pid+" arrived");
							}
							
						}
					}
				}
				if(!x.ReadyQ.isEmpty()||!x.BlockedQ.isEmpty()||running!=null) {
					if(running==null) {
						 running=x.ReadyQ.poll();
						 if(running.instructions.isEmpty()) {
								System.out.println("Process with id "+running.pid+" finished executing DONEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
								running=null;						 
						}
						 else {
							 System.out.println("Now Process with id "+running.pid+" is running");
							 System.out.println("Now the  Process with id "+running.pid+"   instruction "+running.instructions.get(0)+" is going to run");
							 String InsturcionTemp=running.instructions.get(0);
							 this.RunInstruction(running.instructions.get(0), running);

							 if(x.BlockedQ.contains(running)) {
								 System.out.println("the Process with id "+running.pid+" was blocked during   the instruction "+InsturcionTemp);
								 running=null;
								 counter=0;
								 timer++;
							 }
							 else {

								 timer++;
							 }
						 }
					
			
						 
						 
				
						
						
						
						
					}
					else {
						if(counter==InstructionsPerTS) {
							 System.out.println("the Process with id "+running.pid+" has done "+InstructionsPerTS+" instructions so premation will happen ");

							x.ReadyQ.add(running);
							counter=0;
							running=null;
							
						}
						else {
							if(running.instructions.isEmpty()) {
								System.out.println("Process with id "+running.pid+" finished executing DONEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
								counter=0;

								running=null;
								
								
							}else {
							
							 System.out.println("Now Process with id "+running.pid+" is running");
							 System.out.println("Now the  Process with id "+running.pid+" instruction "+running.instructions.get(0)+" is going to run");
							 String InsturcionTemp=running.instructions.get(0);

							 this.RunInstruction(running.instructions.get(0), running);
							 
							 if(x.BlockedQ.contains(running)) {
								 System.out.println("the Process with id "+running.pid+" was blocked during   the instruction "+InsturcionTemp);
								 running=null;
								 counter=0;
								 timer++;
							 }
							 else {
								 timer++;
							 }

						}
					}
						

						
					}
				}else {
					System.out.print("FINISHED!!");
					break;
				}
		
				
				
			}
			

			
		}
		

		
		
			
	
		

	public static void main(String[] args) {
		Logic test=new Logic();
		test.Schedule(2, 4, 0, 2);




		
		
//        Process p=new Process("src/program_1.txt","2");
//		test.RunInstruction("assign b src/program_1.txt", p);
//		test.RunInstruction("assign a readFile b", p);
//		test.RunInstruction("print a", p);



//		System.out.print(test.Memory[0][0]);
//		System.out.print(test.Memory[0][1]);
//		System.out.println(test.Memory[0][2]);
//		System.out.print(test.Memory[1][0]);
//		System.out.print(test.Memory[1][1]);
//		System.out.println(test.Memory[1][2]);
//		


//
//		test.RunInstruction("SemWait userInput", p);
//		test.RunInstruction("SemWait userInput", p2);
//		test.RunInstruction("SemSignal userInput", p);










	}

}
