package com.island.contactphotoeraser;
import java.io.*;
import java.util.*;
public class FileEraser
{
	public static final String START="PHOTO";
	public static final String ALREADY_CLOSED="Scanner already closed";
	public static final String PARSING_ERROR="Error parsing file";
	public static final String VARIABLE_ERROR="Corrupted variable";
	public static void erase(BufferedReader in,BufferedWriter out)
	{
		PrintWriter writer=new PrintWriter(out);
		Scanner scan=new Scanner(in);
		boolean image=false;
		try
		{
			while(scan.hasNextLine())
			{
				try
				{
					String line=scan.nextLine();
					try
					{
						if(line.contains(START))image=true;
						else if(line.isEmpty())image=false;
						if(!image)writer.println(line);
					}
					catch(NullPointerException e)
					{
						System.err.println(VARIABLE_ERROR);
						e.printStackTrace();
						throw new OutOfMemoryError();
					}
				}
				catch(NoSuchElementException e)
				{
					System.out.println(PARSING_ERROR);
					e.printStackTrace();
				}
			}
		}
		catch(IllegalStateException e)
		{
			System.err.println(ALREADY_CLOSED);
			e.printStackTrace();
		}
		writer.close();
		scan.close();
	}
}
