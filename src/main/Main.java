package main;

import java.util.Random;

import javax.swing.*;

import templates.json.datareading.AutoGen;
import templates.json.savedata.lang.Lang;
import templates.json.savedata.settings.Save;
import util.Reference;
import util.Resolution;
import util.reference.LangMenuRef;
import util.reference.LangTitlesRef;

public class Main 
{
	public static Random rand;
	
	public static void main(String[] args)
	{
		rand = new Random(System.currentTimeMillis());
		Main gui = new Main();
		gui.start();
	}
	
	public void start()
	{
		String path = Save.save.ExportPath();
		String reso = Save.save.Resolution();
		String mclk = Save.save.SelectModeClick();
		String mtab = Save.save.SelectModeTab();
		AutoGen.gen.Import();
		
		if(path.length() == 0)
		{
			path = ".";
		}
		
		if(mclk.isEmpty())
		{
			mclk = LangMenuRef.MODE_HIGHLIGHT;
		}
		
		if(mtab.isEmpty())
		{
			mtab = LangMenuRef.MODE_HIGHLIGHT;
		}
		
		ImageIcon img = new ImageIcon(Reference.ICON_FILE);
		
		Resolution res = new Resolution();
		
		JFrame frame = new JFrame(Lang.lang.getTitle(LangTitlesRef.APP_TITLE));
		frame.setIconImage(img.getImage());
		
		JPanel contentPane = (JPanel)frame.getContentPane();
		
		Window window = new Window(frame, contentPane, res, path, reso, mclk, mtab);
		
		window.makeMenus();
		window.makeContent("");
		frame.setVisible(true);
	}
}
