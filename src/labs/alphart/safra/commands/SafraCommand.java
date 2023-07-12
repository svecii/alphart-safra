package labs.alphart.safra.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import labs.alphart.safra.system.CropRotationSystem;

public class SafraCommand implements CommandExecutor {
   private final CropRotationSystem cropRotationSystem;
   private final String currentCropMessage;

   public SafraCommand(CropRotationSystem cropRotationSystem, String currentCropMessage) {
      this.cropRotationSystem = cropRotationSystem;
      this.currentCropMessage = currentCropMessage;
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      String message = this.currentCropMessage.replace("{crop}", this.cropRotationSystem.getCurrentCrop().getName());
      sender.sendMessage(message.replace('&', '§'));
      return true;
   }
}
