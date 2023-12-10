package com.bd.udisk.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.bd.udisk.dao.FileDao;
import com.bd.udisk.entity.FileEntity;
import com.bd.udisk.service.FileService;


@Service("fileService")
public class FileServiceImpl extends ServiceImpl<FileDao, FileEntity> implements FileService {



}