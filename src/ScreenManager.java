import Audio.BackgroundMusic;
import Screen.Customize.Customize;
import Screen.AboutScreen;
import Screen.SettingScreen.SettingScreen;
import Screen.StatsScreen.GameStatsData;
import Modes.PayloadMode.PayloadMode;
import Modes.SurvivalMode.SurvivalMode;
import Screen.DeadScreen.DeadScreen;
import Screen.Menu;
import Screen.ModeScreen;
import Screen.Shop.Shop;
import Screen.Shop.ShopManager;
import Screen.StatsScreen.StatsScreen;
import StatsManager.*;

import java.awt.*;
import java.awt.event.*;

public class ScreenManager implements KeyListener, MouseListener, MouseMotionListener {
    private final BackgroundMusic backgroundMusic = new BackgroundMusic();

    private final GameStats gameStats = new GameStats();
    private final StatsUI statsUI = new StatsUI(gameStats);

    private final Menu menu = new Menu();

    private final SettingScreen settingScreen = new SettingScreen(backgroundMusic);
    private final AboutScreen aboutScreen = new AboutScreen();
    private final StatsScreen statsScreen = new StatsScreen(gameStats);

    private final Customize customize = new Customize(gameStats);
    private final ShopManager shopManager = new ShopManager();
    private final Shop shop = new Shop(gameStats, shopManager);

    private final ModeScreen modeScreen = new ModeScreen();

    private final SurvivalMode survivalMode = new SurvivalMode();
    private final PayloadMode payloadMode = new PayloadMode();
    private final DeadScreen deadScreen = new DeadScreen(gameStats);

    private final ScreenHandler screenHandler = new ScreenHandler(
            gameStats,
            statsUI,
            menu,
            settingScreen,
            aboutScreen,
            statsScreen,
            customize,
            shopManager,
            shop,
            modeScreen,
            survivalMode,
            payloadMode,
            deadScreen);

    public void update() {
        backgroundMusic.update();
        screenHandler.update();
    }

