package util.json.parser;

import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonHandler
{
	public static String readFromElement(JsonElement e)
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
	
	public static void readFromElement(JsonElement e, Map<String, String> map)
	{
		if (e != null)
		{
			JsonObject o = e.getAsJsonObject();
			if (o != null)
			{
				Set<String> keys = o.keySet();
				for (String key : keys)
				{
					map.put(key, o.get(key).getAsString());
				}
			}
		}
	}
}
