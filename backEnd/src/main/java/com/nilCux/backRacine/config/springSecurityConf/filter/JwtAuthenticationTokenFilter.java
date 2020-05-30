package com.nilCux.backRacine.config.springSecurityConf.filter;

import com.nilCux.backRacine.config.Constants;
import com.nilCux.backRacine.config.springSecurityConf.impl.UserDetailsImpl;
import com.nilCux.backRacine.modules.dao.entities.UsersNoisette;
import com.nilCux.backRacine.modules.services.UserDBService;
import com.nilCux.backRacine.modules.utils.CookieStringHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Autoor:杨文彬
 * @Date:2019/1/4
 * @Description：
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserDBService userDBService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        log.info("Filter Working");
        HashMap<String, String> cookieTable = new HashMap<>();
        try {
            cookieTable = CookieStringHandler.parseCookie(request.getHeader("Cookie"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (cookieTable != null && !(cookieTable.isEmpty())) {
                log.info("Cookie Table Not Empty");
                String userID = cookieTable.get(Constants.USERID_KEY);
                String token = cookieTable.get(Constants.TOKEN_KEY);
                //jwtTokenUtil.validateToken(authHeader);//验证令牌
                if (userID != null && SecurityContextHolder.getContext().getAuthentication() == null && !userID.equals("none")) {
                    log.info("Token verifying");
                    UsersNoisette usersNoisette = userDBService.findUserById(userID);
                    if ( usersNoisette.getToken() != null && usersNoisette.getToken().equals(token)) {
                        log.info("Saved Token: " + usersNoisette.getToken());
                        log.info("Received token: " + token);
                        UserDetailsImpl userDetails = new UserDetailsImpl(usersNoisette);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.info("Should be authenticated");
                    }
                }
            }
            chain.doFilter(request, response);
        }
        /*
        catch (ExpiredJwtException e){

            e.printStackTrace();
            Map<String,Object> map = jwtTokenUtil.parseJwtPayload(authHeader);
            String userid = (String)map.get("userid");
            //这里的方案是如果令牌过期了，先去判断redis中存储的令牌是否过期，如果过期就重新登录，如果redis中存储的没有过期就可以
            //继续生成token返回给前端存储方式key:userid,value:令牌
            String redisResult = redisUtil.get(userid);
            String username= (String) map.get("sub");
            if(StringUtils.isNoneEmpty(redisResult)){
                JwtUser jwtUser = new JwtUser();
                jwtUser.setUserid(userid);
                jwtUser.setUsername(username);
                Map<String, Object> claims = new HashMap<>(2);
                claims.put("sub", jwtUser.getUsername());
                claims.put("userid", jwtUser.getUserid());
                claims.put("created", new Date());
                String token = jwtTokenUtil.generateToken(jwtUser);
                //更新redis中的token
                //首先获取key的有效期，把新的token的有效期设为旧的token剩余的有效期
                redisUtil.setAndTime(userid,token,redisUtil.getExpireTime(userid));
                if (token != null && StringUtils.isNotEmpty(token)) {
                    jwtTokenUtil.validateToken(token);//验证令牌
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                        if (jwtTokenUtil.validateToken(token)) {
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
                response.setHeader("newToken",token);
                response.addHeader("Access-Control-Expose-Headers","newToken");
                response.setContentType("application/json;charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                try {
                    chain.doFilter(request, response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ServletException e1) {
                    e1.printStackTrace();
                }

            } else {
                response.addHeader("Access-Control-Allow-origin","http://localhost:9528");
                RetResult retResult = new RetResult(RetCode.EXPIRED.getCode(),"抱歉，您的登录信息已过期，请重新登录");
                response.setContentType("application/json;charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                try {
                    response.getWriter().write(JSON.toJSONString(retResult));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("redis过期");
            }


        } */catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
