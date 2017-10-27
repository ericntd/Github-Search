package tech.ericntd.githubsearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResult {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("private")
    @Expose
    private Boolean _private;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("stargazers_count")
    @Expose
    private Integer stargazersCount;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("has_wiki")
    @Expose
    private Boolean hasWiki;
    @SerializedName("archived")
    @Expose
    private Boolean archived;
    @SerializedName("score")
    @Expose
    private Double score;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public Boolean get_private() {
        return _private;
    }

    public String getDescription() {
        return description;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getStargazersCount() {
        return stargazersCount;
    }

    public String getLanguage() {
        return language;
    }

    public Boolean getHasWiki() {
        return hasWiki;
    }

    public Boolean getArchived() {
        return archived;
    }

    public Double getScore() {
        return score;
    }
}
