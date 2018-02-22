package tech.ericntd.githubsearch.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.ericntd.githubsearch.R;
import tech.ericntd.githubsearch.models.SearchResult;
import tech.ericntd.githubsearch.repositories.GitHubApi;
import tech.ericntd.githubsearch.repositories.GitHubRepository;

public class MainActivity extends AppCompatActivity implements SearchViewContract {

    private SearchResultRvAdapter rvAdapter;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubRepository repository = new GitHubRepository(retrofit.create(GitHubApi.class));
        final SearchPresenterContract presenter = new SearchPresenter(this, repository);

        final EditText etSearchQuery = findViewById(R.id.et_search_query);
        etSearchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v,
                                          int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.searchGitHubRepos(etSearchQuery
                            .getText()
                            .toString());
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

    @Override
    public void displaySearchResults(@NonNull List<SearchResult> searchResults,
                                     @NonNull Integer totalCount) {
        tvStatus.setText(String.format(getString(R.string.num_of_repos), totalCount));
        rvAdapter.updateResults(searchResults);
    }

    @Override
    public void displayError() {
        tvStatus.setText(getString(R.string.err_generic));
        Toast.makeText(this, getString(R.string.err_generic), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void displayError(String s) {
        tvStatus.setText(s);
        Toast.makeText(this, s, Toast.LENGTH_SHORT)
                .show();
    }
}
