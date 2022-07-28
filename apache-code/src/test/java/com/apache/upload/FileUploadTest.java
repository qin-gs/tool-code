package com.apache.upload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@DisplayName("apache upload 测试类")
public class FileUploadTest {
    /**
     * 文件上传
     */
    @Test
    public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 创建一个'硬盘文件条目'工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置jvm一次能处理的文件大小
        factory.setSizeThreshold(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);
        // 临时文件目录
        factory.setRepository(new File("tmp.txt"));
        // 文件上传的核心
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 最大可支持的文件大小
        upload.setFileSizeMax(1024 * 1024 * 10);
        upload.setHeaderEncoding("utf-8");
        // 检查是否有文件上传请求
        if (ServletFileUpload.isMultipartContent(request)) {
            List<FileItem> fileItems = upload.parseRequest(request);
            for (FileItem item : fileItems) {
                // 如果是普通表单元素
                if (item.isFormField()) {
                    String name = item.getName(); // 普通表单字段 返回null
                    String fieldName = item.getFieldName(); // 字段名
                    String value = item.getString("utf-8"); // 字段值
                } else { // 如果是文件
                    String name = item.getName(); // 文件名
                    String fieldName = item.getFieldName(); // 字段名
                    long size = item.getSize(); // 文件大小
                    item.write(new File("dest.txt"));
                    FileUtils.copyInputStreamToFile(item.getInputStream(), new File("dext.txt"));
                }
            }
        }
    }
}
