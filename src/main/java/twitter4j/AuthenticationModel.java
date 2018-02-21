package twitter4j;

import java.lang.reflect.Field;

/** https://developer.twitter.com/en/docs/basics/authentication/overview/oauth */
public class AuthenticationModel {
  private final HttpParameter[] IMPLICIT_PARAMS;
  private final TwitterImpl mTwitter;

  /* package */ AuthenticationModel(TwitterImpl twitter) {
    this.IMPLICIT_PARAMS = createHttpParameterArray(twitter);
    this.mTwitter = twitter;
  }

  private HttpParameter[] createHttpParameterArray(TwitterImpl twitter) {
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

  public HttpResponse get(String url) throws TwitterException {
    return mTwitter.http.get(url, IMPLICIT_PARAMS, mTwitter.auth, mTwitter);
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
