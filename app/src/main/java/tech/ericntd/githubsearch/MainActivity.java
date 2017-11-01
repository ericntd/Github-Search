package tech.ericntd.githubsearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tech.ericntd.githubsearch.models.SearchResult;

public class MainActivity extends AppCompatActivity implements SearchViewContract {

    private SearchResultRvAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SearchPresenterContract presenter = new SearchPresenter(this);

        final EditText etSearchQuery = findViewById(R.id.et_search_query);
        etSearchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v,
                                          int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.searchGitHubRepos(etSearchQuery.getText().toString());
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
    public void displaySearchResults(List<SearchResult> searchResults) {
        rvAdapter.updateResults(searchResults);
    }

    @Override
    public void displayError() {
        Toast.makeText(this, "some error happned", Toast.LENGTH_SHORT).show();
    }
}
