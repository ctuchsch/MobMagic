package ctuchsch.mods.mobmagic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ctuchsch.mods.mobmagic.blocks.BlockCrystalFurnaceCharger;
import ctuchsch.mods.mobmagic.blocks.BlockEssenceAcid;
import ctuchsch.mods.mobmagic.blocks.BlockEssenceCreeper;
import ctuchsch.mods.mobmagic.blocks.BlockEssenceEnderman;
import ctuchsch.mods.mobmagic.blocks.BlockEssenceSpider;
import ctuchsch.mods.mobmagic.blocks.BlockEssenceTank;
import ctuchsch.mods.mobmagic.blocks.BlockEssenciteBlock;
import ctuchsch.mods.mobmagic.blocks.BlockEssenciteOre;
import ctuchsch.mods.mobmagic.blocks.BlockFossilOre;
import ctuchsch.mods.mobmagic.blocks.BlockStarshineOre;
import ctuchsch.mods.mobmagic.blocks.BlockToolCharger;
import ctuchsch.mods.mobmagic.entity.EntityProjectileCreeper;
import ctuchsch.mods.mobmagic.entity.EntityProjectileEnderman;
import ctuchsch.mods.mobmagic.handlers.BucketHandler;
import ctuchsch.mods.mobmagic.handlers.GuiHandler;
import ctuchsch.mods.mobmagic.items.ItemEssenceAcidBucket;
import ctuchsch.mods.mobmagic.items.ItemEssenceCreeperBucket;
import ctuchsch.mods.mobmagic.items.ItemEssenceCreeperProjectile;
import ctuchsch.mods.mobmagic.items.ItemEssenceEndermanBucket;
import ctuchsch.mods.mobmagic.items.ItemEssenceEndermanProjectile;
import ctuchsch.mods.mobmagic.items.ItemEssenceSpiderBucket;
import ctuchsch.mods.mobmagic.items.ItemEssenciteDust;
import ctuchsch.mods.mobmagic.items.ItemEssenciteIngot;
import ctuchsch.mods.mobmagic.items.ItemEssencitePanel;
import ctuchsch.mods.mobmagic.items.ItemEssenciteStrut;
import ctuchsch.mods.mobmagic.items.ItemFossil;
import ctuchsch.mods.mobmagic.items.ItemInfusionFrame;
import ctuchsch.mods.mobmagic.items.ItemInfusionPedestal;
import ctuchsch.mods.mobmagic.items.ItemLinkingCore;
import ctuchsch.mods.mobmagic.items.ItemMachineChassis;
import ctuchsch.mods.mobmagic.items.ItemMagicDelinker;
import ctuchsch.mods.mobmagic.items.ItemMagicLinker;
import ctuchsch.mods.mobmagic.items.ItemMobWand;
import ctuchsch.mods.mobmagic.items.ItemStarshineCore;
import ctuchsch.mods.mobmagic.items.ItemStarshineCrystal;
import ctuchsch.mods.mobmagic.items.crafting.CraftingRecipes;
import ctuchsch.mods.mobmagic.items.crafting.SmeltingRecipes;
import ctuchsch.mods.mobmagic.messages.MessageInfuserGuiButton;
import ctuchsch.mods.mobmagic.tileentities.TileCrystalFurnaceCharger;
import ctuchsch.mods.mobmagic.tileentities.TileEssenceTank;
import ctuchsch.mods.mobmagic.tileentities.TileToolCharger;

@Mod(modid = MobMagic.MODID, version = MobMagic.VERSION)
public class MobMagic {

	public static final String MODID = "mobmagic";
	public static final String VERSION = "0.0.01";
	
	@Instance(MODID)
	public static MobMagic  instance = new MobMagic();
	
	@SidedProxy( clientSide = "ctuchsch.mods.mobmagic.ClientProxy", serverSide = "ctuchsch.mods.mobmagic.CommonProxy")
	public static Proxy proxy;
	
	private static int modGuiIndex = 0;
	public static final int GUI_MOB_WAND = modGuiIndex++;
	public static final int GUI_MOB_TOOLCHARGER = modGuiIndex++;
	public static final int GUI_MOB_CrystalFurnaceCharger = modGuiIndex++;
	
	private static int entityIndex = 0;
	public static final int ENTITY_INDEX_ESSENCE_CREEPER = entityIndex++;
	public static final int ENTITY_INDEX_ESSENCE_ENDERMAN = entityIndex++;
	public static SimpleNetworkWrapper infuserButtonChannel;
	
