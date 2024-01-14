package util.json.generator;

import java.util.ArrayList;
import java.util.Arrays;

public enum JsonFlag
{
	Empty(new JsonType[] {}), 
	BasicItem(new JsonType[] { JsonType.BASIC }), 
	Tool(new JsonType[] { JsonType.TOOL_SET }), 
	Armor(new JsonType[] { JsonType.ARMOR_SET }), 
	Block(new JsonType[] { JsonType.BLOCK, JsonType.BLOCK_ITEM, JsonType.BLOCK_STATE}),
	Flame(new JsonType[] { JsonType.FLAME_STATE, JsonType.BLOCK_ITEM, JsonType.FLAME }),
	//TODO: block state and item for below
	Pane(new JsonType[] { JsonType.PANE_BLOCK, JsonType.PANE, JsonType.PANE_STATE }),
	Portal(new JsonType[] { JsonType.PORTAL_BLOCK, JsonType.BLOCK_ITEM,  JsonType.PORTAL_STATE }),
	Stairs(new JsonType[] { JsonType.STAIRS_BLOCK, JsonType.STAIRS_INNER_BLOCK, JsonType.STAIRS_OUTER_BLOCK, JsonType.BLOCK_ITEM, JsonType.STAIRS_STATE });
	
	private ArrayList<JsonType> type;
	
	private JsonFlag(JsonType[] type)
	{
		this.type = new ArrayList<JsonType>();
		this.type.addAll(Arrays.asList(type));
	}
	
	public ArrayList<JsonType> getType()
	{
		ArrayList<JsonType> tmp = new ArrayList<JsonType>();
		tmp.addAll(type);
		return tmp;
	}
}
