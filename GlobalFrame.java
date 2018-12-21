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
 * Problems:
 * 	-InputData class Bi always has temperature 0.9
 * 
 * To do for next version:
 * 	-Handle exception cases for the input
 * 	-LongSimulation implementation
 * Implemented in this version:
 * 	-simulation and results button actions
 * 	-choosing a file through its directory	
 * 	-added test print to the graph methods in the results class
 * 	-added implementation of IFlipAcceptance to Og
 * 	-added inheritance of InputData to Bi
 * 	-changed doIaccept to doIAccept in IFlipAcceptance
 * 	-added arrayList of results initialization in Re
 * 
 * Class that creates the JFrame objects that will allow to perform
 * the simulation and show the results. 
 */
public class GlobalFrame {
	
	Boolean multipleTemps=false;//variable to check if only one simulation is going to be done
	
	String filePath=null;//contains the name of the file
	InputData inputData;//input data
	Double inTemp=0.0;
	Double finTemp=300.0;
	Double temperatureIncrement=30.0;
	
	JFrame inputFrame; 
	JFrame simulationFrame;
	JFrame resultsFrame;
	JFrame temperatureFrame;
	JFrame simOptionsFrame;
	File file;
	JFileChooser jfc;
	IR2MS sim;
	IResults results;
	Result	tempResult;
	
	
	/**
	 * Class constructor, creates three frames: input, temperatures and simulation/results. Only the input 
	 * is visible at the start
	 */
	public GlobalFrame() {
		
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
			           inputData=new Bi(); //Creating input object TODO choose prefered class for InputData
			           inputData.read(filePath);; //Reading file 
			           inputFrame.setVisible(false); //Closing frame
			           simOptionsFrame.setVisible(true); //Opening new frame
			           
			           
			}
			});
		inputFrame.add(fileReader);//adding button  
		inputFrame.add(tf);//adding file name input slot

		inputFrame.setSize(400,500);//400 width and 500 height  
		inputFrame.setLayout(null);//using no layout managers  
		inputFrame.setVisible(true);//making the frame visible  
		
		/**
		 * Creating simulation options frame
		 */
		
		simOptionsFrame=new JFrame();
			
			//Button to choose only one simulation
			JButton oneSim=new JButton("One temperature");//creating instance of JButton  
			oneSim.setBounds(100,100,200, 40);//x axis, y axis, width, height  
	
			oneSim.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
					simOptionsFrame.setVisible(false);
		          	simulationFrame.setVisible(true);  
			}
			});
			
			//Button for multiple temperature simulations
			JButton multSim=new JButton("Various temperatures");//creating instance of JButton  
			multSim.setBounds(100,200,200, 40);//x axis, y axis, width, height  
	
			multSim.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
					multipleTemps=true;
					simOptionsFrame.setVisible(false);
					temperatureFrame.setVisible(true);  
			}
			});
			
		simOptionsFrame.add(oneSim);
		simOptionsFrame.add(multSim);
		
		simOptionsFrame.setSize(400,500);//400 width and 500 height  
		simOptionsFrame.setLayout(null);//using no layout managers  
		simOptionsFrame.setVisible(false);//making the frame visible 
		
		
		/**
		 * Creating temperatures frame
		 */
		
		temperatureFrame=new JFrame();//creating instance of JFrame  
			final JTextField initialTemp=new JTextField();//creating text box for the initial temperature
			final JTextField finalTemp=new JTextField();//text box for final temperature
			final JTextField tempIncrement=new JTextField();//text box for the temperature increment
			initialTemp.setBounds(100,100,200,20);
			finalTemp.setBounds(100, 130, 200, 20);
			tempIncrement.setBounds(100,160,200,20);
	    
			initialTemp.setText("Initial Temperature");
			finalTemp.setText("Final Temperature");
			tempIncrement.setText("Temperature Increment");
			
			//Creating a button to get the input for the temperature
		     
			JButton readTemps=new JButton("Read temperatures");//creating instance of JButton  
			readTemps.setBounds(100,200,200, 40);//x axis, y axis, width, height  
		
			readTemps.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				if(!initialTemp.getText().equals("Initial Temperature")) {//checking for input, if there is no input use default values
			          inTemp=Double.parseDouble(initialTemp.getText());
			          finTemp=Double.parseDouble(finalTemp.getText());
			          temperatureIncrement=Double.parseDouble(tempIncrement.getText());
				}
			          temperatureFrame.setVisible(false);    
			          simulationFrame.setVisible(true);          
			}
			});
			temperatureFrame.add(readTemps);//adding button
			temperatureFrame.add(initialTemp);//adding text boxes
			temperatureFrame.add(finalTemp);
			temperatureFrame.add(tempIncrement);
			
			
			temperatureFrame.setSize(400,500);//400 width and 500 height  
			temperatureFrame.setLayout(null);//using no layout managers
			temperatureFrame.setVisible(false);//hiding frame
		
		/**
		 * Creating simulation frame
		 */
		
		simulationFrame = new JFrame();// creating simulation option frame
		
		
			//Creating button to show results
		 
		
			final JButton resultHandler = new JButton("Show results");// creating result button
			resultHandler.setBounds(100, 150, 150, 20);// result x axis, y axis, width, height

			resultHandler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//////////////////////////////
				//Result button actions///////	
				//////////////////////////////
				results.plotXvsT( inputData.latticeLength, inputData.H);// print the result
			}
			});
		
		simulationFrame.add(resultHandler);
		resultHandler.setVisible(false);//hiding results button
		

		
			//Creating button to perform the simulation
		 

			JButton simulationStart = new JButton("Start simulation");// creating simulation starter button
			simulationStart.setBounds(100, 100, 200, 40);// simulation x axis, y axis, width, height

			simulationStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//////////////////////////////
				//Simulation button actions///	
				//////////////////////////////
				sim=new He();//TODO choose preferred class for Simulation
				results=new Re();//TODO choose preferred class for Results
				if(multipleTemps) {
				for(double T=inTemp; T<=finTemp; T=T+temperatureIncrement) {
					//Creating an inputData with a different temperature for every simulation
					//TODO choose preferred class for InputData
					InputData inputDataVar=new Bi(inputData.latticeLength, T, inputData.H, inputData.nJ, inputData.J, inputData.mcs, inputData.therm, inputData.skip);
					tempResult=sim.run(inputDataVar,results);// run the simulation multiple times
				}
				}
				else {
					tempResult=sim.run(inputData,results);// run the simulation once
				}
				
				resultHandler.setVisible(true);//Show next button
			}
			});
		simulationFrame.add(simulationStart);
		
		
		simulationFrame.setSize(400,500);//400 width and 500 height  
		simulationFrame.setLayout(null);//using no layout managers
		simulationFrame.setVisible(false);//hiding frame
	}
		
}