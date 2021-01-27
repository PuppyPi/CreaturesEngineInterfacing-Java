package rebound.creatures.caosinjection.impls;

import static rebound.text.StringUtilities.*;
import java.io.IOException;
import rebound.annotations.hints.ImplementationTransparency;
import rebound.creatures.caosinjection.BasicCaosInjector;
import rebound.creatures.caosinjection.ExtendedCaosInjectionResponse;
import rebound.creatures.caosinjection.ExtendedCaosInjector;

public class ExtendedFromBasicCaosInjectorDecorator
implements ExtendedCaosInjector
{
	protected final BasicCaosInjector underlying;
	
	public ExtendedFromBasicCaosInjectorDecorator(BasicCaosInjector underlying)
	{
		this.underlying = underlying;
	}
	
	@ImplementationTransparency
	public BasicCaosInjector getUnderlying()
	{
		return underlying;
	}
	
	
	@Override
	public ExtendedCaosInjectionResponse injectExtended(String caos) throws IOException
	{
		//Same algorithm me (RProgrammer aka PuppyPi) and Amaikokonut came up with for Albian Warp :D
		
		String bargle = ":thisworked";  //This can be anything so long as it doesn't end with "..." or "\""  :3
		String r = underlying.inject(caos+" outs \""+bargle+"\"");
		
		//Creatures Engine CAOS Injection error messages end with either "..." or the last bit of CAOS, which in our case ends with a double-quote! ;D
		String v = rtrimstrOrNull(r, bargle);
		
		boolean success = v != null;
		
		return new ExtendedCaosInjectionResponse(success, success ? v : r);
	}
}
