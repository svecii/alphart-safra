package labs.alphart.safra.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import labs.alphart.safra.type.CropType;

public class CropRotationSystem extends BukkitRunnable implements Listener {
   private final JavaPlugin plugin;
   private final int updateInterval;
   private final List<Location> cropBlocks;
   private CropType currentCrop;
   private BukkitTask growthTask;

   public CropRotationSystem(JavaPlugin plugin, int updateInterval) {
      this.plugin = plugin;
      this.updateInterval = updateInterval;
      this.currentCrop = CropType.getRandomCrop();
      this.cropBlocks = new ArrayList();
   }

   public void start() {
      this.runTaskTimer(this.plugin, (long)(this.updateInterval * 60) * 20L, (long)(this.updateInterval * 60) * 20L);
      this.updateCrop();
      this.startGrowthTask();
   }

   public JavaPlugin getPlugin() {
      return this.plugin;
   }

   public CropType getCurrentCrop() {
      return this.currentCrop;
   }

   public List<Location> getCropBlocks() {
      return this.cropBlocks;
   }

   public void run() {
      this.updateCrop();
   }

   public void updateCrop() {
      String sound = this.plugin.getConfig().getString("crop-update-sound", "").toUpperCase();
      String title = this.plugin.getConfig().getString("crop-update-title").replace('&', '§');
      String subtitle = this.plugin.getConfig().getString("crop-update-subtitle").replace('&', '§');
      List<String> messages = this.plugin.getConfig().getStringList("crop-update-message");
      this.currentCrop = CropType.getRandomCrop();
      Iterator var5 = Bukkit.getOnlinePlayers().iterator();

      while(var5.hasNext()) {
         Player player = (Player)var5.next();
         player.sendTitle(title.replace("{crop}", this.getCurrentCrop().getName()), subtitle.replace("{crop}", this.getCurrentCrop().getName()));
         if (!StringUtils.isBlank(sound)) {
            player.playSound(player.getLocation(), Sound.valueOf(sound), 0.5F, 1.0F);
         }

         Iterator var7 = messages.iterator();

         while(var7.hasNext()) {
            String message = (String)var7.next();
            message = message.replace("{crop}", this.currentCrop.getName().toLowerCase().replace("_", " "));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
         }
      }

   }

   private void startGrowthTask() {
      this.growthTask = (new BukkitRunnable() {
         public void run() {
            CropRotationSystem.this.updateCropGrowth();
         }
      }).runTaskTimer(this.plugin, 0L, (long)(this.plugin.getConfig().getInt("crop-growth-speed") * 20));
   }

   private void updateCropGrowth() {
      Iterator var1 = ((Set)this.cropBlocks.stream().map(Location::getBlock).collect(Collectors.toSet())).iterator();

      while(true) {
         while(var1.hasNext()) {
            Block block = (Block)var1.next();
            byte data = block.getData();
            byte[] growthStages = this.currentCrop.getGrowthStages();

            for(int i = 0; i < growthStages.length; ++i) {
               if (data == growthStages[i]) {
                  if (i != growthStages.length - 1) {
                     this.currentCrop.setGrowthStage(block, i + 1);
                  }
                  break;
               }
            }
         }

         return;
      }
   }

   public void stop() {
      if (this.growthTask != null) {
         this.growthTask.cancel();
      }

      this.cancel();
   }
}
