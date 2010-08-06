package com.xtremelabs.droidsugar.matchers;

import android.widget.TextView;
import com.xtremelabs.droidsugar.ProxyDelegatingHandler;
import com.xtremelabs.droidsugar.fakes.FakeTextView;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

public class HasCompoundDrawablesMatcher extends TypeSafeMatcher<TextView> {
    private String message;
    private FakeTextView.CompoundDrawables expectedCompoundDrawables;

    public HasCompoundDrawablesMatcher(int left, int top, int right, int bottom) {
        expectedCompoundDrawables = new FakeTextView.CompoundDrawables(left, top, right, bottom);
    }

    @Override
    public boolean matchesSafely(TextView actual) {
        if (actual == null) {
            message = "actual view was null";
            return false;
        }

        FakeTextView.CompoundDrawables actualCompoundDrawables = proxyFor(actual).compoundDrawablesWithIntrinsicBounds;
        if (!expectedCompoundDrawables.equals(actualCompoundDrawables)) {
            message = "[" + actualCompoundDrawables + "] to equal [" + expectedCompoundDrawables + "]";
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(message);
    }

    @Factory
    public static Matcher<TextView> hasCompoundDrawables(int left, int top, int right, int bottom) {
        return new HasCompoundDrawablesMatcher(left, top, right, bottom);
    }

    private FakeTextView proxyFor(TextView actual) {
        return (FakeTextView) ProxyDelegatingHandler.getInstance().proxyFor(actual);
    }
}