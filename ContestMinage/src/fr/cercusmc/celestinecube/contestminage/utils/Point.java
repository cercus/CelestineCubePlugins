package fr.cercusmc.celestinecube.contestminage.utils;

import org.bukkit.entity.Player;

public class Point {
    private Player p;
    private int value;

    public Point(Player p, int value) {
        this.p = p;
        this.value = value;
    }

    public Point(Player p) {
        this(p, 0);
    }

    public int getValue() {
        return value;
    }

    public Player getP() {
        return p;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(!(obj instanceof Point))
            return false;
        Point o = (Point) obj;
        return o.getP().getName().equals(this.p.getName());
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.p.getName() + " / " + this.value;
    }

}
