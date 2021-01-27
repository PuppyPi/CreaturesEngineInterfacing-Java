package rebound.creatures.tlc2e.data;

import static java.util.Objects.*;
import static rebound.text.StringUtilities.*;
import static rebound.util.BasicExceptionUtilities.*;
import static rebound.util.collections.CollectionUtilities.*;
import static rebound.util.objectutil.ObjectUtilities.*;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import rebound.annotations.semantic.operationspecification.HashableType;
import rebound.annotations.semantic.simpledata.Emptyable;
import rebound.annotations.semantic.simpledata.Nonempty;
import rebound.creatures.tlc2e.data.CaosInjectionType.StandardCaosInjectionType;
import rebound.creatures.tlc2e.data.CaosInjectionType.UnknownCaosInjectionType;
import rebound.creatures.tlc2e.data.InstanceCreatorName.ExplicitInstanceCreator;
import rebound.creatures.tlc2e.data.InstanceCreatorName.StandardInstanceCreators;
import rebound.net.SimpleNetworkHost;
import rebound.util.collections.prim.PrimitiveCollections.ImmutableByteArrayList;

//TODO!! Add game name to WindowsShm into TLC2E Publishing protocol and in here!!!
//TODO!! Add running-manual to the protocol so people can use this system to add engines manually for autodiscovery/autoconfig! :DD

/**
 * Refer to the spec for the meaning of these fields :>
 * https://github.com/PuppyPi/TransientLC2E/blob/master/docs/Publishing%20Protocol.txt
 */
@HashableType
public class StandardTLC2EPublishedInstanceListing
{
	protected final @Nonnull CaosInjectionType caosInjectionType;
	
	protected final @Nullable Integer caosInjectionPort;
	protected final @Nullable @Nonempty List<SimpleNetworkHost> caosInjectionHosts;
	protected final @Nullable String caosInjectionGameName;
	protected final boolean hidden;
	protected final @Nonnull CreaturesFilesystem readWriteData;
	protected final @Nonnull @Emptyable List<CreaturesFilesystem> readoOnlyDatas;
	
	protected final @Nullable ImmutableByteArrayList icon;  //PNG format is standard
	protected final @Nonnull InstanceCreatorName creator;
	protected final @Nullable String name;
	protected final @Nullable String description;
	protected final @Nullable List<String> worldnames;
	
	
	public StandardTLC2EPublishedInstanceListing(CaosInjectionType caosInjectionType, Integer caosInjectionPort, List<SimpleNetworkHost> caosInjectionHosts, String caosInjectionGameName, boolean hidden, CreaturesFilesystem readWriteData, List<CreaturesFilesystem> readoOnlyDatas, ImmutableByteArrayList icon, InstanceCreatorName creator, String name, String description, List<String> worldnames)
	{
		this.caosInjectionType = requireNonNull(caosInjectionType);
		
		
		if (caosInjectionType == StandardCaosInjectionType.TCP)
		{
			requireNonNull(caosInjectionPort);
			requireNonNull(caosInjectionHosts);
			requireNull(caosInjectionGameName);
			requireNonEmpty(caosInjectionHosts);
			
			if (caosInjectionPort <= 0 || caosInjectionPort > 65535)
				throw new IllegalArgumentException("Invalid TCP port number: "+caosInjectionPort);
		}
		else if (caosInjectionType == StandardCaosInjectionType.WindowsSharedMemory)
		{
			requireNull(caosInjectionPort);
			requireNull(caosInjectionHosts);
			requireNonNull(caosInjectionGameName);
			requireNonEmpty(caosInjectionGameName);
		}
		else if (caosInjectionType == StandardCaosInjectionType.Absent || caosInjectionType == StandardCaosInjectionType.Prevented)
		{
			requireNull(caosInjectionPort);
			requireNull(caosInjectionHosts);
			requireNull(caosInjectionGameName);
		}
		else if (caosInjectionType instanceof UnknownCaosInjectionType)
		{
			//who knows what requirements there are if it's an Unknown (future) type!
		}
		else
		{
			throw newClassCastExceptionOrNullPointerException(caosInjectionType);
		}
		
		
		if (!(creator instanceof StandardInstanceCreators) && !(creator instanceof ExplicitInstanceCreator))
			throw newClassCastExceptionOrNullPointerException(creator);
		
		
		this.caosInjectionPort = caosInjectionPort;
		this.caosInjectionHosts = caosInjectionHosts;
		this.caosInjectionGameName = caosInjectionGameName;
		
		this.hidden = hidden;
		this.readWriteData = requireNonNull(readWriteData);
		this.readoOnlyDatas = requireNonNull(requireNonNullElements(readoOnlyDatas));
		this.icon = icon;
		this.creator = requireNonNull(creator);
		this.name = name;
		this.description = description;
		this.worldnames = worldnames;
	}
	
