/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 Shopify Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.shopify.testify.sample

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.view.View
import com.shopify.testify.ScreenshotRule
import com.shopify.testify.annotation.ScreenshotInstrumentation
import com.shopify.testify.annotation.TestifyLayout
import com.shopify.testify.sample.test.TestHarnessActivity
import com.shopify.testify.sample.test.clientDetailsView
import com.shopify.testify.sample.test.getViewState
import org.junit.Rule
import org.junit.Test

class ScreenshotRuleExampleTests {

    /**
     * [rule] is set up to use a "harness activity". [TestHarnessActivity] is an empty Activity that
     * only exists in the Sample app's androidTest configuration. It is used to load arbitrary
     * [View] instances for testing.
     * [R.id.harness_root] is the topmost/root view in the hierarchy. Testify will load views in
     * this root.
     */
    @get:Rule var rule = ScreenshotRule(
        activityClass = TestHarnessActivity::class.java,
        launchActivity = false,
        rootViewId = R.id.harness_root
    )

    /**
     * Demonstrates the default Testify configuration for a simple layout test.
     *
     * This test uses the [TestifyLayout] annotation to load [R.layout.view_client_details] into
     * the [TestHarnessActivity].
     */
    @TestifyLayout(R.layout.view_client_details)
    @ScreenshotInstrumentation
    @Test
    fun default() {
        rule.setViewModifications { harnessRoot ->
            rule.activity.getViewState(name = "default").let {
                harnessRoot.clientDetailsView.render(it)
                rule.activity.title = it.name
            }
        }.assertSame()
    }

    /**
     * Demonstrates how to change the orientation of your Activity to landscape.
     *
     * Note how the screenshot device key baseline has a longer width than height.
     * e.g. 22-800x480@240dp-en_US
     */
    @TestifyLayout(R.layout.view_client_details)
    @ScreenshotInstrumentation
    @Test
    fun setOrientation() {
        rule
            .setOrientation(requestedOrientation = SCREEN_ORIENTATION_LANDSCAPE)
            .assertSame()
    }
}