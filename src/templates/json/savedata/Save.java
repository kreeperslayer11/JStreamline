package templates.json.savedata;

import java.io.File;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import templates.json.JsonSerializable;
import util.FileHandler;
import util.Reference;

public class Save extends JsonSerializable
{
	String ExportPath;
	String Resolution;
	
	public static Save save = new Save();
	
	private Save()
	{
		load();
	}
	
	private JsonObject readIn()
	{
		File file = new File(Reference.DATA_FILE);
		if (!FileHandler.doesFileSystemItemExist(Reference.RESOURCES_FOLD))
		{
			FileHandler.directoryExists(Reference.RESOURCES_FOLD);
		}
		FileHandler.createFile(file);
		String content = FileHandler.readFileExists(file);
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
	
	private String readFromElement(JsonElement e)
	{
		if (e != null)
		{
			String s = e.getAsString();
			if (s != null)
			{
				return s;
			}
		}
		return "";
	}
	
	private void load()
	{
		JsonObject data = readIn();
		
		ExportPath = readFromElement(data.get("ExportPath"));
		Resolution = readFromElement(data.get("Resolution"));
	}
	
	private boolean write()
	{
		String newContent = serialize();
		File file = new File(Reference.DATA_FILE);
		return FileHandler.writeFileExists(file, newContent);
	}
	
	public boolean updatePath(String newPath)
	{
		this.ExportPath = newPath;
		return write();
	}
	
	public boolean updateRes(String newRes)
	{
		this.Resolution = newRes;
		return write();
	}
	
	public String ExportPath()
	{
		return ExportPath;
	}
	
	public String Resolution()
	{
		return Resolution;
	}
}
