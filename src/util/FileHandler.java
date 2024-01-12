package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

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
	
	public static String[] filesInDirectory(File dir)
	{
		if (doesFileSystemItemExist(dir))
		{
			File[] files = dir.listFiles();
			String[] names = new String[files.length];
			for (int i = 0; i < files.length; i++)
			{
				names[i] = files[i].getName();
			}
			return names;
		}
		return new String[0];
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
	
	public static JsonObject readInResource(String filepath)
	{
		File file = new File(filepath);
		if (!doesFileSystemItemExist(Reference.RESOURCES_FOLD))
		{
			directoryExists(Reference.RESOURCES_FOLD);
		}
		if (!doesFileSystemItemExist(Reference.LANG_FOLD))
		{
			directoryExists(Reference.LANG_FOLD);
		}
		createFile(file);
		String content = readFileExists(file);
		JsonElement json;
		try
		{
			json = JsonParser.parseString(content);
		}
		catch (JsonSyntaxException e)
		{
			json = JsonParser.parseString("{}");
		}
		if (json.isJsonObject())
		{
			return json.getAsJsonObject();
		}
		return new JsonObject();
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