	public static Fluid essenceCreeper;
	public static BlockEssenceCreeper blockEssenceCreeper;
	public static Item bucketEssenceCreeper;
	public static Fluid essenceEnderman;
	public static BlockEssenceEnderman blockEssenceEnderman;
	public static Fluid essenceSpider;
	public static BlockEssenceSpider blockEssenceSpider;
	public static Item bucketEssenceSpider;
	public static Fluid essenceAcid;
	public static BlockEssenceAcid blockEssenceAcid;
	public static Item bucketEssenceAcid;	
	public static Block blockEssenceTank;
	public static Block blockEssenciteOre;
	public static Block blockEssenciteBlock;
	public static Block blockFossilOre;
	public static Block blockStarshineOre;
	public static Block blockToolCharger;
	public static Block blockCrystalFurnaceChargerActive;
	public static Block blockCrystalFurnaceCharger;
	public static Item bucketEssenceEnderman;
	public static Item itemMobWand;
	public static Item entityProjectileCreeper;
	public static Item itemEssenceCreeperProjectile;
	public static Item itemEssenceEndermanProjectile;
	public static Item itemStarshineCrystal;
	public static Item itemStarshineCore;
	public static Item itemFossil;
	public static Item itemEssenciteDust;
	public static Item itemEssenciteIngot;
	public static Item itemMagicLinker;
	public static Item itemMagicDelinker;
	public static Item itemEssencitePanel;
	public static Item itemEssenciteStrut;
	public static Item itemInfusionFrame;
	public static Item itemInfusionPedestal;
	public static Item itemLinkingCore;
	public static Item itemMachineChassis;


	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		GameRegistry.registerWorldGenerator(new WorldGeneration(), 1);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println("MobMagic");
		createAndRegisterFluids();
		createAndRegisterBlocks();
		createAndRegisterItems();
		createAndRegisterEntities();
		createAndRegisterMisc();
		createAndRegisterRecipes();
		proxy.registerRenderers();
		NetworkRegistry.INSTANCE.registerGuiHandler(this,  new GuiHandler());
		infuserButtonChannel =  NetworkRegistry.INSTANCE.newSimpleChannel("MobMagicInfuserButton");
		infuserButtonChannel.registerMessage(MessageInfuserGuiButton.Handler.class, MessageInfuserGuiButton.class, 0, Side.SERVER);
	}
	
	private void createAndRegisterEntities() {
		EntityRegistry.registerModEntity(EntityProjectileCreeper.class, "ProjectileEssenceCreeper", ENTITY_INDEX_ESSENCE_CREEPER, this.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityProjectileEnderman.class, "ProjectileEssenceEnderman", ENTITY_INDEX_ESSENCE_ENDERMAN, this.instance, 64, 10, true);
		GameRegistry.registerTileEntity(TileEssenceTank.class, "tileessencetank");
		GameRegistry.registerTileEntity(TileToolCharger.class, "tiletoolcharger");
		GameRegistry.registerTileEntity(TileCrystalFurnaceCharger.class, "tilecrystalfurnacecharger");
	}

	private void createAndRegisterMisc() {
		BucketHandler.INSTANCE.buckets.put(blockEssenceCreeper, bucketEssenceCreeper);
		BucketHandler.INSTANCE.buckets.put(blockEssenceEnderman, bucketEssenceEnderman);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(proxy);
	}

	private void createAndRegisterItems() {
		bucketEssenceCreeper = new ItemEssenceCreeperBucket(blockEssenceCreeper).setUnlocalizedName("essencecreeperbucket").setContainerItem(Items.bucket).setTextureName(this.MODID +":bucketEssenceCreeper");
		GameRegistry.registerItem(bucketEssenceCreeper, "essencecreeperbucket");
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("essence.creeper", FluidContainerRegistry.BUCKET_VOLUME),new ItemStack(bucketEssenceCreeper),new ItemStack(Items.bucket));
		
		bucketEssenceEnderman = new ItemEssenceEndermanBucket(blockEssenceEnderman).setUnlocalizedName("essenceendermanbucket").setContainerItem(Items.bucket).setTextureName(this.MODID+":bucketEssenceEnderman");
		GameRegistry.registerItem(bucketEssenceEnderman, "essenceendermanbucket");
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("essence.enderman", FluidContainerRegistry.BUCKET_VOLUME),new ItemStack(bucketEssenceEnderman),new ItemStack(Items.bucket));
		
		bucketEssenceSpider = new ItemEssenceSpiderBucket(blockEssenceSpider).setUnlocalizedName("essencespiderbucket").setContainerItem(Items.bucket).setTextureName(this.MODID+":bucketEssenceSpider");
		GameRegistry.registerItem(bucketEssenceSpider, "essencespiderbucket");
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("essence.spider", FluidContainerRegistry.BUCKET_VOLUME),new ItemStack(bucketEssenceSpider),new ItemStack(Items.bucket));
		
		bucketEssenceAcid = new ItemEssenceAcidBucket(blockEssenceAcid).setUnlocalizedName("essenceacidbucket").setContainerItem(Items.bucket).setTextureName(this.MODID+":bucketEssenceAcid");
		GameRegistry.registerItem(bucketEssenceAcid, "essenceacidbucket");
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("essence.acid", FluidContainerRegistry.BUCKET_VOLUME),new ItemStack(bucketEssenceAcid),new ItemStack(Items.bucket));
		
		itemMobWand = new ItemMobWand().setUnlocalizedName("mobwand").setTextureName(this.MODID + ":mobWand");
		GameRegistry.registerItem(itemMobWand, "mobwand");	
		
		itemEssenceCreeperProjectile = new ItemEssenceCreeperProjectile().setUnlocalizedName("essencecreeperprojectile").setTextureName(this.MODID+":orbCreeper");
		GameRegistry.registerItem(itemEssenceCreeperProjectile,"essencecreeperprojectile");

		itemEssenceEndermanProjectile = new ItemEssenceEndermanProjectile().setUnlocalizedName("essenceendermanprojectile").setTextureName(this.MODID+":orbEnderman");
		GameRegistry.registerItem(itemEssenceEndermanProjectile,"essenceendermanprojectile");		
		
		itemStarshineCrystal = new ItemStarshineCrystal().setUnlocalizedName("starshinecrystal").setTextureName(this.MODID + ":starshineCrystal");
		GameRegistry.registerItem(itemStarshineCrystal, "starshinecrystal");
		
		itemStarshineCore = new ItemStarshineCore(10000).setUnlocalizedName("starshinecore").setTextureName(this.MODID + ":starshineCore");
		GameRegistry.registerItem(itemStarshineCore, "starshinecore");
		
		itemFossil = new ItemFossil().setUnlocalizedName("fossil").setTextureName(this.MODID + ":fossil");
		GameRegistry.registerItem(itemFossil, "itemfossil");
		
		itemEssenciteDust = new ItemEssenciteDust().setUnlocalizedName("essencitedust").setTextureName(this.MODID + ":essenciteDust");
		GameRegistry.registerItem(itemEssenciteDust, "itemessencitedust");
		
		itemEssenciteIngot = new ItemEssenciteIngot().setUnlocalizedName("essenciteingot").setTextureName(this.MODID + ":essenciteIngot");
		GameRegistry.registerItem(itemEssenciteIngot, "itemessenciteingot");
		
		itemMagicLinker = new ItemMagicLinker().setUnlocalizedName("magiclinker").setTextureName(this.MODID+":magicLinker");
		GameRegistry.registerItem(itemMagicLinker, "itemmagiclinker");
		
		itemMagicDelinker = new ItemMagicDelinker().setUnlocalizedName("magicdelinker").setTextureName(this.MODID+":magicDelinker");
		GameRegistry.registerItem(itemMagicDelinker, "itemmagicdelinker");
		
		itemEssencitePanel = new ItemEssencitePanel().setUnlocalizedName("essencitepanel").setTextureName(this.MODID+":essencitePanel");
		GameRegistry.registerItem(itemEssencitePanel, "itemessencitepanel");
		
		itemEssenciteStrut = new ItemEssenciteStrut().setUnlocalizedName("essencitestrut").setTextureName(this.MODID+":essenciteStrut");
		GameRegistry.registerItem(itemEssenciteStrut, "itemessencitestrut");
		
		itemInfusionFrame = new ItemInfusionFrame().setUnlocalizedName("infusionframe").setTextureName(this.MODID+":infusionFrame");
		GameRegistry.registerItem(itemInfusionFrame, "itemfusionframe");
		
		itemInfusionPedestal = new ItemInfusionPedestal().setUnlocalizedName("infusionpedestal").setTextureName(this.MODID+":infusionpedestal");
		GameRegistry.registerItem(itemInfusionPedestal, "itemfusionpedestal");
		
		itemLinkingCore = new ItemLinkingCore().setUnlocalizedName("linkingcore").setTextureName(this.MODID+":linkingcore");
		GameRegistry.registerItem(itemLinkingCore, "itemlinkingcore");
		
		itemMachineChassis = new ItemMachineChassis().setUnlocalizedName("machinechassis").setTextureName(this.MODID+":machinechassis");
		GameRegistry.registerItem(itemMachineChassis, "itemmachinechassis");
	}

	private void createAndRegisterBlocks() {
		blockEssenceCreeper = new BlockEssenceCreeper(essenceCreeper, Material.water);
		blockEssenceCreeper.setBlockName("essencecreeper");
		GameRegistry.registerBlock(blockEssenceCreeper,"essencecreeper");
		
		blockEssenceEnderman = new BlockEssenceEnderman(essenceEnderman, Material.water);
		blockEssenceEnderman.setBlockName("essenceenderman");
		GameRegistry.registerBlock(blockEssenceEnderman,"essenceenderman");
		
		blockEssenceSpider = new BlockEssenceSpider(essenceSpider, Material.water);
		blockEssenceSpider.setBlockName("essencespider");
		GameRegistry.registerBlock(blockEssenceSpider, "essencespider");
		
		blockEssenceAcid = new BlockEssenceAcid(essenceAcid, Material.water);
		blockEssenceAcid.setBlockName("essenceacid");
		GameRegistry.registerBlock(blockEssenceAcid, "essenceAcid");
		
		blockEssenceTank = new BlockEssenceTank().setBlockName("blockessencetank");
		GameRegistry.registerBlock(blockEssenceTank, "blockessencetank");
		
		blockEssenciteOre = new BlockEssenciteOre().setBlockName("blockessenciteore").setBlockTextureName(this.MODID + ":blockEssenciteOre");
		GameRegistry.registerBlock(blockEssenciteOre, "blockessenciteore");
		
		blockFossilOre = new BlockFossilOre().setBlockName("blockfossilore").setBlockTextureName(this.MODID + ":blockFossilOre");
		GameRegistry.registerBlock(blockFossilOre, "blockfossilore");
		
		blockStarshineOre = new BlockStarshineOre().setBlockName("blockstarshineore").setBlockTextureName(this.MODID + ":blockStarshineOre");
		GameRegistry.registerBlock(blockStarshineOre, "blockstarshineore");
		
		blockEssenciteBlock = new BlockEssenciteBlock().setBlockName("blockessenciteblock").setBlockTextureName(this.MODID + ":blockEssenciteBlock");
		GameRegistry.registerBlock(blockEssenciteBlock, "blockessenciteblock");
		
		blockToolCharger = new BlockToolCharger().setBlockName("blocktoolcharger");
		GameRegistry.registerBlock(blockToolCharger, "blocktoolcharger");
		
		blockCrystalFurnaceCharger = new BlockCrystalFurnaceCharger(false).setBlockName("blockcrystalfurnacecharger");
		GameRegistry.registerBlock(blockCrystalFurnaceCharger, "blockcrystalfurnacecharger");
		
		blockCrystalFurnaceChargerActive = new BlockCrystalFurnaceCharger(true).setBlockName("blockcrystalfurnacechargeractive");
		GameRegistry.registerBlock(blockCrystalFurnaceChargerActive, "blockcrystalfurnacechargeractive");		
		
	}

	private void createAndRegisterFluids() {
		essenceCreeper = new Fluid("essence.creeper");
		FluidRegistry.registerFluid(essenceCreeper);
		
		essenceEnderman = new Fluid("essence.enderman");
		FluidRegistry.registerFluid(essenceEnderman);
		
		essenceSpider = new Fluid("essence.spider");
		FluidRegistry.registerFluid(essenceSpider);
		
		essenceAcid = new Fluid("essence.acid");
		FluidRegistry.registerFluid(essenceAcid);
	}
	
	private void createAndRegisterRecipes() {
		CraftingRecipes.addRecipes();
		CraftingRecipes.addInfusionRecipe();
		SmeltingRecipes.addRecipes();		
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		// do nothing.
	}
	
	// Custom Creative Tab for MobMagic
	public static CreativeTabs tabCustom = new CreativeTabs("MobMagic") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return MobMagic.itemStarshineCore;
		}
	};


	
}
