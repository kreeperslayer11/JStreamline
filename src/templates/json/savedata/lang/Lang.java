package templates.json.savedata.lang;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

import templates.json.JsonSerializable;
import util.FileHandler;
import util.Reference;
import util.ResVals;
import util.json.parser.JsonHandler;

public class Lang extends JsonSerializable
{
	public static Lang lang = new Lang();
	
	Map<String, String> Titles = new HashMap<>();
	Map<String, String> Menus = new HashMap<>();
	Map<String, String> Resolutions = new HashMap<>();
	Map<String, String> Messages = new HashMap<>();
	Map<String, String> UILabels = new HashMap<>();
	Map<String, String> UIButtons = new HashMap<>();
	Map<String, String> DefaultValues = new HashMap<>();
	
	private Lang()
	{
		load();
	}
	
	private void load()
	{
		JsonObject data = FileHandler.readInResource(Reference.LANG_FILE);
		
		JsonHandler.readFromElement(data.get("Titles"), Titles);
		JsonHandler.readFromElement(data.get("Menus"), Menus);
		JsonHandler.readFromElement(data.get("Resolutions"), Resolutions);
		JsonHandler.readFromElement(data.get("Messages"), Messages);
		JsonHandler.readFromElement(data.get("UILabels"), UILabels);
		JsonHandler.readFromElement(data.get("UIButtons"), UIButtons);
		JsonHandler.readFromElement(data.get("DefaultValues"), DefaultValues);
		for (int i = 0; i < ResVals.values().length; i++)
		{
			ResVals res = ResVals.values()[i];
			if (res != ResVals.DEFAULT)
			{
				String resolution = res.getWidth() + "x" + res.getHeight();
				Resolutions.put(resolution, resolution);
			}
		}
	}
	
	private String getFromMap(String key, Map<String, String> map)
	{
		if (map.containsKey(key))
		{
			return map.get(key);
		}
		return "Not Found" + key;
	}
	
	public String getTitle(String title)
	{
		return getFromMap(title, Titles);
	}
	
	public String getMenu(String menu)
	{
		return getFromMap(menu, Menus);
	}
	
	public String getRes(String res)
	{
		return getFromMap(res, Resolutions);
	}
	
	public String getMessage(String message)
	{
		return getFromMap(message, Messages);
	}
	
	public String getUILabel(String label)
	{
		return getFromMap(label, UILabels);
	}
	
	public String getUIButton(String button)
	{
		return getFromMap(button, UIButtons);
	}
	
	public String getDefaultText(String text)
	{
		return getFromMap(text, DefaultValues);
	}
}
