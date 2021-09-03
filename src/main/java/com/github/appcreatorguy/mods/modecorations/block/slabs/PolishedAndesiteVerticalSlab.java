package com.github.appcreatorguy.mods.modecorations.block.slabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PolishedAndesiteVerticalSlab extends HorizontalFacingBlock {
    public static final EnumProperty<Direction.Axis> AXIS;
    public static final EnumProperty<SlabType> TYPE;

    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape WEST_SHAPE;
    protected static final VoxelShape EAST_SHAPE;
    protected static final VoxelShape TOP_SHAPE;
    protected static final VoxelShape BOTTOM_SHAPE;

    static {
        TYPE = Properties.SLAB_TYPE;
        AXIS = Properties.AXIS;
        NORTH_SHAPE = VoxelShapes.cuboid(0f, 0f, 0f, 1f, 1f, 0.5f);
        SOUTH_SHAPE = VoxelShapes.cuboid(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
        WEST_SHAPE = VoxelShapes.cuboid(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        EAST_SHAPE = VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
        TOP_SHAPE = VoxelShapes.cuboid(0f, 0.5f, 0f, 1f, 1f, 1f);
        BOTTOM_SHAPE = VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.5f, 1f);
    }

    public PolishedAndesiteVerticalSlab(Settings settings) {
        super(settings);
        setDefaultState(
                getStateManager().getDefaultState()
                        .with(TYPE, SlabType.BOTTOM)
                        .with(AXIS, Direction.Axis.Y)
        );
    }

    // CHECK TO SEE IF SLAB CAN BE DOUBLED
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        SlabType slabType = state.get(TYPE);
        Direction.Axis axis = state.get(AXIS);
        if (itemStack.getItem() == (this).asItem()) {
            Direction direction = context.getSide();
            // Determine Position of hit on block for future mode toggle to override vanilla placement
            boolean bl = context.getHitPos().y - (double) context.getBlockPos().getY() > 0.5D;
            boolean blz = context.getHitPos().z - (double) context.getBlockPos().getZ() > 0.5D;
            boolean blx = context.getHitPos().x - (double) context.getBlockPos().getX() > 0.5D;
            if (axis == Direction.Axis.Y) {
                switch (slabType) {
                    case BOTTOM:
                        return direction == Direction.UP;
                    case TOP:
                        return direction == Direction.DOWN;
                    default:
                        return false;
                }
            }
            if (axis == Direction.Axis.X) {
                switch (slabType) {
                    case BOTTOM:
                        return direction == Direction.WEST;
                    case TOP:
                        return direction == Direction.EAST;
                    default:
                        return false;
                }
            }
            if (axis == Direction.Axis.Z) {
                switch (slabType) {
                    case BOTTOM:
                        return direction == Direction.SOUTH;
                    case TOP:
                        return direction == Direction.NORTH;
                    default:
                        return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, AXIS);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state,
                                      BlockView world,
                                      BlockPos pos,
                                      ShapeContext ctx) {
        SlabType slabType = state.get(TYPE);
        Direction.Axis axis = state.get(AXIS);
        switch (axis) {
            case X:
                //noinspection CheckStyle
                switch (slabType) {
                    case TOP:
                        return EAST_SHAPE;
                    case BOTTOM:
                        return WEST_SHAPE;
                }
            case Y:
                //noinspection CheckStyle
                switch (slabType) {
                    case TOP:
                        return TOP_SHAPE;
                    case BOTTOM:
                        return BOTTOM_SHAPE;
                }
                //DEBUG ONLY
                //LOGGER.info("TOP/BOTTOM SLAB IGNORED");
            case Z:
                //noinspection CheckStyle
                switch (slabType) {
                    case TOP:
                        return SOUTH_SHAPE;
                    case BOTTOM:
                        return NORTH_SHAPE;
                }
            default:
                return VoxelShapes.fullCube();
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        if (blockState.isOf(this)) {
            if (blockState.equals(blockState.with(TYPE,
                    SlabType.TOP)) || blockState.equals(blockState.with(TYPE, SlabType.BOTTOM))) {
                return blockState.with(TYPE, SlabType.DOUBLE).with(AXIS, blockState.get(AXIS));
            } else {
                return blockState;
            }
        } else {
            BlockState blockState2 = (this).getDefaultState().with(TYPE, SlabType.BOTTOM).with(AXIS, Direction.Axis.Y);
            Direction direction = ctx.getSide();
            // todo: Add Betterslabs mode toggle?
            switch (direction) {
                case NORTH:
                    return blockState2.with(TYPE, SlabType.TOP).with(AXIS, Direction.Axis.Z);
                case SOUTH:
                    return blockState2.with(TYPE, SlabType.BOTTOM).with(AXIS, Direction.Axis.Z);
                case EAST:
                    return blockState2.with(TYPE, SlabType.TOP).with(AXIS, Direction.Axis.X);
                case WEST:
                    return blockState2.with(TYPE, SlabType.BOTTOM).with(AXIS, Direction.Axis.X);
                case DOWN:
                    return blockState2.with(TYPE, SlabType.TOP).with(AXIS, Direction.Axis.Y);
                default:
                    return blockState2;
            }
        }



        /*
        return this.getDefaultState().with(Properties.HORIZONTAL_FACING,
                ctx.getPlayerFacing().getOpposite());
        */
    }
}
