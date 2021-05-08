package com.example.minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MineField {
    private List<Cell> cells;
    private int size;

    public MineField(int size) {
        this.size = size;
        cells = new ArrayList<>();

        for(int i = 0 ; i<size * size ; ++i){
            cells.add(new Cell(Cell.BLANK));
        }
    }

    public void generateField(int bombCount){
        int placed = 0;

        while(placed < bombCount){
            int x = new Random().nextInt(size);
            int y = new Random().nextInt(size);

            int index = toIndex(x,y);
            if(cells.get(index).getValue() == Cell.BLANK){
                cells.set(index, new Cell(Cell.BOMB));
                ++placed;
            }
        }

        for(int x = 0 ; x < size ; ++x){
            for(int y = 0 ; y < size ; ++y){
                if (cellAt(x, y).getValue() != Cell.BOMB) {
                    List<Cell> adjacentCells = adjacentCells(x,y);

                    int countBombs = 0;

                    for(Cell cell : adjacentCells){
                        if(cell.getValue() == Cell.BOMB)
                            ++countBombs;
                    }

                    if(countBombs > 0)
                        cells.set(x + (y * size), new Cell(countBombs));
                }
            }
        }
    }

    public List<Cell> adjacentCells(int x, int y){
        List<Cell> adjacents = new ArrayList<>();
        List<Cell> cells = new ArrayList<>();

        cells.add(cellAt(x-1, y-1));
        cells.add(cellAt(x, y-1));
        cells.add(cellAt(x+1, y-1));
        cells.add(cellAt(x-1, y));
        cells.add(cellAt(x+1, y));
        cells.add(cellAt(x-1, y+1));
        cells.add(cellAt(x, y+1));
        cells.add(cellAt(x+1, y+1));

        for(Cell cell : cells)
            if(cell != null)
                adjacents.add(cell);

        return adjacents;
    }

    public void showBombs(){
        for(Cell cell : cells){
            if(cell.getValue() == Cell.BOMB)
                cell.setRevealed(true);
        }
    }

    public Cell cellAt(int x, int y){
        if(x < 0 || x >= size || y < 0 || y >= size)
                return null;

        return cells.get(toIndex(x,y));
    }

    public int toIndex(int x, int y){
        return x + (y*size);
    }

    public int[] toXY(int index){
        int y = index / size;
        int x = index - (y * size);
        return new int[]{x, y};
    }

    public List<Cell> getCells() {
        return cells;
    }
}
