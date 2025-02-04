package util.json.parser;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
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
	
	public static boolean readBoolFromElement(JsonElement e)
	{
		if (e != null)
		{
			return e.getAsBoolean();
		}
		return false;
	}
	
	public static void readFromElement(JsonElement e, Map<String, String> map)
	{
		if (e != null)
		{
			JsonObject o = e.getAsJsonObject();
			if (o != null)
			{
				Set<Entry<String, JsonElement>> entries = o.entrySet();
				for (Entry<String, JsonElement> entry : entries)
				{
					map.put(entry.getKey(), entry.getValue().getAsString());
				}
			}
		}
	}
	
	public static JsonObject[] readObjArrayFromElement(JsonElement e)
	{
		if (e != null)
		{
			JsonArray a = e.getAsJsonArray();
			JsonObject[] tmp = new JsonObject[a.size()];
			for (int i = 0; i < tmp.length; i++)
			{
				tmp[i] = a.get(i).getAsJsonObject();
			}
			return tmp;
		}
		return new JsonObject[0];
	}
	
	public static String[] readStringArrayFromElement(JsonElement e)
	{
		if (e != null)
		{
			JsonArray a = e.getAsJsonArray();
			String[] tmp = new String[a.size()];
			for (int i = 0; i < tmp.length; i++)
			{
				tmp[i] = a.get(i).getAsString();
			}
			return tmp;
		}
		return new String[0];
	}
}
