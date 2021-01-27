package rebound.creatures.caosinjection;

public class ExtendedCaosInjectionResponse
{
	protected final boolean success;
	protected final String output;
	
	public ExtendedCaosInjectionResponse(boolean success, String output)
	{
		this.success = success;
		this.output = output;
	}
	
	public boolean isSuccess()
	{
		return success;
	}
	
	public String getOutput()
	{
		return output;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((output == null) ? 0 : output.hashCode());
		result = prime * result + (success ? 1231 : 1237);
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtendedCaosInjectionResponse other = (ExtendedCaosInjectionResponse) obj;
		if (output == null)
		{
			if (other.output != null)
				return false;
		}
		else if (!output.equals(other.output))
			return false;
		if (success != other.success)
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "ExtendedCaosInjectionResponse [success=" + success + ", output=" + output + "]";
	}
}
