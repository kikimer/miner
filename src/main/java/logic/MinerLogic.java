package logic;

import common.*;
import controller.Logic;

import java.awt.*;
import java.util.*;

public class MinerLogic implements Logic {
    /**
     * Наполненность поля минами. В процентах.
     */
    private static final int DEFAULT_MINE_FULLING = 20;
    /**
     * Состояние игры.
     * Истина : запущена
     * Ложь : завершена. (Класс не реагирует на внешние события)
     */
    private boolean gameStarted = false;
    final private GameStatusModel gameStatusModel;
    final private CellModel cellModel;

    public MinerLogic(CellModel cellModel, GameStatusModel gameStatusModel) {
        this.cellModel = cellModel;
        this.gameStatusModel = gameStatusModel;
        this.gameStatusModel.setGameStatus(GameStatus.REDY);
    }

    protected CellType getCellType(Cell cell) {
        if (cell.isOpen()) {
            if (cell.isMine()) return CellType.OpenMine;
            if (cell.getNumMineAround() == 0) return CellType.OpenFree;
            if (cell.getNumMineAround() == 1) return CellType.Open1;
            if (cell.getNumMineAround() == 2) return CellType.Open2;
            if (cell.getNumMineAround() == 3) return CellType.Open3;
            if (cell.getNumMineAround() == 4) return CellType.Open4;
            if (cell.getNumMineAround() == 5) return CellType.Open5;
            if (cell.getNumMineAround() == 6) return CellType.Open6;
            if (cell.getNumMineAround() == 7) return CellType.Open7;
            if (cell.getNumMineAround() == 8) return CellType.Open8;
            return CellType.Error;
        } else if (gameStarted) {
            if (cell.isFlag()) return CellType.CloseFlag;
            return CellType.Close;
        } else {
            if (cell.isBoomed()) return CellType.BoomedMine;
            if (cell.isFlag()) {
                if (cell.isMine()) return CellType.CloseFlagMine;
                return CellType.CloseFlagFree;
            }
            if (cell.isMine()) return CellType.CloseMine;
            return CellType.Close;
        }
    }

    protected void openCellPoint(Point point) {
        Cell cell = cellModel.getCell(point);
        if (!cell.isOpen()) {
            if (cell.isMine()) {
                cell.setBoomed(true);
                gameStarted = false;
                gameStatusModel.setGameStatus(GameStatus.LOSE);
                cellModel.stream()
                        .filter(c -> c.isMine() || c.isFlag())
                        .forEach(c -> c.setCellType(getCellType(c)));
            } else {
                cell.setOpen(true);
                cell.setFlag(false);
                if (cell.getNumMineAround() == 0) {
                    cellModel.getAroundCells(point).forEach(this::openCellPoint);
                }
            }
            cell.setCellType(getCellType(cell));
        }
    }

    protected void checkWin() {
        boolean isWin = cellModel.stream()
                .filter(c -> !c.isOpen())
                .allMatch(c -> c.isMine() && c.isFlag());
        if (isWin) {
            gameStatusModel.setGameStatus(GameStatus.WIN);
            gameStarted = false;
        }
    }

    @Override
    public void newGame() {
        gameStarted = true;
        cellModel.stream().forEach(Cell::reset);
        int mineCount = DEFAULT_MINE_FULLING * cellModel.getHeight() * cellModel.getWidth() / 100;
        Random random = new Random();
        while (mineCount >= 0) {
            int x = random.nextInt(cellModel.getWidth());
            int y = random.nextInt(cellModel.getHeight());
            Cell cell = cellModel.getCell(x, y);
            if (!cell.isMine()) {
                cell.setMine(true);
                mineCount--;
            }
        }

        for (int x = 0; x < cellModel.getWidth(); x++) {
            for (int y = 0; y < cellModel.getHeight(); y++) {
                Cell cell = cellModel.getCell(x, y);
                if (!cell.isMine()) {
                    int numMines = (int) cellModel.getAroundCells(x, y)
                            .stream()
                            .map(cellModel::getCell)
                            .filter(Cell::isMine)
                            .count();
                    cell.setNumMineAround(numMines);
                }
                cell.setCellType(getCellType(cell));
            }
        }
        cellModel.sendNotify();
        gameStatusModel.setGameStatus(GameStatus.STARTED);
    }

    @Override
    public void open(int x, int y) {
        if (!gameStarted) return;
        openCellPoint(new Point(x, y));
        checkWin();
        cellModel.sendNotify();
    }

    @Override
    public void openAround(int x, int y) {
        if (!gameStarted) return;
        Cell cell = cellModel.getCell(x, y);
        if (!cell.isOpen()) return;
        int numMines = (int) cellModel.getAroundCells(x, y)
                .stream()
                .map(cellModel::getCell)
                .filter(Cell::isFlag)
                .count();
        if (numMines == cell.getNumMineAround()) {
            cellModel.getAroundCells(x, y)
                    .stream()
                    .filter(p -> !cellModel.getCell(p).isFlag())
                    .forEach(this::openCellPoint);
            checkWin();
            cellModel.sendNotify();
        }
    }

    @Override
    public void setFlag(int x, int y) {
        if (!gameStarted) return;
        Cell cell = cellModel.getCell(x, y);
        if (!cell.isOpen()) {
            cell.setFlag(!cell.isFlag());
            cell.setCellType(getCellType(cell));
            checkWin();
            cellModel.sendNotify();
        }
    }


}
