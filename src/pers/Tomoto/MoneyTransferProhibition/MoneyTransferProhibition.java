package pers.Tomoto.MoneyTransferProhibition;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MoneyTransferProhibition extends JavaPlugin implements Listener {
    private static double _limit;
    private static boolean _on;
    @Override
    public void onLoad() {
        _limit = getConfig().getDouble("Limit");
        _on = getConfig().getBoolean("On");
    }
    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this,this);
    }
    @Override
    public void onDisable() {
        super.onDisable();
    }
    @EventHandler
    private void onPlayerGive(PlayerCommandPreprocessEvent event) {
        if(_on) {
            if(event.getMessage().contains("eco") || event.getMessage().contains("economy")) {
                if(event.getMessage().contains("give")) {
                    if(!event.getPlayer().isOp()) {
                        try {
                            if(Economy.getMoneyExact(event.getPlayer().getName()).doubleValue() < _limit) {
                                event.setCancelled(true);
                                event.getPlayer().sendMessage(ChatColor.RED + "[MTP]: 存款大于" + ChatColor.BLUE + _limit + ChatColor.RED + "时，才可以转账。");
                            }
                        }
                        catch(UserDoesNotExistException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}