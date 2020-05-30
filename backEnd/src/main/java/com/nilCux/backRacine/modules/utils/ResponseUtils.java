package com.nilCux.backRacine.modules.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nilCux.backRacine.modules.output.ApiResult;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class ResponseUtils {

    /**
     * Use ServletResponse to output JSON
     * based on ApiResult object
     *
     * @param response
     * @param result
     */
    public static void respondWithResult(ServletResponse response, ApiResult result) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(result));
        } catch (Exception e) {
            log.error(e + "Error outputting JSON");
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * Use ServletResponse to output JSON
     * based on String
     * @param httpServletResponse
     * @param msg
     * @param status
     */

    public static void respondWithMsgAndStatus(HttpServletResponse httpServletResponse, String msg, Integer status){
        PrintWriter writer = null;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try {
            writer = httpServletResponse.getWriter();
            writer.print(JSONObject.toJSONString(new ApiResult(status,msg,null)));
        } catch (IOException e) {
            log.error("Error occurred while creating response", e.getMessage());
        } finally {
            if (writer != null){
                writer.close();
            }
        }
    }

}
