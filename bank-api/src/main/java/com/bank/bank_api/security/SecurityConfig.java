package com.bank.bank_api.security;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import com.bank.bank_api.config.TransactionProperties;
import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

        // // @Autowired
        // private RestTemplate restTemplate;

        // // @Autowired
        // private TransactionProperties transactionProperties;

        // private UriComponentsBuilder builder;

        // @Autowired
        // public SecurityConfig(RestTemplateBuilder restTemplateBuilder, TransactionProperties transactionProperties) {
        //         log.info("++++++++++++++++++++++++ : " + transactionProperties);
        //         this.transactionProperties = transactionProperties;
        //         this.restTemplate = restTemplateBuilder.basicAuthentication(transactionProperties.getUsername(),
        //                         transactionProperties.getPassword()).build();
        // }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }


        // @Bean
        // public InMemoryUserDetailsManager userDetailsService() {
        // UserDetails user = User.builder()
        // .username("user")
        // .password(passwordEncoder().encode("springboot2"))
        // .roles("ADMIN", "USER")
        // .build();
        // return new InMemoryUserDetailsManager(user);
        // }

        @Bean
        public UserDetailsService userDetailsService(UserRepository userRepo) {

                return username -> {
                        try {
                                final com.bank.bank_api.domain.User user = userRepo.findByEmailIgnoreCase(username);
                                log.info("User is :" + user);
                                if (user != null) {
                                        // PasswordEncoder encoder =
                                        // PasswordEncoderFactories.createDelegatingPasswordEncoder();
                                        // String password = encoder.encode(user.getPassword());
                                        return org.springframework.security.core.userdetails.User
                                                        .withUsername(user.getEmail()).accountLocked(!user.isEnabled())
                                                        .password(user.getPassword())
                                                        .roles(user.getRole()).build();
                                }
                        } catch (Exception ex) {
                                ex.printStackTrace();
                        }
                        throw new UsernameNotFoundException("User '" + username + "' not found");
                };
        }

        // @Bean
        // public UserDetailsService userDetailsService(UserRepository userRepo) {

        // // return new UserDetailsServiceImpl(userRepo);

        // return username -> {
        // log.info("++++++++++++++++++++++++");
        // try {
        // builder =
        // UriComponentsBuilder.fromUriString(transactionProperties.getFindByEmailUri())
        // .queryParam("email", username);
        // log.info("Querying: " + builder.toUriString());
        // ResponseEntity<EntityModel<com.bank.bank_api.domain.User>> responseEntity =
        // restTemplate
        // .exchange(
        // RequestEntity.get(URI.create(builder.toUriString()))
        // .accept(MediaTypes.HAL_JSON).build(),
        // new ParameterizedTypeReference<EntityModel<com.bank.bank_api.domain.User>>()
        // {
        // });
        // if (responseEntity.getStatusCode() == HttpStatus.OK) {
        // EntityModel<com.bank.bank_api.domain.User> resource =
        // responseEntity.getBody();
        // com.bank.bank_api.domain.User user = resource.getContent();
        // // PasswordEncoder encoder =
        // // PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // // String password = encoder.encode(person.getPassword());
        // return org.springframework.security.core.userdetails.User
        // .withUsername(user.getEmail()).password(user.getPassword())
        // .accountLocked(!user.isEnabled()).roles(user.getRole()).build();
        // }

        // } catch (Exception ex) {
        // ex.printStackTrace();
        // }
        // throw new UsernameNotFoundException("User '" + username + "' not found");
        // };

        // }

        @Bean
        // @Order(3)
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .authorizeRequests((requests) -> requests
                                                // .anyRequest().fullyAuthenticated()
                                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()) 
                                                .permitAll()
                                                .requestMatchers("/actuator/**").permitAll()
                                                .requestMatchers("/**").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/**", "/api/**").hasRole("ADMIN")
                                                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
                                                // .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/", "/api/**").hasRole("USER")
                                                .requestMatchers("/h2-console/**").permitAll()
                                                .requestMatchers("/webjars/**", "/css/**", "/js/**", "/images/**").permitAll()
                                // .requestMatchers(HttpMethod.DELETE, "/api/ingredients").hasAuthority("SCOPE_deleteIngredients")
                                .anyRequest().authenticated()
                                )
                                .csrf((requests) -> requests
                                                .ignoringRequestMatchers("/h2-console/**")
                                                .ignoringRequestMatchers("/api/**")
                                )
                                // .csrf().disable()
                                .headers((requests) -> requests
                                                .frameOptions().sameOrigin())
                                .formLogin((form) -> form
                                                .loginPage("/login").defaultSuccessUrl("/home").permitAll())
                                .logout((logout) -> logout.logoutSuccessUrl("/login"))
                                .httpBasic()
                                .and()
                                .build();
        }

}
