package templates.json.blockstates;

import java.util.Map;

import templates.json.JsonSerializable;
import util.MapInit;

public class BasicBlockState extends JsonSerializable
{
	//Map<String, Model> variants;
	Map<String, Map<String, Object>> variants;
	
	public BasicBlockState(String modid, String name)
	{
		this(new MapInit<String, Map<String, Object>>().init("normal", new ModelInit(modid, name).get()).get());
	}
	
	public BasicBlockState(Map<String, Map<String, Object>> variants)
	{
		this.variants = variants;
	}
}
