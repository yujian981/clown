package cn.org.admin;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

/**
 * @author 健康监控
 */
@Configuration
@EnableAutoConfiguration
@EnableAdminServer
public class AdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }


    @Configuration
    public static class SecuritySecureConfig extends WebSecurityConfigurerAdapter {

        private final AdminServerProperties adminServer;

        public SecuritySecureConfig(AdminServerProperties adminServer) {
            this.adminServer = adminServer;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
            successHandler.setTargetUrlParameter("redirectTo");
            successHandler.setDefaultTargetUrl(this.adminServer.path("/"));

            http.authorizeRequests()
                    .antMatchers(this.adminServer.path("/assets/**")).permitAll()
                    .antMatchers(this.adminServer.path("/login")).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage(this.adminServer.path("/login")).successHandler(successHandler).and()
                    .logout().logoutUrl(this.adminServer.path("/logout")).and()
                    .httpBasic().and()
                    .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringRequestMatchers(
                            new AntPathRequestMatcher(this.adminServer.path("/instances"), HttpMethod.POST.toString()),
                            new AntPathRequestMatcher(this.adminServer.path("/instances/*"), HttpMethod.DELETE.toString()),
                            new AntPathRequestMatcher(this.adminServer.path("/actuator/**"))
                    )
                    .and()
                    .rememberMe().key(UUID.randomUUID().toString()).tokenValiditySeconds(1209600);
        }
    }

}
