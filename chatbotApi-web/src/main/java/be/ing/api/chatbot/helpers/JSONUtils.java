package be.ing.api.chatbot.helpers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Created by Freddy Snijder (ING) on 18/06/2017.
 *
 */
public class JSONUtils {

    static public class JSONBody {
        private static final Logger LOG = LoggerFactory.getLogger(JSONBody.class);

        @Getter
        private final JsonObject contents = new JsonObject();

        public boolean add(String prop, JsonElement value) {
            boolean success = true;

            try {
                contents.add(prop, value);
            } catch(Exception e) {
                LOG.error("Exception occurred, unable to add property [{}] to body : ", prop, e);
                success = false;
            }

            return success;
        }

        public boolean add(String prop, String value) {
            boolean success = true;

            try {
                contents.addProperty(prop, value);
            } catch(Exception e) {
                LOG.error("Exception occurred, unable to add property [{}] to body : ", prop, e);
                success = false;
            }

            return success;
        }

        public String toString() {
            return contents.toString();
        }
    }

}
