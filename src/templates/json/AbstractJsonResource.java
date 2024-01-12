package templates.json;

import java.io.File;

import util.FileHandler;
import util.Reference;

public abstract class AbstractJsonResource extends JsonSerializable
{
	protected AbstractJsonResource()
	{
		load();
	}
	
	protected boolean write()
	{
		String newContent = serialize();
		File file = new File(Reference.DATA_FILE);
		return FileHandler.writeFileExists(file, newContent);
	}
	
	protected abstract void load();
}
