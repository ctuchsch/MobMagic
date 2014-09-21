package ctuchsch.mods.mobmagic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import ctuchsch.mods.mobmagic.blocks.BlockEssenceCreeper;
import ctuchsch.mods.mobmagic.blocks.BlockEssenceEnderman;
import ctuchsch.mods.mobmagic.blocks.BlockEssenceTank;
<<<<<<< HEAD
import ctuchsch.mods.mobmagic.blocks.BlockEssenciteBlock;
import ctuchsch.mods.mobmagic.blocks.BlockEssenciteOre;
import ctuchsch.mods.mobmagic.blocks.BlockFossilOre;
import ctuchsch.mods.mobmagic.blocks.BlockStarshineOre;
=======
import ctuchsch.mods.mobmagic.blocks.BlockToolCharger;
>>>>>>> origin/master
import ctuchsch.mods.mobmagic.entity.EntityProjectileCreeper;
import ctuchsch.mods.mobmagic.entity.EntityProjectileEnderman;
import ctuchsch.mods.mobmagic.handlers.BucketHandler;
import ctuchsch.mods.mobmagic.items.ItemEssenceCreeperBucket;
import ctuchsch.mods.mobmagic.items.ItemEssenceCreeperProjectile;
import ctuchsch.mods.mobmagic.items.ItemEssenceEndermanBucket;
import ctuchsch.mods.mobmagic.items.ItemEssenceEndermanProjectile;
import ctuchsch.mods.mobmagic.items.ItemFossil;
import ctuchsch.mods.mobmagic.items.ItemMobWand;
import ctuchsch.mods.mobmagic.items.ItemStarshineCrystal;
import ctuchsch.mods.mobmagic.tileentities.TileEssenceTank;
import ctuchsch.mods.mobmagic.tileentities.TileToolCharger;

@Mod(modid = MobMagic.MODID, version = MobMagic.VERSION)
public class MobMagic {

	public static final String MODID = "mobmagic";
	public static final String VERSION = "0.0.01";
	
	@Instance(MODID)
	public static MobMagic  instance = new MobMagic();
	
	@SidedProxy(clientSide = "ctuchsch.mods.mobmagic.ClientProxy", serverSide = "ctuchsch.mods.mobmagic.CommonProxy")
	public static CommonProxy proxy;
	
	private static int modGuiIndex = 0;
	public static final int GUI_MOB_WAND = modGuiIndex++;
	
	private static int entityIndex = 0;
	public static final int ENTITY_INDEX_ESSENCE_CREEPER = entityIndex++;
	public static final int ENTITY_INDEX_ESSENCE_ENDERMAN = entityIndex++;
	
