package Screen.DeadScreen;

import Screen.StatsScreen.GameStatsData;
import ParticleSystem.ParticleEmitter;
import Screen.Button;
import StatsManager.GameStats;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

public class DeadScreen implements MouseMotionListener, MouseListener {
    protected final GameStats gameStats;
    protected int originalScore;
    protected boolean highscore;
    protected int score;
    protected int cent;
    protected int kill;
    protected int survivalTime;
    private int halfWidth;
    public Button menu;
    public Button restart;
    protected final Button[] button;
    private final UI ui;

    public final ParticleEmitter sparkEmitter = new ParticleEmitter();

    public DeadScreen(GameStats gameStats) {
        this.gameStats = gameStats;
        try {
            menu = new Screen.Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Menu-1.png")), ImageIO.read(new File("Assets\\2D models\\Buttons\\Menu-2.png")));
            restart = new Screen.Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Restart-1.png")), ImageIO.read(new File("Assets\\2D models\\Buttons\\Restart-2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        button = new Button[]{menu, restart};
        initSparkParticleSystem();
        ui = new UI(this);
    }

    private void initSparkParticleSystem() {
        sparkEmitter.setAngle(90, -90);
        sparkEmitter.setParticleColor(Color.WHITE);
        sparkEmitter.setParticleShape("Rectangle");
        sparkEmitter.setAmountOfParticle(5);
        sparkEmitter.setAge(1000, 750);
        sparkEmitter.setSizeBound(6, 3);
        sparkEmitter.setVelocity(3, 1);
        sparkEmitter.setPhysic(true);
    }

    public void reset() {
        score = 0;
        cent = 0;
        kill = 0;
        survivalTime = 0;
        updateStats = false;
        updateTimer = 0;

        originalScore = 0;
        highscore = false;

        for (Screen.Button button : button) {
            button.clicked = false;
            button.touched = false;
        }
        halfWidth = 0;
        killCounter = 0;
    }

    public void paint(Graphics2D g2d) {
        ui.paint(g2d);
    }

    public void setAfterGameStats(int totalCent, int totalKill, int totalSurvivalTime, int score) {
        this.cent = totalCent;
        this.kill = totalKill;
        this.survivalTime = totalSurvivalTime;
        this.score = score / 100;
        this.originalScore = score;
        //set up record.
        if (gameStats.highScore < score) {
            gameStats.highScore = score;
            highscore = true;
        }
        if (gameStats.longestSurvivalTime < totalSurvivalTime) {
            gameStats.longestSurvivalTime = totalSurvivalTime;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!updateStats) {
            for (Screen.Button button : button) {
                button.mouseDragged(e);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!updateStats) {
            for (Screen.Button button : button) {
                button.mouseMoved(e);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!updateStats) {
            for (Screen.Button button : button) {
                button.mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!updateStats) {
            for (Screen.Button button : button) {
                button.mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!updateStats) {
            for (Screen.Button button : button) {
                button.mouseEntered(e);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void appear() {
        if (halfWidth < 683) {
            halfWidth += 5;
        } else {
            halfWidth = 683;
        }
        int screenX = 683 - halfWidth;
        int screenY = 384 - 384 * halfWidth / 683;
        int screenWidth = halfWidth * 2;
        int screenLength = 768 * screenWidth / 1366;

        menu.x = screenX + screenWidth * 583 / 1366;
        menu.y = screenY + screenLength * 475 / 768;
        menu.width = 200 * screenWidth / 1366;
        menu.length = 60 * screenLength / 768;

        restart.x = screenX + screenWidth * 583 / 1366;
        restart.y = screenY + screenLength * 560 / 768;
        restart.width = 200 * screenWidth / 1366;
        restart.length = 60 * screenLength / 768;
    }

    private int updateTimer;
    private int killCounter;
    //this attribute shows if the stats is updating or not
    private boolean updateStats;


    private void updateStats() {
        if (halfWidth >= 683) {

            if (score > 0 || cent > 0 || kill > 0) {
                updateTimer++;
                updateStats = true;
                sparkEmitter.setPosition(508 + (double) 400 * gameStats.currentLevelPoint / gameStats.requiredLevelPoint, 310);
                sparkEmitter.spreadRepeatly(true, 25);
            } else if (score == 0 && cent == 0 && kill == 0) {
                updateTimer = 0;
                updateStats = false;
                sparkEmitter.spreadRepeatly(false, 0);
            }

            if (updateTimer > 0 && updateTimer % 5 == 0) {
                if (score > 0) {
                    score--;
                    gameStats.currentLevelPoint++;
                } else {
                    if (kill > 0) {
                        kill--;
                        gameStats.kill++;
                        killCounter++;
                        if (killCounter == 5) {
                            gameStats.currentLevelPoint++;
                            killCounter = 0;
                        }
                    } else {
                        if (cent > 0) {
                            cent--;
                            gameStats.cent++;
                        }
                    }
                }
                if (score == 0 && kill == 0 && cent == 0) {
                    GameStatsData.writeStats(gameStats);
                    updateTimer = 0;
                }
            }

        }
    }

    public void update() {
        appear();
        updateStats();
        if (!updateStats) {
            for (Button button : button) {
                button.update();
            }
        }
        sparkEmitter.update();
    }
}
