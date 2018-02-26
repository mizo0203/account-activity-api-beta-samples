package twitter4j;

public class Twitter4JJSONUtil {

  public static Status asStatus(JSONObject json) throws TwitterException {
    return new StatusJSONImpl(json);
  }
}
