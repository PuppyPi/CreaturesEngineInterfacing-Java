package rebound.creatures.caosinjection.impls.tcp;

import static rebound.io.util.JRECompatIOUtilities.*;
import static rebound.text.StringUtilities.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import rebound.creatures.caosinjection.BasicCaosInjector;

public class TCPCaosInjector
implements BasicCaosInjector
{
	protected final String host;
	protected final int port;
	protected final Charset encoding;
	
	public TCPCaosInjector(String host, int port)
	{
		this.host = host;
		this.port = port;
		this.encoding = StandardCharsets.UTF_8;  //afaik the game only uses ASCII, but it was made to include French so idk!—I'm an uncultured American XD  —PP
	}
	
	public TCPCaosInjector(String host, int port, Charset encoding)
	{
		this.host = host;
		this.port = port;
		this.encoding = encoding;
	}
	
	
	
	
	
	protected byte[] buffer = new byte[1024];
	
	/**
	 * The given script doesn't include "\nrscr\n"; that is included by this method.
	 */
	@Override
	public synchronized String inject(String caos) throws IOException
	{
		/*
		 * Notes on interfacing to C2E:
		 * 		+ It will terminate the connection after the first script sent (so it's script-per-connection)
		 * 		+ The script must end in "\nrscr\n"
		 * 		+ CAOS Errors will be sent back, also as ASCII(ish?), indistinguishable from output (if formatted to look like an error)
		 */
		
		
		//Encode to the buffer
		int bufferSize;
		{
			byte[] rawscript = encodeTextToByteArrayReporting(caos, encoding);
			
			int needed = rawscript.length+Terminator.length;
			
			if (needed > buffer.length)
				buffer = new byte[needed];
			
			System.arraycopy(rawscript, 0, buffer, 0, rawscript.length);
			System.arraycopy(Terminator, 0, buffer, rawscript.length, Terminator.length);
			
			bufferSize = needed;
		}
		
		
		try (Socket socket = new Socket(host, port))
		{
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			
			out.write(buffer, 0, bufferSize);
			
			byte[] resp = readAll(in);
			
			return decodeTextToStringReporting(resp, encoding);
		}
	}
	
	
	protected static final byte[] Terminator = encodeTextToByteArrayReportingUnchecked("\nrscr\n", StandardCharsets.US_ASCII);
}
