package in.movieapp.com.movieapp.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DivyaSethi on 17/07/18.
 */
public class TrailerResultResponse {

    @SerializedName("results")
    public List<TrailerResultsInfo> results = new ArrayList<>();


    public ArrayList getTrailerResults() {
        return (ArrayList) results;
    }

    public void setTrailerResults(List results) {
        this.results = results;
    }

    public class TrailerResultsInfo {

        @SerializedName("id")
        public String id;
        @SerializedName("iso_639_1")
        public String language;
        @SerializedName("iso_3166_1")
        public String country;
        @SerializedName("key")
        public String key;
        @SerializedName("name")
        public String name;
        @SerializedName("site")
        public String site;
        @SerializedName("size")
        public int size;
        @SerializedName("type")
        public String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }


        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }
}
