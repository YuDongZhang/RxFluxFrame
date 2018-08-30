package com.pateo.yudo.radio.config;

/**
 * Created by pateo on 18-5-9.
 */

public class ConfigConsts {

    public static class Share {
        public static final String FILE_NAME = "Radio";
    }
    public static class SCAN_RADIO{
        public static final int SCAN_MAX_DURATION=15*1000;//扫描十秒钟内结束
        public static final int SCAN_UPDATE_PROGRESS_TIME=30;//限定时间内更新进度条次数
    } public static class GRID_RADIO{
        public static final int GRID_ROWS=2;//两行
        public static final int  GRID_COLUMNS=4;//4列
    }

}
