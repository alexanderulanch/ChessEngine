package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final Board chessBoard;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);

    private final Color lightTileColor = Color.decode("#EBECD0");
    private final Color darkTileColor = Color.decode("#779556");

    private static String defaultPieceImagesPath = "images/";


    public Table() {
        gameFrame = new JFrame("Chess");
        gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        gameFrame.setJMenuBar(tableMenuBar);
        gameFrame.setSize(OUTER_FRAME_DIMENSION);
        gameFrame.setResizable(false);
        chessBoard = Board.createStandardBoard();
        boardPanel = new BoardPanel();
        gameFrame.add(boardPanel, BorderLayout.CENTER);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN File");

        openPGN.addActionListener(e -> System.out.println("open up that pgn file silly"));

        fileMenu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8,8));
            boardTiles = new ArrayList<>();

            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                boardTiles.add(tilePanel);
                add(tilePanel);
            }

            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }
    }

    private class TilePanel extends JPanel {
        private final int tileId;

        TilePanel(final BoardPanel boardPanel,
                  final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            validate();

        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();

            if (board.getTile(tileId).isOccupied()) {
                String fileName = defaultPieceImagesPath +
                        board.getTile(tileId)
                                .getPiece()
                                .getPieceAlliance()
                                .toString()
                                .substring(0, 1) +
                        board.getTile(tileId).getPiece().toString() + ".png";
                try {
                    final BufferedImage tileImage = ImageIO.read(
                            new File(fileName));
                    add(new JLabel(new ImageIcon(tileImage)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private void assignTileColor() {
            if (BoardUtils.EIGHTH_RANK[tileId] ||
                BoardUtils.SIXTH_RANK[tileId] ||
                BoardUtils.FOURTH_RANK[tileId] ||
                BoardUtils.SECOND_RANK[tileId]) {
                setBackground(tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (BoardUtils.SEVENTH_RANK[tileId] ||
                BoardUtils.FIFTH_RANK[tileId] ||
                BoardUtils.THIRD_RANK[tileId] ||
                BoardUtils.FIRST_RANK[tileId]) {
                setBackground(tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }
}
