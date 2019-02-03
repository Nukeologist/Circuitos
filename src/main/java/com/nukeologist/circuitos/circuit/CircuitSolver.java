package com.nukeologist.circuitos.circuit;

import com.mathworks.Jama.Matrix;
import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.block.tileblock.BasicGenerator.BlockBasicGenerator;
import com.nukeologist.circuitos.block.tileblock.BasicGenerator.TileEntityBasicGenerator;
import com.nukeologist.circuitos.block.tileblock.BasicResistor.TileEntityBasicResistor;
import com.nukeologist.circuitos.block.tileblock.BasicWire.TileEntityBasicWire;
import com.nukeologist.circuitos.init.ModBlocks;
import com.nukeologist.circuitos.utility.LogHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/** Every Generator that is master shall create an object of this.
 *  This will solve the circuit by applying Linear Algebra:
 *  A * X = Z;
 *  where:
 *  A -> nodes and elements matrix
 *  X -> node voltages and the currents
 *  Z -> known quantities
 */
public class CircuitSolver {

    private int nodes, generators, index;
    private double[][] A, X, Z;
    private TileEntityBasicGenerator master;
    private Stack<BaseTileEntity> tempStack;
    private Stack<TileEntityBasicWire> tempWireStack;
    private CircuitGraph graph;
    private Map<TileEntityBasicWire, Integer> wireNodeIndexMap;
    private Matrix aMatrix, xMatrix, zMatrix;

    public CircuitSolver(TileEntityBasicGenerator master) {
        this.master = master;
        this.graph = master.getGraph();
        this.index = 0;
        this.tempStack = new Stack<>();
        this.tempWireStack = new Stack<>();
        this.wireNodeIndexMap = new HashMap<>();
        this.makeGraph();
        this.nodes = getNodeNumbers();
        this.generators = getGeneratorNumbers(master);

        A = new double[nodes + generators - 1][nodes + generators - 1];
        X = new double[nodes + generators - 1][1];
        Z = new double[nodes + generators - 1][1];
    }

