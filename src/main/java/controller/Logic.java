package controller;

/**
 * Интерфейс получения внешних событий классом логики игры
 */
public interface Logic {

    /**
     * Открыть закрытую ячейку с возможностью взорваться на мине.
     *
     * @param x координата ячейки
     * @param y координата ячейки
     */
    void open(int x, int y);


    /**
     * Открыть окружающие закрытые ячейки с возможностью взорваться на мине.
     * Данное действие возможно если количество установленных флажков равно
     * количеству мин вокруг ячейки.
     *
     * @param x координата ячейки
     * @param y координата ячейки
     */
    void openAround(int x, int y);


    /**
     * Установить флажок на закрытой ячейке
     *
     * @param x координата ячейки
     * @param y координата ячейки
     */
    void setFlag(int x, int y);

    /**
     * Начать новую игру
     */
    void newGame();
}
