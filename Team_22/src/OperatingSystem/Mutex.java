package OperatingSystem;
import java.io.*;
import java.util.*;


public class Mutex {
	
	private String InputownerID;
	private String OutputownerID;
	private String FileownerID;

	private int assignSemaphore = 1;
	private int printSemaphore = 1;
	private int fileSemaphore = 1;
	//bit semaphore;
	
	public List<String> instructions = Arrays.asList("print","readFile","writeFile","input");
	
	
	Deque<Process> FileBQ = new LinkedList<Process>();
	Deque<Process> userInputBQ = new LinkedList<Process>();
	Deque<Process> printBQ = new LinkedList<Process>();
	Deque<Process> fileBQ = new LinkedList<Process>();
	Queue<Process> ReadyQ = new LinkedList<Process>();
	Deque<Process> BlockedQ = new LinkedList<Process>();
	
	
	public void semWait(String instruction, Process P){

		switch(instruction) {
			case "userInput":
				if(assignSemaphore == 1) {
					System.out.println("Proces with id "+P.pid+" have the userInput rescourse");
					InputownerID = P.pid;
					assignSemaphore = 0;
				}
				else {
					System.out.println("Proces with id "+P.pid+" is blocked");
					userInputBQ.add(P);
					BlockedQ.add(P);
					
				} break;
				
			case "userOutput":
				if(printSemaphore == 1) {
					System.out.println("Proces with id "+P.pid+" have the userOutput rescourse");

					OutputownerID = P.pid;
					printSemaphore = 0;
				}
				else {
					System.out.println("Proces with id "+P.pid+" is blocked");

					printBQ.add(P);
					BlockedQ.add(P);
				} break;
				
			case"file":
				if(fileSemaphore == 1) {
					System.out.println("Proces with id "+P.pid+" have the file rescourse");
					FileownerID = P.pid;
					fileSemaphore = 0;
				}
				else {
					System.out.println("Proces with id "+P.pid+" is blocked");
					fileBQ.add(P);
					BlockedQ.add(P);
				} break;
		}
		
		
		}
	
	
	
	public void semSignal(String instruction, Process P) {

			switch(instruction) {
			case "userInput":
				if(InputownerID.equals(P.pid)) {
					System.out.println("Proces with id "+P.pid+" Released the UserInput rescourse");
					if(userInputBQ.isEmpty()) 
						assignSemaphore = 1;

					else {
						Process unblocked=userInputBQ.remove(); //removed from the innerblocked
						BlockedQ.remove(unblocked); //removed from the mainblocked
						InputownerID=unblocked.pid;
						ReadyQ.add(unblocked);
						System.out.println("Proces with id "+unblocked.pid+" is unblocked");
						System.out.println("Proces with id "+unblocked.pid+" have the userInput rescourse");


						
						
					}
				}
				else {
					System.out.println("Proces with id "+P.pid+" doesnt have the authority as its not the owner of the rescourse");

					
				}
				break;
				
				
			case "userOutput":
				if(OutputownerID.equals(P.pid)) {
					System.out.println("Proces with id "+P.pid+" Released the UserOutput rescourse");

					if(printBQ.isEmpty()) 
						printSemaphore = 1;
					else {
						Process unblocked=printBQ.remove(); //removed from the innerblocked
						BlockedQ.remove(unblocked); //removed from the mainblocked
						OutputownerID=unblocked.pid;
						ReadyQ.add(unblocked);
						System.out.println("Proces with id "+unblocked.pid+" is unblocked");
						System.out.println("Proces with id "+unblocked.pid+" have the userOutput rescourse");


					}
				}
				else {
					System.out.println("Proces with id "+P.pid+" doesnt have the authority as its not the owner of the rescourse");

					
				}
			break;
				
			case"file":
				if(FileownerID.equals(P.pid)) {
					System.out.println("Proces with id "+P.pid+" Released the file rescourse");
					if(fileBQ.isEmpty()) 
						fileSemaphore = 1;
					else {
						Process unblocked=fileBQ.remove(); //removed from the innerblocked
						BlockedQ.remove(unblocked); //removed from the mainblocked
						FileownerID=unblocked.pid;
						ReadyQ.add(unblocked);
						System.out.println("Proces with id "+unblocked.pid+" is unblocked");
						System.out.println("Proces with id "+unblocked.pid+" have the file rescourse");


					}
				}
				else {
					System.out.println("Proces with id "+P.pid+" doesnt have the authority as its not the owner of the rescourse");

					
				}
				
				break;
		}
			
		
	}
	
	
	
	
	
	
	

}