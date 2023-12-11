package xyz.fragmentmc.entropyfactionsintegration;

import org.bukkit.plugin.java.JavaPlugin;

public final class EntropyFactionsIntegration extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new IsInCannonListener(new ClaimCache()), this);
    }

    @Override
    public void onDisable() {
    }
}
