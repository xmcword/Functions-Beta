package org.functions.Bukkit.API;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.Main.functions.Utils;

import java.util.ArrayList;
import java.util.List;

public class WorldBlock {
    int x = 0;
    int y = 0;
    int z = 0;
    Location loc = null;
    Block block = null;
    public WorldBlock(WorldBlock worldBlock) {
        this(worldBlock.getBlock().getLocation());
    }
    public WorldBlock(Location location) {
        this(location.getBlock());
        loc = location;
    }
    public WorldBlock(int x, int y, int z, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        block = world.getBlockAt(x,y,z);
        loc = new Location(world,x,y,z);
    }
    public WorldBlock(Block block) {
        this(block.getX(),block.getY(),block.getZ(),block.getWorld());
    }
    int status = 0;
    public int destroy() {
        block.breakNaturally();
        int t = 0;
        status++;
        t = status;
        status = 0;
        return t;
    }
    public int QuietDestroy() {
        return set(Material.AIR);
    }
    public int set(Material material) {
        block.setType(material,true);
        int t = 0;
        status++;
        t = status;
        status = 0;
        return t;
    }

    public Block getBlock() {
        return loc.getWorld().getBlockAt(x,y,z);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Location getLoc() {
        return loc;
    }

    public boolean isBlock() {
        if (getBlock().getType() == Material.AIR || getBlock().isLiquid() || getBlock().getType() == Material.FIRE) {
            return false;
        }
        return getBlock().getType().isBlock();
    }

    public void addX(int x) {
        this.x = this.x + x;
    }

    public void addY(int y) {
        this.y = this.y + y;
    }

    public void addZ(int z) {
        this.z = this.z + z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    static int min_ground = 0;
    static {
        if (Utils.Version.isCurrentLower(Utils.Version.v1_18_R1)) min_ground = -64;
    }

    public WorldBlock down() {
        return new WorldBlock(x,y - 1, z, loc.getWorld());
    }

    public WorldBlock up() {
        return new WorldBlock(x,y + 1, z, loc.getWorld());
    }

    public boolean isGround() {
        Material material;
        WorldBlock worldBlock = new WorldBlock(x,loc.getWorld().getMaxHeight(),z,loc.getWorld());
        do {
            if (worldBlock.getY() <= min_ground) {
                return false;
            }

            worldBlock = worldBlock.down();
            material = worldBlock.getBlock().getType();
        } while(material == Material.AIR);
        return worldBlock.isBlock();
    }

    public int onGroundY() {
        Material material;
        WorldBlock worldBlock = new WorldBlock(x,loc.getWorld().getMaxHeight(),z,loc.getWorld());
        for (int i = loc.getWorld().getMaxHeight(); i > min_ground; i--) {
            if (worldBlock.getY() <= min_ground) {
                return min_ground;
            }

            worldBlock = worldBlock.down();
            material = worldBlock.getBlock().getType();
            if (material == Material.AIR) {
                continue;
            }
            return worldBlock.isBlock() ? i : min_ground;
        }
        return min_ground;
    }

}
