package darva.shadowcraft;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.Icon;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import darva.shadowcraft.blocks.BlockPlanks;
import darva.shadowcraft.blocks.ShadowBlock;
import darva.shadowcraft.blocks.ShadowWell;
import darva.shadowcraft.blocks.TreeBlock;
import darva.shadowcraft.blocks.TreeLeaves;
import darva.shadowcraft.blocks.blockSapling;
import darva.shadowcraft.entities.EntityBlast;
import darva.shadowcraft.entities.ShadowWellEntity;
import darva.shadowcraft.handlers.BonemealHandler;
import darva.shadowcraft.handlers.ClientTickHandler;
import darva.shadowcraft.handlers.DamageHandler;
import darva.shadowcraft.handlers.GuiHandler;
import darva.shadowcraft.handlers.PacketHandler;
import darva.shadowcraft.item.ItemRune;
import darva.shadowcraft.item.ItemShadow;
import darva.shadowcraft.item.ShadowArmor;



@Mod(modid="ShadowCraft", name="Shadow Craft", version="0.0.0")
@NetworkMod(clientSideRequired=true, channels={"ShadowFlight"}, packetHandler = PacketHandler.class)
public class Main {
	@Instance(value = "ShadowCraft")
	public static Main instance;
	public static Block TreeBlock;
	public static Block LeavesBlock;
	public static Block SaplingBlock;
	public static TreeGenerator treeGen;
	public static ItemShadow itemShadow;
	public static Icon shadowIcon;
	public static Item shadowArmor;
	public static Block shadowWell;
	public static ShadowBlock shadowBlock;
	private ItemStack shadow;
	public static Item runeItem;
	public static ClientTickHandler cfh;
	public static Settings settings;
	public static BlockPlanks shadowPlank;

	
	@SidedProxy(clientSide="darva.shadowcraft.ClientProxy", serverSide="darva.shadowcraft.CommonProxy")
    public static CommonProxy proxy;
	@EventHandler // used in 1.6.2
    public void preInit(FMLPreInitializationEvent event) { 
		treeGen = new TreeGenerator();
		MinecraftForge.EVENT_BUS.register(new BonemealHandler());
		
		settings = new Settings(event);
		initBlocks();
		initItems();
		setupItems();
		new GuiHandler();
	}
   
    @EventHandler // used in 1.6.2
    public void load(FMLInitializationEvent event) {
            proxy.registerRenderers();
            GameRegistry.registerWorldGenerator(treeGen);
    		
    		GameRegistry.registerTileEntity(ShadowWellEntity.class, "Well of Shadows");
    		//GameRegistry.registerTileEntity(EntityBlast.class, "ShadowBlast");
    		EntityRegistry.registerModEntity(EntityBlast.class, "ShadowBlast", 510, this.instance, 10, 1, false);
    		
    		setupBlocks();
    		shadow = new ItemStack(itemShadow);
    		
    		setupRecipies();
    		
    		MinecraftForge.EVENT_BUS.register(new DamageHandler() );
    		
    }
   
    @EventHandler // used in 1.6.2
    public void postInit(FMLPostInitializationEvent event) {
            // Stub Method
    	setupTickHandlers();
    }
    private void initBlocks()
    {
		TreeBlock = new TreeBlock(settings.woodID, Material.wood)
        .setHardness(0.3F).setStepSound(Block.soundWoodFootstep)
        .setUnlocalizedName("Shadow Wood").setCreativeTab(CreativeTabs.tabBlock);
		LeavesBlock = new TreeLeaves(settings.leavesID, Material.leaves)
        .setHardness(0.0F).setStepSound(Block.soundSnowFootstep)
        .setUnlocalizedName("Shadow Leaves").setCreativeTab(CreativeTabs.tabBlock);
		SaplingBlock = new blockSapling(settings.saplingID, Material.plants)
        .setHardness(0.0F).setStepSound(Block.soundGrassFootstep)
        .setUnlocalizedName("Shadow Sapling").setCreativeTab(CreativeTabs.tabBlock);
		shadowWell = new ShadowWell(settings.wellID, Material.ground).setHardness(.5f);
    	shadowBlock = new ShadowBlock(settings.shadowBlockID, Material.air);
    	shadowPlank = new BlockPlanks(settings.shadowPlankID, Material.wood);
        
    }
    private void initItems()
    {
		itemShadow = new ItemShadow(settings.shadowID);
		shadowArmor = new ShadowArmor(settings.armorID, EnumArmorMaterial.CLOTH, 4,1).setCreativeTab(CreativeTabs.tabCombat)
				.setMaxDamage(100);
		
		runeItem = new ItemRune(settings.runeID).setCreativeTab(CreativeTabs.tabMisc);
    }
    
