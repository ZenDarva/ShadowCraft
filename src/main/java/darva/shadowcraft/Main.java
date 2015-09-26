package darva.shadowcraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import darva.shadowcraft.blocks.*;
import darva.shadowcraft.entities.EntityBlast;
import darva.shadowcraft.entities.ShadowWellEntity;
import darva.shadowcraft.handlers.BonemealHandler;
import darva.shadowcraft.handlers.ClientTickHandler;
import darva.shadowcraft.handlers.DamageHandler;
import darva.shadowcraft.handlers.GuiHandler;
import darva.shadowcraft.item.ItemRune;
import darva.shadowcraft.item.ItemShadow;
import darva.shadowcraft.item.ShadowArmor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.MinecraftForge;



@Mod(modid="ShadowCraft", name="Shadow Craft", version="0.0.0")
//@NetworkMod(clientSideRequired=true, channels={"ShadowFlight"}, packetHandler = PacketHandler.class)
public class Main {
	@Instance(value = "ShadowCraft")
	public static Main instance;
	public static Block TreeBlock;
	public static Block LeavesBlock;
	public static Block SaplingBlock;
	public static TreeGenerator treeGen;
	public static ItemShadow itemShadow;
	public static IIcon shadowIcon;
	public static Item shadowArmor;
	public static Block shadowWell;
	public static ShadowBlock shadowBlock;
	private ItemStack shadow;
	public static Item runeItem;
	public static ClientTickHandler cfh;
	public static Settings settings;
	public static BlockPlanks shadowPlank;
    public static int renderId = -5;

	
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

            GameRegistry.registerWorldGenerator(treeGen, 0);
    		
    		GameRegistry.registerTileEntity(ShadowWellEntity.class, "Well of Shadows");
    		//GameRegistry.registerTileEntity(EntityBlast.class, "ShadowBlast");
    		EntityRegistry.registerModEntity(EntityBlast.class, "ShadowBlast", 510, this.instance, 10, 1, false);
    		
    		setupBlocks();
    		shadow = new ItemStack(itemShadow);
    		
    		setupRecipies();
    		
