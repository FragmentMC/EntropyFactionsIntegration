package xyz.fragmentmc.entropyfactionsintegration;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.bukkit.Chunk;

public class ClaimCache {
    private Long2ObjectMap<String> claimMap = new Long2ObjectOpenHashMap<String>();

    public String get(Chunk chunk) {
        return claimMap.computeIfAbsent(getKey(chunk), k -> {
            Faction fac = FactionsBridge.getFactionsAPI().getClaim(chunk).getFaction();
            String res = "";
            if (fac == null) res = "";
            else res = fac.getId();
            return res;
        });
    }

    public void set(Chunk chunk, String owner) {
        claimMap.put(getKey(chunk), owner);
    }

    private long getKey(Chunk chunk) {
        return chunk.getChunkKey() ^ chunk.getWorld().getKey().hashCode();
    }
}
