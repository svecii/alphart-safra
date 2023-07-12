package labs.alphart.safra.listener;

import labs.alphart.safra.system.CropRotationSystem;
import labs.alphart.safra.type.CropType;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;

public class CropPlaceListener implements Listener {
   private final CropRotationSystem rotationSystem;
   private final String validWorld;
   private final Effect particleEffect;

   public CropPlaceListener(CropRotationSystem rotationSystem, String validWorld, String particleEffect) {
      this.rotationSystem = rotationSystem;
      this.validWorld = validWorld;
      this.particleEffect = Effect.valueOf(particleEffect.toUpperCase());
   }

   @EventHandler
   public void onCropBreak(BlockBreakEvent event) {
      Block block = event.getBlock();
      this.rotationSystem.getCropBlocks().remove(block.getLocation());
   }

   @EventHandler
   public void onCropPlace(BlockGrowEvent event) {
      Block block = event.getBlock();
      World world = event.getBlock().getWorld();
      if (world.getName().equalsIgnoreCase(this.validWorld)) {
         Material cropMaterial = block.getType();
         boolean isCrop = false;
         CropType[] var7 = CropType.values();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            CropType cropType = var7[var9];
            if (cropType.getMaterial() == cropMaterial) {
               isCrop = true;
               break;
            }
         }

         if (isCrop) {
            CropType currentCrop = this.rotationSystem.getCurrentCrop();
            if (currentCrop != null && currentCrop.getMaterial() == cropMaterial) {
               this.rotationSystem.getCropBlocks().add(block.getLocation());
            }
         }
      }

   }
}