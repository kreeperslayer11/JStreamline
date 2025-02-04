package templates.json.datareading;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import templates.json.datareading.options.Field;
import util.json.parser.IterationFieldStorage;

public class SingleTemplateFile
{
	private String saveFileName;
	private String saveLocation;
	private String contents;
	private ArrayList<IterationFieldStorage> iterateFileLevel = new ArrayList<>();
	private ArrayList<IterationFieldStorage> iterateContentLevel = new ArrayList<>();

	public SingleTemplateFile(String fileName, String filePath, JsonObject fileContents, IterationFieldStorage[] IterationTypes) 
	{
		this.saveFileName = fileName;
		this.saveLocation = filePath;
		this.contents = stringContents(fileContents);
		for (int i = 0; i < IterationTypes.length; i++)
		{
			for (int j = 0; j < IterationTypes[i].level.length; j++)
			{
				if (IterationTypes[i].level[j].equals("ContentLevel"))
				{
					iterateContentLevel.add(IterationTypes[i]);
				}
				if (IterationTypes[i].level[j].equals("FileLevel"))
				{
					iterateFileLevel.add(IterationTypes[i]);
				}
			}
		}
	}

	private SingleTemplateFile(String fileName, String filePath, String fileContents, ArrayList<IterationFieldStorage> iterateFile, ArrayList<IterationFieldStorage> iterateContent) 
	{
		this.saveFileName = fileName;
		this.saveLocation = filePath;
		this.contents = fileContents;
		this.iterateFileLevel = iterateFile;
		this.iterateContentLevel = iterateContent;
	}

	public SingleTemplateFile duplicate() 
	{
		return new SingleTemplateFile(saveFileName, saveLocation, contents, iterateFileLevel, iterateContentLevel);
	}
	
	public boolean isIterableFileName(String onId)
	{
		return saveFileName.contains("**" + onId + "**");
	}
	
	public boolean doesFieldIterateOnThisFile(Field field)
	{
		for (int i = 0; i < iterateFileLevel.size(); i++)
		{
			if (iterateFileLevel.get(i).id.equals(field.getID()))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean doesFieldIterateOnContent(Field field)
	{
		for (int i = 0; i < iterateContentLevel.size(); i++)
		{
			if (iterateContentLevel.get(i).id.equals(field.getID()))
			{
				return true;
			}
		}
		return false;
	}
	
	public SingleTemplateFile duplicateFromIterateField(String fieldId, String FieldContent)
	{
		if (saveFileName.contains("**" + fieldId + "**"))
		{
			return new SingleTemplateFile(saveFileName.replace("**" + fieldId + "**", FieldContent), saveLocation, contents, iterateFileLevel, iterateContentLevel);
		}
		return duplicate();
	}
	
	public void Fill(Field field)
	{
		Fill(field, false, -1, null);
	}
	
	private String iterId(String id, int which)
	{
		return "**" + id + "[" + which + "]**";
	}

	public void Fill(Field field, boolean iterateContents, int parsedMaxIteration, String useMeInsteadOfFieldContentOnNonIterateContent)
	{
		saveFileName = saveFileName.replace("**" + field.getID() + "**", field.getCurrentFiller());
		saveLocation = saveLocation.replace("**" + field.getID() + "**", field.getCurrentFiller());
		if (iterateContents)
		{
			int MaxLinesToDuplicate = 0;
			boolean foundANewOne;
			do
			{
				foundANewOne = contents.contains(iterId(field.getID(), MaxLinesToDuplicate));
				if (foundANewOne)
				{
					MaxLinesToDuplicate++;
				}
			}
			while(foundANewOne);
			
			ArrayList<String> actualLines = new ArrayList<>();
			String[] butchered = contents.split("\n");
			int currentLineToDuplicate = 0;
			for (int line = 0; line < butchered.length; line++)
			{
				String lineId = iterId(field.getID(), currentLineToDuplicate);
				if (butchered[line].contains(lineId))
				{
					for (int iteration = 0; iteration < parsedMaxIteration; iteration++)
					{
						String tmpLine = butchered[line].replace("**" + field.getID() + "**", "" + iteration);
						tmpLine = tmpLine.replace("\"" + lineId + "\": ", "");
						tmpLine = tmpLine.replace("\\\"", "\"");
						tmpLine = tmpLine.replace("\"\"", "\"");
						tmpLine = tmpLine.substring(0, tmpLine.length() - 1);
						if (iteration != parsedMaxIteration - 1)
						{
							tmpLine = tmpLine + ",";
						}
						actualLines.add(tmpLine);
					}
				}
				else
				{
					actualLines.add(butchered[line]);
				}
			}
			String recombined = "";
			for (int i = 0; i < actualLines.size(); i++)
			{
				recombined = recombined + actualLines.get(i) + "\n";
			}
			contents = recombined.trim();
		}
		else
		{
			if (useMeInsteadOfFieldContentOnNonIterateContent == null)
			{
				contents = contents.replace("**" + field.getID() + "**", field.getCurrentFiller());							
			}
			else
			{
				contents = contents.replace("**" + field.getID() + "**", useMeInsteadOfFieldContentOnNonIterateContent);	
			}
		}
	}

	private String stringContents(JsonObject fileContents) 
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String serial = gson.toJson(fileContents);
		// the rest of this method exists because f*ck you google that's why. No way to
		// change indent
		String prettier = "";
		String[] butchered = serial.split("\n");
		for (int i = 0; i < butchered.length; i++) {
			if (butchered[i].startsWith(" ")) {
				int j = 0;
				while (butchered[i].charAt(j) == ' ') 
				{
					j++;
				}
				// google puts 2 spaces for indent. I want 4 to match most programs auto
				// formating when editing jsons
				// TODO: setting for indent spaces
				char[] extraIndent = new char[j];
				Arrays.fill(extraIndent, ' ');
				butchered[i] = new String(extraIndent) + butchered[i];
			}
			prettier = prettier + (prettier.isEmpty() ? "" : "\n") + butchered[i];
		}
		return prettier.replace("\\u003d", "=");
	}

	public String getFileName() 
	{
		return saveFileName + ".json";
	}

	public String getFileNameWithPath() 
	{
		return saveLocation + "/" + getFileName();
	}

	public String getFolder() 
	{
		return saveLocation;
	}

	public String getContents() 
	{
		return this.contents;
	}
}
