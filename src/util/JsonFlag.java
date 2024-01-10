package util;

import java.util.ArrayList;
import java.util.Arrays;

public enum JsonFlag
{
	Empty(new JsonType[] {}), 
	BasicItem(new JsonType[] { JsonType.BASIC }), 
	Tool(new JsonType[] { JsonType.TOOL_SET }), 
	Armor(new JsonType[] { JsonType.ARMOR_SET }), 
	Block(new JsonType[] { JsonType.BLOCK, JsonType.BLOCK_ITEM, JsonType.BLOCK_STATE});
	
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
