package com.shadowsafe.secretsmanagerbackend.security.config

import com.shadowsafe.secretsmanagerbackend.security.filter.JWTAuthorizationFilter
import com.shadowsafe.secretsmanagerbackend.security.filter.JwtAuthenticationEntryPoint
import com.shadowsafe.secretsmanagerbackend.security.service.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebConfig(
    @Autowired val bCryptPasswordEncoder: BCryptPasswordEncoder,
    @Autowired val userDetailsService: UserDetailsServiceImpl,
    @Autowired val securityProperties: SecurityProperties,
    @Autowired val jwtAuthorizationFilter: JWTAuthorizationFilter,
    @Autowired val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
            .antMatchers(HttpMethod.POST, "/public/users").permitAll()
            .antMatchers(HttpMethod.GET, "/public/admin-check").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder)
    }

    @Bean
    fun authProvider(): DaoAuthenticationProvider = DaoAuthenticationProvider().apply {
        setUserDetailsService(userDetailsService)
        setPasswordEncoder(bCryptPasswordEncoder)
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource = UrlBasedCorsConfigurationSource().also { cors ->
        CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf("POST", "PUT", "DELETE", "GET", "OPTIONS", "HEAD")
            allowedHeaders = listOf(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers",
            )
            exposedHeaders = listOf(
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Authorization",
                "Content-Disposition",
                "Auth",
            )
            maxAge = 3600
            cors.registerCorsConfiguration("/**", this)
        }
    }
}