    		MinecraftForge.EVENT_BUS.register(new DamageHandler());

    		
    }
   
    @EventHandler // used in 1.6.2
    public void postInit(FMLPostInitializationEvent event) {
            // Stub Method
    	setupTickHandlers();
		proxy.RegisterPackets();
    }
    private void initBlocks()
    {
		TreeBlock = new TreeBlock( Material.wood)
        .setHardness(0.3F).setStepSound(Block.soundTypeWood).setCreativeTab(CreativeTabs.tabBlock);
		LeavesBlock = new TreeLeaves(Material.leaves)
        .setHardness(0.0F).setStepSound(Block.soundTypeSnow).setCreativeTab(CreativeTabs.tabBlock);
		SaplingBlock = new blockSapling(Material.plants)
        .setHardness(0.0F).setStepSound(Block.soundTypeGrass).setCreativeTab(CreativeTabs.tabBlock);
		shadowWell = new ShadowWell(Material.ground).setHardness(.5f);
    	shadowBlock = new ShadowBlock(Material.web);
    	shadowPlank = new BlockPlanks(Material.wood);
        
    }
    private void initItems()
    {
		itemShadow = new ItemShadow();
		shadowArmor = new ShadowArmor(ItemArmor.ArmorMaterial.CLOTH, 4,1).setCreativeTab(CreativeTabs.tabCombat)
				.setMaxDamage(100);
		
		runeItem = new ItemRune().setCreativeTab(CreativeTabs.tabMisc);
    }
    
    private void setupBlocks()
    {
        GameRegistry.registerBlock(TreeBlock, "ShadowWood");
        GameRegistry.registerBlock(LeavesBlock, "ShadowLeaves");

		GameRegistry.registerBlock(SaplingBlock, "ShadowSapling");

		GameRegistry.registerBlock(shadowWell, "ShadowWell");

		GameRegistry.registerBlock(shadowPlank, "ShadowPlank");

		GameRegistry.registerBlock(shadowBlock, "Shadows");
    }
    private void setupItems()
    {
    	
		
    	
    	GameRegistry.registerItem(itemShadow, "CongealedShadow");

		GameRegistry.registerItem(shadowArmor, "Shadow Cloak");

		GameRegistry.registerItem(runeItem, "Rune");
		
		ItemStack item;
		for(int i = 0; i < ItemRune.LocalNames.length; i++) {
				ItemStack Item;
				Item = new ItemStack(runeItem,1,i);
			}
    	
    }
    private void setupRecipies()
    {
		GameRegistry.addRecipe(new ItemStack(shadowWell, 1),"xxx","xzx","xxx", 'x', new ItemStack(Blocks.stonebrick), 'z', new ItemStack(this.TreeBlock));

		GameRegistry.addRecipe(new ItemStack(shadowArmor), "xxx", "xxx", "xxx",'x', shadow);
		//Blank Iron Rune
		GameRegistry.addRecipe(new ItemStack(runeItem), "xyx","yzy","xyx", 'x', shadow, 'y', Items.iron_ingot, 'z', new ItemStack(this.TreeBlock));

		//Iron Flight Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 1),"xyx","yzy","xyx", 'x', shadow, 'y', Items.feather, 'z', new ItemStack(this.runeItem,1));
		//Iron Blast Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 13),"xyx","yzy","xyx", 'x', shadow, 'y', Items.gunpowder, 'z', new ItemStack(this.runeItem,1));
		//Iron Fog Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 7),"xyx","yzy","xyx", 'x', shadow, 'y', Items.reeds, 'z', new ItemStack(this.runeItem,1));
		//Iron Concentration Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 10),"xyx","yzy","xyx", 'x', shadow, 'y', Items.bowl, 'z', new ItemStack(this.runeItem,1));
		//Iron Dissipation Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 4),"xyx","yzy","xyx", 'x', shadow, 'y', Items.iron_ingot, 'z', new ItemStack(this.runeItem,1));

		//Blank Diamond Rune
		GameRegistry.addRecipe(new ItemStack(runeItem,1, 16), "xyx","yzy","xyx", 'x', shadow, 'y', Items.diamond, 'z', new ItemStack(this.TreeBlock));
		
		//Diamond Flight Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 2),"xyx","qzq","xyx", 'x', shadow, 'y', Items.feather, 'q', Items.glowstone_dust, 'z', new ItemStack(this.runeItem,1,16));
		//Diamond Blast Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 14),"xyx","qzq","xyx", 'x', shadow, 'y', Items.gunpowder, 'q', Items.blaze_powder, 'z', new ItemStack(this.runeItem,1,16));
		//Diamond Fog Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 8),"xyx","yzy","xyx", 'x', shadow, 'y', Items.reeds, 'z', new ItemStack(this.runeItem,1,16));

		//Diamond Concentration Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 11),"xyx","qzq","xyx", 'x', shadow, 'y', Items.bucket, 'q', Items.bowl, 'z', new ItemStack(this.runeItem,1,16));
		//Diamond Dissipation Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 5),"xyx","yzy","xyx", 'x', shadow, 'y', Items.gold_ingot, 'z', new ItemStack(this.runeItem,1,16));

		
		//Blank Emerald Rune
		GameRegistry.addRecipe(new ItemStack(runeItem,1, 17), "xyx","yzy","xyx", 'x', shadow, 'y', Items.emerald, 'z', new ItemStack(this.TreeBlock));
		
		//Emerald Flight Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 3),"xyx","qzq","xyx", 'x', shadow, 'y', Items.blaze_powder, 'q', Items.glowstone_dust, 'z', new ItemStack(this.runeItem,1,17));
		//Emerald Blast Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 15),"xyx","qzq","xyx", 'x', shadow, 'y', Items.blaze_rod, 'q', Items.blaze_powder, 'z', new ItemStack(this.runeItem,1,17));
		//Emerald Fog Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 9),"xyx","yzy","xyx", 'x', shadow, 'y', Items.reeds, 'z', new ItemStack(this.runeItem,1,17));

		//Emerald Concentration Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 12),"xyx","qzq","xyx", 'x', shadow, 'y', Items.bucket, 'q', Items.glass_bottle, 'z', new ItemStack(this.runeItem,1,17));
		//Emerald Dissipation Rune
		GameRegistry.addRecipe(new ItemStack(runeItem, 1, 6),"xyx","yzy","xyx", 'x', shadow, 'y', Items.diamond, 'z', new ItemStack(this.runeItem,1,17));

		//Smelt Shadow Wood to Congealed Shadows
		GameRegistry.addSmelting(this.TreeBlock, new ItemStack(this.itemShadow), 2);
		
		//Wood to planks.
		GameRegistry.addShapelessRecipe(new ItemStack(this.shadowPlank,1), new ItemStack(this.TreeBlock,1) );
		
    }
    private void setupTickHandlers()
    {
    	proxy.registerTickHandlers();
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
				if (i.getItem() == Main.shadowArmor)
				{
					
						return i;
					
							
				}
		}
		return null;
	}
}
