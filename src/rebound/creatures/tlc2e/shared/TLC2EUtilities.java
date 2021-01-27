package rebound.creatures.tlc2e.shared;

import static rebound.math.SmallIntegerMathUtilities.*;
import static rebound.net.NetworkUtilities.*;
import static rebound.util.CodeHinting.*;
import static rebound.util.collections.CollectionUtilities.*;
import static rebound.util.objectutil.BasicObjectUtilities.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import rebound.annotations.semantic.reachability.PossiblySnapshotPossiblyLiveValue;
import rebound.creatures.caosinjection.BasicCaosInjector;
import rebound.creatures.caosinjection.ExtendedCaosInjector;
import rebound.creatures.caosinjection.impls.ExtendedFromBasicCaosInjectorDecorator;
import rebound.creatures.caosinjection.impls.tcp.TCPCaosInjector;
import rebound.creatures.tlc2e.data.CaosInjectionType;
import rebound.creatures.tlc2e.data.CaosInjectionType.StandardCaosInjectionType;
import rebound.creatures.tlc2e.data.StandardTLC2EPublishedInstanceListing;
import rebound.exceptions.NotYetImplementedException;
import rebound.net.SimpleNetworkHost;

public class TLC2EUtilities
{
	/**
	 * @throws IllegalArgumentException if caos injection is unavailable or using a newer protocol than this code supports
	 */
	public static BasicCaosInjector newBasicCaosInjectorForTLC2EListing(StandardTLC2EPublishedInstanceListing listing) throws IllegalArgumentException
	{
		CaosInjectionType caosInjectionType = listing.getCaosInjectionType();
		
		if (caosInjectionType == StandardCaosInjectionType.TCP)
			return new TCPCaosInjector(listing.getCaosInjectionHosts().get(0), listing.getCaosInjectionPort());
		else if (caosInjectionType == StandardCaosInjectionType.WindowsSharedMemory)
			throw new NotYetImplementedException();  //TODO!?! :DDD
		else
			throw new IllegalArgumentException("Tried to initialize CAOS injection for injection-type: "+caosInjectionType);
	}
	
	
	
	/**
	 * @throws IllegalArgumentException if caos injection is unavailable or using a newer protocol than this code supports
	 */
	public static ExtendedCaosInjector newExtendedCaosInjectorForTLC2EListing(StandardTLC2EPublishedInstanceListing listing) throws IllegalArgumentException
	{
		BasicCaosInjector basic = newBasicCaosInjectorForTLC2EListing(listing);
		if (basic instanceof ExtendedCaosInjector)
			return (ExtendedCaosInjector)basic;
		else
			return new ExtendedFromBasicCaosInjectorDecorator(basic);
	}
	
	
	
	
	
	
	
	
	
	
	@PossiblySnapshotPossiblyLiveValue
	public static Set<StandardTLC2EPublishedInstanceListing> filterHiddensMaybe(Set<StandardTLC2EPublishedInstanceListing> listings, boolean filterHiddens)
	{
		return filterHiddens ? filterToSet(l -> !l.isHidden(), listings) : listings;
	}
	
	
	
	public static final Comparator<StandardTLC2EPublishedInstanceListing> ListingsSortOrderForDisplay = (a, b) ->
	{
		int r = cmp2(a.getName(), b.getName());
		if (r != 0) return r;
		
		CaosInjectionType at = a.getCaosInjectionType();
		CaosInjectionType bt = b.getCaosInjectionType();
		r = cmp2(at, bt);
		if (r != 0) return r;
		
		if (arbitrary(at, bt) == StandardCaosInjectionType.WindowsSharedMemory)
		{
			r = cmp2(a.getCaosInjectionGameName(), b.getCaosInjectionGameName());
			if (r != 0) return r;
		}
		
		r = cmp2(a.getDescription(), b.getDescription());
		if (r != 0) return r;
		
		r = defaultListsCompare(a.getWorldnames(), b.getWorldnames());
		if (r != 0) return r;
		
		return cmp(a.hashCode(), b.hashCode());
	};
	
	public static List<StandardTLC2EPublishedInstanceListing> sortListingsForDisplay(Collection<StandardTLC2EPublishedInstanceListing> listings)
	{
		return sorted(listings, ListingsSortOrderForDisplay);
	}
	
	
	
	
	
	
	/**
	 * @return If you can connect to the engine using the loopback address on the local system :3
	 */
	public static boolean isLoopbackable(StandardTLC2EPublishedInstanceListing listing)
	{
		if (listing.getCaosInjectionType() == StandardCaosInjectionType.TCP)
		{
			List<SimpleNetworkHost> hosts = listing.getCaosInjectionHosts();
			return
			hosts.contains(a) ||
			hosts.contains(b) ||
			hosts.contains(c) ||
			hosts.contains(d) ||
			hosts.contains(e);
		}
		else
		{
			return false;
		}
	}
	
	private static final SimpleNetworkHost a = parseHost("localhost");
	private static final SimpleNetworkHost b = parseHost("127.0.0.1");
	private static final SimpleNetworkHost c = parseHost("::1");
	private static final SimpleNetworkHost d = parseHost("0.0.0.0");
	private static final SimpleNetworkHost e = parseHost("::");
}
