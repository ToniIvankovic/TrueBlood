package progi.megatron.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider customAuthProvider;

    public WebSecurity(CustomAuthenticationProvider customAuthProvider) {
        this.customAuthProvider = customAuthProvider;

        // Inherit security context in async function calls
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and();

        http = http.headers().frameOptions().sameOrigin().and(); // fixes h2-console problem

        http = http.cors().and().csrf().disable();

        http = http
                .authorizeRequests().antMatchers("/api/v1/donor/registration").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .httpBasic().and();
//                .addFilterBefore(
//                        jwtTokenFilter,
//                        UsernamePasswordAuthenticationFilter.class
//                );
        http.logout().logoutUrl("/api/v1/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)).invalidateHttpSession(true).deleteCookies("JSESSIONID");//.invalidateHttpSession(true).deleteCookies("JSESSIONID");
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
