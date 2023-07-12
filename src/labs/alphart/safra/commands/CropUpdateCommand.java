package labs.alphart.safra.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import labs.alphart.safra.system.CropRotationSystem;

public class CropUpdateCommand implements CommandExecutor {
   private final CropRotationSystem cropRotationSystem;

   public CropUpdateCommand(CropRotationSystem cropRotationSystem) {
      this.cropRotationSystem = cropRotationSystem;
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!sender.hasPermission("croprotation.update")) {
         sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.cropRotationSystem.getPlugin().getConfig().getString("no-permission")));
         return true;
      } else {
         this.cropRotationSystem.updateCrop();
         sender.sendMessage(ChatColor.GREEN + "O tipo de safra foi atualizado.....");
         return true;
      }
   }
}
