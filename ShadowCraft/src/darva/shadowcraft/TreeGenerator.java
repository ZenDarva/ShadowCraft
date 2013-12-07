package darva.shadowcraft;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class TreeGenerator implements IWorldGenerator {



	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub
		int x = chunkX * 16;
		int z = chunkZ * 16;

		BiomeGenBase biome;
		biome = world.getBiomeGenForCoords(x, z);
		int chance = 1;
		if (BiomeDictionary.isBiomeOfType(biome, Type.FOREST))
		{
			chance+=5;
		}
		if (BiomeDictionary.isBiomeOfType(biome, Type.PLAINS))
		{
			chance+=2;
		}
		if (BiomeDictionary.isBiomeOfType(biome, Type.SWAMP))
		{
			chance+=2;
		}
		if (BiomeDictionary.isBiomeOfType(biome, Type.WATER))
		{
			chance=1;
		}
		if (BiomeDictionary.isBiomeOfType(biome, Type.HILLS))
		{
			chance+=2;
		}

		if (BiomeDictionary.isBiomeOfType(biome, Type.MAGICAL))
		{
			chance*=2;
		}


		
		if (random.nextInt(100) >= chance)
			return;
		
		int destY;
		int destX;
		int destZ;
		
		boolean goodHere = true;
		destX = x + random.nextInt(16);
		destZ = z + random.nextInt(16);

		//destY = getTopBlockY(world, destX, destZ);
		destY = world.getTopSolidOrLiquidBlock(destX, destZ);
		if (world.getBlockId(destX, destY-1 , destZ) != Block.dirt.blockID && world.getBlockId(destX, destY-1 , destZ) != Block.grass.blockID)
		{
			System.out.print("Failed on block type: " + world.getBlockId(destX, destY, destZ) + "\n\r");
			return;
		}
		for (int x1 = -1; x1<1; x1++)
		{
			for (int z1 = -1; z1 < 1; z1++)
			{
				if (world.getBlockMaterial(x1, destY, z1) == Material.wood || 
						world.getBlockMaterial( x1,destY,z1) == Material.water)
				{
					goodHere = false;
				}
			}
		}
		
		if (goodHere == true)
		{
			growTree(world, random, destX, destY, destZ);
		}
		
		
		
	}
	
	
	public void growTree(World world, Random random, int X, int Y, int Z)
	{
		int maxHeight = random.nextInt(4) + 3;
		int curHeight;
		int levelsOut = 1;
		
		for (int height = 0; height<= maxHeight; height++ )
			world.setBlock(X, Y+ height, Z, Main.TreeBlock.blockID);
		curHeight = Y + maxHeight;
		
		world.setBlock(X, curHeight+1, Z, Main.LeavesBlock.blockID);
		
		while (curHeight - Y >=2)
		{
			for (int x = levelsOut * -1; x < levelsOut +1; x++)
				for (int z = levelsOut * -1; z < levelsOut+1; z++)
				{
					if (x == 0 && z == 0)
							continue; // Don't want to change the wood.
					if (world.getBlockId(X+x, curHeight, Z+z) != 0) 
					{
						//Don't break blocks to place leaves.
						// 0 is id for air.
						continue;
					}
					if (Math.abs(x) == 2 || Math.abs(z) == 2)
					{
						if (random.nextInt(10) > 2)
							world.setBlock(X+x, curHeight, Z+z, Main.LeavesBlock.blockID);
					}
					else
					{
						world.setBlock(X+x, curHeight, Z+z, Main.LeavesBlock.blockID);
					}
				}
			curHeight--;
			if (levelsOut == 1)
			{
				levelsOut = 2;
			}
			else
			{
				levelsOut = 1;
			}
		}
	}
	
	
	private int getTopBlockY(World world, int x, int z)
	{
		int tempY;
		
		tempY = world.getHeightValue(x, z);
		
		while (world.getBlockId(x, tempY, z) == 0)
				{
			tempY = tempY -1;
				}
		
		return tempY;
	}

}
