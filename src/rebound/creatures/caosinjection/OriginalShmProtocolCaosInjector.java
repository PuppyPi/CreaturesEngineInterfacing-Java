package rebound.creatures.caosinjection;

import java.io.IOException;

public interface OriginalShmProtocolCaosInjector
extends ExtendedCaosInjector
{
	public OriginalShmProtocolCaosInjectionResponse injectOriginal(String caos) throws IOException;
	
	
	@Override
	public default ExtendedCaosInjectionResponse injectExtended(String caos) throws IOException
	{
		OriginalShmProtocolCaosInjectionResponse r = injectOriginal(caos);
		return new ExtendedCaosInjectionResponse(r.isSuccess(), r.getOutput());  //Emulate the behavior of the extended interface with our more extended interface! XD
	}
}
