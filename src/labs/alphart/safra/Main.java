package labs.alphart.safra;

import org.bukkit.plugin.java.JavaPlugin;

import labs.alphart.safra.commands.CropUpdateCommand;
import labs.alphart.safra.commands.SafraCommand;
import labs.alphart.safra.listener.CropPlaceListener;
import labs.alphart.safra.system.CropRotationSystem;

public class Main extends JavaPlugin {
   private CropRotationSystem rotationSystem;
   private CropPlaceListener placeListener;

   public void onEnable() {
      this.saveDefaultConfig();
      int rotationIntervalMinutes = this.getConfig().getInt("rotation-interval-minutes", 120);
      String particleEffect = this.getConfig().getString("particle-effect", "happy_villager");
      String validWorld = this.getConfig().getString("crop-world", "world");
      String currentCropMessage = this.getConfig().getString("current-crop-message");
      this.rotationSystem = new CropRotationSystem(this, rotationIntervalMinutes);
      this.placeListener = new CropPlaceListener(this.rotationSystem, validWorld, particleEffect);
      this.getServer().getPluginManager().registerEvents(this.rotationSystem, this);
      this.getServer().getPluginManager().registerEvents(this.placeListener, this);
      this.getCommand("cropupdate").setExecutor(new CropUpdateCommand(this.rotationSystem));
      this.getCommand("safra").setExecutor(new SafraCommand(this.rotationSystem, currentCropMessage));
      this.rotationSystem.start();
   }
public void onDisable() {
      this.rotationSystem.stop();
   }
}