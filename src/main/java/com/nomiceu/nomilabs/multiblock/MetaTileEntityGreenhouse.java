package com.nomiceu.nomilabs.multiblock;

import com.nomiceu.nomilabs.registry.LabsRecipeMaps;
import com.nomiceu.nomilabs.registry.LabsTextures;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import org.jetbrains.annotations.NotNull;

public class MetaTileEntityGreenhouse extends GCYMRecipeMapMultiblockController {

    public MetaTileEntityGreenhouse(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, LabsRecipeMaps.GREENHOUSE_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityGreenhouse(this.metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCCCC", "BGGGGGB", "BGGGGGB", "#BGGGB#", "##BLB##")
                .aisle("CDDPDDC", "GAAAAAG", "GAAAAAG", "#GAAAG#", "##GGG##").setRepeatable(3)
                .aisle("CDDPDDC", "BAAAAAB", "BAAAAAB", "#BAAAB#", "##BLB##")
                .aisle("CDDPDDC", "GAAAAAG", "GAAAAAG", "#GAAAG#", "##GGG##").setRepeatable(3)
                .aisle("CCCSCCC", "BGGGGGB", "BGGGGGB", "#BGGGB#", "##BLB##")
                .where('S', selfPredicate())
                .where('C', states(getCasingState1()).setMinGlobalLimited(20).or(autoAbilities()))
                .where('B', states(getCasingState1())) // Like C, but only accepts solid steel
                .where('G', states(getCasingState2()))
                .where('D', states(getCasingStateDirt()))
                .where('L', states(getCasingStateLamp())) // Custom lamp later, with fan texture on top, steel on sides, and lamp bottom, where the multi only activates if block above lamps is clear? Also, make light turn on when multi on
                .where('P', states(getCasingStatePipe()))
                .where('A', air())
                .where('#', any())
                .build();

    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    protected IBlockState getCasingState1() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    protected IBlockState getCasingState2() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    protected IBlockState[] getCasingStateDirt() {
        assert Blocks.DIRT != null;
        assert Blocks.GRASS != null;
        return new IBlockState[] {Blocks.DIRT.getDefaultState(), Blocks.GRASS.getDefaultState()}; // Allow dirt or grass
    }

    protected IBlockState getCasingStateLamp() {
        return MetaBlocks.LAMPS.get(EnumDyeColor.PURPLE).getDefaultState();
    }

    protected IBlockState getCasingStatePipe() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }

    @Override
    protected @NotNull ICubeRenderer getFrontOverlay() {
        return LabsTextures.GREENHOUSE_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }
}