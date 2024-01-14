package util;

public class RefString
{
	public String value;
	
	public RefString(String val)
	{
		this.value = val;
	}
	
	public RefString()
	{
		this("");
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof RefString)
		{
			return ((RefString)obj).value == value;
		}
		return false;
	}
}