    public void solveCircuit() {
        for (CircuitNode node : graph.getNodes()) {
            LogHelper.logInfo("Node has index: " + node.getIndex());
            LogHelper.logInfo("and " + node.getConnections().size() + " edges");
        }
        double result = 0;

        //Making A matrix first, Conductance = 1/Resistance
        for (int i = 0; i < nodes - 1; i++) {

            for (CircuitEdge edge : graph.getNodeByIndex(i + 1).getConnections()) {

                if (edge.getConnector() instanceof IResistor) {
                    IResistor resistor = (IResistor) edge.getConnector();
                    result += (1 / (double)resistor.getResistance());
                }

            }
            //Sets diagonals to Sum of conductances for each node
            A[i][i] = result;
            result = 0;
        }

        for (CircuitNode node : graph.getNodes()) {

            if (node.getIndex() == 0) continue;

            for (CircuitEdge edge : node.getConnections()) {

                int index1 = edge.getA().getIndex();
                int index2 = edge.getB().getIndex();
                if (index1 == 0 || index2 == 0) continue;
                if (edge.getConnector() instanceof IResistor) {
                    IResistor resistor = (IResistor) edge.getConnector();
                    double conductance = -1 / (double)resistor.getResistance();

                    A[index1 - 1][index2 - 1] = conductance;
                    A[index2 - 1][index1 - 1] = conductance;
                }
            }
        }
        //Now the voltages, we also need to check if terminal is pos/neg at edge connection

        for (CircuitNode node : graph.getNodes()) {

            for (CircuitEdge edge : node.getConnections()) {

                if (edge.getConnector() instanceof TileEntityBasicGenerator) {
                    TileEntityBasicGenerator generator = (TileEntityBasicGenerator) edge.getConnector();

                    IBlockState genBlock = master.getWorld().getBlockState(generator.getPos());
                    EnumFacing blockFacing = genBlock.getValue(BlockBasicGenerator.FACING);

                    TileEntity te1 = master.getWorld().getTileEntity(master.getPos().offset(EnumFacing.EAST));
                    TileEntity te2 = master.getWorld().getTileEntity(master.getPos().offset(EnumFacing.WEST));
                    TileEntity te3 = master.getWorld().getTileEntity(master.getPos().offset(EnumFacing.NORTH));
                    TileEntity te4 = master.getWorld().getTileEntity(master.getPos().offset(EnumFacing.SOUTH));
                    int index1, index2, genIndex;
                    genIndex = generator.getGenIndex();

                   if (blockFacing == EnumFacing.NORTH) {
                        if ( te1 instanceof TileEntityBasicWire && te2 instanceof TileEntityBasicWire ) {

                            if (master.isPartOfCircuit((TileEntityBasicWire)te1, generator, "east")) {
                                index1 = ((TileEntityBasicWire) te1).getNodeIndex(); //Positive Terminal
                                index2 = ((TileEntityBasicWire) te2).getNodeIndex(); //Negative Terminal


                                if (index1 != 0) {
                                    A[index1 - 1][nodes + genIndex - 1] = 1;
                                    A[nodes + genIndex - 1][index1 - 1] = 1;
                                }
                                if (index2 != 0) {
                                    A[index2 - 1][nodes + genIndex - 1] = -1;
                                    A[nodes + genIndex - 1][index2 - 1] = -1;
                                }
                            }

                        }
                   }else if (blockFacing == EnumFacing.SOUTH) {
                       if ( te2 instanceof TileEntityBasicWire && te1 instanceof TileEntityBasicWire ) {

                           if (master.isPartOfCircuit((TileEntityBasicWire)te2, generator, "west")) {
                               index1 = ((TileEntityBasicWire) te2).getNodeIndex(); //Positive Terminal
                               index2 = ((TileEntityBasicWire) te1).getNodeIndex(); //Negative Terminal


                               if (index1 != 0) {
                                   A[index1 - 1][nodes + genIndex - 1] = 1;
                                   A[nodes + genIndex - 1][index1 - 1] = 1;
                               }
                               if (index2 != 0) {
                                   A[index2 - 1][nodes + genIndex - 1] = -1;
                                   A[nodes + genIndex - 1][index2 - 1] = -1;
                               }
                           }
                       }


                   }else if (blockFacing == EnumFacing.EAST) {
                       if ( te4 instanceof TileEntityBasicWire && te3 instanceof TileEntityBasicWire ) {

                           if (master.isPartOfCircuit((TileEntityBasicWire)te4, generator, "south")) {
                               index1 = ((TileEntityBasicWire) te4).getNodeIndex(); //Positive Terminal
                               index2 = ((TileEntityBasicWire) te3).getNodeIndex(); //Negative Terminal


                               if (index1 != 0) {
                                   A[index1 - 1][nodes + genIndex - 1] = 1;
                                   A[nodes + genIndex - 1][index1 - 1] = 1;
                               }
                               if (index2 != 0) {
                                   A[index2 - 1][nodes + genIndex - 1] = -1;
                                   A[nodes + genIndex - 1][index2 - 1] = -1;
                               }
                           }
                       }

                   }else if (blockFacing == EnumFacing.WEST) {
                       if ( te3 instanceof TileEntityBasicWire && te4 instanceof TileEntityBasicWire ) {

                           if (master.isPartOfCircuit((TileEntityBasicWire)te3, generator, "north")) {
                               index1 = ((TileEntityBasicWire) te3).getNodeIndex(); //Positive Terminal
                               index2 = ((TileEntityBasicWire) te4).getNodeIndex(); //Negative Terminal


                               if (index1 != 0) {
                                   A[index1 - 1][nodes + genIndex - 1] = 1;
                                   A[nodes + genIndex - 1][index1 - 1] = 1;
                               }
                               if (index2 != 0) {
                                   A[index2 - 1][nodes + genIndex - 1] = -1;
                                   A[nodes + genIndex - 1][index2 - 1] = -1;
                               }
                           }
                       }

                   }

                }
            }
        }
        //A matrix now done, rest is 0s because of no dependant current/voltage sources.

        //The X matrix is the answer, so we need to make the Z matrix.

        for (BaseTileEntity te : master.machineList) {
            if (te instanceof TileEntityBasicGenerator) {
                TileEntityBasicGenerator gen = (TileEntityBasicGenerator) te;
                int index = gen.getGenIndex();

                Z[nodes - 1 + index][0] = gen.getFem();
                //First part of the vector/matrix is 0s because of lack of current sources
            }
        }//Making the Matrix objects

        aMatrix = new Matrix(A);
        zMatrix = new Matrix(Z);

        //This solves everything
        xMatrix = aMatrix.inverse().times(zMatrix);

        LogHelper.logInfo("matrix solved, @" + master.getPos().toString());
        //Now we assign current to each block/tile, and also a Voltage for each node.
        X = xMatrix.getArray();
        LogHelper.logInfo("solved: " + Arrays.toString(X));

        for (int i = 0; i < X.length; i++ ) {
            LogHelper.logInfo("Pos: " + i + " num: " + X[i][0]);
        }

        graph.getNodeByIndex(0).setVoltage(0);
        for (int i = 0; i < nodes - 1; i++) {
            graph.getNodeByIndex(i + 1).setVoltage(Math.abs(X[i][0]));
            //node 0 already has a default voltage of 0 (ground)
        }

        setGeneratorCurrents();
        setWireCurrents();

    }

