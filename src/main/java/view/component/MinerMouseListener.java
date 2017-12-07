package view.component;

public interface MinerMouseListener {
    /**
     * Обработчик событий от мыши
     *
     * @param x     координата x ячейки (не пикселя!)
     * @param y     координата y ячейки (не пикселя!)
     * @param left  признак нажатия левой кнопки мыши
     * @param right признак нажатия правой кнопки мыши
     */
    void action(int x, int y, boolean left, boolean right);
}