	public CaosInjectionType getCaosInjectionType()
	{
		return caosInjectionType;
	}
	
	public Integer getCaosInjectionPort()
	{
		return caosInjectionPort;
	}
	
	/**
	 * Note that if some over-the-network system uses this, it should rewrite this field to be whatever address the client that this {@link StandardTLC2EPublishedInstanceListing} is given to should use to connect, and not include loopbacks (eg, 127.0.0.1) or wildcards (eg, 0.0.0.0) !
	 */
	public List<SimpleNetworkHost> getCaosInjectionHosts()
	{
		return caosInjectionHosts;
	}
	
	public String getCaosInjectionGameName()
	{
		return caosInjectionGameName;
	}
	
	public boolean isHidden()
	{
		return hidden;
	}
	
	public CreaturesFilesystem getReadWriteData()
	{
		return readWriteData;
	}
	
	public List<CreaturesFilesystem> getReadOnlyDatas()
	{
		return readoOnlyDatas;
	}
	
	public ImmutableByteArrayList getIcon()
	{
		return icon;
	}
	
	public InstanceCreatorName getCreator()
	{
		return creator;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public List<String> getWorldnames()
	{
		return worldnames;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caosInjectionHosts == null) ? 0 : caosInjectionHosts.hashCode());
		result = prime * result + ((caosInjectionPort == null) ? 0 : caosInjectionPort.hashCode());
		result = prime * result + ((caosInjectionType == null) ? 0 : caosInjectionType.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (hidden ? 1231 : 1237);
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((readWriteData == null) ? 0 : readWriteData.hashCode());
		result = prime * result + ((readoOnlyDatas == null) ? 0 : readoOnlyDatas.hashCode());
		result = prime * result + ((worldnames == null) ? 0 : worldnames.hashCode());
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
		StandardTLC2EPublishedInstanceListing other = (StandardTLC2EPublishedInstanceListing) obj;
		if (caosInjectionHosts == null)
		{
			if (other.caosInjectionHosts != null)
				return false;
		}
		else if (!caosInjectionHosts.equals(other.caosInjectionHosts))
			return false;
		if (caosInjectionPort == null)
		{
			if (other.caosInjectionPort != null)
				return false;
		}
		else if (!caosInjectionPort.equals(other.caosInjectionPort))
			return false;
		if (caosInjectionType == null)
		{
			if (other.caosInjectionType != null)
				return false;
		}
		else if (!caosInjectionType.equals(other.caosInjectionType))
			return false;
		if (creator == null)
		{
			if (other.creator != null)
				return false;
		}
		else if (!creator.equals(other.creator))
			return false;
		if (description == null)
		{
			if (other.description != null)
				return false;
		}
		else if (!description.equals(other.description))
			return false;
		if (hidden != other.hidden)
			return false;
		if (icon == null)
		{
			if (other.icon != null)
				return false;
		}
		else if (!icon.equals(other.icon))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (readWriteData == null)
		{
			if (other.readWriteData != null)
				return false;
		}
		else if (!readWriteData.equals(other.readWriteData))
			return false;
		if (readoOnlyDatas == null)
		{
			if (other.readoOnlyDatas != null)
				return false;
		}
		else if (!readoOnlyDatas.equals(other.readoOnlyDatas))
			return false;
		if (worldnames == null)
		{
			if (other.worldnames != null)
				return false;
		}
		else if (!worldnames.equals(other.worldnames))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "StandardTLC2EPublishedInstanceListing [caosInjectionType=" + caosInjectionType + ", caosInjectionPort=" + caosInjectionPort + ", caosInjectionHosts=" + caosInjectionHosts + ", hidden=" + hidden + ", rwdata=" + readWriteData + ", rodatas=" + readoOnlyDatas + ", icon=" + icon + ", creator=" + creator + ", name=" + name + ", description=" + description + ", worldnames=" + worldnames + "]";
	}
}
