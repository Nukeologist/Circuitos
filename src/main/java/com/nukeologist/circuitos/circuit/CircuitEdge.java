package com.nukeologist.circuitos.circuit;

import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;


public class CircuitEdge {
    public CircuitNode start;
    public CircuitNode end;
    public BaseTileEntity endTile;
    public BaseTileEntity startTile;
    public IResistor edgeStart;
    public IResistor edgeEnd;

    public CircuitEdge(BaseTileEntity startTile, BaseTileEntity endTile){
        this.startTile = startTile;
        this.endTile = endTile;
    }

}
