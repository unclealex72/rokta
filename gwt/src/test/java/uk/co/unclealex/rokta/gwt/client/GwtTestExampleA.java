package uk.co.unclealex.rokta.gwt.client;

import junit.framework.Assert;

import com.google.gwt.junit.client.GWTTestCase;

public class GwtTestExampleA extends GWTTestCase {

   public String getModuleName() {
      return "uk.co.unclealex.rokta.gwt.Application";
   }

   public void testSomething() {
      // Not much to actually test in this sample app
      // Ideally you would test your Controller here (NOT YOUR UI)
      // (Make calls to RPC services, test client side model objects, test client side logic, etc)
      Assert.assertTrue(true);
   }
}