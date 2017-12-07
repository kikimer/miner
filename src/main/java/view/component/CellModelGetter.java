package view.component;

import common.Cell;
import common.CellType;

import java.util.stream.Stream;

public interface CellModelGetter {
    int getWidth();

    int getHeight();

    /**
     * Получить ячейку
     *
     * @param x координата x
     * @param y координата y
     * @return ячейка
     */
    CellType getCellType(int x, int y);

    /** Поток всех ячеек
     * @return
     */
    Stream<Cell> stream();

}
