package com.liuyh.hlsvod;


import lombok.Data;

@Data
public class M3U8Entity {
    //业务名，默认live,如app=live
    String app;
    //直播码，如房间号3619101，或加上其他校验码，如stream=3619101?code=sdas
    String stream;
    //直播的开始时间，如下午15点04分05秒，startTime=150405
    String startTime;
    //直播日期，如2006年01月02日，date=20060102
    String date;

}
