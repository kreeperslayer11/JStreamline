package util;

public class Localization 
{
	public final int RES_LEN = 12;
	public final int MENU_LEN = 11;
	public final int MSGS_LEN = 3;
	public final int BASIC_LEN = 3;
	public final int TOOL_LEN = 9;
	public final int ARMOR_LEN = 7;
	public final int BLOCKS_LEN = 3;
	public final int UI_LEN = 7;
	public final int DEFAULT_LEN = 12;
	public final int JBASIC_LEN = 9;
	public final int JTOOL_LEN = 9;
	public final int JARMOR_LEN = 9;
	public final int JBITEM_LEN = 5;
	public final int JBSTATE_LEN = 7;
	public final int JBLOCK_LEN = 9;
	private String title;
	private String selection;
	private String special;
	private String message;
	private String add;
	private String[] res = new String[RES_LEN];
	private String[] menus = new String[MENU_LEN];
	private String[] msgs = new String[MSGS_LEN];
	private String[] bscItm = new String[BASIC_LEN];
	private String[] tools = new String[TOOL_LEN];
	private String[] armors = new String[ARMOR_LEN];
	private String[] blks = new String[BLOCKS_LEN];
	private String[] uiOpt = new String[UI_LEN];
	private String[] defaults = new String[DEFAULT_LEN];
	private String[] jsonBasic = new String[JBASIC_LEN];
	private String[] jsonTool = new String[JTOOL_LEN];
	private String[] jsonArmor = new String[JARMOR_LEN];
	private String[] jsonBlockItem = new String[JBITEM_LEN];
	private String[] jsonBlockstate = new String[JBSTATE_LEN];
	private String[] jsonBlock = new String[JBLOCK_LEN];
	
	public Localization()
	{
		title = "title.not_found";
		selection = "selection.not_found";
		special = "special.not_found";
		message = "result.not_found";
		add = "add.not_found";
		res[0] = "default.not_found";
		for(int i = 1; i < 11; i++)
		{
			res[i] = "res" + i + ".not_found";
		}
		res[11] = "4k.not_found";
		menus[0] = "file.not_found";
		menus[1] = "reset.not_found";
		menus[2] = "options.not_found";
		menus[3] = "language.not_found";
		menus[4] = "exit.not_found";
		menus[5] = "resultuions.not_found";
		menus[6] = "types.not_found";
		menus[7] = "basic item.not_found";
		menus[8] = "tool set.not_found";
		menus[9] = "armor set.not_found";
		menus[10]= "blocks.not_found";
		
		for(int i = 0; i < MSGS_LEN; i++)
		{
			msgs[i] = "msg-" + i + ".not_found";
		}
		for(int i = 0; i < BASIC_LEN; i++)
		{
			bscItm[i] = "text.not_found";
		}
		for(int i = 0; i < TOOL_LEN; i++)
		{
			tools[i] = "text.not_found";
		}
		for(int i = 0; i < ARMOR_LEN; i++)
		{
			armors[i] = "text.not_found";
		}
		for(int i = 0; i < BLOCKS_LEN; i++)
		{
			blks[i] = "text.not_found";
		}
		for(int i = 0; i < UI_LEN; i++)
		{
			uiOpt[i] = "text.not_found";
		}
		for(int i = 0; i < DEFAULT_LEN; i++)
		{
			defaults[i] = "text.not_found";
		}
		for(int i = 0; i < JBASIC_LEN; i++)
		{
			jsonBasic[i] = "jsonText.not_found";
		}
		for(int i = 0; i < JTOOL_LEN; i++)
		{
			jsonTool[i] = "jsonText.not_found";
		}
		for(int i = 0; i < JARMOR_LEN; i++)
		{
			jsonArmor[i] = "jsonText.not_found";
		}
		for(int i = 0; i < JBITEM_LEN; i++)
		{
			jsonBlockItem[i] = "jsonText.not_found";
		}
		for(int i = 0; i < JBSTATE_LEN; i++)
		{
			jsonBlockstate[i] = "jsonText.not_found";
		}
		for(int i = 0; i < JBLOCK_LEN; i++)
		{
			jsonBlock[i] = "jsonText.not_found";
		}
	}
	
	protected void setTitle(String title)
	{
		this.title = title;
	}
	
	protected void setSelections(String selection)
	{
		this.selection = selection;
	}
	
	protected void setSpecial(String special)
	{
		this.special = special;
	}
	
	protected void setRes(int index, String txt)
	{
		res[index] = txt;
	}
	
	protected void setMenu(int index, String txt)
	{
		menus[index] = txt;
	}
	
	protected void setMsgs(int index, String txt)
	{
		msgs[index] = txt;
	}
	
	protected void setBscItm(int index, String txt)
	{
		bscItm[index] = txt;
	}
	
	protected void setTools(int index, String txt)
	{
		tools[index] = txt;
	}
	
	protected void setArmors(int index, String txt)
	{
		armors[index] = txt;
	}
	
	protected void setBlks(int index, String txt)
	{
		blks[index] = txt;
	}
	
	protected void setUIopt(int index, String txt)
	{
		uiOpt[index] = txt;
	}
	
	protected void setDefaults(int index, String txt)
	{
		defaults[index] = txt;
	}
	
	protected void setJBasic(int index, String txt)
	{
		jsonBasic[index] = txt;
	}

	protected void setJTools(int index, String txt)
	{
		jsonTool[index] = txt;
	}
			
	protected void setJArmor(int index, String txt)
	{
		jsonArmor[index] = txt;
	}
	
	protected void setJBItem(int index, String txt)
	{
		jsonBlockItem[index] = txt;
	}
	
	protected void setJBstate(int index, String txt)
	{
		jsonBlockstate[index] = txt;
	}
	
	protected void setJBlock(int index, String txt)
	{
		jsonBlock[index] = txt;
	}
	
	protected void setMessage(String message)
	{
		this.message = message;
	}
	
	protected void setAdd(String add)
	{
		this.add = add;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getSelection()
	{
		return selection;
	}
	
	public String getSpecial()
	{
		return special;
	}
	
	public String getRes(int index)
	{
		return this.res[index];
	}
	
	public int getResLength()
	{
		return res.length;
	}
	
	public String getMenus(int index)
	{
		return this.menus[index];
	}
	
	public String getMsgs(int index)
	{
		return this.msgs[index];
	}
	
	public String getBscItm(int index)
	{
		return this.bscItm[index];
	}
	
	public String getTools(int index)
	{
		return this.tools[index];
	}
	
	public String getArmors(int index)
	{
		return this.armors[index];
	}
	
	public String getBlks(int index)
	{
		return this.blks[index];
	}
	
	public String getUIopt(int index)
	{
		return this.uiOpt[index];
	}
	
	public String getDefaults(int index)
	{
		return this.defaults[index];
	}
	
	public String getJBasic(int index)
	{
		return this.jsonBasic[index];
	}

	public String getJTools(int index)
	{
		return this.jsonTool[index];
	}
			
	public String getJArmor(int index)
	{
		return this.jsonArmor[index];
	}
	
	public String getJBItem(int index)
	{
		return this.jsonBlockItem[index];
	}
	
	public String getJBstate(int index)
	{
		return this.jsonBlockstate[index];
	}
	
	public String getJBlock(int index)
	{
		return this.jsonBlock[index];
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public String getAdd()
	{
		return add;
	}
}
