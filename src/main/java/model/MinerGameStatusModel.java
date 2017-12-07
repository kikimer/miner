package model;

import logic.GameStatusModel;
import common.GameStatus;

import java.util.LinkedList;

public class MinerGameStatusModel implements GameStatusModel {
    private GameStatus gameStatus;
    final private java.util.List<ChangeGameStatusListener> changeGameStatusListeners = new LinkedList<>();

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        changeGameStatusListeners.forEach(l -> l.statusChange(gameStatus));
    }

    public void addGameStatusListeners(ChangeGameStatusListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("incorrect listener");
        }
        changeGameStatusListeners.add(listener);
    }

    public void removeGameStatusListener(ChangeGameStatusListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("incorrect listener");
        }
        this.changeGameStatusListeners.remove(listener);
    }

}