    private int getNodeNumbers(){
        return this.graph.getNodes().size();
    }

    private int getGeneratorNumbers(TileEntityBasicGenerator tile) {
        int i = 0;
        for(BaseTileEntity te : tile.machineList){
            if(te instanceof TileEntityBasicGenerator) {
                ((TileEntityBasicGenerator) te).setGenIndex(i);
                i++;
            }
        }
        return(i);
    }

    private void setWireCurrents() {
        //for resistors its easy:
        for (CircuitNode node : graph.getNodes()) {
            for (CircuitEdge edge: node.getConnections()) {
                if (edge.getConnector() instanceof  TileEntityBasicResistor) {
                    TileEntityBasicResistor resistor = (TileEntityBasicResistor) edge.getConnector();
                    double voltage1, voltage2;
                    voltage1 = edge.getA().getVoltage();
                    voltage2 = edge.getB().getVoltage();
                    resistor.setCurrent(Math.abs((voltage1 - voltage2) / resistor.getResistance()));
                }
            }
        }
    }

    private void setGeneratorCurrents() {
        /*
        for (BaseTileEntity te : master.machineList) {
            if (te instanceof TileEntityBasicGenerator) {
                TileEntityBasicGenerator generator = (TileEntityBasicGenerator) te;
                generator.setCurrent(X[nodes - 1 + generator.getGenIndex()][0]);
            }
        }
        */
        master.machineList.stream()
                .filter(machine -> machine instanceof TileEntityBasicGenerator)
                .forEach(generator -> ((TileEntityBasicGenerator) generator).setCurrent(Math.abs(X[nodes - 1 + ((TileEntityBasicGenerator)generator).getGenIndex()][0])));
    }

