package ma.xproce.schoolnewsboard.SecurityConfig;//package ma.xproce.schoolboard.SecurityConfig;
//
//import ma.xproce.schoolboard.service.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
////    @Autowired @Lazy
////    private PasswordEncoder passwordEncoder;
//    private final CustomUserDetailsService userDetailsService;
//    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
//
//    @Autowired
//    public SecurityConfig(@Lazy CustomUserDetailsService userDetailsService,
//                           CustomAuthenticationSuccessHandler authenticationSuccessHandler) {
//        this.userDetailsService = userDetailsService;
//        this.authenticationSuccessHandler = authenticationSuccessHandler;
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        // In-memory authentication for admin
//        auth.inMemoryAuthentication()
//                .withUser("admin")
////                .password(passwordEncoder.encode("admin123"))
//                .password("{noop}admin123")
//                .roles("ADMIN");
//
//        // Database authentication for regular users
//       auth.userDetailsService(userDetailsService); //.passwordEncoder(passwordEncoder)
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers("/login").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login")
//                        .successHandler(authenticationSuccessHandler)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .permitAll()
//                );
//
//        return http.build();
//    }
//
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//}
