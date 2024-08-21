// package com.bank.bank_api.service.impl;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.factory.PasswordEncoderFactories;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.bank.bank_api.domain.User;
// // import com.bank.bank_api.domain.User;
// import com.bank.bank_api.repository.UserRepository;
// // import com.bank.bank_api.service.UserDetailsService;

// import lombok.extern.slf4j.Slf4j;

// @Service
// @Slf4j
// public class UserDetailsServiceImpl implements UserDetailsService {

//     private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

//     private UserRepository userRepo;

//     public UserDetailsServiceImpl(UserRepository userRepo) {
//         this.userRepo = userRepo;
//     }

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         log.info("++++++++++++++++++++++++");
//         try {
//             final com.bank.bank_api.domain.User user = userRepo.findByEmailIgnoreCase(username);
//             log.info("User is :" + user);
//             if (user != null) {
//                 // PasswordEncoder encoder =
//                 // PasswordEncoderFactories.createDelegatingPasswordEncoder();
//                 // String password = encoder.encode(user.getPassword());
//                 return org.springframework.security.core.userdetails.User
//                         .withUsername(user.getEmail()).accountLocked(!user.isEnabled())
//                         .password(user.getPassword())
//                         .roles(user.getRole()).build();
//             }
//         } catch (Exception ex) {
//             ex.printStackTrace();
//         }
//         throw new UsernameNotFoundException(username);
//     }

// }
