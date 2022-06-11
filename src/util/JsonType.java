package util;

public enum JsonType 
{
	BASIC("item/generated"),
	TOOL_SET("item/handheld"),
	ARMOR_SET("item/generated"),
	BLOCK_ITEM("block/"),
	BLOCK_STATE(""),
	BLOCK("block/cube_all");
	
	private final String parent;
	
	private JsonType(String parent)
	{
		this.parent = parent;
	}
	
	public String getParent()
	{
		return this.parent;
	}
}
