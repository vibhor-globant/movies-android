package com.globant.movies.model;

import java.util.ArrayList;

import rx.subjects.PublishSubject;

/**
 * Created by vibhor on 14/01/16.
 */
public enum MoviesModel {
    INSTANCE;

    private int currentPage;
    private int totalPages;
    private ArrayList<Result> movies;
    private final PublishSubject<Position> onAppend;

    MoviesModel() {
        currentPage = 0;
        totalPages = 1;
        movies = new ArrayList<>();
        onAppend = PublishSubject.create();
    }

    public int getCurrentPage() {
        return currentPage;
    }

//    public void setCurrentPage(int currentPage) {
//        this.currentPage = currentPage;
//    }

    public int getTotalPages() {
        return totalPages;
    }

//    public void setTotalPages(int totalPages) {
//        this.totalPages = totalPages;
//    }

    public ArrayList<Result> getMovies() {
        return movies;
    }

    public void appendMoviesFromPage(Page page) {
        int pos = this.movies.size();
        int size = page.getPage().getResults().size();
        this.movies.addAll(this.movies.size(), page.getPage().getResults());
        this.currentPage = page.getPage().getPage();
        this.totalPages = page.getPage().getTotalPages();
        onAppend.onNext(new Position(pos, size));
    }

    public  PublishSubject<Position> getObservable() {
        return onAppend;
    }

    public class Position {
        public int position;
        public int size;
        Position(int position, int size) {
            this.position = position;
            this.size = size;
        }
    }
}
