import net.sf.jclec.exprtree.fun.Argument;

public class X extends Argument<Double>
{
	public X()
	{
		super(Double.class, 0);
	}
	
	public boolean equals(Object other)
	{
		return other instanceof X;
	}
	
	public String toString()
	{
		return "X";
	}
}