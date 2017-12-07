package game;

import model.ChangeGameStatusListener;
import common.GameStatus;

import javax.swing.*;
import java.awt.*;

public class GameOwerNotifer implements ChangeGameStatusListener {
    private JLabel updateLabel;

    public GameOwerNotifer(JLabel updateLabel) {
        this.updateLabel = updateLabel;
    }

    @Override
    public void statusChange(GameStatus gameStatus) {
        EventQueue.invokeLater(()->updateLabel.setText(gameStatus.name()));
    }
}
