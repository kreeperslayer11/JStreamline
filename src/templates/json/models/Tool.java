package templates.json.models;

import util.json.generator.JsonType;

public class Tool extends Model
{
	public Tool(String modid, String texture, String option)
	{
		super(JsonType.TOOL_SET, "layer0", modid + ":items/" + texture + option);
	}
}
