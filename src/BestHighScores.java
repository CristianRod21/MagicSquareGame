import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Formatter;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Class responsible for the verification of 
 * the existence of the registry file with 
 * the best scores. Load scores and show them
 * 
 * @author Diego Orozco & Cristian Rodriguez
 */
public class BestHighScores 
{

	/**
	 * this method is responsible for reading the file from 
	 * the route given by parameter, also create a file, if 
	 * it does not exist, in addition to verify if the new 
	 * score is less than the current score
	 * 
	 * @param ruta, where is the file to read
	 * @param elapsedSeconds,new time in seconds 
	 * @return, true, if all is correct
	 */
	public boolean isBest(String ruta, long elapsedSeconds)	
	{
		BufferedReader textReader = null;
		File file = null;
		String temp[] = null;


		// reads the file from the route given by parameter
		try
		{
			file = new File(this.getClass().getResource(ruta).toURI());
		} 
		catch (URISyntaxException e){}
		catch (NullPointerException ne)
		{
			overWriteBestScore(ruta, elapsedSeconds);
			return false;
		}

		// create the text file
		try 
		{
			textReader = new BufferedReader(new FileReader(file));
		} 
		catch (FileNotFoundException e1){e1.printStackTrace();}

		// read the file and determine if the new score is less than the read
		try 
		{
			temp = textReader.readLine().split(" ");
		} 
		catch (IOException e){ e.printStackTrace(); }

		if (Integer.valueOf(temp[0]) > (elapsedSeconds))
			return true;
		else
			return false;
	}

	/**
	 * This method is responsible for creating the text with the best score 
	 * data with format the text
	 * @param ruta, where to create and read the text
	 * @param elapsedSeconds,  time in seconds 
	 * @return the final text, with the all the data
	 */
	public String getBestTimeRecord(String ruta, long elapsedSeconds)
	{
		BufferedReader textReader = null;
		File file = null;
		String temp[] = null;

		// create a new file from the given route
		// if it does not exist it shows: "No best time register"
		try
		{
			file = new File(this.getClass().getResource(ruta).toURI());
		} 
		catch (URISyntaxException e){}
		catch (NullPointerException ne){ return "No best time register"; }

		// read from the file
		try 
		{
			textReader = new BufferedReader(new FileReader(file));
		} 
		catch (FileNotFoundException e1) {e1.printStackTrace();}

		// separate each element that is in the file
		try 
		{
			temp = textReader.readLine().split(" ");
		} 
		catch (IOException e){ e.printStackTrace(); }
		catch (NullPointerException ne){ return "No best time register"; }


		int temporal = Integer.valueOf(temp[0]);
		StringBuilder name = new StringBuilder();
		
		// add the name for to show
		for (int index = 1; index < temp.length; index++)
		{
			name.append(temp[index]);
			name.append(" ");
		}

		// convert the seconds in minutes and second
		long minutes = temporal / 60;
		long seconds = temporal % 60;

		// create the String with the best Score with format
		String text = String.format("Best time is from %sand it is %02d:%02d min"
				, name.toString()
				, minutes
				, seconds);

		// return the final text, with the all the data
		return text;
	}


	/**
	 * Ask the user's name. overwrite a file.
	 * @param ruta, where to create and overwrite the text
	 * @param elapsedSeconds,  time in seconds 
	 */
	public void overWriteBestScore(String ruta,long elapsedSeconds)
	{	
		Formatter textFormatter = null;
		//**
		try 
		{			
			textFormatter  = new Formatter(ruta);
		}
		catch(Exception e){}

		// Ask the user's name.
		String message = JOptionPane.showInputDialog("Insert your name");

		// set the name witg the seconds
		textFormatter.format( "%d %s",elapsedSeconds,  message );

		textFormatter.close();

	}
	
	/**
	 * This method is responsible for reading the text file, to be shown during 
	 * the development of the game
	 */
	public void readTextFile(int dimensions, BufferedReader textReader)
	{
		boolean random1 = new Random().nextBoolean();
		int myInt = (random1) ? 1 : 0;

		File file = null;
		
		// access the text file to read it
		try
		{
			file = new File(this.getClass().getResource("level0" + (dimensions-2) + "-0"  + (myInt + 1)   + ".txt").toURI());
		} 
		catch (URISyntaxException e){ e.printStackTrace(); }
		
		// read the file
		try 
		{
			textReader = new BufferedReader(new FileReader(file));
		} 
		catch (FileNotFoundException e1) { e1.printStackTrace(); }
	}	
	
}
