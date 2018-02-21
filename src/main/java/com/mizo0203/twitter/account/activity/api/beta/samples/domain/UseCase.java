package com.mizo0203.twitter.account.activity.api.beta.samples.domain;

import com.mizo0203.twitter.account.activity.api.beta.samples.repo.TwitterClient;

import java.util.logging.Logger;

public class UseCase implements AutoCloseable {
  private static final Logger LOG = Logger.getLogger(UseCase.class.getName());
  private final TwitterClient mTwitterClient;

  public UseCase() {
    mTwitterClient = new TwitterClient();
  }

  @Override
  public void close() {}

  public void registersWebhookURL() {
    mTwitterClient.registersWebhookURL();
  }

  /**
   * Returns all URLs and their statuses for the given app for all events. Currently, only one
   * webhook URL can be registered to an application. We mark a URL as invalid if it fails the daily
   * validation check. In order to re-enable the URL, call the update endpoint.
   *
   * <p>指定されたすべてのイベントのURLとそのステータスを返します。 現在、Webhook URLはアプリケーションに1つしか登録できません。
   * 毎日の妥当性チェックに失敗した場合は、URLを無効とマークします。 URLを再度有効にするには、更新エンドポイントを呼び出します。
   */
  public void returnsAllUrls() {
    mTwitterClient.returnsAllUrls();
  }

  public void subscriptions() {
    mTwitterClient.subscriptions();
  }

  /**
   * Returns the count of subscriptions that are currently active on your account for all
   * activities. Note that the /count endpoint requires App-only Oauth, so that you should make
   * requests using a bearer token instead of app-user auth.
   *
   * <p>すべてのアクティビティに対してアカウントで現在アクティブなサブスクリプションの数を返します。 / countエンドポイントにはApp-only Oauthが必要なので、app-user
   * authの代わりにベアラトークンを使用してリクエストを行う必要があることに注意してください。
   */
  public void countSubscriptions() {
    mTwitterClient.countSubscriptions();
  }

  /**
   * Provides a way to determine if a webhook configuration is subscribed to the provided user’s All
   * events. If the provided user context has an active subscription with provided app, returns 204
   * OK. If the response code is not 204, then the user does not have an active subscription. See
   * HTTP Response code and error messages below for details.
   *
   * <p>Webhook設定が、提供されたユーザのすべてのイベントに登録されているかどうかを判断する方法を提供します。
   * 提供されたユーザーコンテキストに、提供されたアプリケーションによるアクティブなサブスクリプションがある場合は、204 OKを返します。
   * 応答コードが204でない場合、ユーザーにはアクティブなサブスクリプションがありません。 詳細については、下記の「HTTPレスポンスコードとエラーメッセージ」を参照してください。
   */
  public void isSubscribed() {
    mTwitterClient.isSubscribed();
  }

  /**
   * Deactivates subscription(s) for the provided user context and app for all activities. After
   * deactivation, all All events for the requesting user will no longer be sent to the webhook URL.
   *
   * <p>提供されたユーザーコンテキストのサブスクリプションとすべてのアクティビティのアプリケーションを非アクティブ化します。
   * 非アクティブ化後、要求元ユーザのすべてのすべてのイベントはWebHook URLに送信されなくなります。
   */
  public void deactivatesSubscriptions() {
    mTwitterClient.deactivatesSubscriptions();
  }
}
