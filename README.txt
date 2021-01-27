# CreaturesEngineInterfacing-Java
For Java tools connecting to the Creatures Engine :>

There are three main ways to interact with the Creatures Evolution Engine:

• CAOS Injection
	• On Windows (original engine) this takes place over a Shared Memory interface
	• On Linux (lc2e) and Mac (exodus) this takes place over an ASCII protocol on a TCP connection (usually on localhost:20001)

• Filesystem
	• Some folders are Read-Write (eg, Journal, My Creatures, My Worlds, etc.)
	• Some folders are Read-Only or can be (eg, Bootstrap, Images, etc.)

• Direct Memory Interfacing
	To my knowledge no one does this yet XD'
	But it's possible (at least on Linux/lc2e) to directly reach into the memory of the engine and rewrite its Heap at the binary level 8)
	As far as I know, this is the only way to do some things, like get the current creature poses/gaits, monitor when sound effects play (without rewriting all CAOS), and a probably a few other things
	Also, this might be or be closely related to the My Worlds save file format!!



This project currently only handles the first two.
The main feature is as a client for the TLC2E Publishing Protocol (to autodiscover and connect to instances of the game engine managed and published by TLC2E).

See Jagent Core for decoders for the various creatures file format :)
