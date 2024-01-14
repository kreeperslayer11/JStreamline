package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapList<x, y>
{
	private List<Map<x, y>> inner;
	int next = 0;
	int max;
	
	public MapList(int i)
	{
		this.inner = new ArrayList<Map<x, y>>(i);
		this.max = i;
	}
	
	public MapList<x, y> init(Map<x, y> map)
	{
		if (next < max)
		{
			inner.add(map);
		}
		return this;			
	}
	
	public List<Map<x, y>> get()
	{
		return inner;
	}
}
