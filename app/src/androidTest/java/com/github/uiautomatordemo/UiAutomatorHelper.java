package com.github.uiautomatordemo;

import android.graphics.Rect;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import java.util.Random;

/**
 * Created by xulin on 2017/5/17 0017.
 */

public abstract class UiAutomatorHelper extends UiAutomatorTestCase {

    public UiDevice mUiDevice;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mUiDevice = getUiDevice();
    }

    public abstract void testMethod();

    /**
     * 从UiCollection中随机选择一个子元素并点击
     *
     * @param idCollection UiCollection id
     * @param idChild      Child id
     * @return
     */
    public boolean randomClickCollectionChild(String idCollection, String idChild) {
        boolean clickSucceed = false;
        UiCollection collection = getCollection(idCollection);
        UiSelector selectorChild = getSelector(idChild);
        int childCount = collection.getChildCount(selectorChild);
        if (childCount == 0)
            return false;
        int randChild = new Random().nextInt(childCount);
        log("randChild=" + randChild);
        try {
            UiObject obj = collection.getChildByInstance(selectorChild, randChild);
            clickSucceed = clickObj(obj);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return clickSucceed;
    }

    /**
     * 从UiCollection中随机选择指定子元素并点击
     *
     * @param idCollection UiCollection id
     * @param idChild      Child id
     * @param index        child index
     * @return
     */
    public boolean clickCollectionChild(String idCollection,String idChild,int index){
        boolean clickSucceed = false;
        UiCollection collection = getCollection(idCollection);
        UiSelector selector = getSelector(idChild);
        try {
            UiObject objChild = collection.getChildByInstance(selector, index);
            clickSucceed = clickObj(objChild);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return clickSucceed;
    }

    public UiCollection getCollection(String id) {
        UiSelector selector = getSelector(id);
        return new UiCollection(selector);
    }

    public UiSelector getSelector(String id) {
        return new UiSelector().resourceId(id);
    }

    public UiObject getObjById(String id) {
        return new UiObject(getSelector(id));
    }

    /**
     * 点击元素
     * @param id
     */
    public boolean clickObj(String id) {
        log("click id=" + id);
        UiObject obj = getObjById(id);
        return clickObj(obj);
    }

    public boolean clickObj(UiObject obj) {
        if (!obj.exists())
            return false;
        boolean clickSucceed = false;
        try {
            clickSucceed = obj.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (!clickSucceed) {
                Rect rect = obj.getBounds();
                int clickX = (rect.left + rect.right) / 2;
                int clickY = (rect.top + rect.bottom) / 2;
                log("click:" + clickX + "," + clickY);
                clickSucceed = mUiDevice.click(clickX, clickY);
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return clickSucceed;
    }

    public void waitForUi(String id,long timeOut){
        UiObject obj = getObjById(id);
        obj.waitForExists(timeOut);
    }

    public boolean inputText(UiObject obj,String text){
        boolean succeed = false;
        try {
            succeed = obj.setText(text);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return succeed;
    }

    public void log(String textContent) {
        System.out.println(textContent);
        Log.i("UiAutomator", "log: " + textContent);
    }

    /**
     * 页面向下滑动一段距离
     */
    public void swipe2bottom() {
        mUiDevice.swipe(312, 600, 407, 325, 10);
    }

    /**
     * 向上滑动一段距离
     */
    public void swip2top() {
        mUiDevice.swipe(312, 325, 407, 600, 10);
    }

    /**
     * 向右滑动一段距离
     */
    public void swip2right() {
        mUiDevice.swipe(400, 500, 200, 500, 10);
    }

    /**
     * 向左滑动一段距离
     */
    public void swip2left() {
        mUiDevice.swipe(200, 500, 400, 500, 10);
    }

}
