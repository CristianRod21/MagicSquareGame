import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Formatter;
import java.util.Random;

import javax.swing.JOptionPane;

public class BestHighScores 
{

	public boolean isBest(String ruta, long elapsedSeconds)	
	{
		BufferedReader textReader = null;
		File file = null;
		String temp[] = null;

		try
		{
			file = new File(this.getClass().getResource(ruta).toURI());
		} 
		catch (URISyntaxException e)
		{

		}
		catch (NullPointerException ne)
		{

			overWriteBestScore(ruta, elapsedSeconds);
			return false;
		}

		try 
		{
			textReader = new BufferedReader(new FileReader(file));
		} 
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}

		try 
		{
			temp = textReader.readLine().split(" ");
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (Integer.valueOf(temp[0]) > (elapsedSeconds))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public String getBestTimeRecord(String ruta, long elapsedSeconds)
	{
		BufferedReader textReader = null;
		File file = null;
		String temp[] = null;

		try
		{
			file = new File(this.getClass().getResource(ruta).toURI());
		} 
		catch (URISyntaxException e)
		{

		}
		catch (NullPointerException ne)
		{
			return "No best time register";
		}

		try 
		{
			textReader = new BufferedReader(new FileReader(file));
		} 
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}

		try 
		{
			temp = textReader.readLine().split(" ");
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException ne)
		{
			return "No best time register";
		}


		int temporal = Integer.valueOf(temp[0]);
		StringBuilder name = new StringBuilder();

		for (int index = 1; index < temp.length; index++)
		{
			name.append(temp[index]);
			name.append(" ");
		}

		long minutes = temporal / 60;
		long seconds = temporal % 60;

		String text = String.format("Best time is from %sand it is %02d:%02d min"
				, name.toString()
				, minutes
				, seconds);

		return text;
	}


	public void overWriteBestScore(String ruta,long elapsedSeconds)
	{	
		Formatter textFormatter = null;
		//**
		try 
		{			
			textFormatter  = new Formatter(ruta);
		}
		catch(Exception e)
		{
		
		}

		String message = JOptionPane.showInputDialog("Insert your name");

		textFormatter.format( "%d %s",elapsedSeconds,  message );

		textFormatter.close();

	}
	
	/**
	 * 
	 */
	public void readTextFile(int dimensions, BufferedReader textReader)
	{
		boolean random1 = new Random().nextBoolean();
		int myInt = (random1) ? 1 : 0;

		File file = null;
		
		try
		{
			file = new File(this.getClass().getResource("level0" + (dimensions-2) + "-0"  + (myInt + 1)   + ".txt").toURI());
		} 
		catch (URISyntaxException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try 
		{
			textReader = new BufferedReader(new FileReader(file));
		} 
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
	}	
	
}
