package rebound.creatures.tlc2e.data;

import javax.annotation.Nullable;
import rebound.annotations.semantic.operationspecification.HashableType;

@HashableType
public class CreaturesFilesystem
{
	protected final @Nullable String
	Main,
	Backgrounds,
	BodyData,
	Bootstrap,
	Catalogue,
	CreatureGalleries,
	Genetics,
	Images,
	Journal,
	MyAgents,
	MyCreatures,
	MyWorlds,
	OverlayData,
	Sounds,
	Users;
	
	
	public CreaturesFilesystem(String main, String backgrounds, String bodyData, String bootstrap, String catalogue, String creatureGalleries, String genetics, String images, String journal, String myAgents, String myCreatures, String myWorlds, String overlayData, String sounds, String users)
	{
		Main = main;
		Backgrounds = backgrounds;
		BodyData = bodyData;
		Bootstrap = bootstrap;
		Catalogue = catalogue;
		CreatureGalleries = creatureGalleries;
		Genetics = genetics;
		Images = images;
		Journal = journal;
		MyAgents = myAgents;
		MyCreatures = myCreatures;
		MyWorlds = myWorlds;
		OverlayData = overlayData;
		Sounds = sounds;
		Users = users;
	}
	
	
	
	public String getMain()
	{
		return Main;
	}
	
	public String getBackgrounds()
	{
		return Backgrounds;
	}
	
	public String getBodyData()
	{
		return BodyData;
	}
	
	public String getBootstrap()
	{
		return Bootstrap;
	}
	
	public String getCatalogue()
	{
		return Catalogue;
	}
	
	public String getCreatureGalleries()
	{
		return CreatureGalleries;
	}
	
	public String getGenetics()
	{
		return Genetics;
	}
	
	public String getImages()
	{
		return Images;
	}
	
	public String getJournal()
	{
		return Journal;
	}
	
	public String getMyAgents()
	{
		return MyAgents;
	}
	
	public String getMyCreatures()
	{
		return MyCreatures;
	}
	
	public String getMyWorlds()
	{
		return MyWorlds;
	}
	
	public String getOverlayData()
	{
		return OverlayData;
	}
	
	public String getSounds()
	{
		return Sounds;
	}
	
	public String getUsers()
	{
		return Users;
	}
	
	
	
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Backgrounds == null) ? 0 : Backgrounds.hashCode());
		result = prime * result + ((BodyData == null) ? 0 : BodyData.hashCode());
		result = prime * result + ((Bootstrap == null) ? 0 : Bootstrap.hashCode());
		result = prime * result + ((Catalogue == null) ? 0 : Catalogue.hashCode());
		result = prime * result + ((CreatureGalleries == null) ? 0 : CreatureGalleries.hashCode());
		result = prime * result + ((Genetics == null) ? 0 : Genetics.hashCode());
		result = prime * result + ((Images == null) ? 0 : Images.hashCode());
		result = prime * result + ((Journal == null) ? 0 : Journal.hashCode());
		result = prime * result + ((Main == null) ? 0 : Main.hashCode());
		result = prime * result + ((MyAgents == null) ? 0 : MyAgents.hashCode());
		result = prime * result + ((MyCreatures == null) ? 0 : MyCreatures.hashCode());
		result = prime * result + ((MyWorlds == null) ? 0 : MyWorlds.hashCode());
		result = prime * result + ((OverlayData == null) ? 0 : OverlayData.hashCode());
		result = prime * result + ((Sounds == null) ? 0 : Sounds.hashCode());
		result = prime * result + ((Users == null) ? 0 : Users.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreaturesFilesystem other = (CreaturesFilesystem) obj;
		if (Backgrounds == null)
		{
			if (other.Backgrounds != null)
				return false;
		}
		else if (!Backgrounds.equals(other.Backgrounds))
			return false;
		if (BodyData == null)
		{
			if (other.BodyData != null)
				return false;
		}
		else if (!BodyData.equals(other.BodyData))
			return false;
		if (Bootstrap == null)
		{
			if (other.Bootstrap != null)
				return false;
		}
		else if (!Bootstrap.equals(other.Bootstrap))
			return false;
		if (Catalogue == null)
		{
			if (other.Catalogue != null)
				return false;
		}
		else if (!Catalogue.equals(other.Catalogue))
			return false;
		if (CreatureGalleries == null)
		{
			if (other.CreatureGalleries != null)
				return false;
		}
		else if (!CreatureGalleries.equals(other.CreatureGalleries))
			return false;
		if (Genetics == null)
		{
			if (other.Genetics != null)
				return false;
		}
		else if (!Genetics.equals(other.Genetics))
			return false;
		if (Images == null)
		{
			if (other.Images != null)
				return false;
		}
		else if (!Images.equals(other.Images))
			return false;
		if (Journal == null)
		{
			if (other.Journal != null)
				return false;
		}
		else if (!Journal.equals(other.Journal))
			return false;
		if (Main == null)
		{
			if (other.Main != null)
				return false;
		}
		else if (!Main.equals(other.Main))
			return false;
		if (MyAgents == null)
		{
			if (other.MyAgents != null)
				return false;
		}
		else if (!MyAgents.equals(other.MyAgents))
			return false;
		if (MyCreatures == null)
		{
			if (other.MyCreatures != null)
				return false;
		}
		else if (!MyCreatures.equals(other.MyCreatures))
			return false;
		if (MyWorlds == null)
		{
			if (other.MyWorlds != null)
				return false;
		}
		else if (!MyWorlds.equals(other.MyWorlds))
			return false;
		if (OverlayData == null)
		{
			if (other.OverlayData != null)
				return false;
		}
		else if (!OverlayData.equals(other.OverlayData))
			return false;
		if (Sounds == null)
		{
			if (other.Sounds != null)
				return false;
		}
		else if (!Sounds.equals(other.Sounds))
			return false;
		if (Users == null)
		{
			if (other.Users != null)
				return false;
		}
		else if (!Users.equals(other.Users))
			return false;
		return true;
	}
	
	
	@Override
	public String toString()
	{
		return "CreaturesFilesystem [Main=" + Main + ", Backgrounds=" + Backgrounds + ", BodyData=" + BodyData + ", Bootstrap=" + Bootstrap + ", Catalogue=" + Catalogue + ", CreatureGalleries=" + CreatureGalleries + ", Genetics=" + Genetics + ", Images=" + Images + ", Journal=" + Journal + ", MyAgents=" + MyAgents + ", MyCreatures=" + MyCreatures + ", MyWorlds=" + MyWorlds + ", OverlayData=" + OverlayData + ", Sounds=" + Sounds + ", Users=" + Users + "]";
	}
}
