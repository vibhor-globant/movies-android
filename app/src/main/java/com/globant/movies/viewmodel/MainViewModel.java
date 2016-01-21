package com.globant.movies.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.globant.movies.model.MoviesModel;
import com.globant.movies.model.Page;
import com.globant.movies.network.NetworkService;

import rx.Observer;
import rx.Subscription;

/**
 * Created by vibhor on 21/01/16.
 */
public class MainViewModel implements ViewModel {
    private Subscription apiSubscription;
    private Subscription modelSubscription;
    private ChangeListener changeListener;

    public ObservableInt recyclerViewVisibility;
    public ObservableInt statusMessageVisibility;
    public ObservableInt activityIndicatorVisibility;
    public ObservableInt retryButtonVisibility;

    public ObservableField<String> statusMessage;

    public MainViewModel(ChangeListener listener) {
        this.changeListener = listener;
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        statusMessageVisibility = new ObservableInt(View.INVISIBLE);
        activityIndicatorVisibility = new ObservableInt(View.INVISIBLE);
        retryButtonVisibility = new ObservableInt(View.GONE);

        statusMessage = new ObservableField<>("");

        modelSubscription = MoviesModel.INSTANCE.getObservable().subscribe(position -> MainViewModel.this.changeListener.onModelChanged(position));

    }

    @Override
    public void destroy() {
        clearPendingSubscription();
        if (modelSubscription != null && !modelSubscription.isUnsubscribed()) {
            modelSubscription.unsubscribe();
        }
    }

    public void getFirstPage() {
        clearPendingSubscription();
        getNextPage();
        statusMessage.set("Getting page from server...");
        recyclerViewVisibility.set(View.INVISIBLE);
        statusMessageVisibility.set(View.VISIBLE);
    }

    public interface ChangeListener {
        void onModelChanged(MoviesModel.Position position);
    }

    public void getNextPage() {
        activityIndicatorVisibility.set(View.VISIBLE);
        retryButtonVisibility.set(View.GONE);
        apiSubscription = NetworkService.INSTANCE.getPage(MoviesModel.INSTANCE.getCurrentPage() + 1).subscribe(new Observer<Page>() {
            @Override
            public void onCompleted() {
                recyclerViewVisibility.set(View.VISIBLE);
                statusMessageVisibility.set(View.GONE);
                activityIndicatorVisibility.set(View.INVISIBLE);
                retryButtonVisibility.set(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                statusMessage.set(e.getLocalizedMessage());
                statusMessageVisibility.set(View.VISIBLE);
                activityIndicatorVisibility.set(View.INVISIBLE);
                retryButtonVisibility.set(View.VISIBLE);
            }

            @Override
            public void onNext(Page page) {
                MoviesModel.INSTANCE.appendMoviesFromPage(page);
            }
        });
    }

    public void onRetryButtonClick(View view) {
        getNextPage();
    }

    private void clearPendingSubscription() {
        if (apiSubscription != null && !apiSubscription.isUnsubscribed()) {
            apiSubscription.unsubscribe();
        }
    }
}
