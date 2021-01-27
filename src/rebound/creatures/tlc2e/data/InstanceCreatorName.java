package rebound.creatures.tlc2e.data;

import rebound.annotations.semantic.operationspecification.HashableType;

@HashableType
public interface InstanceCreatorName
{
	public static enum StandardInstanceCreators
	implements InstanceCreatorName
	{
		Unspecified,
		User,
	}
	
	
	@HashableType
	public static class ExplicitInstanceCreator
	implements InstanceCreatorName
	{
		protected final String value;
		
		public ExplicitInstanceCreator(String value)
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
