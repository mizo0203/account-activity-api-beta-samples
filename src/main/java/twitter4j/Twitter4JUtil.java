package twitter4j;

import com.mizo0203.twitter.account.activity.api.beta.samples.domain.difine.KeysAndAccessTokens;
import twitter4j.auth.AccessToken;

import java.lang.reflect.Field;

public class Twitter4JUtil {
  private final HttpParameter[] IMPLICIT_PARAMS;
  private final TwitterImpl mTwitter;

  public Twitter4JUtil() {
    mTwitter = createTwitterInstance();
    IMPLICIT_PARAMS = createHttpParameterArray(mTwitter);
  }

  private static TwitterImpl createTwitterInstance() {
    TwitterImpl twitter = (TwitterImpl) new TwitterFactory().getInstance();
    twitter.setOAuthConsumer(KeysAndAccessTokens.CONSUMER_KEY, KeysAndAccessTokens.CONSUMER_SECRET);
    twitter.setOAuthAccessToken(
        new AccessToken(KeysAndAccessTokens.TOKEN, KeysAndAccessTokens.TOKEN_SECRET));
    return twitter;
  }

  private static HttpParameter[] createHttpParameterArray(TwitterImpl twitter) {
    try {
      Field field = twitter.getClass().getDeclaredField("IMPLICIT_PARAMS");
      field.setAccessible(true);
      return (HttpParameter[]) field.get(twitter);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Twitter getTwitter() {
    return mTwitter;
  }

  private HttpResponse post(String url) throws TwitterException {
    mTwitter.ensureAuthorizationEnabled();
    if (!mTwitter.conf.isMBeanEnabled()) {
      return mTwitter.http.post(url, IMPLICIT_PARAMS, mTwitter.auth, mTwitter);
    } else {
      // intercept HTTP call for monitoring purposes
      HttpResponse response = null;
      long start = System.currentTimeMillis();
      try {
        response = mTwitter.http.post(url, IMPLICIT_PARAMS, mTwitter.auth, mTwitter);
      } finally {
        long elapsedTime = System.currentTimeMillis() - start;
        TwitterAPIMonitor.getInstance().methodCalled(url, elapsedTime, isOk(response));
      }
      return response;
    }
  }

  public HttpResponse delete(String url) throws TwitterException {
    return mTwitter.http.delete(url, IMPLICIT_PARAMS, mTwitter.auth, mTwitter);
  }

  private boolean isOk(HttpResponse response) {
    return response != null && response.getStatusCode() < 300;
  }
}
