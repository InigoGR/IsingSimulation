package r2ms.gui.modIr;

import java.awt.event.ActionEvent;
import r2ms.common.*;
import r2ms.inputData.modBi.Bi;
import r2ms.simIsing.modHe.*;
import r2ms.results.modRe.*;


import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

/*
 * Author: Inigo Gonzalez Ruiz
 * Version: 17/12/2018
 * To do for next version:
 * 	-LongSimulation implementation
 * Implemented in this version:
 * 	-simulation and results button actions
 * 	-choosing a file through its directory	
 * 	-changed IR2MS to not return a Result object
 * 	-added test print to the graph methods in the results class
 * 	-added implementation of IFlipAcceptance to Og
 * 	-added inheritance of InputData to Bi
 * 	-changed doIaccept to doIAccept in IFlipAcceptance
 * 
 * Class that creates the JFrame objects that will allow to perform
 * the simulation and show the results. 
 */
public class Exp {
	
	boolean areResults = false;// variable to check if there are results
	boolean resultsShown =false;// variable to check if the results have been shown
	String filePath=null;//contains the name of the file
	InputData inputData;
	JFrame inputFrame;
	JFrame simulationFrame;
	JFrame resultsFrame;
	File file;
	JFileChooser jfc;
	IR2MS sim;
	IResults results;
	
	/**
	 * Class constructor, creates two frames: input and simulation/results. Only the input 
	 * is visible at the start
	 */
	public Exp() {
		
		inputFrame=new JFrame();//creating instance of JFrame  
		final JTextField tf=new JTextField();//creating text box  
	    tf.setBounds(100,100,150,20);
	    tf.setText("File path");
	    
	    
	    //Creating a button to get the file path
	     
		JButton filePathButton=new JButton("Select file directory");//creating instance of JButton  
		filePathButton.setBounds(100,150,200, 40);//x axis, y axis, width, height  
		
		filePathButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = jfc.showOpenDialog(null);
				// int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					filePath=(selectedFile.getAbsolutePath());
					tf.setText(filePath);
				}
			           
			           
			}
		});
		inputFrame.add(filePathButton);//adding button  
	    
	    
	    //Creating a button to get the input from the textbox
	     
		JButton fileReader=new JButton("Read from file");//creating instance of JButton  
		fileReader.setBounds(100,200,200, 40);//x axis, y axis, width, height  
		
		fileReader.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			           filePath=tf.getText();  //getting the file name
			           inputData=new Bi(); //Creating input object
			           inputData.read(filePath); //Reading file 
			           inputFrame.setVisible(false); //Closing frame
			           simulationFrame.setVisible(true); //Opening new frame
			           System.out.println(filePath); //Printing file path
			           
			           
			}
		});
		inputFrame.add(fileReader);//adding button  
		inputFrame.add(tf);//adding file name input slot

		inputFrame.setSize(400,500);//400 width and 500 height  
		inputFrame.setLayout(null);//using no layout managers  
		inputFrame.setVisible(true);//making the frame visible  
		
		
		simulationFrame = new JFrame();// creating simulation option frame
		
		
		//Creating button to show results
		 
		
		final JButton resultHandler = new JButton("Show results");// creating result button
		resultHandler.setBounds(100, 150, 150, 20);// result x axis, y axis, width, height

		resultHandler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//////////////////////////////
				//Result button actions///////	TODO
				//////////////////////////////
				results.plotXvsT( inputData.latticeLength, inputData.H);// print the result
			}
		});
		
		simulationFrame.add(resultHandler);
		resultHandler.setVisible(false);
		

		
		//Creating button to perform the simulation
		 

		JButton simulationStart = new JButton("Start simulation");// creating simulation starter button
		simulationStart.setBounds(100, 100, 200, 40);// simulation x axis, y axis, width, height

		simulationStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//////////////////////////////
				//Simulation button actions///	TODO
				//////////////////////////////
				sim=new He();
				results=new Re();
				sim.run(inputData,results);// run the simulation
				resultHandler.setVisible(true);//Show next button
			}

		});
		simulationFrame.add(simulationStart);
		
		
		simulationFrame.setSize(400,500);//400 width and 500 height  
		simulationFrame.setLayout(null);//using no layout managers
		simulationFrame.setVisible(false);//hiding frame
	}
		
}