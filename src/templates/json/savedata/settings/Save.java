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
