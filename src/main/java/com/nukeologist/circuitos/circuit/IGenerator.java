package com.nukeologist.circuitos.circuit;

public interface IGenerator extends IResistor {



    int getFem();


    void setFem(int fem);


    @Override
    int getResistance();

    @Override
    int getDissipatedEnergy();

    @Override
    void setResistance(int resistance);


}
