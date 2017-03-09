package br.com.soulskyye.tecnonutri.presenter;

/**
 * Created by ulissescurti on 3/8/17.
 */

public interface BasePresenter<V> {

    void attachView(V view);

    void detachView();

}
