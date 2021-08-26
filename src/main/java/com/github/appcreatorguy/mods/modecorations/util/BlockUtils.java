package com.github.appcreatorguy.mods.modecorations.util;

import com.github.appcreatorguy.mods.modecorations.ModecorationsMain;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

public record BlockUtils() {

    //////////////////////////////////////////////////
    //              REGISTRATION UTILS              //
    //////////////////////////////////////////////////

    /** Registers a block without a block item.
     *
     *  @param name     Name of the block (path)
     *  @param block    The Block field
     */
    public static void registerBlockOnly(String name, Block block) {
        Registry.register(Registry.BLOCK, ModecorationsMain.id(name), block);
    }

    /** Registers a block and its block item.
     *
     *  @param fireproof    if the Block item should resist to fire & Lava.
     *  @param group        the ItemGroup this block should be in.
     *  @param name         Name of the block (Identifier path).
     *  @param block        The declared Block that will be registered.
     */
    public static void registerBlock(boolean fireproof, @Nullable ItemGroup group, String name, Block block){
        Item.Settings normalSettings = group != null ? new Item.Settings().group(group) : new Item.Settings();
        Item.Settings fireproofSettings = group != null ? new Item.Settings().group(group).fireproof() : new Item.Settings().fireproof();

        Registry.register(Registry.BLOCK, ModecorationsMain.id(name), block);
        Registry.register(Registry.ITEM, ModecorationsMain.id(name), new BlockItem(block, (fireproof ? fireproofSettings : normalSettings)));
    }

    public static void registerBlock(boolean fireproof, String name, Block block) {
        registerBlock(fireproof, null, name, block);
    }
}
