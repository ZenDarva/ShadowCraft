package darva.shadowcraft;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;

public class Settings {

	private Configuration config;
	
	public static int saplingID;
	public static int wellID;
	public static int woodID;
	public static int leavesID;
	public static int shadowBlockID;
	
	public static int shadowID;
	public static int armorID;
	public static int runeID;
	
	public static int blastID; 
	public static int shadowPlankID;
	
	public Settings (FMLPreInitializationEvent event)
	{
		config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.load();
		
		saplingID = config.get("Blocks", "SaplingID", 160).getInt();
		woodID = config.get("Blocks", "WoodID", 161).getInt();
		wellID = config.get("Blocks", "WellID", 600).getInt();
		leavesID = config.get("Blocks", "leafID", 162).getInt();
		shadowBlockID = config.get("Blocks", "ShadowBlockID", 605).getInt();
		shadowPlankID = config.get("Blocks", "ShadowPlankID", 604).getInt();
		
		shadowID = config.get("Items", "ShadowID", 601).getInt();
		armorID = config.get("Items", "ArmorID", 602).getInt();
		runeID = config.get("Items", "RuneID", 603).getInt();
		
		blastID = config.get("Entities", "BlastID", 510).getInt();
		
		config.save();
	}
	
}
