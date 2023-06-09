import game.ScoreCounter;
import game.engine.smart_register.LivingSubscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);

        frame.setLayout(new BorderLayout());
        JPanel upInfoPanel = new JPanel();
        JPanel gamePanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        frame.add(upInfoPanel, BorderLayout.PAGE_START);
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.PAGE_END);

        gamePanel.setBackground(Color.BLACK);

        upInfoPanel.setLayout(new BorderLayout());
        upInfoPanel.setBackground(Color.BLACK);
        upInfoPanel.setForeground(Color.WHITE);
        Font font = new Font(null, Font.PLAIN, 24);
        JLabel scoreLabel = new JLabel("SCORE");
        scoreLabel.setFont(font);
        scoreLabel.setForeground(Color.WHITE);
        JLabel hiScoreLabel = new JLabel("HI-SCORE");
        hiScoreLabel.setFont(font);
        hiScoreLabel.setForeground(Color.WHITE);
        upInfoPanel.add(scoreLabel, BorderLayout.LINE_START);
        upInfoPanel.add(hiScoreLabel, BorderLayout.LINE_END);
        JPanel lowerScores = new JPanel();
        lowerScores.setBackground(Color.BLACK);
        lowerScores.setLayout(new BorderLayout());
        JLabel scoreNumberLabel = new JLabel("0");
        scoreNumberLabel.setFont(font);
        scoreNumberLabel.setForeground(Color.WHITE);
        JLabel hiscoreNumberLabel = new JLabel("0");
        hiscoreNumberLabel.setFont(font);
        hiscoreNumberLabel.setForeground(Color.WHITE);
        lowerScores.add(scoreNumberLabel, BorderLayout.LINE_START);
        lowerScores.add(hiscoreNumberLabel, BorderLayout.LINE_END);
        upInfoPanel.add(lowerScores, BorderLayout.PAGE_END);

        JLabel bottomLabel = new JLabel("LIVES: 0");
        bottomLabel.setFont(font);
        bottomLabel.setForeground(Color.WHITE);
        bottomPanel.add(bottomLabel);
        bottomPanel.setBackground(Color.BLACK);

        GameInfoListener infoListener = lives -> bottomLabel.setText("LIVES: " + lives);

        ScoreCounter scoreCounter = new ScoreCounter() {
            @Override
            public synchronized void setScore(int newScore) {
                super.setScore(newScore);
                scoreNumberLabel.setText("" + newScore);
            }

            @Override
            public synchronized void commitScore() {
                super.commitScore();
                hiscoreNumberLabel.setText("" + getHIScore());
            }
        };

        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.isControlDown() && e.getExtendedKeyCode() == KeyEvent.VK_W) {
                    scoreCounter.commitScore();
                    System.exit(0);
                }
            }
        });
        gamePanel.setFocusable(true);

        scoreNumberLabel.setText("" + scoreCounter.getScore());
        hiscoreNumberLabel.setText("" + scoreCounter.getHIScore());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        JButton button = new JButton("PLAY");
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBackground(Color.BLACK);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder());
        buttonPanel.add(button, BorderLayout.CENTER);
        buttonPanel.setPreferredSize(new Dimension(SpaceInvaders.SCREEN_WIDTH, SpaceInvaders.SCREEN_HEIGHT));
        gamePanel.add(buttonPanel);

        button.addActionListener((actionEvent) -> {
            gamePanel.remove(buttonPanel);
            button.removeActionListener(button.getActionListeners()[0]);
            SpaceInvaders spaceInvaders = new SpaceInvaders(gamePanel, scoreCounter);
            spaceInvaders.register(new LivingSubscriber<>(infoListener));
            spaceInvaders.start();
        });


        frame.pack();
        frame.setBackground(Color.BLACK);
        frame.setVisible(true);
    }
}
