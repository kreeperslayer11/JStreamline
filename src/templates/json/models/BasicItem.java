package templates.json.models;

import templates.inner.BasicItemTextures;
import util.JsonType;

public class BasicItem extends Model
{
	public BasicItem(String modid, String texture)
	{
		super(JsonType.BASIC, new BasicItemTextures(modid + ":items/" + texture));
	}
}
