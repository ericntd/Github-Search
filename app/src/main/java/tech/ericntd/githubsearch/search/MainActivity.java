package tech.ericntd.githubsearch.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.ericntd.githubsearch.R;
import tech.ericntd.githubsearch.models.SearchResponse;
import tech.ericntd.githubsearch.models.SearchResult;
import tech.ericntd.githubsearch.repositories.GitHubApi;
import tech.ericntd.githubsearch.repositories.GitHubRepository;

public class MainActivity extends AppCompatActivity implements SearchViewContract {

    private SearchResultRvAdapter rvAdapter;
    private TextView tvStatus;
    public GitHubRepository repository;
    private SearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);
        tvStatus.setVisibility(View.VISIBLE);
        final EditText etSearchQuery = findViewById(R.id.et_search_query);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        repository = new GitHubRepository(retrofit.create(GitHubApi.class));
        presenter = new SearchPresenter(this, repository);

        etSearchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v,
                                          int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(etSearchQuery.getText().toString());
                    return true;
                }
                return false;
            }
        });

        RecyclerView rvRepos = findViewById(R.id.rv_repos);
        rvRepos.setHasFixedSize(true);
        rvAdapter = new SearchResultRvAdapter();
        rvRepos.setAdapter(rvAdapter);
    }

    public void search(String query) {
//        presenter.searchGitHubRepos(s);
        if (query != null && query.length() > 0) {
            repository.searchRepos(query, new GitHubRepository.GitHubRepositoryCallback() {
                @Override
                public void handleGitHubResponse(Response<SearchResponse> response) {
                    renderSuccess(response);
                }

                @Override
                public void handleGitHubError() {

                }
            });
        }
    }

    private void renderSuccess(Response<SearchResponse> response) {
    }

    @Override
    public void displaySearchResults(@NonNull List<SearchResult> searchResults,
                                     @Nullable Integer totalCount) {
        rvAdapter.updateResults(searchResults);
        tvStatus.setText(String.format(Locale.US, "Number of results: %d", totalCount));
    }

    @Override
    public void displayError() {
        Toast.makeText(this, "some error happened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
