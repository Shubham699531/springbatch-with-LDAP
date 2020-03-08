package com.springsecurity.ldap.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfigFile extends WebSecurityConfigurerAdapter{

	@SuppressWarnings("deprecation")
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.ldapAuthentication()
		.userDnPatterns("uid={0}, ou=people")
		.groupSearchBase("ou=groups")
		.contextSource(contextSource())
		.passwordCompare()
		.passwordEncoder(new LdapShaPasswordEncoder())
		.passwordAttribute("userPassword");
	}

	 @Bean
	    public DefaultSpringSecurityContextSource contextSource() {
	        return new DefaultSpringSecurityContextSource(Arrays.asList("ldap://localhost:8388/"), "dc=springframework,dc=org");
	    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.anyRequest()
		.fullyAuthenticated()
		.and()
		.formLogin();
	}
	

}
