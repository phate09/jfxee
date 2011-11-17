package com.zenjava.playground.cells;

public interface CellFactory<ValueType>
{
    Cell<ValueType> createCell();
}
