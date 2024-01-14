package util;

import java.util.HashMap;
import java.util.Map;

public class MapInit<x, y>
{
	private HashMap<x, y> innerMap;

	public MapInit()
	{
		innerMap = new HashMap<>();
	}
	
	public MapInit<x, y> init(x key, y value)
	{
		innerMap.put(key, value);
		return this;
	}
	
	public Map<x, y> get()
	{
		return innerMap;
	}
}
