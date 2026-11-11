package KexTools.utils;

import KexTools.utils.interfaces.InstanceAccess;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class Rotation implements InstanceAccess{
	
	private final float rotationYaw;

    private final float rotationPitch;
	
    
    public Rotation(float rotationYaw, float rotationPitch) {
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
    }

    public static Rotation fromArray(float[] array) {
        return new Rotation(array[0], array[1]);
    }

    public static Rotation fromFacing(EntityLivingBase entity) {
        Rotation rotation = AimUtil.getRotationsRandom(entity);
        return new Rotation(MathHelper.floor_double(rotation.getRotationYaw()), rotation.getRotationPitch());
    }

    public float getRotationYaw() {
        return rotationYaw;
    }

    public float getRotationPitch() {
        return rotationPitch;
    }

	public static float[] getFixedRotation(final float[] rotations, final float[] lastRotations) {

        final float yaw = rotations[0];
        final float pitch = rotations[1];

        final float lastYaw = lastRotations[0];
        final float lastPitch = lastRotations[1];

        final float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        final float gcd = f * f * f * 1.2F;

        final float deltaYaw = yaw - lastYaw;
        final float deltaPitch = pitch - lastPitch;

        final float fixedDeltaYaw = deltaYaw - (deltaYaw % gcd);
        final float fixedDeltaPitch = deltaPitch - (deltaPitch % gcd);

        final float fixedYaw = lastYaw + fixedDeltaYaw;
        final float fixedPitch = lastPitch + fixedDeltaPitch;

        return new float[]{fixedYaw, fixedPitch};
    }
}
