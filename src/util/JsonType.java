package util;

public enum JsonType 
{
	BASIC("item/generated", Reference.ITEM_FOLD),
	TOOL_SET("item/handheld", Reference.ITEM_FOLD),
	ARMOR_SET("item/generated", Reference.ITEM_FOLD),
	BLOCK_ITEM("block/", Reference.ITEM_FOLD),
	BLOCK_STATE("", Reference.B_STATE_FOLD),
	BLOCK("block/cube_all", Reference.BLCK_FOLD);
	
	public final String parent;
	public final String folder;
	
	private JsonType(String parent, String folder)
	{
		this.parent = parent;
		this.folder = folder;
	}
}
