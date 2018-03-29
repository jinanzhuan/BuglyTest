package com.ljn.buglytest;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by shuwei on 2018/3/29.
 */

public class SampleApplication extends TinkerApplication {
    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.ljn.buglytest.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
