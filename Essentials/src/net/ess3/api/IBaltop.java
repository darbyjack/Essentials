package net.ess3.api;

import java.math.BigDecimal;
import java.util.Map;

public interface IBaltop {

    /**
     * Returns the data that will be put into the cache before it is added and sorted
     *
     * @return Raw data of users from {@link com.earth2me.essentials.UserMap}
     */
    Map<String, BigDecimal> getRawData();

    /**
     * Loads the data into the cache
     */
    void loadCache();
}
