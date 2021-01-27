package rebound.creatures.caosinjection;

import java.io.IOException;

public interface ExtendedCaosInjector
extends BasicCaosInjector
{
	public ExtendedCaosInjectionResponse injectExtended(String caos) throws IOException;
	
	
	@Override
	public default String inject(String caos) throws IOException
	{
		return injectExtended(caos).getOutput();  //Emulate the behavior of the basic interface (eg, raw tcp protocol) with our extended interface (eg, windows shm protocol)  :3
	}
}
