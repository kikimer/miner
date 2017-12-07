package view.component;

import model.ChangeModelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс предназначен для отображения игрового поля в swing фрейме
 */
public class SwingPlayingPlace extends JComponent implements ChangeModelListener {
    /**
     * Размер стороны ячейки
     */
    final protected static int DEFAULT_CELL_SIZE = 30;

    /**
     * Размер границы между ячейками
     */
    final protected static int DEFAULT_CELL_BORDER_SIZE = 1;

    /**
     * Размер границы поля
     */
    final protected static int DEFAULT_BORDER_SIZE = 10;

    /**
     * Подписчики на события от мыши.
     */
    final private List<MinerMouseListener> minerMouseListeners = new LinkedList<>();

    /**
     * Связь с объектом модели.
     */
    private CellModelGetter cellModelGetter;

    /**
     * Класс преобразования CellType в Image
     */
    private CellPaintSchema cellPaintSchema;

    /**
     * Преобразование номера позиции ячейки в номер её первого пиксела
     *
     * @param numCell позиция ячейки
     * @return номер первого пикселя
     */
    protected int numCellToPixel(int numCell) {
        return DEFAULT_BORDER_SIZE + numCell * (DEFAULT_CELL_BORDER_SIZE + DEFAULT_CELL_SIZE);
    }

    protected int PixelToNumCell(int pixel) {
        return (pixel - DEFAULT_BORDER_SIZE) / (DEFAULT_CELL_BORDER_SIZE + DEFAULT_CELL_SIZE);
    }

    protected int PixelToNumCellX(int pixel) {
        int numCell = PixelToNumCell(pixel);
        if (numCell >= cellModelGetter.getWidth()) {
            numCell = -1;
        }
        return numCell;
    }

    protected int PixelToNumCellY(int pixel) {
        int numCell = PixelToNumCell(pixel);
        if (numCell >= cellModelGetter.getHeight()) {
            numCell = -1;
        }
        return numCell;
    }

    public SwingPlayingPlace(CellModelGetter cellModelGetter, CellPaintSchema cellPaintSchema) {
        if (cellModelGetter == null) {
            throw new IllegalArgumentException("incorrect model");
        }
        if (cellPaintSchema == null) {
            throw new IllegalArgumentException("incorrect model");
        }
        this.cellModelGetter = cellModelGetter;
        this.cellPaintSchema = cellPaintSchema;
        addMouseListener(new PlaceMouseListener());
    }

    public CellModelGetter getCellModelGetter() {
        return cellModelGetter;
    }

    public void setCellModelGetter(CellModelGetter cellModelGetter) {
        if (cellModelGetter == null) {
            throw new IllegalArgumentException("incorrect model");
        }
        this.cellModelGetter = cellModelGetter;
    }

    public void addListener(MinerMouseListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("incorrect mouse listener");
        }
        minerMouseListeners.add(listener);
    }

    public void removeListener(MinerMouseListener minerMouseListener) {
        if (minerMouseListener == null) {
            throw new IllegalArgumentException("incorrect mouse listener");
        }
        this.minerMouseListeners.remove(minerMouseListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        paintPlace(g);
        paintCells(g);
    }

    @Override
    public Dimension getPreferredSize() {
        int width = cellModelGetter.getWidth() * DEFAULT_CELL_SIZE
                + (cellModelGetter.getWidth() - 1) * DEFAULT_CELL_BORDER_SIZE
                + 2 * DEFAULT_BORDER_SIZE;
        int height = cellModelGetter.getHeight() * DEFAULT_CELL_SIZE
                + (cellModelGetter.getHeight() - 1) * DEFAULT_CELL_BORDER_SIZE
                + 2 * DEFAULT_BORDER_SIZE;
        return new Dimension(width, height);
    }

    protected void paintPlace(Graphics g) {
        int steep = DEFAULT_CELL_SIZE + DEFAULT_CELL_BORDER_SIZE;
        int start = DEFAULT_CELL_SIZE + DEFAULT_BORDER_SIZE;
        int maxX = DEFAULT_BORDER_SIZE + cellModelGetter.getWidth() * steep - 1;
        int maxY = DEFAULT_BORDER_SIZE + cellModelGetter.getHeight() * steep - 1;
        for (int x = start; x < maxX; x += steep) {
            g.drawLine(x, DEFAULT_BORDER_SIZE, x, maxY);
        }

        for (int y = start; y < maxY; y += steep) {
            g.drawLine(DEFAULT_BORDER_SIZE, y, maxX, y);
        }

    }

    protected void paintCells(Graphics g) {
        for (int cellX = 0; cellX < cellModelGetter.getWidth(); cellX++) {
            for (int cellY = 0; cellY < cellModelGetter.getHeight(); cellY++) {
                Image image = cellPaintSchema.getImageForCellType(cellModelGetter.getCellType(cellX, cellY));
                int x = numCellToPixel(cellX);
                int y = numCellToPixel(cellY);
                g.drawImage(image, x, y, DEFAULT_CELL_SIZE, DEFAULT_CELL_SIZE, null);
            }
        }
    }

    @Override
    public void modelChange() {
        EventQueue.invokeLater(this::repaint);
    }

    private class PlaceMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = SwingPlayingPlace.this.PixelToNumCellX(e.getX());
            int y = SwingPlayingPlace.this.PixelToNumCellY(e.getY());
            boolean left = (e.getModifiersEx() & 1024) != 0;
            boolean right = (e.getModifiersEx() & 4096) != 0;
            if (x >= 0 && y >= 0 && (left || right)) {
                SwingPlayingPlace.this.minerMouseListeners.forEach((l) -> {
                    l.action(x, y, left, right);
                });
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
