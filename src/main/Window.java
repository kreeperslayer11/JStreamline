package main;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.*;

import util.JsonType;
import util.Localization;
import util.Reference;
import util.Resolution;

public class Window 
{
	private int flag;
	private JFrame frame;
	private JPanel contentPane;
	private Resolution res;
	private Localization local;
	private JPanel south, east, west, center;
	//private ArrayList<JsonPreview> jsons;
	private Map<Component, JsonPreview> jsons;
	private JTabbedPane tabs;
	private int currentIndex = -1;
	private String path, message, mod_id, pref, name;
	
	public Window(JFrame frame, JPanel contentPane, Resolution res, Localization local, String path, String reso)
	{
		this.flag = -1;
		this.frame = frame;
		this.contentPane = contentPane;
		this.res = res;
		this.local = local;
		this.path = path;
		this.message = "";
		this.mod_id = local.getDefaults(0);
		this.pref = local.getDefaults(1);
		this.name = local.getDefaults(2);
		
		contentPane.setLayout(new BorderLayout(res.getBorder(), res.getBorder()));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(reso == null)
		{
			frame.setSize(res.getWidth(), res.getHeight());
		}
		else
		{
			String[] s = reso.split("x");
			frame.setSize(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
		}
		
		frame.setVisible(true);
		frame.setResizable(false);
		//contentPane.setPreferredSize(new Dimension(res.getWidth(), res.getHeight()));
		this.jsons = new HashMap<>();
		tabs = new JTabbedPane(JTabbedPane.TOP);
	}
	
	public void makeMenus()
	{
		JMenuBar menuBar;
    	JMenu menu;
    	JMenu pre;
    	JMenu sub;
    	JMenuItem menuItem;
    	
    	menuBar = new JMenuBar();
    	frame.setJMenuBar(menuBar);
    	
    	//FILE=================================================================
    	menu = new JMenu(local.getMenus(0));
    	fontChange(menu);
    	menu.setMnemonic(KeyEvent.VK_F);
    	menuBar.add(menu);
    	//FILE SUB MENU=========================================================
    	//reset
    	menuItem = new JMenuItem(local.getMenus(1));
    	fontChange(menuItem);
    	menuItem.addActionListener( makeResetListener() );
    	menu.add(menuItem);
    	
    	//options
    	pre = new JMenu(local.getMenus(2));
    	fontChange(pre);
    	
    	//resolution
    	sub = new JMenu(local.getMenus(5));
    	fontChange(sub);
    	
    	sub.add(menuItem = new JMenuItem(local.getRes(0)));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeResolutionListener(0));
    	
    	sub.add(menuItem = new JMenuItem(local.getRes(1)));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeResolutionListener(1));
    	