    public void paint(Graphics2D g2d) {
        screenHandler.paint(g2d);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (modeScreen.survival.clicked) {
            survivalMode.keyPressed(e);
        }
        if (modeScreen.payload.clicked) {
            payloadMode.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (modeScreen.survival.clicked) {
            survivalMode.keyReleased(e);
        }
        if (modeScreen.payload.clicked) {
            payloadMode.keyReleased(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!menu.play.clicked &&
                !menu.shop.clicked &&
                !menu.stats.clicked &&
                !menu.customize.clicked &&
                !menu.about.clicked &&
                !menu.setting.clicked) {
            menu.mousePressed(e);
        } else if (menu.play.clicked) {
            if (!modeScreen.survival.clicked && !modeScreen.payload.clicked) {
                modeScreen.mousePressed(e);
            } else if (modeScreen.survival.clicked) {
                survivalMode.mousePressed(e);
                if (survivalMode.gameOver) {
                    deadScreen.mousePressed(e);
                }
            } else {
                payloadMode.mousePressed(e);
                if (payloadMode.gameOver) {
                    deadScreen.mousePressed(e);
                }
            }
        } else if (menu.shop.clicked) {
            shop.mousePressed(e);
        } else if (menu.stats.clicked) {
            statsScreen.mousePressed(e);
        } else if (menu.customize.clicked) {
            customize.mousePressed(e);
        } else if (menu.about.clicked) {
            aboutScreen.mousePressed(e);
        } else {
            settingScreen.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!menu.play.clicked &&
                !menu.shop.clicked &&
                !menu.stats.clicked &&
                !menu.customize.clicked &&
                !menu.about.clicked &&
                !menu.setting.clicked) {
            menu.mouseReleased(e);
        } else if (menu.play.clicked) {
            if (!modeScreen.survival.clicked && !modeScreen.payload.clicked) {
                modeScreen.mouseReleased(e);
            } else if (modeScreen.survival.clicked) {
                survivalMode.mouseReleased(e);
                if (survivalMode.gameOver) {
                    deadScreen.mouseReleased(e);
                }
            } else {
                payloadMode.mouseReleased(e);
                if (payloadMode.gameOver) {
                    payloadMode.mouseReleased(e);
                }
            }
        } else if (menu.shop.clicked) {
            shop.mouseReleased(e);
        } else if (menu.stats.clicked) {
            statsScreen.mouseReleased(e);
        } else if (menu.customize.clicked) {
            customize.mouseReleased(e);
        } else if (menu.about.clicked) {
            aboutScreen.mouseReleased(e);
        } else {
            settingScreen.mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!menu.play.clicked &&
                !menu.shop.clicked &&
                !menu.stats.clicked &&
                !menu.customize.clicked &&
                !menu.about.clicked &&
                !menu.setting.clicked) {
            menu.mouseEntered(e);
        } else if (menu.play.clicked) {
            if (!modeScreen.survival.clicked && !modeScreen.payload.clicked) {
                modeScreen.mouseEntered(e);
            } else if (modeScreen.payload.clicked) {
                survivalMode.mouseEntered(e);
                if (survivalMode.gameOver) {
                    deadScreen.mouseEntered(e);
                }
            } else {
                payloadMode.mouseEntered(e);
                if (payloadMode.gameOver) {
                    deadScreen.mouseEntered(e);
                }
            }
        } else if (menu.shop.clicked) {
            shop.mouseEntered(e);
        } else if (menu.stats.clicked) {
            statsScreen.mouseEntered(e);
        } else if (menu.customize.clicked) {
            customize.mouseEntered(e);
        } else if (menu.about.clicked) {
            aboutScreen.mouseEntered(e);
        } else {
            settingScreen.mouseEntered(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!menu.play.clicked &&
                !menu.shop.clicked &&
                !menu.stats.clicked &&
                !menu.customize.clicked &&
                !menu.about.clicked &&
                !menu.setting.clicked) {
            menu.mouseDragged(e);
        } else if (menu.play.clicked) {
            if (!modeScreen.survival.clicked && !modeScreen.payload.clicked) {
                modeScreen.mouseDragged(e);
            } else if (modeScreen.survival.clicked) {
                survivalMode.mouseDragged(e);
                if (survivalMode.gameOver) {
                    deadScreen.mouseDragged(e);
                }
            } else {
                payloadMode.mouseDragged(e);
                if (payloadMode.gameOver) {
                    deadScreen.mouseDragged(e);
                }
            }
        } else if (menu.shop.clicked) {
            shop.mouseDragged(e);
        } else if (menu.stats.clicked) {
            statsScreen.mouseDragged(e);
        } else if (menu.customize.clicked) {
            customize.mouseDragged(e);
        } else if (menu.about.clicked) {
            aboutScreen.mouseDragged(e);
        } else {
            settingScreen.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!menu.play.clicked &&
                !menu.shop.clicked &&
                !menu.stats.clicked &&
                !menu.customize.clicked &&
                !menu.about.clicked &&
                !menu.setting.clicked) {
            menu.mouseMoved(e);
        } else if (menu.play.clicked) {
            if (!modeScreen.survival.clicked && !modeScreen.payload.clicked) {
                modeScreen.mouseMoved(e);
            } else if (modeScreen.survival.clicked) {
                survivalMode.mouseMoved(e);
                if (survivalMode.gameOver) {
                    deadScreen.mouseMoved(e);
                }
            } else {
                payloadMode.mouseMoved(e);
                if (payloadMode.gameOver) {
                    deadScreen.mouseMoved(e);
                }
            }
        } else if (menu.shop.clicked) {
            shop.mouseMoved(e);
        } else if (menu.stats.clicked) {
            statsScreen.mouseMoved(e);
        } else if (menu.customize.clicked) {
            customize.mouseMoved(e);
        } else if (menu.about.clicked) {
            aboutScreen.mouseMoved(e);
        } else {
            settingScreen.mouseMoved(e);
        }
    }
}
