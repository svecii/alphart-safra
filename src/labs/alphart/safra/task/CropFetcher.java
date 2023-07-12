package labs.alphart.safra.task;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import labs.alphart.safra.type.CropType;

public class CropFetcher extends BukkitRunnable {
   public void run() {
      Iterator var1 = Bukkit.getWorlds().iterator();

      while(var1.hasNext()) {
         World world = (World)var1.next();
         Chunk[] var3 = world.getLoadedChunks();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Chunk chunk = var3[var5];
            ChunkSnapshot snapshot = chunk.getChunkSnapshot();

            for(int y = 0; y < 256; ++y) {
               for(int x = 0; x < 16; ++x) {
                  for(int z = 0; z < 16; ++z) {
                     int blockTypeId = snapshot.getBlockTypeId(x, y, z);
                     Material material = Material.getMaterial(blockTypeId);
                     boolean isCrop = false;
                     CropType[] var14 = CropType.values();
                     int z1 = var14.length;

                     for(int var16 = 0; var16 < z1; ++var16) {
                        CropType type = var14[var16];
                        if (material == type.getMaterial()) {
                           isCrop = true;
                           break;
                        }
                     }

                     if (isCrop) {
                        int x1 = snapshot.getX() << 4 | x;
                        z1 = snapshot.getZ() << 4 | z;
                        new Location(world, (double)x1, (double)y, (double)z1);
                        Bukkit.broadcastMessage(String.format("Found: %s, %s, %s", x1, y, z1));
                     }
                  }
               }
            }
         }
      }

   }
}