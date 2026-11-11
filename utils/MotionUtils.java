package KexTools.utils;

import javax.vecmath.Vector2d;

import KexTools.event.events.EventMove;
import KexTools.module.combat.KillAura;
import KexTools.utils.enums.ACType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public class MotionUtils {
	
	public static final double WALK_SPEED = 0.221;
    public static final double BUNNY_SLOPE = 0.66;
    public static final double MOD_SPRINTING = 1.3F;
    public static final double MOD_SNEAK = 0.3F;
    public static final double MOD_ICE = 2.5F;
    public static final double MOD_WEB = 0.105 / WALK_SPEED;
    public static final double JUMP_HEIGHT = 0.42F;
    public static final double BUNNY_FRICTION = 159.9F;
    public static final double Y_ON_GROUND_MIN = 0.00001;
    public static final double Y_ON_GROUND_MAX = 0.0626;

    public static final double AIR_FRICTION = 0.9800000190734863D;
    public static final double WATER_FRICTION = 0.800000011920929D;
    public static final double LAVA_FRICTION = 0.5D;
    public static final double MOD_SWIM = 0.115F / WALK_SPEED;
    public static final double[] MOD_DEPTH_STRIDER = {
            1.0F,
            0.1645F / MOD_SWIM / WALK_SPEED,
            0.1995F / MOD_SWIM / WALK_SPEED,
            1.0F / MOD_SWIM,
    };
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public double getSpeed() {
    	return Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
    }
	public double speedPotionAmp(final double amp) {
        return mc.thePlayer.isPotionActive(Potion.moveSpeed) ? ((mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) * amp) : 0;
    }
	
	public void strafe() {
        strafe(getSpeed());
    }
	
	public static double getBaseSpeed(ACType value) {
		double val;
        double potVal;
        switch (value) {
        case AAC:
        	val = 0.34;
            potVal = 0.1525;
        	break;
        default:
            val = 0.2873;
            potVal = 0.2;
            break;
        }
        double baseSpeed = val;

        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amp = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
            baseSpeed *= 1 + potVal * amp;
        }

        return baseSpeed;
	}
	
	public boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }
	
	public void strafe(final double speed) {
        if (!isMoving()) {
            return;
        }

        final double yaw = getDirection();
        mc.thePlayer.motionX = -MathHelper.sin((float) yaw) * speed;
        mc.thePlayer.motionZ = MathHelper.cos((float) yaw) * speed;
    }
	
	public double getDirection() {
        float rotationYaw = mc.thePlayer.rotationYaw;

        if (mc.thePlayer.moveForward < 0F) rotationYaw += 180F;

        float forward = 1F;

        if (mc.thePlayer.moveForward < 0F) forward = -0.5F;
        else if (mc.thePlayer.moveForward > 0F) forward = 0.5F;

        if (mc.thePlayer.moveStrafing > 0F) rotationYaw -= 90F * forward;
        if (mc.thePlayer.moveStrafing < 0F) rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }
	
	public void stop() {
		mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
	}
	
	 public static void setMotion(EventMove event, double moveSpeed) {
		 MovementInput movementInput = mc.thePlayer.movementInput;
		 double moveForward = movementInput.moveForward;
	     double moveStrafe = movementInput.moveStrafe;
		 
	     double rotationYaw = mc.thePlayer.rotationYaw;
	     
		 event.setStrafeSpeed(moveSpeed);
		 
		 if (moveForward == 0.0D && moveStrafe == 0.0D) {
	            event.setMotionX(0);
	            event.setMotionZ(0);
	        } else {
	            if (moveStrafe > 0) {
	                moveStrafe = 1;
	            } else if (moveStrafe < 0) {
	                moveStrafe = -1;
	            }
	            if (moveForward != 0.0D) {
	                if (moveStrafe > 0.0D) {
	                    rotationYaw += moveForward > 0.0D ? -45 : 45;
	                } else if (moveStrafe < 0.0D) {
	                    rotationYaw += moveForward > 0.0D ? 45 : -45;
	                }
	                moveStrafe = 0.0D;
	                if (moveForward > 0.0D) {
	                    moveForward = 1.0D;
	                } else if (moveForward < 0.0D) {
	                    moveForward = -1.0D;
	                }
	            }
	            double cos = Math.cos(Math.toRadians(rotationYaw + 90.0F));
	            double sin = Math.sin(Math.toRadians(rotationYaw + 90.0F));
	            event.setMotionX(moveForward * moveSpeed * cos
	                    + moveStrafe * moveSpeed * sin);
	            event.setMotionZ(moveForward * moveSpeed * sin
	                    - moveStrafe * moveSpeed * cos);
	        }
	 }
	
	 public static void setMotion(double moveSpeed) {
	        EntityLivingBase entity = KillAura.target;
	        MovementInput movementInput = mc.thePlayer.movementInput;

	        double moveForward = movementInput.moveForward;
	        double moveStrafe =  movementInput.moveStrafe;
	        double rotationYaw = mc.thePlayer.rotationYaw;

	        if (moveForward == 0.0D && moveStrafe == 0.0D) {
	            mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
	        } else {
	            if (moveStrafe > 0) {
	                moveStrafe = 1;
	            } else if (moveStrafe < 0) {
	                moveStrafe = -1;
	            }
	            if (moveForward != 0.0D) {
	                if (moveStrafe > 0.0D) {
	                    rotationYaw += (moveForward > 0.0D ? -45 : 45);
	                } else if (moveStrafe < 0.0D) {
	                    rotationYaw += (moveForward > 0.0D ? 45 : -45);
	                }
	                moveStrafe = 0.0D;
	                if (moveForward > 0.0D) {
	                    moveForward = 1.0D;
	                } else if (moveForward < 0.0D) {
	                    moveForward = -1.0D;
	                }
	            }
	            rotationYaw *= 0.995;
	            double cos = Math.cos(Math.toRadians(rotationYaw + 90.0F));
	            double sin = Math.sin(Math.toRadians(rotationYaw + 90.0F));
	            mc.thePlayer.motionX = moveForward * moveSpeed * cos
	                    + moveStrafe * moveSpeed * sin;
	            mc.thePlayer.motionZ = moveForward * moveSpeed * sin
	                    - moveStrafe * moveSpeed * cos;
	        }
	    }
	 
	public static double getMotion(float baseMotionY) {
        Potion potion = Potion.jump;
        if (mc.thePlayer.isPotionActive(potion)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(potion).getAmplifier();
            baseMotionY += (amplifier + 1) * 0.1F;
        }

        return baseMotionY;
    }
	
	public static double getMotion(ACType antiCheatType) {
        Potion potion = Potion.jump;
        double baseMotionY = 0.42F;
        
        switch (antiCheatType) {
        case NCP:
        	baseMotionY = 0.4F;
        	break;
        }
        if (mc.thePlayer.isPotionActive(potion)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(potion).getAmplifier();
            baseMotionY += (amplifier + 1) * 0.1F;
        }

        return baseMotionY;
	}
	
	public Vector2d getMotion(double moveSpeed) {
        final MovementInput movementInput = mc.thePlayer.movementInput;

        double moveForward = movementInput.moveForward;
        double moveStrafe = movementInput.moveStrafe;

        double rotationYaw = mc.thePlayer.rotationYaw;
        if (moveForward != 0.0D || moveStrafe != 0.0D) {
            if (moveStrafe > 0) {
                moveStrafe = 1;
            } else if (moveStrafe < 0) {
                moveStrafe = -1;
            }
            if (moveForward != 0.0D) {
                if (moveStrafe > 0.0D) {
                    rotationYaw += moveForward > 0.0D ? -45 : 45;
                } else if (moveStrafe < 0.0D) {
                    rotationYaw += moveForward > 0.0D ? 45 : -45;
                }
                moveStrafe = 0.0D;
                if (moveForward > 0.0D) {
                    moveForward = 1.0D;
                } else if (moveForward < 0.0D) {
                    moveForward = -1.0D;
                }
            }
            rotationYaw *= 0.995;
            final double cos = Math.cos(Math.toRadians(rotationYaw + 90.0F));
            final double sin = Math.sin(Math.toRadians(rotationYaw + 90.0F));
            return new Vector2d(moveForward * moveSpeed * cos
                    + moveStrafe * moveSpeed * sin, moveForward * moveSpeed * sin
                    - moveStrafe * moveSpeed * cos);
        }
        return new Vector2d(0, 0);
    }

	public boolean isMoving2() {
		return mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0;
	}
	
	public int depthStriderLevel() {
        return EnchantmentHelper.getDepthStriderModifier(mc.thePlayer);
    } 

	public boolean canSprint(final boolean legit) {
        return (legit ? mc.thePlayer.moveForward >= 0.8F
                && !mc.thePlayer.isCollidedHorizontally
                && (mc.thePlayer.getFoodStats().getFoodLevel() > 6 || mc.thePlayer.capabilities.allowFlying)
                && !mc.thePlayer.isPotionActive(Potion.blindness)
                && !mc.thePlayer.isUsingItem()
                && !mc.thePlayer.isSneaking()
                : enoughMovementForSprinting());
    }
	
	public boolean enoughMovementForSprinting() {
        return Math.abs(mc.thePlayer.moveForward) >= 0.8F || Math.abs(mc.thePlayer.moveStrafing) >= 0.8F;
    }
	
	public double getAllowedHorizontalDistance() {
        double horizontalDistance;
        boolean useBaseModifiers = false;

        if (mc.thePlayer.isInWeb) {
            horizontalDistance = MOD_WEB * WALK_SPEED;
        } else if (PlayerUtils.isOnLiquid()) {
        	
            horizontalDistance = MOD_SWIM * WALK_SPEED;

            final int depthStriderLevel = depthStriderLevel();
            if (depthStriderLevel > 0) {
                horizontalDistance *= MOD_DEPTH_STRIDER[depthStriderLevel];
                useBaseModifiers = true;
            }

        } else if (mc.thePlayer.isSneaking()) {
            horizontalDistance = MOD_SNEAK * WALK_SPEED;
        } else {
            horizontalDistance = WALK_SPEED;
            useBaseModifiers = true;
        }

        if (useBaseModifiers) {
            if (canSprint(false)) {
                horizontalDistance *= MOD_SPRINTING;
            }
            if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).duration > 0) {
                horizontalDistance *= 1 + (0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1));
            }

            if (mc.thePlayer.isPotionActive(Potion.moveSlowdown)) {
                horizontalDistance = 0.29;
            }
        }

        final Block below = PlayerUtils.getBlockRelativeToPlayer(0, -1, 0);
        if (below == Blocks.ice || below == Blocks.packed_ice) {
            horizontalDistance *= 1.2;
        }

        return horizontalDistance;
    }

	public double jumpMotion() {
        return jumpBoostMotion(JUMP_HEIGHT);
    }
	
	public double jumpBoostMotion(final double motionY) {
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            return motionY + (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
        }
        return motionY;
    }

	public static boolean isBlockUnder() {
        for (int offset = 0; offset < mc.thePlayer.posY + mc.thePlayer.getEyeHeight(); offset += 2) {
            final AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox().offset(0, -offset, 0);

            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, boundingBox).isEmpty())
                return true;
        }
        return false;
    }
}
