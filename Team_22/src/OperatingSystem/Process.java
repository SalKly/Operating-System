package OperatingSystem;

import java.util.ArrayList;

public class Process {
	String pid;
	static int counter;
	String state;
	ArrayList<String> instructions;

	
	
	
	public Process(String filepath,String pid) {
		Parser p=new Parser();
		this.instructions=p.Parse(filepath);
		this.state="new";
		this.pid=pid;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
