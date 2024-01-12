package templates.json.savedata.settings;

import com.google.gson.JsonObject;

import templates.json.AbstractJsonResource;
import util.FileHandler;
import util.Reference;
import util.json.parser.JsonHandler;

public class Save extends AbstractJsonResource
{
	String ExportPath;
	String Resolution;
	String Language;
	boolean Overwrite;
	boolean GenerateFolders;
	
	public static Save save = new Save();
	
	private Save()
	{
		super();
	}
	
	@Override
	protected void load()
	{
		JsonObject data = FileHandler.readInResource(Reference.DATA_FILE);
		
		ExportPath = JsonHandler.readFromElement(data.get("ExportPath"));
		Resolution = JsonHandler.readFromElement(data.get("Resolution"));
		Language = JsonHandler.readFromElement(data.get("Language"));
		Overwrite = JsonHandler.readBoolFromElement(data.get("Overwrite"));
		GenerateFolders = JsonHandler.readBoolFromElement(data.get("GenerateFolders"));
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
	
	public boolean updateLang(String newLang)
	{
		this.Language = newLang;
		return write();
	}
	
	public boolean updateOverwrite(boolean newSetting)
	{
		this.Overwrite = newSetting;
		return write();
	}
	
	public boolean updateGenerate(boolean newSetting)
	{
		this.GenerateFolders = newSetting;
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
	
	public String Language()
	{
		return Language;
	}
	
	public boolean Overwrite()
	{
		return Overwrite;
	}
	
	public boolean GenerateFolders()
	{
		return GenerateFolders;
	}
}
