package com.example.minesweeper;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MineSweeperGame {
    private MineField mineField;
    private boolean clearMode;
    private boolean isGameOver;
    private boolean timeExpired;

    public MineSweeperGame(int size, int numberOfBombs) {

        this.mineField = new MineField(size);
        this.clearMode = true;
        this.isGameOver = false;
        this.timeExpired = false;

        mineField.generateField(numberOfBombs);
    }

    public void cellClick(Cell cell){

        if(!isGameOver && !timeExpired) {
            //if (clearMode) {
                clear(cell);
            //}
        }
    }

    public void clear(Cell cell){
        int index = getMineField().getCells().indexOf(cell);
        getMineField().getCells().get(index).setRevealed(true);

        if(cell.getValue() == Cell.BLANK){
            List<Cell> cellsToClear = new ArrayList<>();
            List<Cell> adjacentCellsToCheck = new ArrayList<>();

            adjacentCellsToCheck.add(cell);

            while(adjacentCellsToCheck.size() > 0){
                Cell c = adjacentCellsToCheck.get(0);

                int i = getMineField().getCells().indexOf(c);
                int[] pos = getMineField().toXY(i);
                for(Cell adjacent : getMineField().adjacentCells(pos[0], pos[1])){
                    if(adjacent.getValue() == Cell.BLANK){
                        if(!cellsToClear.contains(adjacent))
                            adjacentCellsToCheck.add(adjacent);
                    }
                    else {
                        if(!cellsToClear.contains(adjacent))
                            cellsToClear.add(adjacent);
                    }
                }
                adjacentCellsToCheck.remove(c);
                cellsToClear.add(c);
            }

            for(Cell c : cellsToClear)
                c.setRevealed(true);

        }
        else if(cell.getValue() == Cell.BOMB){
            isGameOver = true;

        }
    }


    public boolean isWon(){
        int unrevealedCount = 0;

        for(Cell cell : getMineField().getCells()){
            if(cell.getValue() != Cell.BOMB && cell.getValue() != Cell.BLANK && !cell.isRevealed())
                ++unrevealedCount;
        }

        return unrevealedCount == 0;
    }


    public void timeOver(){
        timeExpired = true;
    }


    public MineField getMineField() {
        return mineField;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}
