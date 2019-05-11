package news.agoda.com.newsconnector.connector;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import news.agoda.com.newsconnector.models.NewsData;
import news.agoda.com.newsconnector.models.NewsDataMediaArray;
import news.agoda.com.newsconnector.models.NewsDataMediaString;

public class NewsDeserializer implements JsonDeserializer<NewsData> {

    private final Gson gson = new Gson();

    @Override
    public NewsData deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement element = jsonObject.get("multimedia");
        if (element instanceof JsonArray) {
            return gson.fromJson(json, NewsDataMediaArray.class);
        }
        else {
            return gson.fromJson(json, NewsDataMediaString.class);
        }
    }
}
