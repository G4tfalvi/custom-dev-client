package KexTools.utils;

import java.util.concurrent.ThreadLocalRandom;

import KexTools.utils.interfaces.InstanceAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;

public class AimUtil implements InstanceAccess{
	
	public static Rotation getRotationsRandom(EntityLivingBase entity) {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        double randomXZ = threadLocalRandom.nextDouble(-0.05, 0.1);
        double randomY = threadLocalRandom.nextDouble(-0.05, 0.1);
        double x = entity.posX + randomXZ;
        double y = entity.posY + (entity.getEyeHeight() / 2.05) + randomY;
        double z = entity.posZ + randomXZ;
        return attemptFacePosition(x, y, z);
    }
	
	public static Rotation getBowAngles(Entity entity) {
        double xDelta = entity.posX - entity.lastTickPosX;
        double zDelta = entity.posZ - entity.lastTickPosZ;
        double distance = mc.thePlayer.getDistanceToEntity(entity) % .8;
        boolean sprint = entity.isSprinting();
        double xMulti = distance / .8 * xDelta * (sprint ? 1.45 : 1.3);
        double zMulti = distance / .8 * zDelta * (sprint ? 1.45 : 1.3);
        double x = entity.posX + xMulti - mc.thePlayer.posX;
        double y = mc.thePlayer.posY + mc.thePlayer.getEyeHeight()
                - (entity.posY + entity.getEyeHeight());
        double z = entity.posZ + zMulti - mc.thePlayer.posZ;
        double distanceToEntity = mc.thePlayer.getDistanceToEntity(entity);
        float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90;
        float pitch = (float) Math.toDegrees(Math.atan2(y, distanceToEntity));
        return new Rotation(yaw, pitch);
    }
	
    public static Rotation attemptFacePosition(double x, double y, double z) {
        double xDiff = x - mc.thePlayer.posX;
        double yDiff = y - mc.thePlayer.posY - 1.2;
        double zDiff = z - mc.thePlayer.posZ;

        double dist = Math.hypot(xDiff, zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180 / Math.PI) - 90;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180 / Math.PI);
        return new Rotation(yaw, pitch);
    }
    public static Rotation attemptFacePosition(BlockPos blockPos) {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        double randomXZ = threadLocalRandom.nextDouble(-0.008, 0.008);

        double xDiff = blockPos.getX() - mc.thePlayer.posX + randomXZ;
        double zDiff = blockPos.getZ() - mc.thePlayer.posZ + randomXZ;

        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180 / Math.PI) - 90;
        return new Rotation(yaw, 80.0F);
    }
}
