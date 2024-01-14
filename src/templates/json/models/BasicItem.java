package templates.json.models;

import util.json.generator.JsonType;

public class BasicItem extends Model
{
	public BasicItem(String modid, String texture)
	{
		super(JsonType.BASIC, "layer0", modid + ":items/" + texture);
	}
	
	public BasicItem(String modid, String texture, String folder)
	{
		super(JsonType.BASIC, "layer0", modid + ":" + folder + "/" + texture);
	}
}
