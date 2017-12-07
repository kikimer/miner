package model;

import common.GameStatus;

/**
 * Наблюдатель за изменением модели
 */
public interface ChangeGameStatusListener {
    void statusChange(GameStatus gameStatus);
}
