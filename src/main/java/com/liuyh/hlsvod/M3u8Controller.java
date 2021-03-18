package com.liuyh.hlsvod;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String getM3u8(String app, String stream, String startTime, String endTime,String date) {
        //时间格式应为[2006][01][02]/[15][04][05].ts;，
        // 例如2006年01月02日15点04分05秒，startTime = "150405",date = "20060102"
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
            FileUtil.copy(tsFrom,tsTo,true);
        }else {
            FileUtil.copy(tsFrom,tsTo,true);
        }
        //拼写 m3u8内容的 sbContent写入文件
        FileWriter writer = new FileWriter(tsTo+C_SLASH+stream+"-"+date+".m3u8");
        writer.write(sbContent.toString());
        //返回 m3u8 文件 http地址
        return "http://172.31.234.199:8080/vod"+app+C_SLASH+stream+"-"+date+".m3u8";
    }

//    public ResultBean getM3u8UrlByTime(String app, String stream, String startTime, String endTime) {
//        //当前要找的m3u8文件的地址，m3u8Path为配置项，和hls配置同步
//        String filePath = m3u8Path + app + "/" + stream + ".m3u8";
//        //生成文件
//        File m3u8File = new File(filePath);
//        //读取m3U8文件存储到list中，方便后续处理
//        List<String> list = getFileList(m3u8File);
//        if (CollectionUtils.isEmpty(list)) {
//            throw new ServiceException(ErrorCodeEnum.FILE_TOLIST_ERROR);
//        }
//        //把时间转换成Long
//        Long startTimeInt = Long.valueOf(startTime);
//        Long endTimeInt = Long.valueOf(endTime);
//
//        //获取新的m3u8的list，此方法里实现逻辑大概是：将m3u8文件的前5行保留，
//        // 循环判断含有ts字样的行数，截取这些行数中的时间戳，在开始结束时间之内的就保留
//        // 保留的ts的前一行也留下，组成最终需要的newFileList
//        List<String> newFileList = getNewFileListByTime(list, startTimeInt, endTimeInt, stream);
//        if (CollectionUtils.isEmpty(newFileList)) {
//            throw new ServiceException(ErrorCodeEnum.FILE_TOLIST_ERROR);
//        }
//        //添加结束标志
//        newFileList.add("#EXT-X-ENDLIST");
//        //将newFileList写入新文件--新文件路径
//        String newM3u8Path = app + "/" + stream + "_" + startTime + "_" + endTime + ".m3u8";
//        String newFilePath = m3u8Path + newM3u8Path;
//        //写入新的文件，逻辑为：把list写入文件
//        createNewFile(newFilePath, newFileList);
//        log.info("create New m3u8 File success");
//
//
//        //更改原ts的文件夹的名称，大概逻辑为复制源ts到新的目录下，
//        // 此步骤若hls配置不清除ts或者根据业务需要定期清除ts，可不做
//        String tsDirPath = m3u8Path + app + "/" + stream;
//        String tsDirPathNew = m3u8Path + app + "/" + stream + "_" + backupFix;
//        copyFolderService.copyFolder(tsDirPath, tsDirPathNew, app, stream);
//        //返回新的m3u8路径，用于点播
//        return new ResultBean(newM3u8Path);
//    }

}
