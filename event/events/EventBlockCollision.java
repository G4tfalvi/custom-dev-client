package KexTools.event.events;

import KexTools.event.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;

public class EventBlockCollision extends Event{

	private AxisAlignedBB axisAlignedBB;
    private final Block block;
    private int x, y, z;

    public EventBlockCollision(AxisAlignedBB axisAlignedBB, Block block, int x, int y, int z) {
        this.axisAlignedBB = axisAlignedBB;
        this.block = block;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AxisAlignedBB getAxisAlignedBB() {
        return axisAlignedBB;
    }

    public void setAxisAlignedBB(AxisAlignedBB axisAlignedBB) {
        this.axisAlignedBB = axisAlignedBB;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Block getBlock() {
        return block;
    }
	
}
