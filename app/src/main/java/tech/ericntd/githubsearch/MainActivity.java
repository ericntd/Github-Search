package tech.ericntd.githubsearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.ericntd.githubsearch.models.SearchResponse;
import tech.ericntd.githubsearch.models.SearchResult;

public class MainActivity extends AppCompatActivity {

    private ReposRvAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etSearchQuery = findViewById(R.id.et_search_query);
        etSearchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v,
                                          int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchGitHubRepos(etSearchQuery.getText().toString());
                    return true;
                }
                return false;
            }
        });

        RecyclerView rvRepos = findViewById(R.id.rv_repos);
        rvAdapter = new ReposRvAdapter();
        rvRepos.setHasFixedSize(true);
        rvRepos.setAdapter(rvAdapter);
    }

    /**
     * @param query search query e.g. "android view stars:>1000 topic:android"
     */
    @DebugLog
    private void searchGitHubRepos(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubSearchService service = retrofit.create(GitHubSearchService.class);
        Call<SearchResponse> call = service.searchRepos(query);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call,
                                   Response<SearchResponse> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<SearchResponse> call,
                                  Throwable t) {
                Log.e("", "onFailure", t);
            }
        });
    }

    @DebugLog
    private void handleResponse(@NonNull Response<SearchResponse> response) {
        if (response.isSuccessful()) {
            SearchResponse searchResponse = response.body();
            if (searchResponse != null) {
                handleSearchResults(searchResponse.getSearchResults());
            } else {
                Log.w("", "empty response");
            }
        } else {
            Log.w("", "not a success");
        }
    }

    @DebugLog
    private void handleSearchResults(List<SearchResult> searchResults) {
        rvAdapter.updateResults(searchResults);
    }

    private static class ReposRvAdapter extends RecyclerView.Adapter<RepoViewHolder> {
        List<SearchResult> results = new ArrayList<>();

        @Override
        public RepoViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new RepoViewHolder(inflater.inflate(R.layout.rv_item_repo, null));
        }

        @Override
        public void onBindViewHolder(RepoViewHolder holder,
                                     int position) {
            holder.bind(results.get(position));
        }

        @Override
        public int getItemCount() {
            return results.size();
        }

        @DebugLog
        void updateResults(List<SearchResult> results) {
            this.results = results;
            notifyDataSetChanged();
        }
    }

    private static class RepoViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRepoName;

        RepoViewHolder(View itemView) {
            super(itemView);

            tvRepoName = itemView.findViewById(R.id.tv_repo_name);
        }

        @DebugLog
        void bind(SearchResult searchResult) {
            tvRepoName.setText(searchResult.getFullName());
        }
    }
}
