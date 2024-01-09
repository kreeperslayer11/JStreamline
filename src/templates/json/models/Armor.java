package templates.json.models;

import templates.inner.BasicItemTextures;
import util.JsonType;

public class Armor extends Model
{

	public Armor(String modid, String texture, String option)
	{
		super(JsonType.ARMOR_SET, new BasicItemTextures(modid + ":items/" + texture + option));
	}

}
