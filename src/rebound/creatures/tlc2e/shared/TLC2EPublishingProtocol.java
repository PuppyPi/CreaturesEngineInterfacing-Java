package rebound.creatures.tlc2e.shared;

import static java.util.Collections.*;
import static rebound.net.NetworkUtilities.*;
import static rebound.util.BasicExceptionUtilities.*;
import static rebound.util.collections.BasicCollectionUtilities.*;
import static rebound.util.collections.CollectionUtilities.*;
import static rebound.util.gds.util.GDSUtilities.*;
import static rebound.util.objectutil.BasicObjectUtilities.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rebound.bits.DataEncodingUtilities;
import rebound.creatures.tlc2e.data.CaosInjectionType;
import rebound.creatures.tlc2e.data.CaosInjectionType.StandardCaosInjectionType;
import rebound.creatures.tlc2e.data.CaosInjectionType.UnknownCaosInjectionType;
import rebound.creatures.tlc2e.data.CreaturesFilesystem;
import rebound.creatures.tlc2e.data.InstanceCreatorName;
import rebound.creatures.tlc2e.data.InstanceCreatorName.ExplicitInstanceCreator;
import rebound.creatures.tlc2e.data.InstanceCreatorName.StandardInstanceCreators;
import rebound.creatures.tlc2e.data.StandardTLC2EPublishedInstanceListing;
import rebound.exceptions.GenericDataStructuresFormatException;
import rebound.exceptions.NoSuchMappingReturnPath.NoSuchMappingException;
import rebound.net.SimpleNetworkHost;
import rebound.util.collections.prim.PrimitiveCollections.ImmutableByteArrayList;

public class TLC2EPublishingProtocol
{
	public static Object toGDS(StandardTLC2EPublishedInstanceListing listing)
	{
		Map root = new HashMap<>();
		
		Map core = new HashMap<>();
		Map extra = new HashMap<>();
		
		
		
		//CAOS Injection
		{
			CaosInjectionType caosInjectionType = listing.getCaosInjectionType();
			
			Object caosInjectionTypeGDS;
			{
				if (caosInjectionType == null)
					throw new IllegalArgumentException("caosInjectionType can't be null!");
				else if (caosInjectionType instanceof UnknownCaosInjectionType)
					caosInjectionTypeGDS = ((UnknownCaosInjectionType)caosInjectionType).getValue();
				else if (caosInjectionType instanceof StandardCaosInjectionType)
				{
					if (caosInjectionType == StandardCaosInjectionType.TCP)
						caosInjectionTypeGDS = "tcp";
					else if (caosInjectionType == StandardCaosInjectionType.WindowsSharedMemory)
						caosInjectionTypeGDS = "windows-shm";
					else if (caosInjectionType == StandardCaosInjectionType.Absent)
						caosInjectionTypeGDS = "absent";
					else if (caosInjectionType == StandardCaosInjectionType.Prevented)
						caosInjectionTypeGDS = "prevented";
					else
						throw newUnexpectedHardcodedEnumValueExceptionOrNullPointerException(caosInjectionType);
				}
				else
					throw newClassCastExceptionOrNullPointerException(caosInjectionType);
			}
			
			putNewMandatory(core, "caosInjectionType", caosInjectionTypeGDS);
			
			
			
			Integer caosInjectionPort = listing.getCaosInjectionPort();
			
			List<SimpleNetworkHost> caosInjectionHosts = listing.getCaosInjectionHosts();
			
			if (caosInjectionType == StandardCaosInjectionType.TCP)
			{
				putNewMandatory(core, "caosInjectionPort", caosInjectionPort);
				putNewMandatory(core, "caosInjectionHosts", mapToList(Object::toString, caosInjectionHosts));
			}
			else if (caosInjectionType == StandardCaosInjectionType.WindowsSharedMemory)
			{
				putNewMandatory(core, "caosInjectionGameName", listing.getCaosInjectionGameName());
			}
			else
			{
				//nothing, leave them out :3
			}
		}
		
		
		
		putNewMandatory(core, "hidden", listing.isHidden());
		
		putNewMandatory(core, "rwdata", creaturesFilesystemToGDS(listing.getReadWriteData()));
		putNewMandatory(core, "rodatas", mapToList(cfs -> creaturesFilesystemToGDS(cfs), listing.getReadOnlyDatas()));
		
		
		if (listing.getIcon() != null)
			putNewMandatory(extra, "icon", DataEncodingUtilities.encodeBase64Standard(listing.getIcon()));
		
		InstanceCreatorName c = listing.getCreator();
		if (c != StandardInstanceCreators.Unspecified)
			putNewMandatory(extra, "creator", c == StandardInstanceCreators.User ? null : ((ExplicitInstanceCreator)c).getValue());
		
		if (listing.getName() != null)
			putNewMandatory(extra, "name", listing.getName());
		
		if (listing.getDescription() != null)
			putNewMandatory(extra, "description", listing.getDescription());
		
		if (listing.getWorldnames() != null)
			putNewMandatory(extra, "worldnames", listing.getWorldnames());
		
		
		
		
		
		putNewMandatory(root, "e6b02a88-7311-4a27-bbb7-d8f3a2d4e353", core);
		
		if (!extra.isEmpty())
			putNewMandatory(root, "2424f4d5-4888-421d-bd19-ba3d4067598d", extra);
		
		
		return root;
	}
	
	
	
	
	
