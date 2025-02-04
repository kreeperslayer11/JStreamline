package main;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.FocusEvent.Cause;
import java.awt.font.FontRenderContext;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.*;

import templates.json.datareading.AutoGen;
import templates.json.datareading.Template;
import templates.json.datareading.options.Field;
import templates.json.datareading.options.FieldHandler;
import templates.json.datareading.options.FieldSection;
import templates.json.savedata.lang.Lang;
import templates.json.savedata.settings.Save;
import util.FileHandler;
import util.InnerPreview;
import util.Language;
import util.RefInt;
import util.RefString;
import util.ResVals;
import util.Resolution;
import util.reference.LangMenuRef;
import util.reference.LangMessageRef;
import util.reference.LangResRef;
import util.reference.LangTitlesRef;
import util.reference.LangUIButtonsRef;

public class Window 
{
	private RefString WhichOptionIsOpen;
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
	private RefString path, message;
	
	//Maps a Field to the index in componentList
	private HashMap<Field, RefInt> fieldUIIds = new HashMap<>();
	private String[] possibleTemplates;
	private ArrayList<Integer> componentList = new ArrayList<>();
	
	private String ClickSelectMode;
	private String TabSelectMode;
	
	public Window(JFrame frame, JPanel contentPane, Resolution res, String path, String reso, String modeClick, String modeTab)
	{
		this.WhichOptionIsOpen = new RefString("");
		
		//Here we create a single field for each field id specified in the json files, which are stored in AutoGen.gen
		ArrayList<Template> alltemplates = AutoGen.gen.getTemplates();
		possibleTemplates = new String[alltemplates.size()];
		for (int i = 0; i < alltemplates.size(); i++)
		{
			possibleTemplates[i] = alltemplates.get(i).whoAmI();
			ArrayList<FieldSection> sectionsForWhoAmI = FieldHandler.handler.acquireSectionsForMe(alltemplates.get(i).whoAmI());
			for (int j = 0; j < sectionsForWhoAmI.size(); j++)
			{
				FieldSection section = sectionsForWhoAmI.get(j);
				while(section.HasNextField())
				{
					Field field = section.getNextField();
					if (!fieldUIIds.containsKey(field))
					{
						fieldUIIds.put(field, new RefInt());
					}
				}
			}
		}
		
		this.frame = frame;
		this.contentPane = contentPane;
		this.res = res;
		this.path = new RefString(path);
		this.message = new RefString("");
		
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
		
		ClickSelectMode = modeClick;
		TabSelectMode = modeTab;
		
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
    	
    	sub = makeMenu(pre, Lang.lang.getMenu(LangMenuRef.SELECTMODE_CLICK));
    	makeMenuOption(sub, Lang.lang.getMenu(LangMenuRef.MODE_HIGHLIGHT), makeClickModeListener(LangMenuRef.MODE_HIGHLIGHT));
    	makeMenuOption(sub, Lang.lang.getMenu(LangMenuRef.MODE_CURSORPRE), makeClickModeListener(LangMenuRef.MODE_CURSORPRE));
    	makeMenuOption(sub, Lang.lang.getMenu(LangMenuRef.MODE_CURSORPOST), makeClickModeListener(LangMenuRef.MODE_CURSORPOST));
    	makeMenuOption(sub, Lang.lang.getMenu(LangMenuRef.MODE_CURSORCLICK), makeClickModeListener(LangMenuRef.MODE_CURSORCLICK));
    	
    	sub = makeMenu(pre, Lang.lang.getMenu(LangMenuRef.SELECTMODE_TAB));
    	makeMenuOption(sub, Lang.lang.getMenu(LangMenuRef.MODE_HIGHLIGHT), makeTabModeListener(LangMenuRef.MODE_HIGHLIGHT));
    	makeMenuOption(sub, Lang.lang.getMenu(LangMenuRef.MODE_CURSORPRE), makeTabModeListener(LangMenuRef.MODE_CURSORPRE));
    	makeMenuOption(sub, Lang.lang.getMenu(LangMenuRef.MODE_CURSORPOST), makeTabModeListener(LangMenuRef.MODE_CURSORPOST));
    	
    	makeMenuOption(menu, Lang.lang.getMenu(LangMenuRef.EXIT), makeExitListener());//exit
    	//END FILE==============================================================
    	//TYPES=================================================================
    	menu = makeMenu(menuBar, Lang.lang.getMenu(LangMenuRef.TYPES), KeyEvent.VK_T);
    	//TYPES SUB MENU========================================================
    	for (int i = 0; i < possibleTemplates.length; i++)
    	{
    		makeMenuOption(menu, Lang.lang.getMenu(possibleTemplates[i]), makeTypeListener(possibleTemplates[i]));
    	}
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
	
	private ActionListener makeClickModeListener(String mode) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save.save.updateSelectModeClick(mode);
				ClickSelectMode = mode;
			}
		};
	}
	
	private ActionListener makeTabModeListener(String mode) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save.save.updateSelectModeTab(mode);
				TabSelectMode = mode;
			}
		};
	}
	
	private ActionListener makeTypeListener(String whoAmI)
	{
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WhichOptionIsOpen.value = whoAmI;
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
				WhichOptionIsOpen.value = "";
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
	
	private void MoveCursorOnSelect(boolean clickedIn, String ModeType, JTextField field)
	{
		if (ModeType.equals(LangMenuRef.MODE_HIGHLIGHT))
    	{
    		field.select(0, field.getText().length());
    	}
    	else if (ModeType.equals(LangMenuRef.MODE_CURSORPRE))
    	{
    		field.setCaretPosition(0);
    	}
    	else if (ModeType.equals(LangMenuRef.MODE_CURSORPOST)) 
    	{
    		field.setCaretPosition(field.getText().length());
    	}
    	else if (!clickedIn && ModeType.equals(LangMenuRef.MODE_CURSORCLICK))
    	{
    		//Only reached if you hack the settings file to give SelectModeTab MODE_CURSORCLICK, which it is incapable of getting through the UI
    		field.select(0, field.getText().length());
    	}
	}
	
	/***
	 * @return the display length of the longest text in
	 */
	private int MakeTextBox(JPanel panel, String title, Field text)
	{
		JLabel ider = new JLabel(title);
		//ider.setBorder(BorderFactory.createEmptyBorder(0, res.getBorder() + 2, 0, 0));
		panel.add(ider);
		
		JTextField label = new JTextField();
		fontChange(label);
		label.setText(text.getFiller());
		label.setMaximumSize(new Dimension(res.getWidth() / 2, res.getFontSize() * 2));
		int width = (int)ider.getFont().getStringBounds(ider.getText(), new FontRenderContext(null, false, false)).getWidth();
		System.out.println(ider.getText() + width);
		panel.add(label);
		fieldUIIds.get(text).value = panel.getComponentCount() - 1;
		label.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	if (e.getCause().equals(Cause.MOUSE_EVENT))
            	{
            		MoveCursorOnSelect(true, ClickSelectMode, label);
            	}
            	else
            	{
            		MoveCursorOnSelect(false, TabSelectMode, label);
            	}
            }

            @Override
            public void focusLost(FocusEvent e) {
                label.select(0, 0);
                text.updateCurrentFiller(((JTextField)panel.getComponent(fieldUIIds.get(text).value)).getText());
                //This is done to enforce int only fields with like those with id i1, i2, ...
                if (!((JTextField)panel.getComponent(fieldUIIds.get(text).value)).getText().equals(text.getCurrentFiller()))
                {
                	((JTextField)panel.getComponent(fieldUIIds.get(text).value)).setText(text.getCurrentFiller());
                }
            }
        });
		return width;
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
		makeButton(panel, Lang.lang.getUIButton(LangUIButtonsRef.SUBMIT), null, makeFilesAfterSubmit(panel, WhichOptionIsOpen.value)).setFocusable(true);
	}
	
	private JPanel makeWestRegion()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(Lang.lang.getTitle(LangTitlesRef.SELECTIONS)));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		fontChange(panel);
		
		JLabel sizer = new JLabel("");
		
		componentList.clear();
		
		if (WhichOptionIsOpen.value.equals(""))
		{
			sizer.setBorder(BorderFactory.createEmptyBorder(0, res.getWidth() / 4, 0, 0));
		    panel.add(sizer);
		}
		else
		{
			panel.add(sizer);
			
			ArrayList<FieldSection> fields = FieldHandler.handler.acquireSectionsForMe(WhichOptionIsOpen.value);
			int smallestWidth = res.getWidth();
			int largestWidth = 0;
			for (int i = 0; i < fields.size(); i++)
			{
				FieldSection section = fields.get(i);
				String title = section.getSectionTitle();
				if (title != "")
				{
					JLabel sectionTitle = new JLabel(title);
					panel.add(sectionTitle);
				}
				while(section.HasNextField())
				{
					Field field = section.getNextField();
					int textWidth = MakeTextBox(panel, field.getTitle(), field);
					largestWidth = Math.max(textWidth, largestWidth);
					smallestWidth = Math.min(textWidth, smallestWidth);
				}
			}
			sizer.setBorder(BorderFactory.createEmptyBorder(0, (res.getWidth() / 4) - (largestWidth - smallestWidth), 0, 0));
			System.out.println((res.getWidth() / 4) + " - " + (largestWidth - smallestWidth) + " to " + ((res.getWidth() / 4) - (largestWidth - smallestWidth)));
			
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
		buttonClose.addActionListener(makeCloseTabListener(tabPanel, scroll, inner));
	}
	
	/*private void getUbiquitousText(JPanel panel)
	{
		name = ((JTextField)panel.getComponent(nameField.value)).getText();
		pref = ((JTextField)panel.getComponent(textureField.value)).getText();
		mod_id = ((JTextField)panel.getComponent(modidField.value)).getText();
	}*/
	
	private Template findTemplateForMe(String WhoAmI)
	{
		Template template = null;;
		ArrayList<Template> templates = AutoGen.gen.getTemplates();
		for (int i = 0; i < templates.size(); i++)
		{
			if (templates.get(i).whoAmI().equals(WhoAmI))
			{
				template = templates.get(i);
			}
		}
		return template;
	}
	
	private ActionListener makeFilesAfterSubmit(JPanel panel, String which)
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
				
				Template template = findTemplateForMe(which);
				if (template == null)
				{
					return;
				}
				
				JsonPreview preview = new JsonPreview(template);
				
				int fileCount = preview.size();
				for (int i = 0; i < fileCount; i++)
				{
					tabMaker(preview.getFiles().get(i).getContents(), preview, true, i);
				}
			}
		};
	}
	
	private ActionListener makeCloseTabListener(Component c, Component scroll, boolean inner)
	{
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!inner)
				{
					jsons.remove(c);
					tabMap.remove(c);
				}
				else
				{
					jsons.get(c).remove();
					jsons.remove(c);
					tabMap.remove(c);
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
