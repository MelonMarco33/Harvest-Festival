package joshie.harvest.blocks;

import joshie.harvest.blocks.BlockStone.Type;
import joshie.harvest.core.HFTab;
import joshie.harvest.core.lib.HFModInfo;
import joshie.harvest.core.util.base.BlockHFEnum;
import joshie.harvest.core.util.generic.Text;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class BlockStone extends BlockHFEnum<Type> {
    public enum Type implements IStringSerializable {
        REAL(true), DECORATIVE_BLANK, DECORATIVE_PURPLE, DECORATIVE_SILVER, DECORATIVE_GREEN, DECORATIVE_BLUE, DECORATIVE_RED,
        REAL_WINTER(true), DECORATIVE_BLANK_WINTER, DECORATIVE_PURPLE_WINTER, DECORATIVE_SILVER_WINTER, DECORATIVE_GREEN_WINTER, DECORATIVE_BLUE_WINTER, DECORATIVE_RED_WINTER;

        private final boolean isReal;

        Type() {
            this.isReal = false;
        }

        Type(boolean isReal) {
            this.isReal = isReal;
        }

        @Override
        public String getName() {
            return toString().toLowerCase();
        }
    }

    public BlockStone() {
        super(Material.ROCK, Type.class, HFTab.MINING);
        setSoundType(SoundType.METAL);
    }

    //TECHNICAL
    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        switch (getEnumFromState(state)) {
            case REAL:
                return -1.0F;
            default:
                return 4F;
        }
    }

    @Override
    public int getToolLevel(Type type) {
        return 2;
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        switch (getEnumFromState(world.getBlockState(pos))) {
            case REAL:
                return 6000000.0F;
            default:
                return 14.0F;
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        if (getEnumFromState(world.getBlockState(pos)) != Type.REAL) {
            ret.add(new ItemStack(HFBlocks.STONE, 1, 1));
        }

        return ret;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalized = getUnlocalizedName();
        String name = stack.getItemDamage() != 0 ? "decorative" : stack.getItem().getUnlocalizedName(stack);
        return Text.localizeFully(unlocalized + "." + name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag) {
        int adjusted = Math.max(0, Math.min(Type.values().length, stack.getItemDamage()));
        Type type = Type.values()[adjusted];
        if (!type.isReal) {
            list.add(Text.translate("tooltip.dirt"));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerModels(Item item, String name) {
        for (int i = 0; i < values.length; i++) {
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(HFModInfo.MODID, "mine/mine_wall_" + getEnumFromMeta(i).getName()), "inventory"));
        }
    }
}