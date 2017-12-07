package model;

import common.Cell;
import common.CellType;
import logic.CellModel;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Stream;

public class MinerCellModel implements CellModel {
    private final Logger log = Logger.getLogger(getClass());
    final private int WIDTH;
    final private int HEIGHT;
    final private java.util.List<ChangeModelListener> chaneCellsListeners = new LinkedList<>();

    private Cell[] cells;

    public MinerCellModel(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.cells = new Cell[WIDTH * HEIGHT];
        Arrays.setAll(this.cells, i -> new Cell());
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public CellType getCellType(int x, int y) {
        return getCell(x, y).getCellType();
    }

    @Override
    public Stream<Cell> stream() {
        return Arrays.stream(cells);
    }

    public void addChangeCellsListeners(ChangeModelListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("incorrect listener");
        }
        chaneCellsListeners.add(listener);
    }

    public void removeChangeCellListener(ChangeModelListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("incorrect listener");
        }
        this.chaneCellsListeners.remove(listener);
    }

    @Override
    public void sendNotify() {
        chaneCellsListeners.forEach(ChangeModelListener::modelChange);
    }

    @Override
    public Cell getCell(int x, int y) {
        return getCellLocal(x, y);
    }

    @Override
    public Cell getCell(Point point) {
        return getCellLocal(point.x, point.y);
    }

    private Cell getCellLocal(int x, int y) {
        if (!isCorrectCell(x, y)) {
            log.error("выход за границы массива модели! x:" + x + ", y:" + y + ", размер: " + WIDTH + "x" + HEIGHT);
            throw new ArrayIndexOutOfBoundsException();
        }
        int index = y * WIDTH + x;
        return cells[index];
    }

    private boolean isCorrectCell(int x, int y) {
        return x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT;
    }

    @Override
    public Set<Point> getAroundCells(int x, int y) {
        return getAroundCellsLocal(x, y);
    }

    @Override
    public Set<Point> getAroundCells(Point point) {
        return getAroundCellsLocal(point.x, point.y);
    }

    private Set<Point> getAroundCellsLocal(int x, int y) {
        Set<Point> result = new HashSet<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                if (isCorrectCell(x + dx, y + dy)) {
                    result.add(new Point(x + dx, y + dy));
                }
            }
        }
        return result;
    }
}
