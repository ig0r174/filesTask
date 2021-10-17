package services;
import org.json.JSONArray;
import java.util.ArrayList;

public class MessagesService {

    private String messageType;
    private String message;

    public MessagesService(String messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }

    @Override
    public String toString() {
        ArrayList <String> data = new ArrayList<>();
        data.add(this.messageType);
        data.add(this.message);
        return new JSONArray(data).toString();
    }

}

