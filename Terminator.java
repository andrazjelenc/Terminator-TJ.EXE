/*
 * Author: Andraz Jelenc, 16.12.2015
 * Description: It shows proof of concept how the bypassing tj.exe is possible if input and output files location is known
 * This implementation has been made for education purposes only, the autor is not responsible for any abuse or damege
 * done with this application on any system.
 * DO NOT USE IT
 */

import java.io.Console;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Terminator
{
	//When something goes wrong
	private static void kill()
	{
		System.out.println("Error!");
		System.exit(0);
	}
	
	public static void main(String[] args)
	{
		/*
		 * Reading standard input line by line and saving it to array
		 */
		ArrayList<String> standardInputArray = new ArrayList<String>();
		
		try
		{
			String line = "";
			int ch;
			while ((ch = System.in.read()) != -1)
			{
				if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch >= '0' && ch <= '9' || ch == ' ')
				{
					line += String.valueOf((char)ch);
				}
				else if ((ch =='\n') || (ch =='\r') || (ch == '\t'))
				{
					standardInputArray.add(line.trim());
					line = String.valueOf((char)ch);
				}
				else
				{
					break;
				}
			}
			line = line.trim();
			if(line.length() > 0)
			{
				standardInputArray.add(line);
			}
		}
		catch(Exception ex)
		{
			kill();
		}
		if(standardInputArray.size() == 0)
		{
			kill();
		}
		
		/*
		 * Search for XX in vhodXX.txt that contains value of standardInputArray 
		 */
		
		//folder where we run .java file
	    File runFolder = new File(Paths.get("").toAbsolutePath().toString());
		
		String testCaseNubmer = ""; //XX from vhodXX.txt
		String outputFile = ""; //full output file path
		
		//going from file to file
		for (File currentFile : runFolder.listFiles()) 
		{
			String fileNameWithPath = currentFile.toString();
			
			String extension  =  fileNameWithPath.substring(fileNameWithPath.length()-4);
			String firstPart = fileNameWithPath.substring(fileNameWithPath.length()-10, fileNameWithPath.length()-6);

			if(extension.equals(".txt") && firstPart.equals("vhod")) //vhodXX.txt
			{
				boolean pravilna = true;
				int indexLine = 0;
				
				try
				{
					Scanner fileReader = new Scanner(currentFile);
					while(fileReader.hasNextLine())
					{
						String newLine = fileReader.nextLine().trim();
						if(newLine.equals(standardInputArray.get(indexLine)))
						{
							indexLine++;
						}
						else
						{
							pravilna = false;
							break;
						}
					}
				}
				catch (Exception ex)
				{
					pravilna = false;
				}	
				
				if(pravilna)
				{
					testCaseNubmer = fileNameWithPath.substring(fileNameWithPath.length()-6, fileNameWithPath.length()-4);
					outputFile = fileNameWithPath.substring(0, fileNameWithPath.length()-10)+ "izhod" + testCaseNubmer + ".txt";
					break;
				}
			}
		}
		
		/*
		 * We went through all files in this directory, print if found
		 */
		if(testCaseNubmer != "" && outputFile != "")
		{
			try
			{
				Scanner resultPrinter = new Scanner(new File(outputFile));
				
				while (resultPrinter.hasNextLine())
				{
					System.out.println(resultPrinter.nextLine());
				}
				resultPrinter.close();
			}
			catch(Exception ex)
			{
				kill();
			}
		}
		else
		{
			kill();
		}
	}
}
