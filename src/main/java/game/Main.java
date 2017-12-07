package game;

import controller.StandardMinerMouseListener;
import game.GameOwerNotifer;
import logic.MinerLogic;
import model.MinerCellModel;
import model.MinerGameStatusModel;
import view.component.SwingPlayingPlace;
import view.paintSchema.CellPropertyPaintSchema;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame mainFrame = new JFrame("miner");
            MinerCellModel cellModel = new MinerCellModel(30, 20);
            MinerGameStatusModel gameStatusModel = new MinerGameStatusModel();

            MinerLogic logic = new MinerLogic(cellModel, gameStatusModel);
            SwingPlayingPlace place = new SwingPlayingPlace(cellModel, new CellPropertyPaintSchema());

            place.addListener(new StandardMinerMouseListener(logic));
            cellModel.addChangeCellsListeners(place);

            JButton newGame = new JButton("new game");
            newGame.addActionListener(e -> logic.newGame());
            JLabel status = new JLabel("status");
            gameStatusModel.addGameStatusListeners(new GameOwerNotifer(status));

            mainFrame.getContentPane().add(newGame, BorderLayout.NORTH);
            mainFrame.getContentPane().add(place, BorderLayout.CENTER);
            mainFrame.getContentPane().add(status, BorderLayout.SOUTH);
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.pack();
            mainFrame.setVisible(true);
            logic.newGame();
        });
    }
}
