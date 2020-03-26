package fr.raph.stuff.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public File dFile;
	private FileConfiguration dConfig;
	
	@Override
	public void onEnable() {
		createDConfig();
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
		getCommand("giveStuff").setExecutor(new Listeners(this));
	}
	
	public FileConfiguration getDConfig() {
		return this.dConfig;
	}
	
	private void createDConfig() {
		dFile = new File(getDataFolder(), "stuff.yml");
		if(!dFile.exists()) {
			dFile.getParentFile().mkdirs();
			saveResource("stuff.yml", true);
		}
		
		dConfig = new YamlConfiguration();
		
		try {
			dConfig.load(dFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

}
