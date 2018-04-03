package tech.ericntd.githubsearch.search;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import tech.ericntd.githubsearch.models.SearchResult;

public interface SearchViewContract {
    void displaySearchResults(@NonNull List<SearchResult> searchResults,
                              @Nullable Integer totalCount);

    void displayError();

    void displayError(String s);
}
