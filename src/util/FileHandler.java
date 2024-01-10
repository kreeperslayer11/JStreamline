package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileHandler
{
	public static boolean doesFileSystemItemExist(String path, String directory)
	{
		return doesFileSystemItemExist(path + "\\" + directory);
	}
	
	public static boolean doesFileSystemItemExist(String directory)
	{
		return doesFileSystemItemExist(new File(directory));
	}
	
	public static boolean doesFileSystemItemExist(File file)
	{
		return file.exists();
	}
	
	public static String directoryExists(String path, String directory)
	{
		return directoryExists(path + "\\" + directory);
	}
	
	public static String directoryExists(String directory)
	{
		File dir = new File(directory);
		dir.mkdirs();
		return directory;
	}
	
	public static void createFile(File file)
	{
		if (!FileHandler.doesFileSystemItemExist(file))
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static String readFileExists(File file)
	{
		Scanner scan = null;
		String content = "";
		try
		{
			scan = new Scanner(file);
			scan.useDelimiter("\\Z");
			content = scan.next();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(NoSuchElementException e)
		{
			System.out.println("file was empty");
		}
		finally
		{
			if (scan != null)
			{
				scan.close();				
			}
		}
		return content;
	}
	
	public static boolean writeFileExists(File file, String content)
	{
		FileWriter fw;
		BufferedWriter bw = null;
		boolean didWrite = false;
		try 
		{
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(content);
			didWrite = true;
		}
		catch(IOException err)
		{
			err.printStackTrace();
			didWrite = false;
		}
		finally
		{
			if (bw != null)
			{
				try
				{
					bw.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
					System.exit(-1);
				}				
			}
		}
		return didWrite;
	}
}
