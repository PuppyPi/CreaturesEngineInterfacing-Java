package rebound.creatures.tlc2e.client.cli;

import static rebound.text.StringUtilities.*;
import static rebound.util.collections.CollectionUtilities.*;
import static rebound.util.objectutil.BasicObjectUtilities.*;
import java.util.List;
import rebound.creatures.tlc2e.data.CaosInjectionType;
import rebound.creatures.tlc2e.data.InstanceCreatorName;
import rebound.creatures.tlc2e.data.CaosInjectionType.StandardCaosInjectionType;
import rebound.creatures.tlc2e.data.InstanceCreatorName.ExplicitInstanceCreator;
import rebound.creatures.tlc2e.data.InstanceCreatorName.StandardInstanceCreators;
import rebound.creatures.tlc2e.data.StandardTLC2EPublishedInstanceListing;

public class TLC2EConsoleConnections
{
	public static String formatListing(StandardTLC2EPublishedInstanceListing listing)
	{
		CaosInjectionType caosInjectionType = listing.getCaosInjectionType();
		Integer port = listing.getCaosInjectionPort();
		boolean hidden = listing.isHidden();
		
		String name = listing.getName();
		List<String> worldnames = listing.getWorldnames();
		InstanceCreatorName creator = listing.getCreator();
		
		//Todo use StringBuilder instead ^^'
		
		String r = "";
		
		if (name != null)
			r += name + " (a ";
		
		if (hidden)
			r += "HIDDEN ";
		
		if (creator != StandardInstanceCreators.Unspecified)
		{
			r += creator == StandardInstanceCreators.User ? "User-made" : ((ExplicitInstanceCreator)creator).getValue();
			r += " ";
		}
		
		if (caosInjectionType == StandardCaosInjectionType.TCP)
		{
			r += "TLC2E Engine on port "+port;
		}
		else if (caosInjectionType == StandardCaosInjectionType.WindowsSharedMemory)
		{
			r += "TLC2E Engine running "+listing.getCaosInjectionGameName();
		}
		else
		{
			r += "TLC2E Engine without caos injection";
		}
		
		if (worldnames != null)
		{
			worldnames = filterToList(n -> !eq(n, "Startup"), worldnames);
			
			int n = worldnames.size();
			
			if (n == 0)
				r += " (with no worlds)";
			else if (n == 1)
				r += " (with only the one world: "+worldnames.get(0)+")";
			else
				r += " (with worlds: "+joinStrings(worldnames, ", ")+")";
		}
		
		if (name != null)
			r += ")";
		
		return r;
	}
}
