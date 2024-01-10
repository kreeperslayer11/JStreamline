package main;

import javax.swing.*;

import templates.json.savedata.Save;
import util.Localization;
import util.Parser;
import util.Reference;
import util.Resolution;

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
		
		Localization local = new Localization();
		Parser parser = new Parser(local);
		Resolution res = new Resolution();
		parser.Localize();
		
		JFrame frame = new JFrame(local.getTitle());
		frame.setIconImage(img.getImage());
		
		JPanel contentPane = (JPanel)frame.getContentPane();
		
		Window window = new Window(frame, contentPane, res, local, path, reso);
		
		window.makeMenus();
		window.makeContent("");
		frame.setVisible(true);
	}
}
