package paoo.game;

public class ScoreInfo {
    public ScoreInfo(String name, int score, String date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    private String name;
    private int score;
    private String date;
}
