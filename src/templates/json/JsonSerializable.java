package templates.json;

import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class JsonSerializable
{
	public String serialize()
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String serial = gson.toJson(this);
		//the rest of this method exists because f*ck you google that's why.  No way to change indent
		String prettier = "";
		String[] butchered = serial.split("\n");
		for (int i = 0; i < butchered.length; i++)
		{
			if (butchered[i].startsWith(" "))
			{
				int j = 0;
				while (butchered[i].charAt(j) == ' ')
				{
					j++;
				}
				//google puts 2 spaces for indent.  I want 4 to match most programs auto formating when editing jsons
				//TODO: setting for indent spaces
				char[] extraIndent  = new char[j];
				Arrays.fill(extraIndent, ' ');
				butchered[i] = new String(extraIndent) + butchered[i];
			}
			prettier = prettier + (prettier.isEmpty() ? "" : "\n") + butchered[i];
		}
		return prettier;
	}
}
