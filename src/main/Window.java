package main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.*;

import templates.json.savedata.Save;
import util.FileHandler;
import util.JsonFlag;
import util.JsonType;
import util.Localization;
import util.RefInt;
import util.Resolution;

public class Window 
{
	private JsonFlag flag;
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
	
	private RefInt nameField = new RefInt(), textureField  = new RefInt(), modidField  = new RefInt(), 
			swordField = new RefInt(), axeField = new RefInt(), shovelField = new RefInt(), 
			pickaxeField = new RefInt(), hoeField = new RefInt(), helmetField = new RefInt(), 
			chestplateField = new RefInt(), leggingsField = new RefInt(), bootsField = new RefInt();
	private ArrayList<Integer> componentList = new ArrayList<>();
	
	public Window(JFrame frame, JPanel contentPane, Resolution res, Localization local, String path, String reso)
	{
		this.flag = JsonFlag.Empty;
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
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		if(reso == null)
		{
			frame.setSize(res.getWidth(), res.getHeight());
		}
		else
		{
			String[] s = reso.split("x");
			res.resolutionChange(res.getIndexFromSaveData(s));
			frame.setSize(res.getWidth(), res.getHeight());
		}
		
		frame.setVisible(true);
		frame.setResizable(false);
		//contentPane.setPreferredSize(new Dimension(res.getWidth(), res.getHeight()));
		this.jsons = new HashMap<>();
		tabs = new JTabbedPane(JTabbedPane.TOP);
	}
	
	private void makeMenuOption(JMenu category, String optionText, ActionListener listener)
	{
		JMenuItem option = new JMenuItem(optionText);
		category.add(option);
    	fontChange(option);
    	if (listener != null)
    	{
    		option.addActionListener(listener);    		
    	}
	}
	
	private JMenu makeMenu(String menuText, int mnumonic)
	{
		JMenu menu = new JMenu(menuText);
    	fontChange(menu);
    	if (mnumonic != -1)
    	{
    		menu.setMnemonic(mnumonic);    		
    	}
    	return menu;
	}
	
	private JMenu makeMenu(JMenu SubsetOf, String menuText)
	{
		return (JMenu)SubsetOf.add(makeMenu(menuText, -1));
	}
	
	private JMenu makeMenu(JMenuBar SubsetOf, String menuText, int mnumonic)
	{
		return SubsetOf.add(makeMenu(menuText, mnumonic));
	}
	
	public void makeMenus()
	{
		JMenuBar menuBar;
    	JMenu menu;
    	JMenu pre;
    	JMenu sub;
    	
    	menuBar = new JMenuBar();
    	frame.setJMenuBar(menuBar);
    	
    	//FILE=================================================================
    	menu = makeMenu(menuBar, local.getMenus(0), KeyEvent.VK_F);
    	//FILE SUB MENU=========================================================
    	makeMenuOption(menu, local.getMenus(1), makeResetListener());//reset
    	
    	pre = makeMenu(menu, local.getMenus(2));//options
    	
    	sub = makeMenu(pre, local.getMenus(5));//resolutions
    	for (int i = 0; i < local.getResLength(); i++)
    	{
    		makeMenuOption(sub, local.getRes(i), makeResolutionListener(i));//individual resolution
    	}
    	
    	makeMenuOption(pre, local.getMenus(3), null);//language
    	
    	//TODO: exit listener
    	makeMenuOption(menu, local.getMenus(4), null);//exit
    	//END FILE==============================================================
    	//TYPES=================================================================
    	menu = makeMenu(menuBar, local.getMenus(6), KeyEvent.VK_T);
    	//TYPES SUB MENU========================================================
    	makeMenuOption(menu, local.getMenus(7), makeTypeListener(JsonFlag.BasicItem));
    	makeMenuOption(menu, local.getMenus(8), makeTypeListener(JsonFlag.Tool));
    	makeMenuOption(menu, local.getMenus(9), makeTypeListener(JsonFlag.Armor));
    	makeMenuOption(menu, local.getMenus(10), makeTypeListener(JsonFlag.Block));
	}
	
