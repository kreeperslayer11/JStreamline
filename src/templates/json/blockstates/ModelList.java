package templates.json.blockstates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModelList
{
	private List<Map<String, Object>> inner;
	int next = 0;
	int max;
	
	public ModelList(int i)
	{
		this.inner = new ArrayList<Map<String, Object>>(i);
		this.max = i;
	}
	
	public ModelList init(Map<String, Object> map)
	{
		if (next < max)
		{
			inner.add(map);
		}
		return this;			
	}
	
	public List<Map<String, Object>> get()
	{
		return inner;
	}
}
