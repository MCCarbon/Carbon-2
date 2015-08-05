package com.lastabyss.carbon;

import com.lastabyss.carbon.network.NetworkInjector;
import com.lastabyss.carbon.utils.Metrics;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Carbon extends JavaPlugin {

    private PluginDescriptionFile pluginDescriptionFile = this.getDescription();
    private FileConfiguration spigotConfig = YamlConfiguration.loadConfiguration(new File(getServer().getWorldContainer(), "spigot.yml"));

    public static final Logger log = Bukkit.getLogger();

    @Override
    public void onLoad() {
        // call to server shutdown if worlds are already loaded, prevents various errors when loading plugin on the fly
        if (!Bukkit.getWorlds().isEmpty()) {
            log.log(Level.SEVERE, "World loaded before{0} {1}! (Was {2} loaded on the fly?)", new Object[]{pluginDescriptionFile.getName(), pluginDescriptionFile.getVersion(), pluginDescriptionFile.getName()});
            if (spigotConfig.getBoolean("settings.restart-on-crash")) {
                getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
            }

            Bukkit.shutdown();
            return;
        }

        /*saveResource("libraries/natives/32/linux/libattach.so", true);
        saveResource("libraries/natives/32/solaris/libattach.so", true);
        saveResource("libraries/natives/32/windows/attach.dll", true);
        saveResource("libraries/natives/64/linux/libattach.so", true);
        saveResource("libraries/natives/64/mac/libattach.dylib", true);
        saveResource("libraries/natives/64/solaris/libattach.so", true);
        saveResource("libraries/natives/64/windows/attach.dll", true);*/

        // Inject 1.8 features. Stop server if something fails
        try {
            //new Instrumentator(this, new File(getDataFolder(), "libraries/natives/").getPath()).instrumentate();
            Injector injector = new Injector(this);
            injector.registerAll();
            injector.registerRecipes();
        } catch (Throwable e) {
            e.printStackTrace(System.out);
            log.warning("[Carbon] 1.9 injection failed! Something went wrong, server cannot start properly, shutting down...");
            Bukkit.shutdown();
            return;
        }

        log.info("Carbon has finished injecting all 1.9 functionalities.");
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new NetworkInjector(), this);
        //getServer().getPluginManager().registerEvents(new PlayerListener(this), this); - temporaly disable, until we find a way to actually break the item when durability end, should probably just reimplement shovels
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
        }
        log.info("Carbon is enabled.");
    }

}