	private ActionListener makeResolutionListener(int num)
	{
		return new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			res.resolutionChange(num);
    			killContent();
    			frame.setSize(res.getWidth(), res.getHeight());
    			Save.save.updateRes(res.getWidth() + "x" + res.getHeight());
    			refreshContent("");
    		}
    	};
	}
	
	private ActionListener makeTypeListener(JsonFlag num)
	{
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag = num;
				west = makeWestRegion();
				remakeContent(BorderLayout.WEST);
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
				flag = JsonFlag.Empty;
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
	
	private JPanel addToPane(String layout)
	{
		switch (layout)
		{
			case BorderLayout.CENTER:
				return center;
			case BorderLayout.WEST:
				return west;
			case BorderLayout.EAST:
				return east;
			case BorderLayout.SOUTH:
				return south;
			default:
				return null;
		}
	}
	/**
	 * remake all content except for id at except
	 */
	public void remakeContent(String except)
	{
		killContent();
		makeMenus();
		if (except.equals(BorderLayout.WEST) || !except.equals(BorderLayout.CENTER) 
				|| !except.equals(BorderLayout.EAST) || !except.equals(BorderLayout.SOUTH))
		{
			contentPane.add(addToPane(except), except);			
		}
		makeNorthRegion();
		if (!except.equals(BorderLayout.WEST))
		{
			west = makeWestRegion();
		}
		if (!except.equals(BorderLayout.CENTER))
		{
			center = makeCenterRegion();
		}
		if (!except.equals(BorderLayout.EAST))
		{
			east = makeEastRegion();
		}
		if (!except.equals(BorderLayout.SOUTH))
		{
			south = makeSouthRegion();
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
	
	private void MakeTextBox(JPanel panel, String title, String text, RefInt fieldId)
	{
		JLabel ider = new JLabel(title);
		//ider.setBorder(BorderFactory.createEmptyBorder(0, res.getBorder() + 2, 0, 0));
		panel.add(ider);
		
		JTextField label = new JTextField();
		fontChange(label);
		label.setText(text);
		label.setMaximumSize(new Dimension(res.getWidth() / 2, res.getFontSize() * 2));
		panel.add(label);
		fieldId.value = panel.getComponentCount() - 1;
	}
	
	private void submitButton(JPanel panel)
	{
		//submit
	    JButton jbutton = new JButton(local.getUIopt(5));
    	fontChange(jbutton);
    	jbutton.setFocusable(false);
	    
	    jbutton.addActionListener( makeFilesAfterSubmit(panel, flag) );
	    
	    panel.add(jbutton);
	}
	
	private JPanel makeWestRegion()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(local.getSelection()));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		fontChange(panel);
		
		JLabel sizer = new JLabel("");
		
		componentList.clear();
		
		//NO SELECTION
		if(flag == JsonFlag.Empty)
		{
			sizer.setBorder(BorderFactory.createEmptyBorder(0, res.getWidth() / 4, 0, 0));
		    panel.add(sizer);
		}
		//BASIC ITEM
		else if(flag == JsonFlag.BasicItem)
		{
			
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 40, 0, 0));
		    panel.add(sizer);
			
		    MakeTextBox(panel, local.getBscItm(2), name, nameField);
		    MakeTextBox(panel, local.getBscItm(1), pref, textureField);
		    MakeTextBox(panel, local.getBscItm(0), mod_id, modidField);
		    
		    submitButton(panel);
		}
		//Tool Sets
		else if(flag == JsonFlag.Tool)
		{
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 47, 0, 0));
		    panel.add(sizer);
		    
		    MakeTextBox(panel, local.getTools(2), name, nameField);
		    MakeTextBox(panel, local.getTools(1), pref, textureField);
		    MakeTextBox(panel, local.getTools(0), mod_id, modidField);
		    //postfixes
		    JLabel ider = new JLabel(local.getTools(3));
			//ider.setBorder(BorderFactory.createEmptyBorder(0, res.getBorder() + 2, 0, 0));
		    panel.add(ider);
			MakeTextBox(panel, local.getTools(4), local.getDefaults(3), swordField);
			MakeTextBox(panel, local.getTools(5), local.getDefaults(4), axeField);
			MakeTextBox(panel, local.getTools(6), local.getDefaults(5), shovelField);
			MakeTextBox(panel, local.getTools(7), local.getDefaults(6), pickaxeField);
			MakeTextBox(panel, local.getTools(8), local.getDefaults(7), hoeField);
			componentList.addAll(Arrays.asList(new Integer[] { swordField.value, axeField.value, shovelField.value, pickaxeField.value, hoeField.value }));
		    
			submitButton(panel);
		}
		//armor sets
		else if(flag == JsonFlag.Armor)
		{
			//JScrollPane scroll = new JScrollPane();
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 47, 0, 0));
		    panel.add(sizer);
		    
		    MakeTextBox(panel, local.getArmors(2), name, nameField);
		    MakeTextBox(panel, local.getArmors(1), pref, textureField);
		    MakeTextBox(panel, local.getArmors(0), mod_id, modidField);
		    //postfixes
		    JLabel ider = new JLabel(local.getTools(3));
			//ider.setBorder(BorderFactory.createEmptyBorder(0, res.getBorder() + 2, 0, 0));
			panel.add(ider);
			MakeTextBox(panel, local.getArmors(3), local.getDefaults(8), helmetField);
			MakeTextBox(panel, local.getArmors(4), local.getDefaults(9), chestplateField);
			MakeTextBox(panel, local.getArmors(5), local.getDefaults(10), leggingsField);
			MakeTextBox(panel, local.getArmors(6), local.getDefaults(11), bootsField);
			componentList.addAll(Arrays.asList(new Integer[] { helmetField.value, chestplateField.value, leggingsField.value, bootsField.value }));
		    
			submitButton(panel);
		}
		//blocks All
		else if(flag == JsonFlag.Block)
		{
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 40, 0, 0));
		    panel.add(sizer);
			
		    MakeTextBox(panel, local.getBlks(2), name, nameField);
		    MakeTextBox(panel, local.getBlks(1), pref, textureField);
		    MakeTextBox(panel, local.getBlks(0), mod_id, modidField);
		    
		    submitButton(panel);
		}
		
		contentPane.add(panel, BorderLayout.WEST);
		return panel;
	}
	
	private void tabMaker(String text, JsonPreview prev, boolean inner, int j)
	{
		String fileName = prev.getName() + (inner ? prev.getOptions().get(j) : "");
		
		//create place to put read only text
		JTextPane label = new JTextPane();
		label.setEditable(false);
		
		fontChange(label);
		
		//set the read only text
		label.setText(text);
		
		//make it scrollable
		JScrollPane scroll = new JScrollPane(label);
		tabs.addTab(fileName, scroll);
		
		//X button
		int index = tabs.getTabCount() - 1;
		JPanel tabPanel = new JPanel(new GridBagLayout());
		tabPanel.setOpaque(false);
		JLabel titleLabel = new JLabel(fileName);
		fontChange(titleLabel);
		JButton buttonClose = new JButton("x");
		fontChange(buttonClose);
		buttonClose.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				buttonClose.setForeground(Color.RED);
			}
			
			public void mouseExited(MouseEvent e)
			{
				buttonClose.setForeground(Color.BLACK);
			}
		});
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
		buttonClose.addActionListener(makeCloseTabListener(tabPanel, scroll, inner, inner ? prev.getOptions().get(j) : ""));
	}
	
	private void getUbiquitousText(JPanel panel)
	{
		name = ((JTextField)panel.getComponent(nameField.value)).getText();
		pref = ((JTextField)panel.getComponent(textureField.value)).getText();
		mod_id = ((JTextField)panel.getComponent(modidField.value)).getText();
	}
	
	private ActionListener makeFilesAfterSubmit(JPanel panel, JsonFlag kind)
	{
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getUbiquitousText(panel);
				ArrayList<String> components = new ArrayList<>();
				for (int i = 0; i < componentList.size(); i++)
				{
					components.add(((JTextField)panel.getComponent(componentList.get(i))).getText());
				}
				if (components.size() == 0)
				{
					components.add("");
				}
				
				ArrayList<JsonType> fileTypes = kind.getType();
				JsonPreview[] prevs = new JsonPreview[fileTypes.size()];
				for (int i = 0; i < fileTypes.size(); i++)
				{
					prevs[i] = new JsonPreview(fileTypes.get(i), name, pref, mod_id).add(components);
				}
				
				for (int i = 0; i < prevs.length; i++)
				{
					String[] files = prevs[i].getFiles();
					for(int j = 0; j < prevs[i].getOptions().size(); j++)
					{
						String text = files[j];
						tabMaker(text, prevs[i], true, j);
					}
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
	
	private int saveJson(String directory, Entry<Component, JsonPreview> json, int j)
	{
		String fileName = json.getValue().getName() + json.getValue().getOptions().get(j) + ".json";
		File file = new File(directory + "\\" + fileName);
		FileHandler.createFile(file);
		if (FileHandler.writeFileExists(file, json.getValue().getFiles()[j]))
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	//TODO: save failed saves into a passed in hash map. then when the has map is reset later, set it to that instead so unsaved jsons aren't deleted
	private int[] saveJsons()
	{
		int[] flags = new int[2];
		flags[0] = 0;
		flags[1] = 0;
		for(Entry<Component, JsonPreview> en : jsons.entrySet())
		{
			for(int j = 0; j < en.getValue().getOptions().size(); j++)
			{
				String tmp;
				tmp = directoryExists(path, en.getValue().getType().folder);
				flags[saveJson(tmp, en, j)]++;
			}
		}
		return flags;
	}
	
	private ActionListener makeSaveAsListener(JPanel panel)
	{
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String messageFollows = "";
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
					int[] tmp = saveJsons();
					messageFollows = (tmp[0] > 0 ? (tmp[0] + " " + local.getMsgs(1)) : "")
							+ (tmp[0] > 0 && tmp[1] > 0 ? " " : "")
							+ (tmp[1] > 0 ? (tmp[1] + " " + local.getMsgs(0)) : "");
					//File file = new File(this.getClass().getClassLoader().getResource(Reference.DATA_FILE).getFile().replace("%20", " "));
					if (!Save.save.updatePath(path))
					{
						refreshContent(messageFollows);
						return;
					}
				}
				tabs = new JTabbedPane(JTabbedPane.TOP);
				jsons = new HashMap<>();
				currentIndex = -1;
				//saved
				refreshContent(messageFollows);
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
				
				int[] tmp = saveJsons();
				String messageFollows = (tmp[0] > 0 ? (tmp[0] + " " + local.getMsgs(1)) : "")
						+ (tmp[0] > 0 && tmp[1] > 0 ? " " : "")
						+ (tmp[1] > 0 ? (tmp[1] + " " + local.getMsgs(0)) : "");
				tabs = new JTabbedPane(JTabbedPane.TOP);
				jsons = new HashMap<>();
				currentIndex = -1;
				//saved
				refreshContent(messageFollows);
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
