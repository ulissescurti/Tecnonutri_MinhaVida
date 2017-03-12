package br.com.soulskyye.tecnonutri.presenter;

import br.com.soulskyye.tecnonutri.model.Item;

/**
 * Created by ulissescurti on 3/12/17.
 */

public interface AnalyticsLogger {

    void logItemLikeChanged(Item item, boolean isLiked);
}