    private void setupBlocks()
    {
        GameRegistry.registerBlock(TreeBlock, "ShadowWood");
		LanguageRegistry.addName(TreeBlock, "Shadow Wood");
		MinecraftForge.setBlockHarvestLevel(TreeBlock, "axe", 0);
		
        GameRegistry.registerBlock(LeavesBlock, "ShadowLeaves");
		LanguageRegistry.addName(LeavesBlock, "Shadow Leaves");
		MinecraftForge.setBlockHarvestLevel(LeavesBlock, "axe", 0);
		
		GameRegistry.registerBlock(SaplingBlock, "ShadowSapling");
		LanguageRegistry.addName(SaplingBlock, "Shadow Sapling");
		MinecraftForge.setBlockHarvestLevel(SaplingBlock, "axe", 0);
		
		GameRegistry.registerBlock(shadowWell, "ShadowWell");
		LanguageRegistry.addName(shadowWell, "Well of Shadows");
		MinecraftForge.setBlockHarvestLevel(shadowWell, "pick", 0);
		
		GameRegistry.registerBlock(shadowPlank, "ShadowPlank");
		LanguageRegistry.addName(shadowPlank, "Shadow Wood Plank");
		MinecraftForge.setBlockHarvestLevel(shadowPlank, "axe", 0);
		
		GameRegistry.registerBlock(shadowBlock, "Shadows");
		LanguageRegistry.addName(shadowBlock, "Shadows");
    }
    private void setupItems()
    {
    	
		
    	
    	GameRegistry.registerItem(itemShadow, "CongealedShadow");
		LanguageRegistry.addName(itemShadow, "Congealed Shadow");
		
		GameRegistry.registerItem(shadowArmor, "Shadow Cloak");
		LanguageRegistry.addName(shadowArmor, "Shadow Cloak");
		GameRegistry.registerItem(runeItem, "Rune");
		
		ItemStack item;
		for(int i = 0; i < ItemRune.LocalNames.length; i++) {
				ItemStack Item;
				Item = new ItemStack(runeItem,1,i);
				LanguageRegistry.addName(Item, ItemRune.LocalNames[i]);
			}
    	
    }
    private void setupRecipies()
    {
		GameRegistry.addRecipe(new ItemStack(shadowWell, 1),"xxx","xzx","xxx", 'x', new ItemStack(Block.stoneBrick), 'z', new ItemStack(this.TreeBlock));

		GameRegistry.addRecipe(new ItemStack(shadowArmor), "xxx", "xxx", "xxx",'x', shadow);
		//Blank Iron Rune
		GameRegistry.addRecipe(new ItemStack(runeItem), "xyx","yzy","xyx", 'x', shadow, 'y', Item.ingotIron, 'z', new ItemStack(this.TreeBlock));

		//Iron Flight Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 1),"xyx","yzy","xyx", 'x', shadow, 'y', Item.feather, 'z', new ItemStack(this.runeItem,1));
		//Iron Blast Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 13),"xyx","yzy","xyx", 'x', shadow, 'y', Item.gunpowder, 'z', new ItemStack(this.runeItem,1));
		//Iron Fog Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 7),"xyx","yzy","xyx", 'x', shadow, 'y', Item.reed, 'z', new ItemStack(this.runeItem,1));
		//Iron Concentration Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 10),"xyx","yzy","xyx", 'x', shadow, 'y', Item.bowlEmpty, 'z', new ItemStack(this.runeItem,1));
		//Iron Dissipation Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 4),"xyx","yzy","xyx", 'x', shadow, 'y', Item.ingotIron, 'z', new ItemStack(this.runeItem,1));

		//Blank Diamond Rune
		GameRegistry.addRecipe(new ItemStack(runeItem,1, 16), "xyx","yzy","xyx", 'x', shadow, 'y', Item.diamond, 'z', new ItemStack(this.TreeBlock));
		
		//Diamond Flight Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 2),"xyx","qzq","xyx", 'x', shadow, 'y', Item.feather, 'q', Item.glowstone, 'z', new ItemStack(this.runeItem,1,16));
		//Diamond Blast Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 14),"xyx","qzq","xyx", 'x', shadow, 'y', Item.gunpowder, 'q', Item.blazePowder, 'z', new ItemStack(this.runeItem,1,16));
		//Diamond Fog Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 8),"xyx","yzy","xyx", 'x', shadow, 'y', Item.reed, 'z', new ItemStack(this.runeItem,1,16));

		//Diamond Concentration Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 11),"xyx","qzq","xyx", 'x', shadow, 'y', Item.bucketEmpty, 'q', Item.bowlEmpty, 'z', new ItemStack(this.runeItem,1,16));
		//Diamond Dissipation Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 5),"xyx","yzy","xyx", 'x', shadow, 'y', Item.ingotGold, 'z', new ItemStack(this.runeItem,1,16));

		
		//Blank Emerald Rune
		GameRegistry.addRecipe(new ItemStack(runeItem,1, 17), "xyx","yzy","xyx", 'x', shadow, 'y', Item.emerald, 'z', new ItemStack(this.TreeBlock));
		
		//Emerald Flight Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 3),"xyx","qzq","xyx", 'x', shadow, 'y', Item.blazePowder, 'q', Item.glowstone, 'z', new ItemStack(this.runeItem,1,17));
		//Emerald Blast Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 15),"xyx","qzq","xyx", 'x', shadow, 'y', Item.blazeRod, 'q', Item.blazePowder, 'z', new ItemStack(this.runeItem,1,17));
		//Emerald Fog Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 9),"xyx","yzy","xyx", 'x', shadow, 'y', Item.reed, 'z', new ItemStack(this.runeItem,1,17));

		//Emerald Concentration Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 12),"xyx","qzq","xyx", 'x', shadow, 'y', Item.bucketEmpty, 'q', Item.glassBottle, 'z', new ItemStack(this.runeItem,1,17));
		//Emerald Dissipation Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 6),"xyx","yzy","xyx", 'x', shadow, 'y', Item.diamond, 'z', new ItemStack(this.runeItem,1,17));

		//Smelt Shadow Wood to Congealed Shadows
		GameRegistry.addSmelting(this.TreeBlock.blockID, new ItemStack(this.itemShadow), 2);
		
		//Wood to planks.
		GameRegistry.addShapelessRecipe(new ItemStack(this.shadowPlank,1), new ItemStack(this.TreeBlock,1) );
		
    }
    private void setupTickHandlers()
    {
    	proxy.registerTickHandlers();
    }
    
    public static Packet250CustomPayload buildPacket(int type, int data)
	{
	ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
	DataOutputStream outputStream = new DataOutputStream(bos);
	try {
			outputStream.writeInt(type);
	        outputStream.writeInt(data);
	        
	} catch (Exception ex) {
	        ex.printStackTrace();
	}

	Packet250CustomPayload packet = new Packet250CustomPayload();
	packet.channel = "ShadowFlight";
	packet.data = bos.toByteArray();
	packet.length = bos.size();
	return packet;
	}	
    public static ItemStack getArmor(EntityPlayer player)
	{
		if (player == null )
		{
			return null;
		}
		if (player.inventory == null)
		{
			return null;
		}
		if (player.inventory.armorInventory == null)
			return null;
		for (ItemStack i : player.inventory.armorInventory)
		{
			if (i != null)
				if (i.itemID == Main.shadowArmor.itemID)
				{
					
						return i;
					
							
				}
		}
		return null;
	}
}
