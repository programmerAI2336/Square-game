package Screen.Shop;

import Screen.Shop.UpgradeManager.PlayerUpgradeManager;
import Screen.Shop.UpgradeManager.WeaponUpgradeManager;

public class ShopManager {
    public final PlayerUpgradeManager playerUpgradeManager = new PlayerUpgradeManager();
    public final WeaponUpgradeManager weaponUpgradeManager = new WeaponUpgradeManager();
}
