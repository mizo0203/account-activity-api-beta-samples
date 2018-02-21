package twitter4j;

import com.mizo0203.twitter.account.activity.api.beta.samples.domain.difine.KeysAndAccessTokens;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Twitter4JUtil {
  private final AuthenticationModel mUserAuthentication;
  private final AuthenticationModel mApplicationOnlyAuthentication;

  public Twitter4JUtil() {
    mUserAuthentication = new AuthenticationModel(createTwitterInstance());
    mApplicationOnlyAuthentication =
        new AuthenticationModel(createTwitterApplicationOnlyAuthInstance());
  }

  private static TwitterImpl createTwitterInstance() {
    TwitterImpl twitter = (TwitterImpl) new TwitterFactory().getInstance();
    twitter.setOAuthConsumer(KeysAndAccessTokens.CONSUMER_KEY, KeysAndAccessTokens.CONSUMER_SECRET);
    twitter.setOAuthAccessToken(
        new AccessToken(KeysAndAccessTokens.TOKEN, KeysAndAccessTokens.TOKEN_SECRET));
    return twitter;
  }

  private static TwitterImpl createTwitterApplicationOnlyAuthInstance() {
    Configuration conf =
        new ConfigurationBuilder()
            .setApplicationOnlyAuthEnabled(true)
            .setOAuthConsumerKey(KeysAndAccessTokens.CONSUMER_KEY)
            .setOAuthConsumerSecret(KeysAndAccessTokens.CONSUMER_SECRET)
            .build();
    TwitterImpl twitter = (TwitterImpl) new TwitterFactory(conf).getInstance();
    try {
      twitter.getOAuth2Token();
      return twitter;
    } catch (TwitterException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * User authentication ユーザー認証
   *
   * <p>This is the most common form of resource authentication in Twitter’s OAuth 1.0a
   * implementation. A signed request identifies an application’s identity in addition to the
   * identity accompanying granted permissions of the end-user the application is making API calls
   * on behalf of, represented by the user’s access token.
   *
   * <p>これは、TwitterのOAuth 1.0a実装で最も一般的なリソース認証の形式です。
   * 署名付きリクエストは、アプリケーションがユーザーのアクセストークンで表されるAPI呼び出しを行うエンドユーザーの付与されたアクセス許可に伴うIDに加えて、アプリケーションのIDを識別します。
   *
   * @return User authentication ユーザー認証
   */
  public AuthenticationModel getUserAuthentication() {
    return mUserAuthentication;
  }

  /**
   * Application-only authentication アプリケーションのみの認証
   *
   * <p>Application-only authentication is a form of authentication where an application makes API
   * requests on its own behalf, without a user context. API calls are still rate limited per API
   * method, but the pool each method draws from belongs to the entire application at large, rather
   * than from a per-user limit. API methods that support this form of authentication will contain
   * two rate limits in their documentation, one that is per user (for application-user
   * authentication) and the other is per app (for this form of application-only authentication).
   * Not all API methods support application-only authentication, because some methods require a
   * user context (for example, a Tweet can only be created by a logged-in user, so user context is
   * required for that operation).
   *
   * <p>アプリケーションオンリー認証は、アプリケーションがユーザーコンテキストなしでAPI要求を独自に行う認証の一形態です。
   * API呼び出しはまだAPIメソッドごとにレート制限がありますが、各メソッドが取得するプールは、ユーザーごとの制限ではなく、アプリケーション全体に属します。
   * この形式の認証をサポートするAPIメソッドには、ユーザーごと（アプリケーションユーザー認証用）とアプリケーション別（アプリケーション専用認証用）の2つのレート制限がドキュメントに含まれています。
   * 一部のメソッドではユーザーコンテキストが必要なため（たとえば、ログインしたユーザーのみがTweetを作成できるため、その操作にユーザーコンテキストが必要です）、すべてのAPIメソッドでアプリケーションのみの認証がサポートされているわけではありません。
   *
   * @return Application-only authentication アプリケーションのみの認証
   */
  public AuthenticationModel getApplicationOnlyAuthentication() {
    return mApplicationOnlyAuthentication;
  }
}
