package templates.json.blockstates;

import java.util.Map;

import util.MapInit;

public class ModelInit
{
	private Map<String, Object> model;
	
	public ModelInit(String modid, String texture)
	{
		this.model = new MapInit<String, Object>().init("model", modid + ":" + texture).get();
	}
	
	public ModelInit init(String key, Object value)
	{
		this.model.put(key, value);
		return this;
	}
	
	public Map<String, Object> get()
	{
		return model;
	}
}
