package templates.json.models;

import templates.inner.BasicItemTextures;
import util.JsonType;

public class Tool extends Model
{
	public Tool(String modid, String texture, String option)
	{
		super(JsonType.TOOL_SET, new BasicItemTextures(modid + ":items/" + texture + option));
	}
}