	public static Fluid essenceCreeper;
	public static BlockEssenceCreeper blockEssenceCreeper;
	public static Item bucketEssenceCreeper;
	public static Fluid essenceEnderman;
	public static BlockEssenceEnderman blockEssenceEnderman;
	public static Block blockEssenceTank;
	public static Block blockEssenciteOre;
	public static Block blockEssenciteBlock;
	public static Block blockFossilOre;
	public static Block blockStarshineOre;
	public static Item bucketEssenceEnderman;
	public static Item mobWand;
	public static Item entityProjectileCreeper;
	public static Item itemEssenceCreeperProjectile;
	public static Item itemEssenceEndermanProjectile;
<<<<<<< HEAD
	public static Item itemStarshineCrystal;
	public static Item itemFossil;
=======
	public static Block blockToolCharger;
>>>>>>> origin/master
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		// do nothing.
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println("MobMagic");
		createAndRegisterFluids();
		createAndRegisterBlocks();
		createAndRegisterItems();
		createAndRegisterEntities();
		createAndRegisterMisc();
		proxy.registerRenderers();
		NetworkRegistry.INSTANCE.registerGuiHandler(this,  new CommonProxy());
	}
	
	private void createAndRegisterEntities() {
		EntityRegistry.registerModEntity(EntityProjectileCreeper.class, "ProjectileEssenceCreeper", ENTITY_INDEX_ESSENCE_CREEPER, this.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityProjectileEnderman.class, "ProjectileEssenceEnderman", ENTITY_INDEX_ESSENCE_ENDERMAN, this.instance, 64, 10, true);
		GameRegistry.registerTileEntity(TileEssenceTank.class, "tileessencetank");
		GameRegistry.registerTileEntity(TileToolCharger.class, "tiletoolcharger");
		
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
		
		mobWand = new ItemMobWand().setUnlocalizedName("mobwand").setTextureName(this.MODID + ":mobWand");
		GameRegistry.registerItem(mobWand, "mobwand");	
		
		itemEssenceCreeperProjectile = new ItemEssenceCreeperProjectile().setUnlocalizedName("essencecreeperprojectile").setTextureName(this.MODID+":orbCreeper");
		GameRegistry.registerItem(itemEssenceCreeperProjectile,"essencecreeperprojectile");

		itemEssenceEndermanProjectile = new ItemEssenceEndermanProjectile().setUnlocalizedName("essenceendermanprojectile").setTextureName(this.MODID+":orbEnderman");
		GameRegistry.registerItem(itemEssenceEndermanProjectile,"essenceendermanprojectile");		
		
		itemStarshineCrystal = new ItemStarshineCrystal().setUnlocalizedName("starshinecrystal").setTextureName(this.MODID + ":starshineCrystal");
		GameRegistry.registerItem(itemStarshineCrystal, "starshinecrystal");
		
		itemFossil = new ItemFossil().setUnlocalizedName("fossil").setTextureName(this.MODID + ":fossil");
		GameRegistry.registerItem(itemFossil, "itemfossil");
	}

	private void createAndRegisterBlocks() {
		blockEssenceCreeper = new BlockEssenceCreeper(essenceCreeper, Material.water);
		blockEssenceCreeper.setBlockName("essencecreeper");
		GameRegistry.registerBlock(blockEssenceCreeper,"essencecreeper");
		
		blockEssenceEnderman = new BlockEssenceEnderman(essenceEnderman, Material.water);
		blockEssenceEnderman.setBlockName("essenceenderman");
		GameRegistry.registerBlock(blockEssenceEnderman,"essenceenderman");
		
		blockEssenceTank = new BlockEssenceTank().setBlockName("blockessencetank");
		GameRegistry.registerBlock(blockEssenceTank, "blockessencetank");
		
<<<<<<< HEAD
		blockEssenciteOre = new BlockEssenciteOre().setBlockName("blockessenciteore").setBlockTextureName(this.MODID + ":blockEssenciteOre");
		GameRegistry.registerBlock(blockEssenciteOre, "blockessenciteore");
		
		blockFossilOre = new BlockFossilOre().setBlockName("blockfossilore").setBlockTextureName(this.MODID + ":blockFossilOre");
		GameRegistry.registerBlock(blockFossilOre, "blockfossilore");
		
		blockStarshineOre = new BlockStarshineOre().setBlockName("blockstarshineore").setBlockTextureName(this.MODID + ":blockStarshineOre");
		GameRegistry.registerBlock(blockStarshineOre, "blockstarshineore");
		
		blockEssenciteBlock = new BlockEssenciteBlock().setBlockName("blockessenciteblock").setBlockTextureName(this.MODID + ":blockEssenciteBlock");
		GameRegistry.registerBlock(blockEssenciteBlock, "blockessenciteblock");
		
=======
		blockToolCharger = new BlockToolCharger().setBlockName("blocktoolcharger");
		GameRegistry.registerBlock(blockToolCharger, "blocktoolcharger");
>>>>>>> origin/master
		
	}

	private void createAndRegisterFluids() {
		essenceCreeper = new Fluid("essence.creeper");
		FluidRegistry.registerFluid(essenceCreeper);
		
		essenceEnderman = new Fluid("essence.enderman");
		FluidRegistry.registerFluid(essenceEnderman);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		// do nothing.
	}

	
}
