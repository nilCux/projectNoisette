package com.nilCux.backRacine.config.springSecurityConf;

import com.nilCux.backRacine.config.springSecurityConf.filter.CustomizedAuthenticationProcessingFilter;
import com.nilCux.backRacine.config.springSecurityConf.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *  <p> Security 核心配置类 </p>
 *
 * @author：  zhengqing <br/>
 * @date：  2019/9/30$ 10:58$ <br/>
 * @version：  <br/>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    CustomizedAuthenticationProcessingFilter customizedAuthenticationProcessingFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.antMatcher("/**").authorizeRequests();

        // 禁用CSRF 开启跨域
        http.csrf().disable().cors();
        //http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 标识只能在 服务器本地ip[127.0.0.1或localhost] 访问`/home`接口，其他ip地址无法访问
        //registry.antMatchers("/home").hasIpAddress("127.0.0.1");
        // 允许匿名的url - 可理解为放行接口 - 多个接口使用,分割
        registry.antMatchers("/login", "/registry").permitAll();
        // OPTIONS(选项)：查找适用于一个特定网址资源的通讯选择。 在不需执行具体的涉及数据传输的动作情况下， 允许客户端来确定与资源相关的选项以及 / 或者要求， 或是一个服务器的性能
        registry.antMatchers(HttpMethod.OPTIONS, "/**").denyAll();
        // 自动登录 - cookie储存方式
        registry.and().rememberMe();
        // 其余所有请求都需要认证
        registry.anyRequest().authenticated();
        // 防止iframe 造成跨域
        registry.and().headers().frameOptions().disable();
        //Token Filter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 自定义过滤器认证用户名密码
        http.addFilterAt(customizedAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET,
                "/favicon.ico",
                "/*.html",
                "/**/*.css",
                "/**/*.js");
    }

}
