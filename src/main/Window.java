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

import templates.json.savedata.lang.Lang;
import templates.json.savedata.settings.Save;
import util.FileHandler;
import util.InnerPreview;
import util.Language;
import util.RefInt;
import util.RefString;
import util.ResVals;
import util.Resolution;
import util.json.generator.JsonFlag;
import util.json.generator.JsonType;
import util.reference.LangDefaultValueRef;
import util.reference.LangMenuRef;
import util.reference.LangMessageRef;
import util.reference.LangResRef;
import util.reference.LangTitlesRef;
import util.reference.LangUIButtonsRef;
import util.reference.LangUIFieldsRef;

public class Window 
{
	private JsonFlag flag;
	private JFrame frame;
	private JPanel contentPane;
	private Resolution res;
	private JPanel south, east, west, center;
	//private ArrayList<JsonPreview> jsons;
	private Map<Component, InnerPreview> jsons;
	private Map<Component, JScrollPane> tabMap;
	private Map<Component, InnerPreview> failedSaves;
	private JTabbedPane tabs;
	private int currentIndex = -1;
	private RefString path, message, mod_id, pref, side, paneside, top, bottom, name;
	
	private RefInt nameField = new RefInt(), textureField  = new RefInt(), modidField  = new RefInt(), 
			swordField = new RefInt(), axeField = new RefInt(), shovelField = new RefInt(), 
			pickaxeField = new RefInt(), hoeField = new RefInt(), helmetField = new RefInt(), 
			chestplateField = new RefInt(), leggingsField = new RefInt(), bootsField = new RefInt(),
			topField = new RefInt(), bottomField = new RefInt(), sideField = new RefInt(),
			northSouthField = new RefInt(), eastWestField = new RefInt();
	private ArrayList<Integer> componentList = new ArrayList<>();
	
