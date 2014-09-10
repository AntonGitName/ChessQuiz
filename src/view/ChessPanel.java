package view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.ChessQuizSolver;

public class ChessPanel extends JPanel {

    private static final String RESOURCES_FOLDER = "resources/";
    
    public ChessPanel(int borderSize) {
        super();

        this.boardSize = borderSize;
        
        Image queenTmp = null;
        Image squareBlackTmp = null;
        Image squareWhiteTmp = null;
        try {
            queenTmp = ImageIO.read(new File(RESOURCES_FOLDER + "queen.png"));
            squareBlackTmp = ImageIO.read(new File(RESOURCES_FOLDER + "squareBlack.png"));
            squareWhiteTmp = ImageIO.read(new File(RESOURCES_FOLDER + "squareWhite.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        ImageQueen = queenTmp;
        ImageSquareBlack = squareBlackTmp;
        ImageSquareWhite = squareWhiteTmp;
    }

    private static final long serialVersionUID = -2890468414051199908L;

    private int boardSize;
    private double startTemperature;
    private double alpha;
    private int iter;
    
    private boolean isSolved = false;
    private int[] queens;

    public void setBoardParameters(int boardSize, double startTemperature, double alpha, int iter) {
        this.boardSize = boardSize;
        this.startTemperature = startTemperature;
        this.alpha = alpha;
        this.iter = iter;
        isSolved = false;
        repaint();
    }

    private int squareSize = 64;
    private final Image ImageQueen;
    private final Image ImageSquareBlack;
    private final Image ImageSquareWhite;
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        squareSize = Math.min(getWidth(), getHeight()) / boardSize;
        
        paintChessboard(g);
        
        if (isSolved) {
            paintQueens(g);
        }
    }

    private void paintChessboard(Graphics g) {
        Image square = ImageSquareWhite;
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                square = (((i + j) & 1) == 0) ? ImageSquareBlack : ImageSquareWhite;
                g.drawImage(square, j * squareSize, i  * squareSize, squareSize, squareSize, null);
            }
        }
    }
    
    public int solveQuiz() {
        queens = new int[boardSize];
        int totalIterations = ChessQuizSolver.solve(boardSize, startTemperature, alpha, iter, queens);
        isSolved = true;
        repaint();
        return totalIterations;
    }
    
    private void paintQueens(Graphics g) {
        for (int i = 0; i < queens.length; ++i) {
            g.drawImage(ImageQueen, i * squareSize, queens[i]  * squareSize, squareSize, squareSize, null);
        }
    }
    
    public boolean isSolutionCorrect() {
        if (isSolved) {
            int len = queens.length;
            for (int i = 0; i < len; ++i) {
                for (int j = i + 1; j < len; ++j) {
                    if (Math.abs(i - j) == Math.abs(queens[i] - queens[j])) {
                        return false;
                    }
                }
            }
        }
        return isSolved;
    }
    
}
