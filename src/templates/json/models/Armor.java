package templates.json.models;

import util.json.generator.JsonType;

public class Armor extends Model
{

	public Armor(String modid, String texture, String option)
	{
		super(JsonType.ARMOR_SET, "layer0", modid + ":items/" + texture + option);
	}

}
