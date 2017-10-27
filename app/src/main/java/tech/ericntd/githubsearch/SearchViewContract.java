package tech.ericntd.githubsearch;

import java.util.List;

import tech.ericntd.githubsearch.models.SearchResult;

public interface SearchViewContract {
    void displaySearchResults(List<SearchResult> searchResults);
    void displayError();
}
