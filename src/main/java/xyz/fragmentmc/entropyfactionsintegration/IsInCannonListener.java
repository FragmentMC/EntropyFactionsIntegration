package xyz.fragmentmc.entropyfactionsintegration;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.FactionClaimEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionUnclaimAllEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionUnclaimEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;
import xyz.fragmentmc.entropy.event.IsInCannonEvent;

public class IsInCannonListener implements Listener {
    private final ClaimCache claimCache;

    public IsInCannonListener(ClaimCache claimCache) {
        this.claimCache = claimCache;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onIsInCannonCheck(IsInCannonEvent event) {
        Location origin = event.getEntity().getOrigin();
        if (origin == null) {
            event.setCancelled(true);
            return;
        }
        Chunk originChunk = origin.getChunk();
        Chunk current = event.getEntity().getChunk();
        event.setCancelled(claimCache.get(originChunk).equals(claimCache.get(current)));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClaim(FactionClaimEvent event) {
        onClaimChange(event.getClaim().getChunk(), getId(event.getFaction()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onUnclaim(FactionUnclaimEvent event) {
        String wilderness = getId(FactionsBridge.getFactionsAPI().getWilderness());
        onClaimChange(event.getClaim().getChunk(), wilderness);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onUnclaimAll(FactionUnclaimAllEvent event) {
        String wilderness = getId(FactionsBridge.getFactionsAPI().getWilderness());
        for (Claim claim : event.getFaction().getAllClaims()) {
            onClaimChange(claim.getChunk(), wilderness);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDisband(FactionDisbandEvent event) {
        String wilderness = getId(FactionsBridge.getFactionsAPI().getWilderness());
        for (Claim claim : event.getFaction().getAllClaims()) {
            onClaimChange(claim.getChunk(), wilderness);
        }
    }

    private String getId(@Nullable Faction faction) {
        if(faction == null) return "";
        return faction.getId();
    }

    private void onClaimChange(Chunk claim, String owner) {
        claimCache.set(claim, owner);
    }
}
