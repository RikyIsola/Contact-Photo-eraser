package com.island.contactphotoeraser;
import java.io.*;
import java.util.*;
public class FileEraser
{
	private static final String START="PHOTO";
	/**
	 * Delete from a vcf file all contact images
	 * @param input The input file to read
	 * @param output The output file without photos
	 */
	public static void erase(BufferedReader input,BufferedWriter output)
	{
		PrintWriter writer=new PrintWriter(output);
		Scanner scanner=new Scanner(input);
		boolean image=false;
		while(scanner.hasNextLine())
		{
			String line=scanner.nextLine();
			if(line.contains(START))image=true;
			else if(line.isEmpty())image=false;
			if(!image)writer.println(line);
		}
		writer.close();
		scanner.close();
	}
}
