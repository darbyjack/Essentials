package com.earth2me.essentials;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Baltop implements net.ess3.api.IBaltop {

    private final Essentials essentials;
    private final Cache<String, BigDecimal> balanaces = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

    public Baltop(Essentials essentials) {
        this.essentials = essentials;
    }

    @Override
    public Map<String, BigDecimal> getRawData() {
        final Map<String, BigDecimal> balances = new HashMap<>();
        for (UUID uuid : essentials.getUserMap().getAllUniqueUsers()) {
            final User user = essentials.getUserMap().getUser(uuid);
            if (user != null) {
                if (!essentials.getSettings().isNpcsInBalanceRanking() && user.isNPC()) {
                    continue;
                }
                final BigDecimal balance = user.getMoney();
                final String name = user.isHidden() ? user.getName() : user.getDisplayName();
                balances.put(name, balance);
            }
        }
        return balances;
    }

    @Override
    public void loadCache() {
        final List<Map.Entry<String, BigDecimal>> sortedEntries = new ArrayList<>(getRawData().entrySet());
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        sortedEntries.forEach(entry -> balanaces.put(entry.getKey(), entry.getValue()));
    }
}
