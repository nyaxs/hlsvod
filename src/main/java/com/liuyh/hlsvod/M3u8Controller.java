package com.liuyh.hlsvod;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static cn.hutool.core.util.StrUtil.*;

@RestController
public class M3u8Controller {
    @GetMapping("/m3u8")
    public String getM3u8(@RequestBody M3U8Entity m3U8Entity) {
        //时间格式应为[2006][01][02]/[15][04][05].ts;，
        // 例如2006年01月02日15点04分05秒，startTime = "150405",date = "20060102"
        String app = m3U8Entity.getApp();
        String stream = m3U8Entity.getStream();
        String startTime = m3U8Entity.getStartTime();
        String date = m3U8Entity.getDate();
        String srsHttpBasePath = "/home/liuyh/srs/trunk/objs/nginx/html/";
        String hlsvodSavePath = "/home/liuyh/vod/";
        String tsFilePath = "/home/liuyh/srs/trunk/objs/nginx/html/"+app+C_SLASH+stream+C_SLASH+date+C_SLASH;
        String tsFrom = srsHttpBasePath+app+C_SLASH+stream;
        String tsTo = hlsvodSavePath+app+C_SLASH+stream+C_SLASH+date+startTime;
        String tsDir = stream+C_SLASH+date+C_SLASH;
        String m3u8Start = "#EXTM3U" + C_LF +
                "#EXT-X-PLAYLIST-TYPE:VOD" + C_LF +
                "#EXT-X-TARGETDURATION:10\n";
        String m3u8End = "#EXT-X-ENDLIST";
        String tsDesc = "#EXTINF:1.000, no desc\n";
        String ts = "150740.ts";
        StringBuilder sbContent = StrUtil.builder(ts.length() * 1800);
        sbContent.append(m3u8Start);
        String tsTemp = "123456";
        //获取文件列表
        List<String> tsNames = FileUtil.listFileNames(tsFilePath);
        List<Integer> tsNamesNumber = new ArrayList<>();
        String temp = "123456";
        Integer from = Integer.valueOf(startTime);
        //截取时间序列，便于排序和编辑
        for (int i = 0; i < tsNames.size(); i++) {
            temp = tsNames.get(i);
            tsNamesNumber.add(Integer.valueOf(StrUtil.sub(temp,0,6)));
        }
        tsNamesNumber.sort(Comparator.naturalOrder());
        //生成 m3u8 文件
        for (int i = 0; i <tsNamesNumber.size(); i++) {
            if (tsNamesNumber.get(i) >from){
                sbContent.append(tsDesc).append(tsDir).append(tsNamesNumber.get(i)).append(".ts\n");
            }
        }
        sbContent.append(m3u8End);
        if (!FileUtil.exist(app)){
            FileUtil.copy(tsFrom,tsTo,true);
        }else {
            FileUtil.copy(tsFrom,tsTo,true);
        }
        //拼写 m3u8内容的 sbContent写入文件
        FileWriter writer = new FileWriter(tsTo+C_SLASH+stream+"-"+date+startTime+".m3u8");
        writer.write(sbContent.toString());
        //返回 m3u8 文件 http地址
        return "http://172.31.234.199:8080/vod/"+app+C_SLASH+stream+"-"+date+startTime+".m3u8";
    }

    @GetMapping("/test")
    public String test() {
        String app = "live";String stream = "22";String startTime = "101136"; String endTime = "101344";String date= "20210318";
        //时间格式应为[2006][01][02]/[15][04][05].ts;，
        // 例如2006年01月02日15点04分05秒，startTime = "150405",date = "20060102"
        String srsHttpBasePath = "C:\\Users\\liuyo\\Desktop\\fsdownload\\";
        String hlsvodSavePath = "C:\\Users\\liuyo\\Desktop\\fsdownload\\vod\\";
        String tsFilePath = "/home/liuyh/srs/trunk/objs/nginx/html/"+app+C_SLASH+stream+C_SLASH+date+C_SLASH;
        String tsFrom = srsHttpBasePath+app+C_SLASH+stream+C_SLASH+date;
        String fullTime = date+startTime;
        String tsTo = hlsvodSavePath+app+C_SLASH+stream+C_SLASH;
        String tsDir = fullTime+C_SLASH+date+C_SLASH;
        String m3u8Start = "#EXTM3U" + C_LF +
                "#EXT-X-PLAYLIST-TYPE:VOD" + C_LF +
                "#EXT-X-TARGETDURATION:10\n";
        String m3u8End = "#EXT-X-ENDLIST";
        String tsDesc = "#EXTINF:1.000, no desc\n";
        String ts = "150740.ts";
        StringBuilder sbContent = StrUtil.builder(ts.length() * 1800);
        sbContent.append(m3u8Start);
        String tsTemp = "123456";
        //获取文件列表
        List<String> tsNames = FileUtil.listFileNames("C:\\Users\\liuyo\\Desktop\\fsdownload\\live\\22\\20210318");
        List<Integer> tsNamesNumber = new ArrayList<>();
        String temp = "123456";
        Integer from = Integer.valueOf("101136");
        for (int i = 0; i < tsNames.size(); i++) {
            temp = tsNames.get(i);
            tsNamesNumber.add(Integer.valueOf(StrUtil.sub(temp,0,6)));
        }
        tsNamesNumber.sort(Comparator.naturalOrder());
        //生成 m3u8 文件
        for (int i = 0; i <tsNamesNumber.size(); i++) {
            if (tsNamesNumber.get(i) >from){
                sbContent.append(tsDesc).append(tsDir).append(tsNamesNumber.get(i)).append(".ts\n");
            }
        }
        sbContent.append(m3u8End);
        if (!FileUtil.exist(app)){
            FileUtil.copy(tsFrom,tsTo+fullTime,true);
        }else {
            FileUtil.copy(tsFrom,tsTo+fullTime,true);
        }
        //拼写 m3u8内容的 sbContent写入文件
        FileWriter writer = new FileWriter(tsTo+C_SLASH+stream+"-"+fullTime+".m3u8");
        writer.write(sbContent.toString());
        //返回 m3u8 文件 http地址
        return "http://172.31.234.199:8080/vod"+app+C_SLASH+stream+"-"+fullTime+".m3u8";
    }


}
