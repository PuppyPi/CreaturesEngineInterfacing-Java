package rebound.creatures.tlc2e.client.gui;

import static rebound.GlobalCodeMetastuffContext.*;
import static rebound.text.StringUtilities.*;
import static rebound.util.BasicExceptionUtilities.*;
import static rebound.util.collections.CollectionUtilities.*;
import static rebound.util.objectutil.BasicObjectUtilities.*;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import rebound.creatures.tlc2e.client.gui.components.TLC2EChooserDedicatedWindow;
import rebound.creatures.tlc2e.client.gui.components.TLC2EChooserEntrySwing;
import rebound.creatures.tlc2e.data.CaosInjectionType;
import rebound.creatures.tlc2e.data.CaosInjectionType.StandardCaosInjectionType;
import rebound.creatures.tlc2e.data.CaosInjectionType.UnknownCaosInjectionType;
import rebound.creatures.tlc2e.data.InstanceCreatorName;
import rebound.creatures.tlc2e.data.InstanceCreatorName.ExplicitInstanceCreator;
import rebound.creatures.tlc2e.data.InstanceCreatorName.StandardInstanceCreators;
import rebound.creatures.tlc2e.data.StandardTLC2EPublishedInstanceListing;
import rebound.creatures.tlc2e.shared.TLC2EUtilities;
import rebound.util.collections.prim.PrimitiveCollections.ImmutableByteArrayList;
import rebound.util.functional.FunctionInterfaces.NullaryFunction;
import rebound.util.functional.FunctionInterfaces.UnaryProcedure;

public class TLC2EGUIConnections
{
	public static TLC2EChooserEntrySwing newEntryForManualTCPListing(int port)
	{
		return new TLC2EChooserEntrySwing(null, "a Creatures Engine on port "+port, null);
	}
	
	public static TLC2EChooserEntrySwing newEntryForManualWindowsSharedMemoryListing(String gameName)
	{
		return new TLC2EChooserEntrySwing(null, gameName, null);
	}
	
	
	
	
	public static TLC2EChooserEntrySwing newEntryForListing(StandardTLC2EPublishedInstanceListing listing, NullaryFunction<BufferedImage> notifyIconDataInvalid)
	{
		BufferedImage icon;
		{
			ImmutableByteArrayList iconData = listing.getIcon();
			
			if (iconData == null)
			{
				icon = null;
			}
			else
			{
				Iterator<ImageReader> rs = ImageIO.getImageReadersByMIMEType("image/png");
				
				if (!rs.hasNext())
				{
					logBug("JRE is missing a PNG decoder!!");
					icon = null;
				}
				else
				{
					ImageReader r = rs.next();
					
					r.setInput(new ByteArrayInputStream(iconData.toByteArray()));
					
					try
					{
						icon = r.read(0);
					}
					catch (IOException exc)
					{
						icon = notifyIconDataInvalid.f();
					}
				}
			}
		}
		
		String name;
		String description;
		{
			CaosInjectionType caosInjectionType = listing.getCaosInjectionType();
			Integer port = listing.getCaosInjectionPort();
			boolean hidden = listing.isHidden();
			
			String nam = listing.getName();
			List<String> worldnames = listing.getWorldnames();
			InstanceCreatorName creator = listing.getCreator();
			
			
			if (nam == null)
			{
				if (caosInjectionType == StandardCaosInjectionType.TCP)
				{
					name = "a TLC2E Engine on port "+port;
				}
				else if (caosInjectionType == StandardCaosInjectionType.WindowsSharedMemory)
				{
					name = "a TLC2E Engine running "+listing.getCaosInjectionGameName();
				}
				else
				{
					name = "a TLC2E Engine without caos injection";
				}
			}
			else
			{
				name = nam;
			}
			
			
			StringBuilder desc = new StringBuilder();
			
			if (hidden)
				desc.append("(HIDDEN)\n");
			
			if (caosInjectionType == StandardCaosInjectionType.TCP)
			{
				desc.append("CAOS Injection on TCP port: "+port+"\n");
				if (TLC2EUtilities.isLoopbackable(listing))
					desc.append("  On local system loopback interface\n");
				else
					desc.append("  On any of: "+joinStrings(mapToList(h -> h.toString(), listing.getCaosInjectionHosts()), ", ")+"\n");
			}
			else if (caosInjectionType == StandardCaosInjectionType.WindowsSharedMemory)
			{
				desc.append("Windows-SHM CAOS Injection registered as: "+listing.getCaosInjectionGameName()+"\n");
			}
			else if (caosInjectionType == StandardCaosInjectionType.Absent)
			{
				desc.append("With no CAOS Injection\n");
			}
			else if (caosInjectionType == StandardCaosInjectionType.Prevented)
			{
				desc.append("With CAOS Injection disabled\n");
			}
			else if (caosInjectionType instanceof UnknownCaosInjectionType)
			{
				desc.append("With unrecognized CAOS Injection type: "+repr(((UnknownCaosInjectionType)caosInjectionType).getValue())+"\n");
			}
			else
			{
				logBug(newClassCastExceptionOrNullPointerException(caosInjectionType));
			}
			
			
			if (creator != StandardInstanceCreators.Unspecified)
			{
				desc.append("Creating program: "+(creator == StandardInstanceCreators.User ? "User" : ((ExplicitInstanceCreator)creator).getValue())+"\n");
			}
			
			if (worldnames != null)
			{
				worldnames = filterToList(n -> !eq(n, "Startup"), worldnames);
				
				int n = worldnames.size();
				
				desc.append("Worlds: ");
				
				if (n == 0)
					desc.append("(none)");
				else
					desc.append(joinStrings(worldnames, ", "));
				
				desc.append("\n");
			}
			
			description = desc.toString();
		}
		
		return new TLC2EChooserEntrySwing(icon, name, description);
	}
	
	
	
	
	
	
	
	
	
	public static void showDedicatedPrompt(Set<StandardTLC2EPublishedInstanceListing> listings, boolean filterHiddens, @Nullable UnaryProcedure<StandardTLC2EPublishedInstanceListing> userSelected, @Nullable Runnable userCancelled)
	{
		listings = TLC2EUtilities.filterHiddensMaybe(listings, filterHiddens);
		
		List<StandardTLC2EPublishedInstanceListing> listingsInOrder = TLC2EUtilities.sortListingsForDisplay(listings);
		
		List<TLC2EChooserEntrySwing> entryGUIs = mapToList(l -> newEntryForListing(l, () -> null), listingsInOrder);
		
		Window w = new TLC2EChooserDedicatedWindow(entryGUIs, userSelected == null ? null : (i -> userSelected.f(listingsInOrder.get(i))), userCancelled);
		
		w.setVisible(true);
	}
}
