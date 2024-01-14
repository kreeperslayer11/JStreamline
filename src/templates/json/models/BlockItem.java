package templates.json.models;

import templates.json.JsonSerializable;
import util.json.generator.JsonType;

public class BlockItem extends JsonSerializable
{
	String parent;
	
	public BlockItem(String modid, String texture, String postfix)
	{
		this.parent = modid + ":" + JsonType.BLOCK_ITEM.parent + texture + postfix;
	}
}
