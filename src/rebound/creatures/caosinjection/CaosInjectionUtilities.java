package rebound.creatures.caosinjection;

import java.io.IOException;

public class CaosInjectionUtilities
{
	public static boolean isOperational(ExtendedCaosInjector injector) throws IOException
	{
		ExtendedCaosInjectionResponse r = injector.injectExtended("setv va00 9  mulv va00 va00  outv va00");    // Square of 9 == 81  :>
		return r.isSuccess() && r.getOutput().trim().equals("81");
	}
}
