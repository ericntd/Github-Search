package tech.ericntd.githubsearch.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    @NonNull
    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @Nullable
    @SerializedName("items")
    @Expose
    private List<SearchResult> searchResults = null;

    public SearchResponse(@NonNull Integer totalCount,
                          @NonNull List<SearchResult> searchResults) {
        this.totalCount = totalCount;
        this.searchResults = searchResults;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    @Nullable
    public List<SearchResult> getSearchResults() {
        return searchResults;
    }
}
