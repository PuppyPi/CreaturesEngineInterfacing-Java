package rebound.creatures.caosinjection;

public class OriginalShmProtocolCaosInjectionResponse
{
	protected final int resultCode;
	protected final String output;
	
	public OriginalShmProtocolCaosInjectionResponse(int resultCode, String output)
	{
		this.resultCode = resultCode;
		this.output = output;
	}
	
	/**
	 * All I know is that this is 0 for success (and thus anything else is a failure!)
	 * Also it's probably one byte: [0, 256)
	 */
	public int getResultCode()
	{
		return resultCode;
	}
	
	public boolean isSuccess()
	{
		return getResultCode() == 0;
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
		result = prime * result + resultCode;
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
		OriginalShmProtocolCaosInjectionResponse other = (OriginalShmProtocolCaosInjectionResponse) obj;
		if (output == null)
		{
			if (other.output != null)
				return false;
		}
		else if (!output.equals(other.output))
			return false;
		if (resultCode != other.resultCode)
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "OriginalShmProtocolCaosInjectionResponse [resultCode=" + resultCode + ", output=" + output + "]";
	}
}