    	sub.add(menuItem = new JMenuItem(local.getRes(2)));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeResolutionListener(2));
    	
    	sub.add(menuItem = new JMenuItem(local.getRes(3)));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeResolutionListener(3));
    	
    	sub.add(menuItem = new JMenuItem(local.getRes(4)));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeResolutionListener(4));
    	
    	sub.add(menuItem = new JMenuItem(local.getRes(5)));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeResolutionListener(5));
    	
    	sub.add(menuItem = new JMenuItem(local.getRes(6)));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeResolutionListener(6));
    	
    	sub.add(menuItem = new JMenuItem(local.getRes(7)));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeResolutionListener(7));
    	
    	sub.add(menuItem = new JMenuItem(local.getRes(8)));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeResolutionListener(8));
    	
    	sub.add(menuItem = new JMenuItem(local.getRes(9)));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeResolutionListener(9));
    	
    	sub.add(menuItem = new JMenuItem(local.getRes(10)));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeResolutionListener(10));
    	
    	sub.add(menuItem = new JMenuItem(local.getRes(11)));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeResolutionListener(11));
    	
    	pre.add(sub);
    	
    	//language
    	menuItem = new JMenuItem(local.getMenus(3));
    	fontChange(menuItem);
    	pre.add(menuItem);
    	
    	menu.add(pre);
    	
    	menuItem = new JMenuItem(local.getMenus(4));
    	fontChange(menuItem);
    	//menuItem.addActionListener(new saveListener());
    	menu.add(menuItem);
    	//END FILE==============================================================
    	//TYPES=================================================================
    	menu = new JMenu(local.getMenus(6));
    	fontChange(menu);
    	menu.setMnemonic(KeyEvent.VK_T);
    	menuBar.add(menu);
    	//TYPES SUB MENU========================================================
    	//basic
    	menuItem = new JMenuItem(local.getMenus(7));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeTypeListener(0));
    	menu.add(menuItem);
    	
    	//tool
    	menuItem = new JMenuItem(local.getMenus(8));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeTypeListener(1));
    	menu.add(menuItem);
    	
    	//armor
    	menuItem = new JMenuItem(local.getMenus(9));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeTypeListener(2));
    	menu.add(menuItem);
    	
    	//blocks
    	menuItem = new JMenuItem(local.getMenus(10));
    	fontChange(menuItem);
    	menuItem.addActionListener(makeTypeListener(3));
    	menu.add(menuItem);
	}
	
	private ActionListener makeResolutionListener(int num)
	{
		return new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			res.resolutionChange(num);
    			killContent();
    			frame.setSize(res.getWidth(), res.getHeight());
    			File file = new File(Reference.DATA_FILE);
    			Scanner scan;
				try {
					scan = new Scanner(file);
					String line = ".";
					String contents = "";
					if(scan.hasNext())
					{
						line = scan.next();
					}
					if(scan.hasNext())
					{
						scan.next();
					}
					while(scan.hasNext())
					{
						contents = "\n" + scan.next();
					}
					scan.close();
					
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(line);
					bw.write("\n" + res.getWidth() + "x" + res.getHeight());
					bw.write(contents);
					bw.close();
					
				} 
				catch (FileNotFoundException e1) 
				{
					e1.printStackTrace();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
    			
    			//makeMenus();
    			//makeContent();
    			refreshContent("");
    		}
    	};
	}
	
	private ActionListener makeTypeListener(int num)
	{
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag = num;
				west = makeWestRegion();
				remakeContent(2);
				refreshContent("");
			}
		};
	}
	
	private ActionListener makeResetListener()
	{
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//jsons = new ArrayList<JsonPreview>();
				jsons = new HashMap<>();
				tabs = new JTabbedPane(JTabbedPane.TOP);
				flag = -1;
				currentIndex = -1;
				refreshContent("");
			}
		};
	}
	
	public void killContent()
	{
		currentIndex = tabs.getSelectedIndex();
		contentPane.removeAll();
	}
	
	public void makeContent(String message)
	{ 	
		this.message = message;
		makeNorthRegion();
    	west = makeWestRegion();
    	center = makeCenterRegion();
    	east = makeEastRegion();
    	south = makeSouthRegion();
	}
	
	public void refreshContent(String message)
	{
		killContent();
		makeMenus();
		makeContent(message);
		updateUI();
	}
	
	/**
	 * remake all conent except for id at excpet
	 * @param except 1 : Center |
	 *               2 : West   |
	 *               3 : East   |
	 *               4 : South  |
	 */
	public void remakeContent(int except)
	{
		if(except == 1)
		{
			killContent();
			makeMenus();
			contentPane.add(center, BorderLayout.CENTER);
			makeNorthRegion();
	    	west = makeWestRegion();
	    	east = makeEastRegion();
	    	south = makeSouthRegion();
		}
		else if(except == 2)
		{
			killContent();
			makeMenus();
			contentPane.add(west, BorderLayout.WEST);
			makeNorthRegion();
	    	center = makeCenterRegion();
	    	east = makeEastRegion();
	    	south = makeSouthRegion();
		}
		else if(except == 3)
		{
			killContent();
			makeMenus();
			contentPane.add(east, BorderLayout.EAST);
			makeNorthRegion();
	    	west = makeWestRegion();
	    	center = makeCenterRegion();
	    	south = makeSouthRegion();
		}
		else if(except == 4)
		{
			killContent();
			makeMenus();
			contentPane.add(south, BorderLayout.SOUTH);
			makeNorthRegion();
	    	west = makeWestRegion();
	    	center = makeCenterRegion();
	    	east = makeEastRegion();
		}
	}
	
	public void updateUI()
	{
		west.updateUI();
		center.updateUI();
		east.updateUI();
		south.updateUI();
	}
	
	private void makeNorthRegion()
	{
		JLabel label = new JLabel();
		contentPane.add(label, BorderLayout.NORTH);
	}
	
	private void MakeTextBox(JPanel panel, String title, String text)
	{
		JLabel ider = new JLabel(title);
		//ider.setBorder(BorderFactory.createEmptyBorder(0, res.getBorder() + 2, 0, 0));
		panel.add(ider);
		
		JTextField label = new JTextField();
		fontChange(label);
		label.setText(text);
		label.setMaximumSize(new Dimension(res.getWidth() / 2, res.getFontSize() * 2));
		panel.add(label);
	}
	
	private JPanel makeWestRegion()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(local.getSelection()));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		fontChange(panel);
		
		JLabel sizer = new JLabel("");
		
		
		//NO SELECTION
		if(flag == -1)
		{
			sizer.setBorder(BorderFactory.createEmptyBorder(0, res.getWidth() / 4, 0, 0));
		    panel.add(sizer);
		}
		//BASIC ITEM
		else if(flag == 0)
		{
			
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 40, 0, 0));
		    panel.add(sizer);
			
		    //component 2
		    MakeTextBox(panel, local.getBscItm(2), name);
		    //component 4
		    MakeTextBox(panel, local.getBscItm(1), pref);
		    //component 6
		    MakeTextBox(panel, local.getBscItm(0), mod_id);
		    
		    //submit
		    JButton jbutton = new JButton(local.getUIopt(5));
	    	fontChange(jbutton);
	    	jbutton.setFocusable(false);
		    
		    jbutton.addActionListener( makeSubmitListener(panel, flag) );
		    
		    panel.add(jbutton);
		}
		//Tool Sets
		else if(flag == 1)
		{
			//JScrollPane scroll = new JScrollPane();
			//JPanel p = new JPanel();
			//p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
			//fontChange(p);
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 47, 0, 0));
		    panel.add(sizer);
		    
		    //component 2
		    MakeTextBox(panel, local.getTools(2), name);
		    //component 4
		    MakeTextBox(panel, local.getTools(1), pref);
		    //component 6
		    MakeTextBox(panel, local.getTools(0), mod_id);
		    //postfixes
		    JLabel ider = new JLabel(local.getTools(3));
			//ider.setBorder(BorderFactory.createEmptyBorder(0, res.getBorder() + 2, 0, 0));
		    panel.add(ider);
		    //component 9 sword
			MakeTextBox(panel, local.getTools(4), local.getDefaults(3));
		    //component 11 axe
			MakeTextBox(panel, local.getTools(5), local.getDefaults(4));
		    //component 13 shovel
			MakeTextBox(panel, local.getTools(6), local.getDefaults(5));
		    //component 15 pickaxe
			MakeTextBox(panel, local.getTools(7), local.getDefaults(6));
		    //component 17 hoe
			MakeTextBox(panel, local.getTools(8), local.getDefaults(7));
		    
			//submit
		    JButton jbutton = new JButton(local.getUIopt(5));
	    	fontChange(jbutton);
	    	jbutton.setFocusable(false);
		    
		    jbutton.addActionListener( makeSubmitListener(panel, flag) );
		    
		    panel.add(jbutton);
		    
		    //scroll.add(p);
		    
		    //panel.add(scroll);
		}
		//armor sets
		else if(flag == 2)
		{
			//JScrollPane scroll = new JScrollPane();
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 47, 0, 0));
		    panel.add(sizer);
		    
		    //component 2
		    MakeTextBox(panel, local.getArmors(2), name);
		    //component 4
		    MakeTextBox(panel, local.getArmors(1), pref);
		    //component 6
		    MakeTextBox(panel, local.getArmors(0), mod_id);
		    //postfixes
		    JLabel ider = new JLabel(local.getTools(3));
			//ider.setBorder(BorderFactory.createEmptyBorder(0, res.getBorder() + 2, 0, 0));
			panel.add(ider);
		    //component 9 helmet
			MakeTextBox(panel, local.getArmors(3), local.getDefaults(8));
		    //component 11 chestplate
			MakeTextBox(panel, local.getArmors(4), local.getDefaults(9));
		    //component 13 leggings
			MakeTextBox(panel, local.getArmors(5), local.getDefaults(10));
		    //component 15 boots
			MakeTextBox(panel, local.getArmors(6), local.getDefaults(11));
		    
			//submit
		    JButton jbutton = new JButton(local.getUIopt(5));
	    	fontChange(jbutton);
	    	jbutton.setFocusable(false);
		    
		    jbutton.addActionListener( makeSubmitListener(panel, flag) );
		    
		    panel.add(jbutton);
		}
		//blocks All
		else if(flag == 3)
		{
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 40, 0, 0));
		    panel.add(sizer);
			
		    //component 2
		    MakeTextBox(panel, local.getBlks(2), name);
		    //component 4
		    MakeTextBox(panel, local.getBlks(1), pref);
		    //component 6
		    MakeTextBox(panel, local.getBlks(0), mod_id);
		    
		    //submit
		    JButton jbutton = new JButton(local.getUIopt(5));
	    	fontChange(jbutton);
	    	jbutton.setFocusable(false);
		    
		    jbutton.addActionListener( makeSubmitListener(panel, flag) );
		    
		    panel.add(jbutton);
		}
		
		contentPane.add(panel, BorderLayout.WEST);
		return panel;
	}
	
	/**
	 * 
	 * @param panel
	 * @param kind 0 : BASIC       |
	 *             1 : TOOL        |
	 *             2 : ARMOR       |
	 *             3 : BLOCKS      |
	 * @return
	 */
	private ActionListener makeSubmitListener(JPanel panel, int kind)
	{
		if(kind == 0)
		{
			return makeBasicSubmit(panel);
		}
		else if(kind == 1)
		{
			return makeToolSubmit(panel);
		}
		else if(kind == 2)
		{
			return makeArmorSubmit(panel);
		}
		else if(kind == 3)
		{
			return makeBlockSubmit(panel);
		}
		return makeBasicSubmit(panel);
		
	}
	
	/**
	 * create json on button press for items
	 * @param panel
	 * @return
	 */
	private ActionListener makeBasicSubmit(JPanel panel)
	{
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemName = ((JTextField)panel.getComponent(2)).getText();
				String texture = ((JTextField)panel.getComponent(4)).getText();
				String modid = ((JTextField)panel.getComponent(6)).getText();
				mod_id = modid;
				pref = texture;
				name = itemName;
				
				JsonPreview prev = new JsonPreview(local, JsonType.BASIC, itemName, texture, modid, 3);
				//jsons.add(prev);
				
				String text = prev.getFiles()[0];
				
				//create place to put read only text
				JTextPane label = new JTextPane();
				label.setEditable(false);
				
				fontChange(label);
				
				//set the read only text
				label.setText(text);
				
				//make it scrollable
				JScrollPane scroll = new JScrollPane(label);
				tabs.addTab(prev.getName(), scroll);//scroll);
				
				//X button
				int index = tabs.getTabCount() - 1;
				JPanel tabPanel = new JPanel(new GridBagLayout());
				tabPanel.setOpaque(false);
				JLabel titleLabel = new JLabel(prev.getName());
				fontChange(titleLabel);
				JButton buttonClose = new JButton("x");
				fontChange(buttonClose);
				//buttonClose.setFocusable(false);
				buttonClose.setBorder(null);
				buttonClose.setOpaque(false);
				buttonClose.setContentAreaFilled(false);
				buttonClose.setBorderPainted(false);
				
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.weightx = 1;
				
				tabPanel.add(titleLabel, gbc);
				
				gbc.gridx++;
				gbc.weightx = 0;
				tabPanel.add(new JLabel(" "), gbc);
				
				gbc.gridx++;
				
				tabPanel.add(buttonClose, gbc);
				tabs.setTabComponentAt(index, tabPanel);
				jsons.put(tabPanel, prev);
				buttonClose.addActionListener(makeCloseTabListener(tabPanel, scroll, false, ""));
			}
		};
	}
	
	/**
	 * create jsons on button press for tool sets
	 * @param panel
	 * @return
	 */
	private ActionListener makeToolSubmit(JPanel panel)
	{
		return new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			String setName = ((JTextField)panel.getComponent(2)).getText();
    			String prefix = ((JTextField)panel.getComponent(4)).getText();
    			String modid = ((JTextField)panel.getComponent(6)).getText();
    			mod_id = modid;
    			pref = prefix;
    			name = setName;
    			
    			ArrayList<String> post = new ArrayList<String>();
    			post.add(((JTextField)panel.getComponent(9)).getText());
    			post.add(((JTextField)panel.getComponent(11)).getText());
    			post.add(((JTextField)panel.getComponent(13)).getText());
    			post.add(((JTextField)panel.getComponent(15)).getText());
    			post.add(((JTextField)panel.getComponent(17)).getText());
    			
    			JsonPreview prev = new JsonPreview(local, JsonType.TOOL_SET, setName, prefix, modid, 3).add(post);
    			//jsons.add(prev);

    			for(int j = 0; j < prev.getOptions().size(); j++)
				{
					String text = prev.getFiles()[j];
					
					//create place to put read only text
					JTextPane label = new JTextPane();
					label.setEditable(false);
					
					fontChange(label);
					
					//set the read only text
					label.setText(text);
					
					//make it scrollable
					JScrollPane scroll = new JScrollPane(label);
					tabs.addTab(prev.getName() + prev.getOptions().get(j), scroll);//scroll);
				
					//X button
					int index = tabs.getTabCount() - 1;
					JPanel tabPanel = new JPanel(new GridBagLayout());
					tabPanel.setOpaque(false);
					JLabel titleLabel = new JLabel(prev.getName() + prev.getOptions().get(j));
					fontChange(titleLabel);
					JButton buttonClose = new JButton("x");
					fontChange(buttonClose);
					buttonClose.setFocusable(false);
					buttonClose.setOpaque(false);
					buttonClose.setContentAreaFilled(false);
					buttonClose.setBorderPainted(false);
					
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.weightx = 1;
					
					tabPanel.add(titleLabel, gbc);
					
					gbc.gridx++;
					gbc.weightx = 0;
					tabPanel.add(new JLabel(" "), gbc);
					
					gbc.gridx++;
					
					tabPanel.add(buttonClose, gbc);
					tabs.setTabComponentAt(index, tabPanel);
					jsons.put(tabPanel, prev);
					buttonClose.addActionListener(makeCloseTabListener(tabPanel, scroll, true, prev.getOptions().get(j)));
				}
    		}
    	};
	}
	
	/**
	 * create jsons on button press for armor sets
	 * @param panel
	 * @return
	 */
	private ActionListener makeArmorSubmit(JPanel panel)
	{
		return new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			String setName = ((JTextField)panel.getComponent(2)).getText();
    			String prefix = ((JTextField)panel.getComponent(4)).getText();
    			String modid = ((JTextField)panel.getComponent(6)).getText();
    			mod_id = modid;
    			pref = prefix;
    			name = setName;
    			
    			ArrayList<String> post = new ArrayList<String>();
    			post.add(((JTextField)panel.getComponent(9)).getText());
    			post.add(((JTextField)panel.getComponent(11)).getText());
    			post.add(((JTextField)panel.getComponent(13)).getText());
    			post.add(((JTextField)panel.getComponent(15)).getText());
    			
    			JsonPreview prev = new JsonPreview(local, JsonType.ARMOR_SET, setName, prefix, modid, 3).add(post);
    			//jsons.add(prev);

    			for(int j = 0; j < prev.getOptions().size(); j++)
				{
					String text = prev.getFiles()[j];
					
					//create place to put read only text
					JTextPane label = new JTextPane();
					label.setEditable(false);
					
					fontChange(label);
					
					//set the read only text
					label.setText(text);
					
					//make it scrollable
					JScrollPane scroll = new JScrollPane(label);
					tabs.addTab(prev.getName() + prev.getOptions().get(j), scroll);//scroll);
				
					//X button
					int index = tabs.getTabCount() - 1;
					JPanel tabPanel = new JPanel(new GridBagLayout());
					tabPanel.setOpaque(false);
					JLabel titleLabel = new JLabel(prev.getName() + prev.getOptions().get(j));
					fontChange(titleLabel);
					JButton buttonClose = new JButton("x");
					fontChange(buttonClose);
					buttonClose.setFocusable(false);
					buttonClose.setOpaque(false);
					buttonClose.setContentAreaFilled(false);
					buttonClose.setBorderPainted(false);
					
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.weightx = 1;
					
					tabPanel.add(titleLabel, gbc);
					
					gbc.gridx++;
					gbc.weightx = 0;
					tabPanel.add(new JLabel(" "), gbc);
					
					gbc.gridx++;
					
					tabPanel.add(buttonClose, gbc);
					tabs.setTabComponentAt(index, tabPanel);
					jsons.put(tabPanel, prev);
					buttonClose.addActionListener(makeCloseTabListener(tabPanel, scroll, true, prev.getOptions().get(j)));
				}
    		}
    	};
	}
	
	/**
	 * create jsons on button press for blocks
	 * @param panel
	 * @return
	 */
	private ActionListener makeBlockSubmit(JPanel panel)
	{
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemName = ((JTextField)panel.getComponent(2)).getText();
				String texture = ((JTextField)panel.getComponent(4)).getText();
				String modid = ((JTextField)panel.getComponent(6)).getText();
				mod_id = modid;
				pref = texture;
				name = itemName;
				
				JsonPreview prev[] = { 
						new JsonPreview(local, JsonType.BLOCK_ITEM, itemName, texture, modid, 3),
						new JsonPreview(local, JsonType.BLOCK_STATE, itemName, texture, modid, 3),
						new JsonPreview(local, JsonType.BLOCK, itemName, texture, modid, 3)
				};
				//jsons.add(prev[0]);
				//jsons.add(prev[1]);
				//jsons.add(prev[2]);
				
				for(int i = 0; i < 3; i++)
				{
					String text = prev[i].getFiles()[0];
					
					//create place to put read only text
					JTextPane label = new JTextPane();
					label.setEditable(false);
					
					fontChange(label);
					
					//set the read only text
					label.setText(text);
					
					//make it scrollable
					JScrollPane scroll = new JScrollPane(label);
					tabs.addTab(prev[i].getName(), scroll);//scroll);
				
					//X button
					int index = tabs.getTabCount() - 1;
					JPanel tabPanel = new JPanel(new GridBagLayout());
					tabPanel.setOpaque(false);
					JLabel titleLabel = new JLabel(prev[i].getName());
					fontChange(titleLabel);
					JButton buttonClose = new JButton("x");
					fontChange(buttonClose);
					buttonClose.setFocusable(false);
					buttonClose.setOpaque(false);
					buttonClose.setContentAreaFilled(false);
					buttonClose.setBorderPainted(false);
					
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.weightx = 1;
					
					tabPanel.add(titleLabel, gbc);
					
					gbc.gridx++;
					gbc.weightx = 0;
					tabPanel.add(new JLabel(" "), gbc);
					
					gbc.gridx++;
					
					tabPanel.add(buttonClose, gbc);
					tabs.setTabComponentAt(index, tabPanel);
					jsons.put(tabPanel, prev[i]);
					buttonClose.addActionListener(makeCloseTabListener(tabPanel, scroll, false, ""));
				}
				
			}
		};
	}
	
	private ActionListener makeCloseTabListener(Component c, Component scroll, boolean inner, String option)
	{
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//int i = tabs.indexOfTabComponent(c.getParent());
				if(!inner)
				{
					jsons.remove(c);
				}
				else
				{
					jsons.get(c).getOptions().remove(option);
					if(jsons.get(c).getOptions().isEmpty())
					{
						jsons.remove(c);
					}
				}
				tabs.remove(scroll);
			}
			
		};
	}
	
	private JPanel makeCenterRegion()
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout());
		panel.setBorder(BorderFactory.createTitledBorder(local.getSpecial()));
		fontChange(panel);
		
		//JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
		tabs.setFocusable(false);
		tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		tabs.setSelectedIndex(currentIndex);
		
		panel.add(tabs);
		contentPane.add(panel, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel makeEastRegion()
	{
		JPanel panel = new JPanel();
		//save all
		panel.setBorder(BorderFactory.createTitledBorder(local.getUIopt(6)));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		fontChange(panel);
		
		JLabel sizer = new JLabel("");
    	
    	//JScrollPane scroll = new JScrollPane();
		sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 47, 0, 0));
	    panel.add(sizer);
	    
	    //save as file
	    JButton saveAsButton = new JButton(local.getUIopt(0));
	    //tool tip
	    saveAsButton.setToolTipText(local.getUIopt(1));
    	fontChange(saveAsButton);
    	saveAsButton.setFocusable(false);
	    
	    saveAsButton.addActionListener( makeSaveAsListener(panel) );
	    
	    //save file
	    JButton saveButton = new JButton(local.getUIopt(2));
	    //tool tip
	    saveButton.setToolTipText(local.getUIopt(3));
	    fontChange(saveButton);
	    saveButton.setFocusable(false);
	    
	    saveButton.addActionListener( makeSaveListener(panel) );
	    
	    panel.add(saveAsButton);
	    panel.add(saveButton);
	    contentPane.add(panel, BorderLayout.EAST);
	    return panel;
	}
	
	private ActionListener makeSaveAsListener(JPanel panel)
	{
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(jsons.size() == 0)
				{
					return;
				}
				JFileChooser save = new JFileChooser();
				save.setCurrentDirectory(new java.io.File(path));
				//save
				save.setDialogTitle(local.getUIopt(4));
				save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				save.setAcceptAllFileFilterUsed(false);
				
				String localPath = null;
				if(save.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
				{
					localPath = save.getCurrentDirectory() + "\\" + save.getSelectedFile().getName();
				}
				else
				{
					return;
				}
				
				if(localPath != null)
				{
					path = localPath;
					//for (int i = 0; i < jsons.size(); i++)
					for(Entry<Component, JsonPreview> en : jsons.entrySet())
					{
						for(int j = 0; j < en.getValue().getOptions().size(); j++)
						{
							String tmp;
							if(en.getValue().getType() == JsonType.BASIC)
							{
								tmp = directoryExists(localPath, Reference.ITEM_FOLD);
							}
							else if(en.getValue().getType() == JsonType.TOOL_SET)
							{
								tmp = directoryExists(localPath, Reference.ITEM_FOLD);
							}
							else if(en.getValue().getType() == JsonType.ARMOR_SET)
							{
								tmp = directoryExists(localPath, Reference.ITEM_FOLD);
							}
							else if(en.getValue().getType() == JsonType.BLOCK_ITEM)
							{
								tmp = directoryExists(localPath, Reference.ITEM_FOLD);
							}
							else if(en.getValue().getType() == JsonType.BLOCK_STATE)
							{
								tmp = directoryExists(localPath, Reference.B_STATE_FOLD);
							}
							else//BLOCK
							{
								tmp = directoryExists(localPath, Reference.BLCK_FOLD);
							}
							
							File file = new File(tmp);
							try {
								FileWriter fw = new FileWriter(file.getAbsoluteFile() + "\\" + en.getValue().getName() + en.getValue().getOptions().get(j) + ".json");
								BufferedWriter bw = new BufferedWriter(fw);
								bw.write(en.getValue().getFiles()[j]);
								bw.close();
							}
							catch(IOException err)
							{
								err.printStackTrace();
								System.exit(-1);
							}
						}
					}
					//File file = new File(this.getClass().getClassLoader().getResource(Reference.DATA_FILE).getFile().replace("%20", " "));
					File file = new File(Reference.DATA_FILE);
					try {
						Scanner scan = new Scanner(file);
						scan.next();
						String contents = "";
						while(scan.hasNext())
						{
							contents = "\n" + contents + scan.next();
						}
						scan.close();
						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(path);
						bw.write(contents);
						bw.close();
					}
					catch(IOException err)
					{
						err.printStackTrace();
						//failed
						refreshContent(local.getMsgs(0));
						return;
						//System.exit(-1);
					}
				}
				tabs = new JTabbedPane(JTabbedPane.TOP);
				jsons = new HashMap<>();
				currentIndex = -1;
				//saved
				refreshContent(local.getMsgs(1));
			}
			
		};
	}
	
	private ActionListener makeSaveListener(JPanel panel)
	{
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(jsons.size() == 0)
				{
					return;
				}
				//for (int i = 0; i < jsons.size(); i++)
				for(Entry<Component, JsonPreview> en : jsons.entrySet())
				{
					for(int j = 0; j < en.getValue().getOptions().size(); j++)
					{
						String tmp;
						if(en.getValue().getType() == JsonType.BASIC)
						{
							tmp = directoryExists(path, Reference.ITEM_FOLD);
						}
						else if(en.getValue().getType() == JsonType.TOOL_SET)
						{
							tmp = directoryExists(path, Reference.ITEM_FOLD);
						}
						else if(en.getValue().getType() == JsonType.ARMOR_SET)
						{
							tmp = directoryExists(path, Reference.ITEM_FOLD);
						}
						else if(en.getValue().getType() == JsonType.BLOCK_ITEM)
						{
							tmp = directoryExists(path, Reference.ITEM_FOLD);
						}
						else if(en.getValue().getType() == JsonType.BLOCK_STATE)
						{
							tmp = directoryExists(path, Reference.B_STATE_FOLD);
						}
						else//BLOCK
						{
							tmp = directoryExists(path, Reference.BLCK_FOLD);
						}
						
						File file = new File(tmp);
						try {
							FileWriter fw = new FileWriter(file.getAbsoluteFile() + "\\" + en.getValue().getName() + en.getValue().getOptions().get(j) + ".json");
							BufferedWriter bw = new BufferedWriter(fw);
							bw.write(en.getValue().getFiles()[j]);
							bw.close();
						}
						catch(IOException err)
						{
							err.printStackTrace();
							//failed
							refreshContent(local.getMsgs(0));
							return;
							//System.exit(-1);
						}
					}
				}
				tabs = new JTabbedPane(JTabbedPane.TOP);
				jsons = new HashMap<>();
				currentIndex = -1;
				//saved
				refreshContent(local.getMsgs(1));
			}
			
		};
	}
	
	private String directoryExists(String path, String directory)
	{
		File dir = new File(path + "\\" + directory);
		dir.mkdirs();	
		return path + "\\" + directory;
	}
	
	private JPanel makeSouthRegion()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder(local.getMessage()));
		fontChange(panel);
		
		JTextPane label = new JTextPane();
		label.setEditable(false);
		
		fontChange(label);
		
		//set the read only text "save to: "
		label.setText(local.getMsgs(2) + path + "\n" + message);
		
		panel.add(label);
		contentPane.add(panel, BorderLayout.SOUTH);
		return panel;
	}
	
	private void fontChange(JMenuItem item)
	{
		item.setFont(new Font("courier", Font.BOLD, res.getFontSize()));
	}
	
	private void fontChange(JMenu menu)
	{
		menu.setFont(new Font("courier", Font.BOLD, res.getFontSize()));
	}
	
	private void fontChange(JPanel panel)
	{
		panel.setFont(new Font("courier", Font.BOLD, res.getFontSize()));
		((javax.swing.border.TitledBorder) panel.getBorder()).setTitleFont(new Font("courier", Font.BOLD, res.getFontSize()));
	}
	
	private void fontChange(JButton button)
	{
		button.setFont(new Font("courier", Font.BOLD, res.getFontSize()));
	}
	
	private void fontChange(JTextPane label)
	{
		label.setFont(new Font("courier", Font.BOLD, res.getFontSize()));
	}
	
	private void fontChange(JTextField label)
	{
		label.setFont(new Font("courier", Font.BOLD, res.getFontSize()));
	}
	
	private void fontChange(JLabel label)
	{
		label.setFont(new Font("courier", Font.BOLD, res.getFontSize()));
	}
}
