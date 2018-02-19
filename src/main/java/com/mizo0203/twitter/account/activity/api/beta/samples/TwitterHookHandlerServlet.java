package com.mizo0203.twitter.account.activity.api.beta.samples;

import com.mizo0203.twitter.account.activity.api.beta.samples.domain.difine.KeysAndAccessTokens;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TwitterHookHandlerServlet extends HttpServlet {

  private static final Logger LOG = Logger.getLogger(TwitterHookHandlerServlet.class.getName());

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    LOG.info(IOUtils.toString(req.getInputStream(), StandardCharsets.UTF_8));
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    PrintWriter out = resp.getWriter();
    String crc_token = req.getParameter("crc_token");
    try {
      String signature = getSignature(crc_token);
      LOG.log(Level.INFO, "doGet signature: " + signature);
      out.print("{\"response_token\": \"sha256=" + signature + "\"}");
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      e.printStackTrace();
    }
  }

  private String getSignature(String httpRequestBody)
      throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
    SecretKeySpec key =
        new SecretKeySpec(KeysAndAccessTokens.CONSUMER_SECRET.getBytes(), "HmacSHA256");
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(key);
    byte[] source = httpRequestBody.getBytes("UTF-8");
    return Base64.encodeBase64String(mac.doFinal(source));
  }
}
