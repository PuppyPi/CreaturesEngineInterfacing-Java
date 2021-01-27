package rebound.creatures.tlc2e.client.gui;

import java.io.IOException;
import java.util.Set;
import javax.annotation.Nullable;
import rebound.annotations.semantic.allowedoperations.UnembeddableMain;
import rebound.creatures.tlc2e.client.TLC2EPublishingClient;
import rebound.creatures.tlc2e.client.cli.TLC2EConsoleConnections;
import rebound.creatures.tlc2e.data.StandardTLC2EPublishedInstanceListing;
import rebound.util.functional.FunctionInterfaces.UnaryProcedure;

public class HelloTLC2E
{
	@UnembeddableMain
	public static void main(String[] args) throws IOException
	{
		boolean filterHiddens = false;
		
		
		
		@Nullable Runnable userCancelled = () ->
		{
			System.out.println("Cancelled :3");
			System.exit(0);
		};
		
		@Nullable UnaryProcedure<StandardTLC2EPublishedInstanceListing> userSelected = l ->
		{
			System.out.println("Picked: "+TLC2EConsoleConnections.formatListing(l));
			System.exit(0);
		};
		
		
		Set<StandardTLC2EPublishedInstanceListing> listings = TLC2EPublishingClient.lookupLocalInstances();
		
		
		System.out.println("Discovered "+listings.size()+" listings!");
		
		
		TLC2EGUIConnections.showDedicatedPrompt(listings, filterHiddens, userSelected, userCancelled);
	}
}
