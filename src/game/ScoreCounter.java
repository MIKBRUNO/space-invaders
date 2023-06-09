package game;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class ScoreCounter {
    public ScoreCounter() {
        try (InputStream in = ScoreCounter.class.getResourceAsStream("scores.txt")) {
            if (in == null) {
                throw new RuntimeException("scores file not found as resource of  " + ScoreCounter.class.getName());
            }
            Scanner scanner = new Scanner(in);
            HIScore = scanner.nextInt();
        } catch (IOException e) {
            throw new RuntimeException("cannot open scores file as resource of " + ScoreCounter.class.getName());
        }
    }

    public synchronized int getHIScore() {
        return HIScore;
    }

    public synchronized int getScore() {
        return Score;
    }

    public synchronized void setScore(int newScore) {
        Score = newScore;
    }

    public synchronized void commitScore() {
        URL url = ScoreCounter.class.getResource("scores.txt");
        if (url == null) {
            throw new RuntimeException("scores file not found as resource of  " + ScoreCounter.class.getName());
        }
        try (PrintWriter writer = new PrintWriter(url.getPath())) {
            if (Score > HIScore) {
                HIScore = Score;
            }
            writer.println(HIScore);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("scores file not found as resource of  " + ScoreCounter.class.getName());
        }
    }

    private int HIScore;
    private int Score = 0;
}
