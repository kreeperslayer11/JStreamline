package templates.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class JsonSerializable
{
	public String serialize()
	{
		Gson gson= new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
