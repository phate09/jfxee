package com.zenjava.playground.browser2.activity;

import javafx.collections.ObservableList;
import javafx.concurrent.Worker;

public interface HasWorkers
{
    ObservableList<Worker> getWorkers();
}
