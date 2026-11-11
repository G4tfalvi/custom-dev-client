package KexTools.utils;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.common.base.Predicates;

import KexTools.utils.interfaces.InstanceAccess;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Vec3;

public class PlayerUtils implements InstanceAccess{
    public static boolean isInLiquid() {
        for(int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY); x < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1; ++x) {
            for(int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1; ++z) {
                BlockPos pos = new BlockPos(x, (int)Minecraft.getMinecraft().thePlayer.boundingBox.minY, z);
                Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
                if(block != null && !(block instanceof BlockAir))
                    return block instanceof BlockLiquid;
            }
        }
        return false;
    }
    
    public static boolean isBlockUnder() {
        for (int offset = 0; offset < mc.thePlayer.posY + mc.thePlayer.getEyeHeight(); offset += 2) {
            final AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox().offset(0, -offset, 0);

            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, boundingBox).isEmpty())
                return true;
        }
        return false;
    }

    public static String genCracked() {
        String name = PlayerUtils.generateName();
        Minecraft.getMinecraft().session = new Session("KexUser" + name, "", "", "mojang");
        return name;
    }
    public static String generateName() {
        return RandomStringUtils.random(2, "0123456789");
    }
    public static String generateString() {
        return RandomStringUtils.random(16, "0123456789");
    }
    
    public static boolean isInsideBlock() {
        for(int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minX); x < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1; x++) {
            for(int y = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY); y < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxY) + 1; y++) {
                for(int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1; z++) {
                    Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if(block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Minecraft.getMinecraft().theWorld, new BlockPos(x, y, z), Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)));
                        if(block instanceof BlockHopper)
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        if(boundingBox != null && Minecraft.getMinecraft().thePlayer.boundingBox.intersectsWith(boundingBox))
                            return true;
                    }
                }
            }
        }
        return false;
    }

	public static boolean isOnLiquid() {
        boolean onLiquid = false;
        final AxisAlignedBB playerBB = PlayerUtils.mc.thePlayer.getEntityBoundingBox();
        final WorldClient world = PlayerUtils.mc.theWorld;
        final int y = (int) playerBB.offset(0.0, -0.01, 0.0).minY;
        for (int x = MathHelper.floor_double(playerBB.minX); x < MathHelper.floor_double(playerBB.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(playerBB.minZ); z < MathHelper.floor_double(playerBB.maxZ) + 1; ++z) {
                final Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }

	public static Block getBlockRelativeToPlayer(final double offsetX, final double offsetY, final double offsetZ) {
        return mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX + offsetX, mc.thePlayer.posY + offsetY, mc.thePlayer.posZ + offsetZ)).getBlock();
    }

    public static Block blockRelativeToPlayer(final double offsetX, final double offsetY, final double offsetZ) {
        return mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).add(offsetX, offsetY, offsetZ)).getBlock();
    }
    
    public static Block getBlock(final double offsetX, final double offsetY, final double offsetZ) {
        return mc.theWorld.getBlockState(new BlockPos(offsetX, offsetY, offsetZ)).getBlock();
    }

	public static net.minecraft.util.MovingObjectPosition getMouseOver(final float yaw, final float pitch, final float range) {
        final float partialTicks = mc.timer.renderPartialTicks;
        final Entity entity = mc.getRenderViewEntity();
        net.minecraft.util.MovingObjectPosition objectMouseOver;
        Entity mcPointedEntity = null;

        if (entity != null && mc.theWorld != null) {

            mc.mcProfiler.startSection("pick");
            final double d0 = mc.playerController.getBlockReachDistance();
            objectMouseOver = entity.rayTrace(d0, partialTicks);
            double d1 = d0;
            final Vec3 vec3 = entity.getPositionEyes(partialTicks);
            final boolean flag = d0 > (double) range;

            if (objectMouseOver != null) {
                d1 = objectMouseOver.hitVec.distanceTo(vec3);
            }

            final Vec3 vec31 = mc.thePlayer.getVectorForRotation(pitch, yaw);
            final Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
            Entity pointedEntity = null;
            Vec3 vec33 = null;
            final float f = 1.0F;
            final List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(f, f, f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
            double d2 = d1;

            for (final Entity entity1 : list) {
                final float f1 = entity1.getCollisionBorderSize();
                final AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
                final net.minecraft.util.MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                if (axisalignedbb.isVecInside(vec3)) {
                    if (d2 >= 0.0D) {
                        pointedEntity = entity1;
                        vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                        d2 = 0.0D;
                    }
                } else if (movingobjectposition != null) {
                    final double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                    if (d3 < d2 || d2 == 0.0D) {
                        pointedEntity = entity1;
                        vec33 = movingobjectposition.hitVec;
                        d2 = d3;
                    }
                }
            }

            if (pointedEntity != null && flag && vec3.distanceTo(vec33) > (double) range) {
                pointedEntity = null;
                objectMouseOver = new net.minecraft.util.MovingObjectPosition(net.minecraft.util.MovingObjectPosition.MovingObjectType.MISS, vec33, null, new BlockPos(vec33));
            }

            if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
                objectMouseOver = new net.minecraft.util.MovingObjectPosition(pointedEntity, vec33);

                if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
                    mcPointedEntity = pointedEntity;
                }
            }

            mc.mcProfiler.endSection();

            assert objectMouseOver != null;
            return objectMouseOver;
        }

        return null;
    }
	
	public static int findSoup() {
        for (int i = 36; i < 45; i++) {
            final ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (itemStack != null && itemStack.getDisplayName().contains("Stew") && itemStack.stackSize > 0 && itemStack.getItem() instanceof ItemFood) {
                return i;
            }
        }

        return -1;
    }
}
