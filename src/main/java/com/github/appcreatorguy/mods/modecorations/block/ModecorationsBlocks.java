package com.github.appcreatorguy.mods.modecorations.block;

import com.github.appcreatorguy.mods.modecorations.block.slabs.PolishedAndesiteVerticalSlab;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import static com.github.appcreatorguy.mods.modecorations.ModecorationsMain.LOGGER;
import static com.github.appcreatorguy.mods.modecorations.util.BlockUtils.registerBlock;

public class ModecorationsBlocks {
    // Blocks
    public static final BlockStateTestBlock TEST_BLOCK =
            new BlockStateTestBlock(AbstractBlock.Settings
                    .of(Material.GLASS)
                    .strength(0.3F)
                    .sounds(BlockSoundGroup.GLASS)
                    .nonOpaque()
                    .allowsSpawning(ModecorationsBlocks::never)
                    .suffocates(ModecorationsBlocks::never)
                    .solidBlock(ModecorationsBlocks::never)
                    .blockVision(ModecorationsBlocks::never));

    public static final PolishedAndesiteVerticalSlab POLISHED_ANDESITE_VERTICAL_SLAB =
            new PolishedAndesiteVerticalSlab(FabricBlockSettings.copy(Blocks.POLISHED_ANDESITE_SLAB));

    // Predicates
    public static Boolean never(BlockState state,
                                BlockView world,
                                BlockPos pos,
                                EntityType<?> type) {
        return false;
    }

    public static boolean never(BlockState state,
                                BlockView world,
                                BlockPos pos) {
        return false;
    }

    public static void init() {
        registerBlock(true, "test_block", TEST_BLOCK);
        registerBlock(true, ItemGroup.BUILDING_BLOCKS, "polished_andesite_vertical_slab", POLISHED_ANDESITE_VERTICAL_SLAB);

        LOGGER.info("Mo' Decorations Blocks Initialized");
    }
}
