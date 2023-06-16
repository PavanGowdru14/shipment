package org.jsp.backend.config;



import org.jsp.backend.model.CustomOAuth2User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Configuration
@EnableWebSecurity
public class OAuth2LoginConfig extends WebSecurityConfiguration {

	protected void configure(HttpSecurity http) throws Exception {
		 http.cors() // enable CORS
         .and()
             .authorizeRequests()
             .requestMatchers(HttpMethod.OPTIONS).permitAll() // permit CORS preflight requests
             .anyRequest().authenticated()
         .and()
             .oauth2Login()
                 .userInfoEndpoint()
                     .oidcUserService(this.oidcUserService())
                     .userService(this.oauth2UserService());
 
	}

	@Bean
	public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
		DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
		return request -> {
			OAuth2User user = delegate.loadUser(request);
			return new CustomOAuth2User((OidcUser) user);
		};
	}

	@Bean
	public OidcUserService oidcUserService() {
		return new OidcUserService() {
			@Override
			public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
				OidcUser oidcUser = super.loadUser(userRequest);
				return (OidcUser) new CustomOAuth2User(oidcUser);
			}
		};
	}
}
