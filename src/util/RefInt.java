package util;

public class RefInt
{
	public Integer value;
	
	public RefInt(int val)
	{
		this.value = val;
	}
	
	public RefInt()
	{
		this(0);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof RefInt)
		{
			return ((RefInt)obj).value == value;
		}
		return false;
	}
}
