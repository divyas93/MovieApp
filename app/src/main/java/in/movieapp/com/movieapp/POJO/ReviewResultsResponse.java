package in.movieapp.com.movieapp.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DivyaSethi on 17/07/18.
 */
public class ReviewResultsResponse {

    @SerializedName("results")
    public List<ReviewResultsInfo> results = new ArrayList<>();


    public ArrayList getReviewResults() {
        return (ArrayList) results;
    }

    public void setReviewResults(List results) {
        this.results = results;
    }

    public class ReviewResultsInfo {

        @SerializedName("author")
        public String author;
        @SerializedName("content")
        public String content;
        @SerializedName("id")
        public String id;
        @SerializedName("url")
        public String url;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }
}

/*
{
	"id": 351286,
	"page": 1,
	"results": [{
		"": "Law",
		"": "I felt embarrassed to be watching this. It's an embarrassing fever dream. I abandoned it halfway through its runtime.",
		"": "5b2d4c080e0a264aea001943",
		"": "https://www.themoviedb.org/review/5b2d4c080e0a264aea001943"
	}],
	"total_pages": 1,
	"total_results": 1
}
 */