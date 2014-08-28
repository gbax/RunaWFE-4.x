package ru.runa.wf.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import ru.runa.common.web.HTMLUtils;
import ru.runa.wf.web.FormSubmissionUtils;
import ru.runa.wfe.InternalApplicationException;

import com.google.common.base.Charsets;

public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Upload File Using Java Servlet API
        // files.addAll(MultipartRequestHandler.uploadByJavaServletAPI(request));

        // 1. Upload File Using Apache FileUpload
        UploadedFile file = new UploadedFile();
        String id = request.getParameter("id");
        if (id == null) {
            throw new InternalApplicationException("id not found");
        }
        String inputId = MultipartRequestHandler.uploadByApacheFileUpload(request, file);
        FormSubmissionUtils.getUploadedFilesMap(request).put(id + FormSubmissionUtils.FILES_MAP_QUALIFIER + inputId, file);

        // 2. Set response type to json
        // response.setContentType("application/json");
        response.setContentType("text/html");
        response.setCharacterEncoding(Charsets.UTF_8.name());
        JSONObject object = new JSONObject();
        object.put("name", file.getName());
        object.put("size", file.getSize());
        response.getOutputStream().write(object.toString().getBytes(Charsets.UTF_8));
        response.getOutputStream().flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String inputId = request.getParameter("inputId");
        String id = request.getParameter("id");
        if (id == null) {
            throw new InternalApplicationException("id not found");
        }
        if ("delete".equals(action)) {
            FormSubmissionUtils.getUploadedFilesMap(request).remove(id + FormSubmissionUtils.FILES_MAP_QUALIFIER + inputId);
        }
        if ("view".equals(action)) {
            Map<String, UploadedFile> map = FormSubmissionUtils.getUploadedFilesMap(request);
            UploadedFile file = map.get(id + FormSubmissionUtils.FILES_MAP_QUALIFIER + inputId);
            if (file == null) {
                LogFactory.getLog(getClass()).error("No session file found by '" + inputId + "', all files = " + map);
                return;
            }
            response.setContentType(file.getMimeType());
            String encodedFileName = HTMLUtils.encodeFileName(request, file.getName());
            response.setHeader("Content-disposition", "attachment; filename=\"" + encodedFileName + "\"");
            response.getOutputStream().write(file.getContent());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }
}