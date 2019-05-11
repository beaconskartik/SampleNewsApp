package news.agoda.com.newsconnector.models;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class NewsDataMediaString extends NewsData {

    @SerializedName("multimedia")
    @Expose
    private String multimedia = null;

    public String getMultimedia() {
        return multimedia;
    }
}
