package com.heitian.ssm.utils.config;

import com.heitian.ssm.model.ImportResult;

import java.io.File;

/**
 * Created by stark.zhang on 2015/11/19.
 */
public abstract class FileImportor {

    public abstract ImportResult getImportResult(File file, String fileName) throws FileImportException;

}
