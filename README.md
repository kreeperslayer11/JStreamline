# JStreamline

JStreamline is a program to create Json Files for Blocks and Items when modding Minecraft 1.12.2

If the files in a different version are the same as 1.12.2, it should work for that version without modification.

It generates files based on string replacement in json files called "Templates"

You can create your own templates, as long as they have all the necessary parts:

**Note:** You will want to update the lang file along with creating a new template.


---------------------------------------------------------------------------------------------------------------
# Templates

Every Template File contains an unnamed json object with a single array named "Options"

The Options array is an array of objects representing a single OPTION from the "Types" menu

Each Option object contains 3 things

1. **"WhoAmI"**  
	this is a key that corresponds to an entry in the lang file.  
	For example, if you had  
	"WhoAmI": "Cheese"  
	<br/>
	It would look for the key: "Cheese Json Type" in the lang file and place that in the Types Menu  
	If you had  
	"Cheese Json Type": "Cheesy"  
	in the "Menus" object in the lang file, a new option named "Cheesy" will be added to the Types menu  
	If you do not, a new option named "Not FoundCheese" will be added to the Types menu  

2. **"Required" array**  
	this is an array of required fields that will populate in the "Selections" section of the JStreamline UI  
	<br/>
	You can create subsections by placing the fields in separate objects inside the "Required" array  
	<br/>

3. **"Template" array**  
	this is an array of files that will be generated and where they will be saved  
	<br/>
	First, let's talk about String replacement.  
	JStreamline will look at your template and find every \*\*id\*\* and replace it with the current value of that field.  
	<br/>
	for example, if \*\*3\*\* is in the template, it will replace it with the Mod_ID field, since that is what uses id "3"  

---------------------------------------------------------------------------------------------------------------
##### **Each object in the "Required" array needs 2 things**

1. **"SectionName"**  
	Typically, you leave this as an empty string for the first section object.  
	The SectionName field applies a subsection name above all the fields in the object
2. **"SectionFields" array**
	An array of objects representing a field

---------------------------------------------------------------------------------------------------------------
##### **Each field object in this array requires 3 things**  

1. **"id"**  
	any string value.  However, certain values have extra functionality  
2. **"Title"**  
	this is a key that corresponds to an entry in the lang file.  
	For example, if you had  
	"Title": "Spice"  
	<br/>
	It would look for the key: "Spice" in the "UILabels" object in the lang file  
	this is used to label the field  
	<br/>
	Missing lang file data will result in a situation just like with "WhoAmI"

3. **"Default"**  
	this is a key that corresponds to an entry in the lang file.  
	for example, if you had  
	"Default": "Pepper"  
	<br/>
	It would look for the key: "Pepper" in the "DefaultValues" object in the lang file  
	this is used to fill default values for the field  
	<br/>
	Missing lang file data will result in a situation just like with "WhoAmI"  

---------------------------------------------------------------------------------------------------------------
###### **ID Special Functions:**
1. **Saving**  
	any number "1", "2", etc... will have the value entered carried over to  
	any field with the same id in a different Type.  
	Here are used ids for precreated templates  
	<br/>
	"1" is used for the "Name" field  
	"2" is used for the "Texture Name" field  
	"3" is used for the "Mod_ID" field  
	<br/>
2. **Iterating**  
	any value beggining with "i" will be an iterator field  
	this allows you to create a dynamic number of similar lines  
	<br/>
	iterator fields only take integer input  
	<br/>
	see "Template" array section for more details  
	<br/>
	see Plants template file, Crop for an example
	<br/>  
3. **Other**  
	any id that contains more than numeric data and does not begin with i  
	will have the value entered remembered only for that field in that Type  
	
So, for example, if you leave the Type A, go to Type B, then return to Type A,  
your previous entries into non numeric id fields from Type A will return  
but any edits you made to numeric id fields in Type B will carry over to Type A  
	
---------------------------------------------------------------------------------------------------------------
##### **Each object in the "Template" array needs 3 things**

1. **"FileName"**  
	What you want the file this object is representing to be named when saved.  
	String replacement is in effect.  Typically, we use \*\*1\*\* here in some capacity  

2. **"SaveTo"**  
	Where you want the file this object is representing to be saved when saved  
	<br/>
	_JStreamline_ asks the user to supply their assets folder, so this should begin with the Mod_ID  
	"\*\*3\*\*/path"  

3. **"Contents"**  
	This contains what the file will contain  
	
4. **"Iterate" array**  
	This is a 4th item that becomes needed **only** when you have an iterate id (starting with "i")  
	This is an array of objects denoting what the iterate id field will iterate over. it has 2 items

---------------------------------------------------------------------------------------------------------------
###### **Contents Example**

```json
{
	"parent": "modid:block/name"
}
```

you would have a Contents object like so:  

```json
"Contents": {
	"parent": "**3**:block/**1**"
}
```

---------------------------------------------------------------------------------------------------------------
###### **Iterate Example**

1. **"For"** - the id of the field
		
2. **"At"** array  
	an array of strings with 2 valid values

###### **Those valid At values are**

**"ContentLevel"**  
This allows JStreamline to write multiple lines of nearly identical json a variable number of times
Let's say you want to write  

```json
"0": { "thing": "val" },
"1": { "thing": "val" },
"2": { "thing": "val" }
```
You would have a field with an id "i1" and enter the value 2 into it  
You would provide "ContentLevel" to "Iterate"  
You would write  

```json
"**i1[0]**: "\"**i1**\": { \"thing\": \"val\" }"
```
in "Contents" in place of those 3 lines  
<br/>

**"FileLevel"**
This lets you generate nearly identical files a variable number of times  
Let's say you want 4 files named  
- thing0.json
- thing1.json
- thing2.json
- thing3.json

You would have a field with an id "i1" and enter the value 3 into it  
You would provide "FileLevel" to "Iterate"  
You would write  

```json
"thing**i1**"
```
in "FileName"

---------------------------------------------------------------------------------------------------------------

# Lang files.

You can create your own lang files for any language you want, simply name it "lang_languageName" and only change the values
and not the keys.  

JStreamline will then add an option "languageName" to the File > Options > Language menu.