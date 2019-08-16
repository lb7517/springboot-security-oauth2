package com.lb.security.oauth2.config;

import com.lb.security.oauth2.entity.MyPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.requestMatchers().antMatchers("/oauth/**","/login/**","/logout/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .formLogin().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //方式一:
        //注意下面的password中使用了PasswordEncoderFactories，
        // 在OAuth2ServerConfig.java中客户端密码也要用此方式处理
        /*auth.inMemoryAuthentication()
                .withUser("cxh")
                .password(PasswordEncoderFactories.
                        createDelegatingPasswordEncoder().encode("cxh"))
                .roles("ADMIN");*/

        //方式二:
        /*auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("cxh")
                .password(new BCryptPasswordEncoder().encode("cxh"))
                .roles("ADMIN");*/

        //方式三:
        auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder())
                .withUser("cxh")
                .password("cxh")
                .roles("ADMIN");
    }


    /**
     * 需要配置这个支持password模式
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
