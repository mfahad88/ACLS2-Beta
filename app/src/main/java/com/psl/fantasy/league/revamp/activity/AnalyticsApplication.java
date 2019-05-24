package com.psl.fantasy.league.revamp.activity;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class AnalyticsApplication extends Application {

  private static GoogleAnalytics sAnalytics;
  private static Tracker sTracker;


  @Override
  public void onCreate() {
    super.onCreate();
  //  Fabric.with(this, new Crashlytics());

    sAnalytics = GoogleAnalytics.getInstance(getApplicationContext());


  }

  /**
   * Gets the default {@link Tracker} for this {@link Application}.
   * @return tracker
   */
  synchronized public Tracker getDefaultTracker() {
    // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
    if (sTracker == null) {
      sTracker = sAnalytics.newTracker("UA-138058830-1");

    }

    return sTracker;
  }
}