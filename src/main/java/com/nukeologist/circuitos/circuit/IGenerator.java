package com.nukeologist.circuitos.circuit;

public interface IGenerator extends IResistor {



    int getFem();


    void setFem(int fem);


    @Override
    int getResistance();

    @Override
    double getDissipatedEnergy();

    @Override
    void setResistance(int resistance);

    @Override
    double getCurrent();

    @Override
    void setCurrent(double current);
}
