package labs.alphart.safra.type;

import org.bukkit.Material;
import org.bukkit.block.Block;

import io.netty.util.internal.ThreadLocalRandom;

public enum CropType {
	WHEAT("Trigo", Material.CROPS, new byte[]{0, 1, 2, 3, 4, 5, 6, 7}),
	CARROT("Cenoura", Material.CARROT, new byte[]{0, 1, 2, 3, 4, 5, 6, 7}),
	POTATO("Batata", Material.POTATO, new byte[]{0, 1, 2, 3, 4, 5, 6, 7});
	
	private final String name;
	private final Material material;
	private final byte[] growthStages;
	
	private CropType(String name, Material material, byte... growthStages) {
	      this.name = name;
	      this.material = material;
	      this.growthStages = growthStages;
	   }

	   public Material getMaterial() {
	      return this.material;
	   }

	   public byte[] getGrowthStages() {
	      return this.growthStages;
	   }

	   public String getName() {
	      return this.name;
	   }
	public void setGrowthStage(Block block, int stage) {
	      block.setData(this.growthStages[stage]);
	   }

	   public static CropType getRandomCrop() {
	      CropType[] crops = values();
	      return crops[ThreadLocalRandom.current().nextInt(crops.length)];
	   }
	}