	public Window(JFrame frame, JPanel contentPane, Resolution res, String path, String reso)
	{
		this.flag = JsonFlag.Empty;
		this.frame = frame;
		this.contentPane = contentPane;
		this.res = res;
		this.path = new RefString(path);
		this.message = new RefString("");
		this.mod_id = new RefString(Lang.lang.getDefaultText(LangDefaultValueRef.MOD_ID));
		this.pref = new RefString(Lang.lang.getDefaultText(LangDefaultValueRef.TEXTURE));
		this.top = new RefString(Lang.lang.getDefaultText(LangDefaultValueRef.TEXTURE));
		this.bottom = new RefString(Lang.lang.getDefaultText(LangDefaultValueRef.TEXTURE));
		this.side = new RefString(Lang.lang.getDefaultText(LangDefaultValueRef.TEXTURE));
		this.paneside = new RefString(Lang.lang.getDefaultText(LangDefaultValueRef.PANE_SIDE));
		this.name = new RefString(Lang.lang.getDefaultText(LangDefaultValueRef.NAME));
		
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
		this.tabMap = new HashMap<>();
		this.failedSaves = new HashMap<>();
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
    	menu = makeMenu(menuBar, Lang.lang.getMenu(LangMenuRef.FILE), KeyEvent.VK_F);
    	//FILE SUB MENU=========================================================
    	makeMenuOption(menu, Lang.lang.getMenu(LangMenuRef.RESET), makeResetListener());//reset
    	
    	pre = makeMenu(menu, Lang.lang.getMenu(LangMenuRef.OPTIONS));//options
    	
    	sub = makeMenu(pre, Lang.lang.getMenu(LangMenuRef.RESOLUTION));//resolutions
    	for (int i = 0; i < ResVals.values().length; i++)
    	{
    		ResVals resolution = ResVals.values()[i];
    		String resKey;
    		if (resolution == ResVals.DEFAULT)
    		{
    			resKey = LangResRef.DEFAULT;
    		}
    		else
    		{
    			resKey = resolution.getWidth() + "x" + resolution.getHeight();
    		}
    		makeMenuOption(sub, Lang.lang.getRes(resKey), makeResolutionListener(i));
    	}
    	
    	sub = makeMenu(pre, Lang.lang.getMenu(LangMenuRef.LANGUAGE));//language
    	String[] langs = Language.lang.getLangs();
    	for (int i = 0; i < langs.length; i++)
    	{
    		makeMenuOption(sub, langs[i], makeLangListener(langs[i]));
    	}
    	
    	makeMenuOption(menu, Lang.lang.getMenu(LangMenuRef.EXIT), makeExitListener());//exit
    	//END FILE==============================================================
    	//TYPES=================================================================
    	menu = makeMenu(menuBar, Lang.lang.getMenu(LangMenuRef.TYPES), KeyEvent.VK_T);
    	//TYPES SUB MENU========================================================
    	makeMenuOption(menu, Lang.lang.getMenu(LangMenuRef.BASIC_ITEM), makeTypeListener(JsonFlag.BasicItem));
    	makeMenuOption(menu, Lang.lang.getMenu(LangMenuRef.TOOL_SET), makeTypeListener(JsonFlag.Tool));
    	makeMenuOption(menu, Lang.lang.getMenu(LangMenuRef.ARMOR_SET), makeTypeListener(JsonFlag.Armor));
    	makeMenuOption(menu, Lang.lang.getMenu(LangMenuRef.BLOCKS), makeTypeListener(JsonFlag.Block));
    	makeMenuOption(menu, Lang.lang.getMenu(LangMenuRef.FLAME), makeTypeListener(JsonFlag.Flame));
    	makeMenuOption(menu, Lang.lang.getMenu(LangMenuRef.PORTAL), makeTypeListener(JsonFlag.Portal));
    	makeMenuOption(menu, Lang.lang.getMenu(LangMenuRef.PANE), makeTypeListener(JsonFlag.Pane));
    	makeMenuOption(menu, Lang.lang.getMenu(LangMenuRef.STAIRS), makeTypeListener(JsonFlag.Stairs));
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
	
	private ActionListener makeLangListener(String lang) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save.save.updateLang(lang);
				Lang.rebindToSave();
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
	
	private ActionListener makeExitListener()
	{
		//TODO: maybe add some other things to this
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
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
		this.message.value = message;
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
		MakeTextBox(panel, title, new RefString(text), false, fieldId);
	}
	
	private void MakeTextBox(JPanel panel, String title, RefString text, RefInt fieldId)
	{
		MakeTextBox(panel, title, text, true, fieldId);
	}
	
	private void MakeTextBox(JPanel panel, String title, RefString text, boolean useRef, RefInt fieldId)
	{
		JLabel ider = new JLabel(title);
		//ider.setBorder(BorderFactory.createEmptyBorder(0, res.getBorder() + 2, 0, 0));
		panel.add(ider);
		
		JTextField label = new JTextField();
		fontChange(label);
		label.setText(text.value);
		label.setMaximumSize(new Dimension(res.getWidth() / 2, res.getFontSize() * 2));
		panel.add(label);
		fieldId.value = panel.getComponentCount() - 1;
		label.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	label.select(0, label.getText().length());
            }

            @Override
            public void focusLost(FocusEvent e) {
                label.select(0, 0);
                if (useRef)
                {
                	text.value = ((JTextField)panel.getComponent(fieldId.value)).getText();                	
                }
            }
        });
	}
	
	private JButton makeButton(JPanel into, String text, String hoverMessage, ActionListener listener)
	{
		JButton button = new JButton(text);
	    //tool tip
		if (hoverMessage != null)
		{
			button.setToolTipText(hoverMessage);			
		}
    	fontChange(button);
    	button.setFocusable(false);
	    
	    button.addActionListener( listener );
	    
	    into.add(button);
	    return button;
	}
	
	private void submitButton(JPanel panel)
	{
		makeButton(panel, Lang.lang.getUIButton(LangUIButtonsRef.SUBMIT), null, makeFilesAfterSubmit(panel, flag)).setFocusable(true);
	}
	
	private JPanel makeWestRegion()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(Lang.lang.getTitle(LangTitlesRef.SELECTIONS)));
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
			
		    MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.NAME), name, nameField);
		    MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.TEXTURE), pref, textureField);
		    MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.MOD_ID), mod_id, modidField);
		}
		//Tool Sets
		else if(flag == JsonFlag.Tool)
		{
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 47, 0, 0));
		    panel.add(sizer);
		    
		    MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.SET_NAME), name, nameField);
		    MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.TEXTURE_PREFIX), pref, textureField);
		    MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.MOD_ID), mod_id, modidField);
		    //postfixes
		    JLabel ider = new JLabel(Lang.lang.getUILabel(LangUIFieldsRef.POSTFIX));
			//ider.setBorder(BorderFactory.createEmptyBorder(0, res.getBorder() + 2, 0, 0));
		    panel.add(ider);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.SWORD), Lang.lang.getDefaultText(LangDefaultValueRef.SWORD), swordField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.AXE), Lang.lang.getDefaultText(LangDefaultValueRef.AXE), axeField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.SHOVEL), Lang.lang.getDefaultText(LangDefaultValueRef.SHOVEL), shovelField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.PICKAXE), Lang.lang.getDefaultText(LangDefaultValueRef.PICKAXE), pickaxeField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.HOE), Lang.lang.getDefaultText(LangDefaultValueRef.HOE), hoeField);
			componentList.addAll(Arrays.asList(new Integer[] { swordField.value, axeField.value, shovelField.value, pickaxeField.value, hoeField.value }));
		}
		//armor sets
		else if(flag == JsonFlag.Armor)
		{
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 47, 0, 0));
		    panel.add(sizer);
		    
		    MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.SET_NAME), name, nameField);
		    MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.TEXTURE_PREFIX), pref, textureField);
		    MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.MOD_ID), mod_id, modidField);
		    //postfixes
		    JLabel ider = new JLabel(Lang.lang.getUILabel(LangUIFieldsRef.POSTFIX));
			//ider.setBorder(BorderFactory.createEmptyBorder(0, res.getBorder() + 2, 0, 0));
			panel.add(ider);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.HELMET), Lang.lang.getDefaultText(LangDefaultValueRef.HELMET), helmetField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.CHESTPLATE), Lang.lang.getDefaultText(LangDefaultValueRef.CHESTPLATE), chestplateField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.LEGGINGS), Lang.lang.getDefaultText(LangDefaultValueRef.LEGGINGS), leggingsField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.BOOTS), Lang.lang.getDefaultText(LangDefaultValueRef.BOOTS), bootsField);
			componentList.addAll(Arrays.asList(new Integer[] { helmetField.value, chestplateField.value, leggingsField.value, bootsField.value }));
		}
		//blocks All
		else if(flag == JsonFlag.Block)
		{
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 40, 0, 0));
		    panel.add(sizer);
			
		    MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.BLOCK_NAME), name, nameField);
		    MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.TEXTURE), pref, textureField);
		    MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.MOD_ID), mod_id, modidField);
		}
		else if(flag == JsonFlag.Flame)
		{
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 40, 0, 0));
			panel.add(sizer);
			
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.FLAME_NAME), name, nameField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.TEXTURE), pref, textureField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.MOD_ID), mod_id, modidField);
		}
		else if(flag == JsonFlag.Portal)
		{
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 40, 0, 0));
			panel.add(sizer);
			
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.BLOCK_NAME), name, nameField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.TEXTURE), pref, textureField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.MOD_ID), mod_id, modidField);
			JLabel ider = new JLabel(Lang.lang.getUILabel(LangUIFieldsRef.POSTFIX));
			panel.add(ider);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.NS_POST), Lang.lang.getDefaultText(LangDefaultValueRef.NS), northSouthField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.EW_POST), Lang.lang.getDefaultText(LangDefaultValueRef.EW), eastWestField);
			componentList.addAll(Arrays.asList(new Integer[] { northSouthField.value, eastWestField.value }));
		}
		else if(flag == JsonFlag.Pane)
		{
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 76, 0, 0));
			panel.add(sizer);
			
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.BLOCK_NAME), name, nameField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.PANE_FACE), pref, textureField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.PANE_EDGE), paneside, sideField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.MOD_ID), mod_id, modidField);
		}
		else if(flag == JsonFlag.Stairs)
		{
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 40, 0, 0));
			panel.add(sizer);
			
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.BLOCK_NAME), name, nameField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.TOP_TEXTURE), top, topField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.BOTTOM_TEXTURE), bottom, bottomField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.SIDE_TEXTURE), side, sideField);
			MakeTextBox(panel, Lang.lang.getUILabel(LangUIFieldsRef.MOD_ID), mod_id, modidField);
		}
		if (flag != JsonFlag.Empty)
		{
			submitButton(panel);			
		}
		contentPane.add(panel, BorderLayout.WEST);
		return panel;
	}
	
	private void tabMaker(String text, JsonPreview prev, boolean inner, int j)
	{
		int innerOption = inner ? j : 0;
		String fileName = prev.getFileName(innerOption);
		
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
		jsons.put(tabPanel, new InnerPreview(prev, innerOption));
		tabMap.put(tabPanel, scroll);
		buttonClose.addActionListener(makeCloseTabListener(tabPanel, scroll, inner, inner ? prev.getOptions().get(j) : ""));
	}
	
	/*private void getUbiquitousText(JPanel panel)
	{
		name = ((JTextField)panel.getComponent(nameField.value)).getText();
		pref = ((JTextField)panel.getComponent(textureField.value)).getText();
		mod_id = ((JTextField)panel.getComponent(modidField.value)).getText();
	}*/
	
	private ActionListener makeFilesAfterSubmit(JPanel panel, JsonFlag kind)
	{
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//getUbiquitousText(panel);
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
					JsonType type = fileTypes.get(i);
					if (type == JsonType.FLAME)
					{
						prevs[i] = new JsonPreview(type, name.value, pref.value, top.value, bottom.value, side.value, mod_id.value)
								.replaceOptions(Arrays.asList(new String[] {
										"_floor", "_floor0", "_floor1", "_side", "_side0", "_side1", "_side_alt", "_side_alt0", "_side_alt1", "_up", "_up0", "_up1", "_up_alt", "_up_alt0", "_up_alt1"
								}));
					}
					else if (type == JsonType.PANE_BLOCK)
					{
						prevs[i] = new JsonPreview(type, name.value, pref.value, top.value, bottom.value, paneside.value, mod_id.value)
								.replaceOptions(Arrays.asList(new String[] {
										"_noside", "_noside_alt", "_post", "_side", "_side_alt"
								}));
					}
					else if (kind == JsonFlag.Portal && type == JsonType.BLOCK_ITEM)
					{
						prevs[i] = new JsonPreview(type, name.value, pref.value, top.value, bottom.value, side.value, mod_id.value);
					}
					else if (kind == JsonFlag.Stairs && type == JsonType.BLOCK_ITEM)
					{
						prevs[i] = new JsonPreview(fileTypes.get(i), name.value, pref.value, top.value, bottom.value, side.value, mod_id.value).replaceOptions("stairs");
					}
					else
					{
						prevs[i] = new JsonPreview(fileTypes.get(i), name.value, pref.value, top.value, bottom.value, side.value, mod_id.value).replaceOptions(components);
					}
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
					tabMap.remove(c);
				}
				else
				{
					//jsons.get(c).getOptions().remove(option);
					jsons.get(c).remove();
					//if(jsons.get(c).getOptions().isEmpty())
					//{
						jsons.remove(c);
						tabMap.remove(c);
					//}
				}
				tabs.remove(scroll);
			}
			
		};
	}
	
	private JPanel makeCenterRegion()
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout());
		panel.setBorder(BorderFactory.createTitledBorder(Lang.lang.getTitle(LangTitlesRef.JSON_BOX)));
		fontChange(panel);
		
		//JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
		tabs.setFocusable(false);
		tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		tabs.setSelectedIndex(currentIndex);
		
		panel.add(tabs);
		contentPane.add(panel, BorderLayout.CENTER);
		return panel;
	}
	
	private ActionListener makeOverwriteListener(boolean newSelection)
	{
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Save.save.updateOverwrite(newSelection);
			}
		};
	}
	
	private ActionListener makeGenerateFoldersListener(boolean newSelection)
	{
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Save.save.updateGenerate(newSelection);
			}
		};
	}
	
	private void makeCheckBox(JPanel into, String text, String hoverMessage, boolean defaultTick, ActionListener listener)
	{
		JCheckBox box = new JCheckBox(text);
		if (hoverMessage != null)
		{
			box.setToolTipText(hoverMessage);			
		}
    	fontChange(box);
    	box.setFocusable(false);
    	box.setSelected(defaultTick);
	    
	    box.addActionListener( listener );
	    
	    into.add(box);
	}
	
	private JPanel makeEastRegion()
	{
		JPanel panel = new JPanel();
		//save all
		panel.setBorder(BorderFactory.createTitledBorder(Lang.lang.getTitle(LangTitlesRef.SAVE)));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		fontChange(panel);
		
		JLabel sizer = new JLabel("");
    	
    	//JScrollPane scroll = new JScrollPane();
		sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - 47, 0, 0));
	    panel.add(sizer);
	    
	    //save as file
	    makeButton(panel, Lang.lang.getUIButton(LangUIButtonsRef.SAVE_AS), Lang.lang.getUIButton(LangUIButtonsRef.SAVE_AS_HOVER), makeSaveAsListener(panel));
	    //save file
	    makeButton(panel, Lang.lang.getUIButton(LangUIButtonsRef.SAVE), Lang.lang.getUIButton(LangUIButtonsRef.SAVE_HOVER), makeSaveListener(panel));
	    
	    makeCheckBox(panel, Lang.lang.getUIButton(LangUIButtonsRef.OVERWRITE), Lang.lang.getUIButton(LangUIButtonsRef.OVERWRITE_HOVER), Save.save.Overwrite(), makeOverwriteListener(!Save.save.Overwrite()));
	    makeCheckBox(panel, Lang.lang.getUIButton(LangUIButtonsRef.GENERATE_FOLD), Lang.lang.getUIButton(LangUIButtonsRef.GENERATE_FOLD_HOVER), Save.save.GenerateFolders(), makeGenerateFoldersListener(!Save.save.GenerateFolders()));
	    contentPane.add(panel, BorderLayout.EAST);
	    return panel;
	}
	
	private int saveJson(String directory, InnerPreview json)
	{
		String fileName = json.getFileName();
		File file = new File(directory + "\\" + fileName);
		FileHandler.createFile(file);
		if (FileHandler.writeFileExists(file, json.getFile()))
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	private int[] saveJsons()
	{
		int[] flags = new int[2];
		flags[0] = 0;
		flags[1] = 0;
		for(Entry<Component, InnerPreview> en : jsons.entrySet())
		{
			//The second condition of the ORs are only relevant if the first condition is false
			if ((Save.save.GenerateFolders() || FileHandler.doesFileSystemItemExist(path.value, en.getValue().getTypeFolder()))
					&& (Save.save.Overwrite() || !FileHandler.doesFileSystemItemExist(path.value + "\\" + en.getValue().getTypeFolder(), en.getValue().getFileName())))
			{
				String tmp = FileHandler.directoryExists(path.value, en.getValue().getTypeFolder());
				flags[saveJson(tmp, en.getValue())]++;
				tabs.remove(tabMap.remove(en.getKey()));
				//indsToRemove.add(j);
			}
			else
			{
				flags[1]++;
				failedSaves.put(en.getKey(), en.getValue());
			}
		}
		return flags;
	}
	
	private void doSaveAction(String localPath)
	{
		String messageFollows = "";
		if(localPath != null)
		{
			path.value = localPath;
			if (!Save.save.updatePath(path.value))
			{
				refreshContent(messageFollows);
				return;
			}
		}
		int[] tmp = saveJsons();
		messageFollows = (tmp[0] > 0 ? (tmp[0] + " " + Lang.lang.getMessage(LangMessageRef.SAVED)) : "")
				+ (tmp[0] > 0 && tmp[1] > 0 ? " " : "")
				+ (tmp[1] > 0 ? (tmp[1] + " " + Lang.lang.getMessage(LangMessageRef.FAILED)) : "")
				+ (tmp[0] > 0 ? "  " + Lang.lang.getMessage(LangMessageRef.ALERT) : "");
		jsons.clear();
		jsons.putAll(failedSaves);
		failedSaves.clear();
		currentIndex = -1;
		//saved
		refreshContent(messageFollows);
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
				save.setCurrentDirectory(new java.io.File(path.value));
				//save
				save.setDialogTitle(Lang.lang.getTitle(LangTitlesRef.SAVE_DIALOG));
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
				
				doSaveAction(localPath);
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
				
				doSaveAction(null);
			}
			
		};
	}
	
	private JPanel makeSouthRegion()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder(Lang.lang.getTitle(LangTitlesRef.MESSAGE_BOX)));
		fontChange(panel);
		
		JTextPane label = new JTextPane();
		label.setEditable(false);
		
		fontChange(label);
		
		//set the read only text "save to: "
		label.setText(Lang.lang.getMessage(LangMessageRef.SAVES_TO) + path.value + "\n" + message.value);
		
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
	
	private void fontChange(JCheckBox box)
	{
		box.setFont(new Font("courier", Font.BOLD, res.getFontSize()));
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
