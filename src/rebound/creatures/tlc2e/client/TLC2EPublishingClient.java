package rebound.creatures.tlc2e.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import rebound.annotations.semantic.simpledata.Emptyable;
import rebound.creatures.tlc2e.data.StandardTLC2EPublishedInstanceListing;
import rebound.creatures.tlc2e.shared.TLC2EPublishingProtocol;
import rebound.dataformats.json.JSONUtilities;
import rebound.exceptions.GenericDataStructuresFormatException;
import rebound.file.FSUtilities;

public class TLC2EPublishingClient
{
	public static final File LocalInstancesDir = FSUtilities.joinPathsStrict(System.getProperty("user.home"), ".tlc2e", "running");
	public static final File LocalManualInstancesDir = FSUtilities.joinPathsStrict(System.getProperty("user.home"), ".tlc2e", "running-manual");
	
	
	public static @Nonnull @Emptyable Set<StandardTLC2EPublishedInstanceListing> lookupLocalInstances() throws IOException
	{
		Set<StandardTLC2EPublishedInstanceListing> s = new HashSet<>();
		
		for (File d : new File[]{LocalInstancesDir, LocalManualInstancesDir})
		{
			if (d.isDirectory())
			{
				File[] cs = d.listFiles();
				
				if (cs != null)
				{
					for (File c : cs)
					{
						Object gds;
						
						try (InputStream in = new FileInputStream(c))
						{
							gds = JSONUtilities.parseJSON(new InputStreamReader(in, StandardCharsets.UTF_8));
						}
						
						try
						{
							s.add(TLC2EPublishingProtocol.fromGDS(gds));
						}
						catch (GenericDataStructuresFormatException exc)
						{
							throw new IOException(exc);
						}
					}
				}
			}
		}
		
		return s;
	}
}
