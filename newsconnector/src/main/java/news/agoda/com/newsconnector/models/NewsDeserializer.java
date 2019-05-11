package news.agoda.com.newsconnector.models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class NewsDeserializer implements JsonDeserializer<News> {

    private final Gson gson = new Gson();

    @Override
    public News deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement element = jsonObject.get("multimedia");
        if (element instanceof JsonArray) {
            return gson.fromJson(json, NewsDataWithMediaMetaData.class);
        }
        else {
            return gson.fromJson(json, NewsDataWithoutMediaMetaData.class);
        }
    }
}
