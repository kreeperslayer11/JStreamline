package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser 
{
	private Localization localization;
	
	public Parser(Localization localization)
	{
		this.localization = localization;
	}
	
	/**
	 * read the language file into localization passed at creation
	 */
	public void Localize()
	{
		File file = new File(Reference.LANG_FILE);
		//File file = new File(this.getClass().getClassLoader().getResource(Reference.LANG_FILE).getFile().replace("%20", " "));//new File(Reference.LANG_FILE);
		
		try
		{
			Scanner scan = new Scanner(file);
			ArrayList<String> words = new ArrayList<>();
			while(scan.hasNextLine())
			{
				String word = scan.nextLine();
				if(word.substring(0, 1).equals("#"))
				{
					
				}
				else
				{
					words.add(word);
				}
				
			}
			scan.close();
			int i = 0;
			localization.setTitle(words.get(i++));
			localization.setSelections(words.get(i++));
			localization.setSpecial(words.get(i++));
			localization.setMessage(words.get(i++));
			localization.setAdd(words.get(i++));
			
			for(int j = 0; j < localization.MENU_LEN; j++)
			{
				localization.setMenu(j, words.get(i++));
			}
			for(int j = 0; j < localization.RES_LEN; j++)
			{
				localization.setRes(j, words.get(i++));
			}
			for(int j = 0; j < localization.MSGS_LEN; j++)
			{
				localization.setMsgs(j, words.get(i++));
			}
			for(int j = 0; j < localization.BASIC_LEN; j++)
			{
				localization.setBscItm(j, words.get(i++));
			}
			for(int j = 0; j < localization.TOOL_LEN; j++)
			{
				localization.setTools(j, words.get(i++));
			}
			for(int j = 0; j < localization.ARMOR_LEN; j++)
			{
				localization.setArmors(j, words.get(i++));
			}
			for(int j = 0; j < localization.BLOCKS_LEN; j++)
			{
				localization.setBlks(j, words.get(i++));
			}
			for(int j = 0; j < localization.UI_LEN; j++)
			{
				localization.setUIopt(j, words.get(i++));
			}
			for(int j = 0; j < localization.DEFAULT_LEN; j++)
			{
				localization.setDefaults(j, words.get(i++));
			}
			for(int j = 0; j < localization.JBASIC_LEN; j++)
			{
				localization.setJBasic(j, words.get(i++));
			}
			for(int j = 0; j < localization.JTOOL_LEN; j++)
			{
				localization.setJTools(j, words.get(i++));
			}
			for(int j = 0; j < localization.JARMOR_LEN; j++)
			{
				localization.setJArmor(j, words.get(i++));
			}
			for(int j = 0; j < localization.JBITEM_LEN; j++)
			{
				localization.setJBItem(j, words.get(i++));
			}
			for(int j = 0; j < localization.JBSTATE_LEN; j++)
			{
				localization.setJBstate(j, words.get(i++));
			}
			for(int j = 0; j < localization.JBLOCK_LEN; j++)
			{
				localization.setJBlock(j, words.get(i++));
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
	}
}
