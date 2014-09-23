package ctuchsch.mods.mobmagic;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneration implements IWorldGenerator {
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		switch (world.provider.dimensionId) {
		case 0: GenerateOverworld(random, chunkX * 16, chunkZ * 16, world); break;
		case 1: GenerateEnd(random, chunkX * 16, chunkZ * 16, world); break;
		case -1: GenerateNether(random, chunkX *16, chunkZ * 16, world); break;
		}
		
	}
	private void GenerateOverworld(Random random, int x, int z, World world) {
		this.addOreSpawn(MobMagic.blockEssenciteOre, world, random, x, z, 4, 10, 15, 0, 63);
		this.addOreSpawn(MobMagic.blockFossilOre, world, random, x, z, 2, 6, 3, 0, 16);
		this.addOreSpawn(MobMagic.blockStarshineOre, world, random, x, z, 2, 6, 2, 80, 255);
	}
		
	private void GenerateEnd(Random random, int x, int z, World world) {
		//no End spawn currently, so do nothing
	}
		
	private void GenerateNether(Random random, int x, int z, World world) {
		//no Nether spawn currently, so do nothing
	}
		
	public void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int minVeinSize, int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
		
		WorldGenMinable minable = new WorldGenMinable(block, (minVeinSize + random.nextInt(maxVeinSize)), Blocks.stone);
		MinableForLoop(minable, world, random, blockXPos, blockZPos, minVeinSize, maxVeinSize, chancesToSpawn, minY, maxY);
		
		WorldGenMinable desertMinable = new WorldGenMinable(block, (minVeinSize + random.nextInt(maxVeinSize)), Blocks.sand);
		MinableForLoop(minable, world, random, blockXPos, blockZPos, minVeinSize, maxVeinSize, chancesToSpawn, minY, maxY);

	}
	
	public void MinableForLoop(WorldGenMinable minable, World world, Random random, int blockXPos, int blockZPos, int minVeinSize, int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
		for(int i=0; i < chancesToSpawn; i++) {
			int posX = blockXPos + random.nextInt(16);
			int posY = minY + random.nextInt(maxY - minY);
			int posZ = blockZPos + random.nextInt(16);
			minable.generate(world, random, posX, posY, posZ);
		}
	}

}
