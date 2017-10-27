package tech.ericntd.githubsearch;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
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

    private RecyclerView rvRepos;
    private ReposRvAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchGitHubRepos();
        rvRepos = findViewById(R.id.rv_repos);
        rvRepos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        rvAdapter = new ReposRvAdapter();
        rvRepos.setAdapter(rvAdapter);
    }

    @DebugLog
    private void searchGitHubRepos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubSearchService service = retrofit.create(GitHubSearchService.class);
        Call<SearchResponse> call = service.searchRepos("android view stars:>1000 topic:android");
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
