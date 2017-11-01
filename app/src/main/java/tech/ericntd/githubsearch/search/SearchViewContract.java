package tech.ericntd.githubsearch.search;

import android.support.annotation.NonNull;

import java.util.List;

import tech.ericntd.githubsearch.models.SearchResult;

public interface SearchViewContract {
    void displaySearchResults(@NonNull List<SearchResult> searchResults);
    void displayError();
}
