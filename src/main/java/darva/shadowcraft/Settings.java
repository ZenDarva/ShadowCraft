package darva.shadowcraft;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;


public class Settings {

	private Configuration config;
	

	
	public Settings (FMLPreInitializationEvent event)
	{
		config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.load();
		

		
		config.save();
	}
	
}
