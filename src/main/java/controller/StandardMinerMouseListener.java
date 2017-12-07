package controller;

import view.component.MinerMouseListener;

public class StandardMinerMouseListener implements MinerMouseListener {
    final private Logic logic;

    public StandardMinerMouseListener(Logic logic) {
        this.logic = logic;
    }

    @Override
    public void action(int x, int y, boolean left, boolean right) {
        if (left && right) {
            logic.openAround(x, y);
        } else if (left) {
            logic.open(x, y);
        } else if (right) {
            logic.setFlag(x, y);
        }
    }
}
