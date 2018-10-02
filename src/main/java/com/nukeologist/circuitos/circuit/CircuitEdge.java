package com.nukeologist.circuitos.circuit;

import net.minecraft.tileentity.TileEntity;

public class CircuitEdge {
    public CircuitNode start;
    public CircuitNode end;
    public TileEntity endTile;
    public IResistor edgeStart;
    public IResistor edgeEnd;

}
