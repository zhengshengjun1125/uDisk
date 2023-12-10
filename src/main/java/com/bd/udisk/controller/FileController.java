package com.bd.udisk.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.bd.udisk.util.R;
import com.bd.udisk.vo.FileVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import com.bd.udisk.service.FileService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


/**
 * @author zsj
 * @email zsjemail666@163.com
 * @date 2023-12-08 18:55:08
 */
@RestController
@RequestMapping("/hdfs")
@Slf4j
public class FileController {
    @Autowired
    private FileService fileService;


    @Autowired
    @Lazy
    private FileSystem fileSystem;

    /**
     * TODO
     * ~~读取文件~~
     * ~~创建文件夹~~
     * ~~下载文件~~
     * 移动文件
     * 重命名文件
     * 打开目录查看文件列表
     */

    @ApiOperation(value = "获取指定目录下的所有信息")
    @GetMapping("/root")
    public R root(@ApiParam("linux上hdfs的目录绝对路径") @RequestParam("r") String r) throws IOException {
        if (!checkIsExist(r)) return R.error("文件或者目录不存在");
        try {
            FileStatus[] fileStatuses = fileSystem.listStatus(new Path(r));
            List<FileVo> result = new ArrayList<>();
            Path[] paths = FileUtil.stat2Paths(fileStatuses);
            for (Path path : paths) {
                if (checkisDir(r, path)) {
                    result.add(new FileVo(path.getName(), true, r));
                } else result.add(new FileVo(path.getName(), false, r));
            }
            return R.ok().put("data", result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @ApiOperation(value = "下载文件")
    @GetMapping("/get")
    public void getFile(@ApiParam("linux上hdfs的文件的绝对路径") @RequestParam("path") String path, HttpServletResponse response) throws IOException {
        String prefix = "";
        String old = "";
        if (path.contains(".")) {
            old = path.substring(1, path.lastIndexOf("."));
            prefix = path.substring(path.lastIndexOf(".") + 1);
        } else {
            old = path.substring(1);
            prefix = "";
        }
        if (checkIsExist(path)) {
            String filename = URLEncoder.encode(old, "UTF-8").replaceAll(" ", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + filename + '.' + prefix);
            ServletOutputStream outputStream = response.getOutputStream();//后面放到这里面
            FSDataInputStream open = fileSystem.open(new Path(path));
            while (true) {
                String read = open.readLine();
                if (read == null) break;
                outputStream.write(read.getBytes());
            }
        }else {
            response.setHeader("msg","不存在 我敲里哇");
        }
    }


    /**
     * 这里无法直接使用文件对象进行上传 因为hdfs的上传方法只只支持路径的方式 <br/>
     * 但是java的 MultipartFile 对象并不能拿到文件的本地绝对路径<br/>
     */
    @ApiOperation("上传文件 支持多上传")
    @PostMapping("/upload")
    public R uploadFile(@NotNull
                        @ApiParam(value = "文件地址 本地地址 记得使用转义字符", required = true)
                        @RequestBody List<String> local,
                        @NotNull
                        @ApiParam(value = "上传到hdfs上的路径名称 请使用‘/’隔开 像这样 /dir/target/filename/ 请一定要使用/收尾", required = true)
                        @RequestParam("path") String path) {
        if (path.charAt(path.length() - 1) != '/') return R.error("路径非法");
        try {
            for (String file : local) {
                fileSystem.copyFromLocalFile(new Path(file), new Path(path));
            }
            return R.ok("上传成功");
        } catch (Exception e) {

            return R.error("上传失败");
        }
    }

    @ApiOperation("创建文件夹")
    @GetMapping("/createDir")
    public R createDir(@ApiParam("文件夹绝对路径") @RequestParam("path") String path) throws IOException {
        boolean exist = checkIsExist(path);
        if (exist) return R.error("文件夹已经存在了");
        boolean mkdirs = fileSystem.mkdirs(new Path(path));
        return mkdirs ? R.ok("创建成功") : R.error("创建失败");
    }


    @ApiOperation("重命名或者移动文件夹")
    @GetMapping("/mv")
    public R renameOrMoveFile(@ApiParam("源路径") @RequestParam("src") String path,
                              @ApiParam("目标路径") @RequestParam("tar") String tar) throws IOException {
        boolean rOm = fileSystem.rename(new Path(path), new Path(tar));
        return rOm ? R.ok("操作成功") : R.error("操作失败");
    }


    //检查是否是一个文件夹
    private boolean checkisDir(String home, Path path) {
        try {
            return fileSystem.isDirectory(new Path(home + path.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //检查文件或文件夹是否存在
    private boolean checkIsExist(String path) throws IOException {
        boolean exists = fileSystem.exists(new Path(path));
        if (exists) log.info("{}文件或者目录是存在的", path);
        else log.info("{}文件或者目录不存在", path);
        return exists;
    }
}
