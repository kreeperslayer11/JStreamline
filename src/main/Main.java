package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;

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
		String path = ".";
		String reso = null;
		try 
		{
			//read = new FileReader(this.getClass().getClassLoader().getResource(Reference.DATA_FILE).getPath().replace("%20", " "));//new FileReader(Reference.DATA_FILE);
			//read = new FileReader(Reference.DATA_FILE);
			File file = new File(Reference.DATA_FILE);
			Scanner scan = new Scanner(file);
			
			if(scan.hasNext())
			{
				path = scan.next();
			}
			if(scan.hasNext())
			{
				reso = scan.next();
			}
			
			scan.close();
			
		} 
		catch (FileNotFoundException e) 
		{
			//path = this.getClass().getClassLoader().getResource(Reference.DATA_FILE).getPath().replace("%20", " ");
			//path = new File(Reference.DATA_FILE).getAbsolutePath();
			e.printStackTrace();
		}
		
		if(path.length() == 0)
		{
			path = ".";
		}
		
		//ImageIcon img = new ImageIcon(this.getClass().getClassLoader().getResource(Reference.ICON_FILE).getFile().replace("%20", " "));
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