	/**
	 * Note that extra unsupported things will be implicitly stripped by this!
	 */
	public static StandardTLC2EPublishedInstanceListing fromGDS(Object gds) throws GenericDataStructuresFormatException
	{
		try
		{
			Map root = (Map)gds;
			
			Map core = (Map) getMandatory(root, "e6b02a88-7311-4a27-bbb7-d8f3a2d4e353");
			Map extra = (Map) root.get("2424f4d5-4888-421d-bd19-ba3d4067598d");
			
			if (extra == null)
				extra = emptyMap();
			
			
			
			//CAOS Injection
			CaosInjectionType caosInjectionType;
			Integer caosInjectionPort;
			String caosInjectionGameName;
			List<SimpleNetworkHost> caosInjectionHosts;
			{
				Object caosInjectionTypeGDS = getMandatory(core, "caosInjectionType");
				{
					if (caosInjectionTypeGDS == null)
						throw new IllegalArgumentException("caosInjectionType can't be null!");
					else if (eq(caosInjectionTypeGDS, "tcp"))
						caosInjectionType = StandardCaosInjectionType.TCP;
					else if (eq(caosInjectionTypeGDS, "windows-shm"))
						caosInjectionType = StandardCaosInjectionType.WindowsSharedMemory;
					else if (eq(caosInjectionTypeGDS, "absent"))
						caosInjectionType = StandardCaosInjectionType.Absent;
					else if (eq(caosInjectionTypeGDS, "prevented"))
						caosInjectionType = StandardCaosInjectionType.Prevented;
					else
						caosInjectionType = new UnknownCaosInjectionType((String)caosInjectionTypeGDS);
				}
				
				
				
				
				if (caosInjectionType == StandardCaosInjectionType.TCP)
				{
					if (!core.containsKey("caosInjectionPort"))
						throw new GenericDataStructuresFormatException("caosInjectionPort must be present ifF caosInjectionType = tcp");
					
					if (!core.containsKey("caosInjectionHosts"))
						throw new GenericDataStructuresFormatException("caosInjectionHosts must be present if caosInjectionType = tcp");
					
					if (core.containsKey("caosInjectionGameName"))
						throw new GenericDataStructuresFormatException("caosInjectionGameName must be present only ifF caosInjectionType = windows-shm");
					
					caosInjectionPort = toIntNonnull(getMandatory(core, "caosInjectionPort"));
					caosInjectionHosts = mapToList(s -> parseHost(s), (List<String>)getMandatory(core, "caosInjectionHosts"));
					caosInjectionGameName = null;
					
					if (caosInjectionPort <= 0 || caosInjectionPort > 65535)
						throw new GenericDataStructuresFormatException("caosInjectionPost must be a valid TCP port, not "+caosInjectionPort);
				}
				else
				{
					if (core.containsKey("caosInjectionPort"))
						throw new GenericDataStructuresFormatException("caosInjectionPort must be present only ifF caosInjectionType = tcp");
					
					if (core.containsKey("caosInjectionHosts"))
						throw new GenericDataStructuresFormatException("caosInjectionHosts must be present only ifF caosInjectionType = tcp");
					
					caosInjectionPort = null;
					caosInjectionHosts = null;
					
					if (caosInjectionType == StandardCaosInjectionType.WindowsSharedMemory)
					{
						if (!core.containsKey("caosInjectionGameName"))
							throw new GenericDataStructuresFormatException("caosInjectionHosts must be present if caosInjectionType = tcp");
						
						caosInjectionGameName = (String) getMandatory(core, "caosInjectionGameName");
					}
					else
					{
						if (core.containsKey("caosInjectionGameName"))
							throw new GenericDataStructuresFormatException("caosInjectionGameName must be present only ifF caosInjectionType = windows-shm");
						
						caosInjectionGameName = null;
					}
				}
			}
			
			
			
			boolean hidden = (Boolean) getMandatory(core, "hidden");
			
			CreaturesFilesystem readWriteData = creaturesFilesystemFromGDS((Map<String, String>)getMandatory(core, "rwdata"));
			List<CreaturesFilesystem> readoOnlyDatas = mapToList(cfs -> creaturesFilesystemFromGDS(cfs), (List<Map<String, String>>)getMandatory(core, "rodatas"));
			
			Object iconGDS = extra.get("icon");
			ImmutableByteArrayList icon = iconGDS == null ? null : DataEncodingUtilities.decodeBase64Standard((String)iconGDS);
			
			Object creatorGDS = extra.get("creator");
			InstanceCreatorName creator = creatorGDS == null ? (extra.containsKey("creator") ? StandardInstanceCreators.User : StandardInstanceCreators.Unspecified) : new ExplicitInstanceCreator((String)creatorGDS);
			
			
			String name = (String) extra.get("name");
			String description = (String) extra.get("description");
			
			List<String> worldnames = (List<String>) extra.get("worldnames");
			
			
			
			return new StandardTLC2EPublishedInstanceListing(caosInjectionType, caosInjectionPort, caosInjectionHosts, caosInjectionGameName, hidden, readWriteData, readoOnlyDatas, icon, creator, name, description, worldnames);
		}
		catch (ClassCastException | NullPointerException | NoSuchMappingException | IllegalArgumentException exc)
		{
			throw new GenericDataStructuresFormatException(exc);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static CreaturesFilesystem creaturesFilesystemFromGDS(Map<String, String> map)
	{
		return new CreaturesFilesystem(
		map.get("Main"),
		map.get("Backgrounds"),
		map.get("Body Data"),
		map.get("Bootstrap"),
		map.get("Catalogue"),
		map.get("Creature Galleries"),
		map.get("Genetics"),
		map.get("Images"),
		map.get("Journal"),
		map.get("My Agents"),
		map.get("My Creatures"),
		map.get("My Worlds"),
		map.get("Overlay Data"),
		map.get("Sounds"),
		map.get("Users")
		);
	}
	
	
	
	public static Map<String, String> creaturesFilesystemToGDS(CreaturesFilesystem structured)
	{
		return mapofSome(
		
		"Main", structured.getMain() == null ? null : just(structured.getMain()),
		"Backgrounds", structured.getBackgrounds() == null ? null : just(structured.getBackgrounds()),
		"Body Data", structured.getBodyData() == null ? null : just(structured.getBodyData()),
		"Bootstrap", structured.getBootstrap() == null ? null : just(structured.getBootstrap()),
		"Catalogue", structured.getCatalogue() == null ? null : just(structured.getCatalogue()),
		"Creature Galleries", structured.getCreatureGalleries() == null ? null : just(structured.getCreatureGalleries()),
		"Genetics", structured.getGenetics() == null ? null : just(structured.getGenetics()),
		"Images", structured.getImages() == null ? null : just(structured.getImages()),
		"Journal", structured.getJournal() == null ? null : just(structured.getJournal()),
		"My Agents", structured.getMyAgents() == null ? null : just(structured.getMyAgents()),
		"My Creatures", structured.getMyCreatures() == null ? null : just(structured.getMyCreatures()),
		"My Worlds", structured.getMyWorlds() == null ? null : just(structured.getMyWorlds()),
		"Overlay Data", structured.getOverlayData() == null ? null : just(structured.getOverlayData()),
		"Sounds", structured.getSounds() == null ? null : just(structured.getSounds()),
		"Users", structured.getUsers() == null ? null : just(structured.getUsers())
		
		);
	}
}
