package rebound.creatures.tlc2e.data;

import rebound.annotations.semantic.operationspecification.HashableType;
import rebound.creatures.tlc2e.data.InstanceCreatorName.ExplicitInstanceCreator;

@HashableType
public interface CaosInjectionType
{
	/**
	 * Refer to the spec for the meaning of these :>
	 * https://github.com/PuppyPi/TransientLC2E/blob/master/docs/Publishing%20Protocol.txt
	 */
	public static enum StandardCaosInjectionType
	implements CaosInjectionType
	{
		TCP,
		WindowsSharedMemory,
		Absent,
		Prevented,
	}
	
	
	@HashableType
	public static class UnknownCaosInjectionType
	implements CaosInjectionType
	{
		protected final String value;
		
		public UnknownCaosInjectionType(String value)
		{
			this.value = value;
		}
		
		public String getValue()
		{
			return value;
		}
		
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
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
			ExplicitInstanceCreator other = (ExplicitInstanceCreator) obj;
			if (value == null)
			{
				if (other.value != null)
					return false;
			}
			else if (!value.equals(other.value))
				return false;
			return true;
		}
		
		@Override
		public String toString()
		{
			return getValue();
		}
	}
}
