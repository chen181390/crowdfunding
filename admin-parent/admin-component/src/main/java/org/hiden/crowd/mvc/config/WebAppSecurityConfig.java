package org.hiden.crowd.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 保证注解@PreAuthority、@PostAuthority、@PreFilter、@PostFilter生效
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    private final String[] permitArr = new String[]{
            "/admin/to/login/page.html",
            "/bootstrap/**",
            "/css/**",
            "/fonts/**",
            "/img/**",
            "/jquery/**",
            "/js/**",
            "/layer/**",
            "/script/**",
            "/ztree/**",
    };

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder pe = new BCryptPasswordEncoder();
//        auth.inMemoryAuthentication().passwordEncoder(pe).withUser("tom").password(pe.encode("123123")).roles("ADMIN");
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()                            // 对请求进行授权
                .antMatchers(permitArr)                         // 针对登录页进行设置
                .permitAll()                                    // 无条件访问
                .anyRequest()                                   // 其他任意请求
                .authenticated()                                // 认证后访问
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, exception)->{
                  request.setAttribute("exception", exception);
                  request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request, response);
                })
                .and()
                .csrf()                                         // 防跨站请求伪造功能
                .disable()
                .formLogin()                                    // 开启表单登录的功能
                .loginPage("/admin/to/login/page.html")        // 指定登录页面
                .loginProcessingUrl("/security/do/login.html")   // 指定处理登录请求的地址
                .defaultSuccessUrl("/admin/to/main/page.html")  // 指定登陆成功后前往的地址
                .usernameParameter("loginAcct")                 // 账号的请求参数名称
                .passwordParameter("userPswd")                  // 密码的请求参数名称
                .and()
                .logout()
                .logoutUrl("/security/do/logout.html")
                .logoutSuccessUrl("/admin/to/login/page.html")
        ;

    }
}
