package tech.ericntd.githubsearch;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        rvRepos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
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
