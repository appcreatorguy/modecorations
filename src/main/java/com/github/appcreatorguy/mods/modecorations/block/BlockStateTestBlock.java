package com.github.appcreatorguy.mods.modecorations.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BlockStateTestBlock extends Block {
    public static final BooleanProperty CHARGED = BooleanProperty.of("charged");

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager){
        stateManager.add(CHARGED);
    }

    public BlockStateTestBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(CHARGED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit){
        ItemStack stackInHand = player.getStackInHand(hand);
        if (!stackInHand.isEmpty()){
            if (stackInHand.getItem() == Items.BLAZE_ROD){
                //Remove a single item from the player's hand
                stackInHand.setCount(stackInHand.getCount() - 1);
                player.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 1);
                world.setBlockState(pos, state.with(CHARGED, true));
                return ActionResult.CONSUME;
            }
            else {
                return ActionResult.PASS;
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity){
        if (world.getBlockState(pos).get(CHARGED)){
            CowEntity cowEntity = EntityType.COW.create(world);
            if (cowEntity != null) {
                cowEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos));
            }
            world.spawnEntity(cowEntity);
            world.setBlockState(pos,state.with(CHARGED, false));
        }
    }
}
