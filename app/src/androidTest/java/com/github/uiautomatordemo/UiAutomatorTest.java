package com.github.uiautomatordemo;

import android.os.SystemClock;
import android.support.test.uiautomator.UiObject;

/**
 * Created by xulin on 2017/5/18 0018.
 */

public class UiAutomatorTest extends UiAutomatorHelper {

    @Override
    public void testMethod() {
        //install UiAutomatorTestApp and open before testing
        //simulate login
        login();

        //wait for login
        waitForUi("com.github.uiautomatortestapp:id/rv_book_list", 10000);

        //click item 2
        clickCollectionChild("com.github.uiautomatortestapp:id/rv_book_list",
                "com.github.uiautomatortestapp:id/card_book", 2);
        SystemClock.sleep(3000);

        //return home screen
        mUiDevice.pressHome();

    }

    public void login() {
        //find email edit text
        UiObject objEmail = getObjById("com.github.uiautomatortestapp:id/email");
        //get focus
        clickObj(objEmail);
        //input email
        inputText(objEmail, "test@gmail.com");

        UiObject objPasswd = getObjById("com.github.uiautomatortestapp:id/password");
        clickObj(objPasswd);
        inputText(objPasswd, "123456");

        //click login button
        clickObj("com.github.uiautomatortestapp:id/email_sign_in_button");
    }
}
