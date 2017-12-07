package view.component;

import common.CellType;

import java.awt.Image;

public interface CellPaintSchema {
    Image getImageForCellType(CellType cellType);
}
