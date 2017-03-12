package br.com.soulskyye.tecnonutri.presenter;

import br.com.soulskyye.tecnonutri.model.Item;

/**
 * Created by ulissescurti on 3/12/17.
 */

public interface Logger {

    void logAnalyticsItemLikeChanged(Item item, boolean isLiked);

    void logAnswersItemLikeChanged(Item item, boolean isLiked);
}
