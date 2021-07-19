import Modes.PayloadMode.PayloadMode;
import Modes.SurvivalMode.SurvivalMode;
import Screen.Customize.Customize;
import Screen.DeadScreen.DeadScreen;
import Screen.AboutScreen;
import Screen.Menu;
import Screen.ModeScreen;
import Screen.SettingScreen.SettingScreen;
import Screen.Shop.Shop;
import Screen.Shop.ShopManager;
import Screen.StatsScreen.StatsScreen;
import Shooter.PlayerController.Player;
import StatsManager.GameStats;
import StatsManager.StatsUI;

import java.awt.*;

public class ScreenHandler {
    private final GameStats gameStats;
    private final StatsUI statsUI;

    private final Menu menu;

    private final SettingScreen settingScreen;
    private final AboutScreen aboutScreen;
    private final StatsScreen statsScreen;
    private final Customize customize;
    private final ShopManager shopManager;
    private final Shop shop;

    private final ModeScreen modeScreen;

    private final SurvivalMode survivalMode;
    private final PayloadMode payloadMode;
    private final DeadScreen deadScreen;

    public ScreenHandler(GameStats gameStats,
                         StatsUI statsUI,
                         Menu menu,
                         SettingScreen settingScreen,
                         AboutScreen aboutScreen,
                         StatsScreen statsScreen,
                         Customize customize,
                         ShopManager shopManager,
                         Shop shop,
                         ModeScreen modeScreen,
                         SurvivalMode survivalMode,
                         PayloadMode payloadMode,
                         DeadScreen deadScreen) {
        this.gameStats = gameStats;
        this.statsUI = statsUI;
        this.menu = menu;
        this.settingScreen = settingScreen;
        this.aboutScreen = aboutScreen;
        this.statsScreen = statsScreen;
        this.customize = customize;
        this.shopManager = shopManager;
        this.shop = shop;
        this.modeScreen = modeScreen;
        this.survivalMode = survivalMode;
        this.payloadMode = payloadMode;
        this.deadScreen = deadScreen;
    }

    public void update() {
        if (!menu.play.clicked &&
                !menu.shop.clicked &&
                !menu.stats.clicked &&
                !menu.customize.clicked &&
                !menu.about.clicked &&
                !menu.setting.clicked) {
            menu.update();
            //reset all next screen
            if (!menu.resetNextScreen) {
                modeScreen.reset();
                shop.reset();
                statsScreen.reset();
                customize.reset();
                aboutScreen.reset();
                settingScreen.reset();
                menu.resetNextScreen = true;
            }
        }
        if (menu.play.clicked) {
            if (!modeScreen.survival.clicked && !modeScreen.payload.clicked) {
                modeScreen.update();
                if (!modeScreen.resetNextScreen) {
                    survivalMode.reset(shopManager, customize);
                    payloadMode.reset(shopManager, customize);
                    deadScreen.reset();
                    modeScreen.resetNextScreen = true;
                }
                if (modeScreen.back.clicked) {
                    menu.reset();
                }
            }
        }
        handlePathToInstruction();
        handlePathToSurvivalGame();
        handlePathToPayloadGame();
        handlePathToShop();
        handlePathToStatsScreen();
        handlePathToCustomize();
        handlePathToSetting();
        gameStats.update();
    }

    private void handlePathToSurvivalGame() {
        if (menu.play.clicked) {
            if (modeScreen.survival.clicked) {
                survivalMode.update();
                if (!survivalMode.gameOver) {
                    Player player = survivalMode.playerController.player;
                    deadScreen.setAfterGameStats(player.cent, player.kill, player.survivalTime, player.score);
                } else {
                    deadScreen.update();
                    if (deadScreen.menu.clicked) {
                        menu.reset();
                    }
                    if (deadScreen.restart.clicked) {
                        survivalMode.reset(shopManager, customize);
                        deadScreen.reset();
                    }
                }
            }
        }
    }

    private void handlePathToPayloadGame() {
        if (menu.play.clicked) {
            if (modeScreen.payload.clicked) {
                payloadMode.update();
                if (!payloadMode.gameOver) {
                    Player player = payloadMode.playerController.player;
                    deadScreen.setAfterGameStats(player.cent, player.kill, player.survivalTime, player.score);
                } else {
                    deadScreen.update();
                    if (deadScreen.menu.clicked) {
                        menu.reset();
                    }
                    if (deadScreen.restart.clicked) {
                        payloadMode.reset(shopManager, customize);
                        deadScreen.reset();
                    }
                }
            }
        }
    }

    private void handlePathToShop() {
        if (menu.shop.clicked) {
            shop.update();
            if (shop.back.clicked) {
                menu.reset();
            }
        }
    }

    private void handlePathToInstruction() {
        if (menu.about.clicked) {
            aboutScreen.update();
            if (aboutScreen.back.clicked) {
                menu.reset();
            }
        }
    }

    private void handlePathToStatsScreen() {
        if (menu.stats.clicked) {
            statsScreen.update();
            if (statsScreen.back.clicked) {
                menu.reset();
            }
        }
    }

    private void handlePathToCustomize() {
        if (menu.customize.clicked) {
            customize.update();
            if (customize.back.clicked) {
                menu.reset();
            }
        }
    }

    private void handlePathToSetting() {
        if (menu.setting.clicked) {
            settingScreen.update();
            if (settingScreen.back.clicked) {
                menu.reset();
            }
        }
    }

    public void paint(Graphics2D g2d) {
        if (!menu.play.clicked &&
                !menu.shop.clicked &&
                !menu.stats.clicked &&
                !menu.customize.clicked &&
                !menu.about.clicked &&
                !menu.setting.clicked) {
            menu.paint(g2d);
            statsUI.paint(g2d);
        } else if (menu.play.clicked) {
            if (!modeScreen.survival.clicked && !modeScreen.payload.clicked) {
                modeScreen.paint(g2d);
            } else if (modeScreen.survival.clicked) {
                survivalMode.paint(g2d);
                if (survivalMode.gameOver) {
                    deadScreen.paint(g2d);
                }
            } else {
                payloadMode.paint(g2d);
                if (payloadMode.gameOver) {
                    deadScreen.paint(g2d);
                }
            }
        } else if (menu.shop.clicked) {
            shop.paint(g2d);
            statsUI.paint(g2d);
        } else if (menu.stats.clicked) {
            statsScreen.paint(g2d);
            statsUI.paint(g2d);
        } else if (menu.customize.clicked) {
            customize.paint(g2d);
            statsUI.paint(g2d);
        } else if (menu.about.clicked) {
            aboutScreen.paint(g2d);
        } else {
            settingScreen.paint(g2d);
        }
    }
}