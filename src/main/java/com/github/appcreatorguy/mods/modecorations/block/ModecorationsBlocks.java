package com.github.appcreatorguy.mods.modecorations.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import static com.github.appcreatorguy.mods.modecorations.util.BlockUtils.registerBlock;
import static com.github.appcreatorguy.mods.modecorations.ModecorationsMain.LOGGER;

public class ModecorationsBlocks {
    // Predicates
    public static Boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type){
        return false;
    }
    public static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    // Blocks
    public static final BlockStateTestBlock TEST_BLOCK = new BlockStateTestBlock(AbstractBlock.Settings.of(Material.GLASS).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(ModecorationsBlocks::never).suffocates(ModecorationsBlocks::never).solidBlock(ModecorationsBlocks::never).blockVision(ModecorationsBlocks::never));

    public static void Init(){
        registerBlock(true, "test_block", TEST_BLOCK);

        LOGGER.info("Mo' Decorations Blocks Initialized");
    }
}
