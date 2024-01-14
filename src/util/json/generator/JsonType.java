package util.json.generator;

import util.Reference;

public enum JsonType 
{
	BASIC("item/generated", Reference.ITEM_FOLD),
	
	TOOL_SET("item/handheld", Reference.ITEM_FOLD),
	
	ARMOR_SET("item/generated", Reference.ITEM_FOLD),
	
	BLOCK_ITEM("block/", Reference.ITEM_FOLD),
	BLOCK_STATE("", Reference.B_STATE_FOLD),
	BLOCK("block/cube_all", Reference.BLCK_FOLD),
	
	FLAME_STATE("", Reference.B_STATE_FOLD),
	FLAME("block/", Reference.BLCK_FOLD),
	
	PORTAL_BLOCK("", Reference.BLCK_FOLD),
	PORTAL_STATE("", Reference.B_STATE_FOLD),
	
	PANE_BLOCK("block/pane", Reference.BLCK_FOLD),
	PANE_STATE("", Reference.B_STATE_FOLD),
	PANE("item/generated", Reference.ITEM_FOLD),
	
	STAIRS_BLOCK("block/stairs", Reference.BLCK_FOLD),
	STAIRS_INNER_BLOCK("block/inner_stairs", Reference.BLCK_FOLD),
	STAIRS_OUTER_BLOCK("block/outer_stairs", Reference.BLCK_FOLD),
	STAIRS_STATE("stairs", Reference.B_STATE_FOLD);
	
	public final String parent;
	public final String folder;
	
	private JsonType(String parent, String folder)
	{
		this.parent = parent;
		this.folder = folder;
	}
}