    @SuppressWarnings("Duplicates")
    private void makeGraph(){
        //Assigns each wire piece to a node.
        for(EnumFacing facing : EnumFacing.VALUES) {
            BlockPos newPos = master.getPos().offset(facing);
            TileEntity te = master.getWorld().getTileEntity(newPos);
            if (te instanceof TileEntityBasicWire) {
                if (master.isPartOfCircuit((TileEntityBasicWire)te, master, facing.getName())) {
                    checkAssignWires((TileEntityBasicWire) te);
                }
            }
        }

        while (!tempStack.isEmpty()) {
            //Same thing, for rest of machines.
            BaseTileEntity tile = tempStack.pop();
            for(EnumFacing facing : EnumFacing.VALUES) {
                BlockPos newPos = tile.getPos().offset(facing);
                TileEntity te = tile.getWorld().getTileEntity(newPos);
                if (te instanceof TileEntityBasicWire) {
                    if (((TileEntityBasicWire) te).isPartOfCircuit((TileEntityBasicWire)te, tile, facing.getName())) {
                        checkAssignWires((TileEntityBasicWire) te);
                    }
                }
            }
        }

        //Finished assigning wires to nodes, now we actually make edges.
        int[] tempArray = new int[2];
        tempArray[0] = -1;
        tempArray[1] = -1;
        /*First the master */
        for(EnumFacing facing : EnumFacing.VALUES) {
            BlockPos newPos = master.getPos().offset(facing);
            TileEntity tile = master.getWorld().getTileEntity(newPos);

            if (tile instanceof TileEntityBasicWire) {
                TileEntityBasicWire wire = (TileEntityBasicWire) tile;
                if (wire.isPartOfCircuit(wire, master, facing.getName())) {
                    if (tempArray[0] == -1) {
                        tempArray[0] = wireNodeIndexMap.get(wire);
                    }else if (tempArray[1] == -1) {
                        tempArray[1] = wireNodeIndexMap.get(wire);
                    }
                }
            }
        }

        CircuitEdge genEdge = new CircuitEdge(graph.getNodeByIndex(tempArray[0]), graph.getNodeByIndex(tempArray[1]), master);

        graph.getNodeByIndex(tempArray[0]).addConnection(genEdge);
        graph.getNodeByIndex(tempArray[1]).addConnection(genEdge);

        /*For the rest of the machines */
        for (BaseTileEntity bTile : master.machineList) {
            tempArray[0] = -1;
            tempArray[1] = -1;
            for(EnumFacing facing : EnumFacing.VALUES) {
                BlockPos newPos = bTile.getPos().offset(facing);
                TileEntity tile = bTile.getWorld().getTileEntity(newPos);

                if (tile instanceof TileEntityBasicWire) {
                    TileEntityBasicWire wire = (TileEntityBasicWire) tile;
                    if (wire.isPartOfCircuit(wire, bTile, facing.getName())) {
                        if (tempArray[0] == -1) {
                            tempArray[0] = wireNodeIndexMap.get(wire);
                        }else if (tempArray[1] == -1) {
                            tempArray[1] = wireNodeIndexMap.get(wire);
                        }
                    }
                }
            }

            CircuitEdge edge = new CircuitEdge(graph.getNodeByIndex(tempArray[0]), graph.getNodeByIndex(tempArray[1]), bTile);

            graph.getNodeByIndex(tempArray[0]).addConnection(edge);
            graph.getNodeByIndex(tempArray[1]).addConnection(edge);
        }
        //Graph is now done!
    }
    /*Does what the name implies, also creates nodes. It traverses a multiblock. */
    private void checkAssignWires(TileEntityBasicWire initialWire) {

        if(wireNodeIndexMap.containsKey(initialWire)) return;
        int nodeIndex = newIndex(); //Assures a new index
        World world = master.getWorld();
        BlockPos tePos = initialWire.getPos();

        //Hashmap between node index and wire. Helps later. Also new Node!
        master.getGraph().addNode(new CircuitNode(nodeIndex));
        wireNodeIndexMap.put(initialWire, nodeIndex);
        initialWire.setNodeIndex(nodeIndex);
        //Make it not throw a Empty Stack Exception
        tempWireStack.push(initialWire);

        do {

            TileEntityBasicWire wire = tempWireStack.pop();
            tePos = wire.getPos();

            for(EnumFacing facing : EnumFacing.VALUES) {
                BlockPos newPos = tePos.offset(facing);
                TileEntity teToAnalyze = world.getTileEntity(newPos);

                //Must be wire and not already been assigned
                if(teToAnalyze instanceof TileEntityBasicWire && !wireNodeIndexMap.containsKey(teToAnalyze)) {

                    TileEntityBasicWire wireNew = (TileEntityBasicWire) teToAnalyze;
                    //push and check

                    if(wire.isPartOfCircuit(wire, wireNew, facing.getName())) {
                        wireNodeIndexMap.put(wireNew, nodeIndex);
                        wireNew.setNodeIndex(nodeIndex);
                        tempWireStack.push(wireNew);
                    }

                //There is a case where it might be a resistor, or something...
                } else if ( teToAnalyze instanceof BaseTileEntity && !(teToAnalyze instanceof TileEntityBasicWire)) {
                    BaseTileEntity intersector = (BaseTileEntity) teToAnalyze;

                    if(wire.isPartOfCircuit(wire, intersector, facing.getName())) {
                        tempStack.push(intersector);
                    }

                }
            }

        } while (!tempWireStack.isEmpty());
    }


    private int newIndex() {
        return this.index++;
    }
}
