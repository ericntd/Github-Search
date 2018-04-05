package tech.ericntd.githubsearch.search;

import org.junit.Test;
import org.mockito.Mockito;

import tech.ericntd.githubsearch.repositories.GitHubRepository;

public class MainActivityTest {
    private MainActivity mainActivity = new MainActivity();

    @Test
    public void search() {
        mainActivity.search("android");

        Mockito.verify(mainActivity.repository).searchRepos("android", (GitHubRepository
                .GitHubRepositoryCallback) mainActivity);
    }
}
