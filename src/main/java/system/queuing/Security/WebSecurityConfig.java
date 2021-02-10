package system.queuing.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.io.FileWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
//
//    @Autowired
//    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

//        auth.jdbcAuthentication()
//                .usersByUsernameQuery(
//                        "select name, password, enabled from employees where name=?")
//                .authoritiesByUsernameQuery(
//                        "select name, role from employees where name=?")
//                .dataSource(dataSource);
//    }
//    }
//

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/specialist**").hasRole("USER")
                .antMatchers("/","/customer/**","/specialist").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/specialist/login")
                        .usernameParameter("name").passwordParameter("password")
                .defaultSuccessUrl("/specialist/specialist")
                    .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select name, password, enabled from employees where name=?")
                .authoritiesByUsernameQuery("select name, role from employees where name=?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void saveFile (String path, String content) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(content);
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
