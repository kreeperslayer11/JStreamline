package main;

import javax.swing.*;

import templates.json.savedata.lang.Lang;
import templates.json.savedata.settings.Save;
import util.Reference;
import util.Resolution;
import util.reference.LangTitlesRef;

public class Main 
{
	public static void main(String[] args)
	{
		Main gui = new Main();
		gui.start();
	}
	
	public void start()
	{
		String path = Save.save.ExportPath();
		String reso = Save.save.Resolution();
		
		if(path.length() == 0)
		{
			path = ".";
		}
		
		ImageIcon img = new ImageIcon(Reference.ICON_FILE);
		
		Resolution res = new Resolution();
		
		JFrame frame = new JFrame(Lang.lang.getTitle(LangTitlesRef.APP_TITLE));
		frame.setIconImage(img.getImage());
		
		JPanel contentPane = (JPanel)frame.getContentPane();
		
		Window window = new Window(frame, contentPane, res, path, reso);
		
		window.makeMenus();
		window.makeContent("");
		frame.setVisible(true);
	}
}
