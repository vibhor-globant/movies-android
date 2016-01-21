package com.globant.movies.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * Created by vibhor on 15/01/16.
 */
public class RecyclerSimpleScrollListener extends RecyclerView.OnScrollListener {
    private final SerializedSubject<Boolean, Boolean> scrollBus;

    public RecyclerSimpleScrollListener() {
        scrollBus = new SerializedSubject<>(PublishSubject.<Boolean> create());
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((GridLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
                scrollBus.onNext(true);
            } else {
                scrollBus.onNext(false);
            }
        } else {
            scrollBus.onNext(false);
        }
    }

    public Observable<Boolean> getLastVisibleObservable() {
        return scrollBus.onBackpressureDrop().distinctUntilChanged().skip(1).filter(lastVisible -> lastVisible).asObservable();
    }
}