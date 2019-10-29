package com.zhrb.testDemo.thread;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.concurrent.Worker;

import java.util.concurrent.CyclicBarrier;

/**
 * @ClassName CellularAutomata
 * @Description
 * @Author Administrator
 * @Date 2019/10/29 11:15
 * @Version
 */
public class CellularAutomata {
    private final Board mainBoard;

    private final CyclicBarrier barrier;

    private final Worker[] workers;

    public CellularAutomata(Board mainBoard, CyclicBarrier barrier, Worker[] workers) {
        this.mainBoard = mainBoard;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                mainBoard.commitNewValalues();
            }
        });
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++){
            //workers[i] = new Worker();
        }

    }

    private class Worker implements Runnable{
        private final Board board;

        public Worker(Board board){this.board = board;}

        @Override
        public void run() {
            while (board instanceof Board){

            }
        }
    }
    private class Board{
        public void commitNewValalues(){

        }

    }
}
