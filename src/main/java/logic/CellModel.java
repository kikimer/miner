package logic;

import common.Cell;
import view.component.CellModelGetter;

import java.awt.*;
import java.util.Set;

public interface CellModel extends CellModelGetter {

    void sendNotify();

    Cell getCell(int x, int y);

    Cell getCell(Point point);

    Set<Point> getAroundCells(int x, int y);

    Set<Point> getAroundCells(Point point);
}
