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
	String SelectModeClick;
	String SelectModeTab;
	boolean Overwrite;
	boolean GenerateFolders;
	String[] orderKnownTemplates;
	
	public static Save save = new Save();
	
	private Save()
	{
		super();
	}
	
	@Override
	protected void load()
	{
		JsonObject data = FileHandler.readInResource(Reference.DATA_FILE);
		
		if (data.get("Language") == null)
		{
			data.addProperty("Language", "english");
		}
		
		ExportPath = JsonHandler.readFromElement(data.get("ExportPath"));
		Resolution = JsonHandler.readFromElement(data.get("Resolution"));
		Language = JsonHandler.readFromElement(data.get("Language"));
		SelectModeClick = JsonHandler.readFromElement(data.get("SelectModeClick"));
		SelectModeTab = JsonHandler.readFromElement(data.get("SelectModeTab"));
		Overwrite = JsonHandler.readBoolFromElement(data.get("Overwrite"));
		GenerateFolders = JsonHandler.readBoolFromElement(data.get("GenerateFolders"));
		orderKnownTemplates = JsonHandler.readStringArrayFromElement(data.get("orderKnownTemplates"));
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
	
	public boolean updateSelectModeClick(String newMode)
	{
		this.SelectModeClick = newMode;
		return write();
	}
	
	public boolean updateSelectModeTab(String newMode)
	{
		this.SelectModeTab = newMode;
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
	
	public String SelectModeClick()
	{
		return SelectModeClick;
	}
	
	public String SelectModeTab()
	{
		return SelectModeTab;
	}
	
	public boolean Overwrite()
	{
		return Overwrite;
	}
	
	public boolean GenerateFolders()
	{
		return GenerateFolders;
	}
	
	public String[] getOrderedOptionFiles()
	{
		return orderKnownTemplates;
	}
}
