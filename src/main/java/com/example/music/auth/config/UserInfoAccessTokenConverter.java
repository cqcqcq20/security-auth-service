package com.example.music.auth.config;

import com.example.common.users.AuthorityEntity;
import com.example.music.auth.entity.DBUserEntity;
import com.example.music.auth.mapper.UserMapper;
import com.example.music.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoAccessTokenConverter extends DefaultAccessTokenConverter {

    private UserMapper userMapper;

    public UserInfoAccessTokenConverter(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, ?> stringMap = super.convertAccessToken(token, authentication);
        DBUserEntity byUid = userMapper.findByUid(authentication.getName());
        Map<String,Object> objectMap = new HashMap<>();
        for (Map.Entry<String, ?> stringEntry : stringMap.entrySet()) {
            objectMap.put(stringEntry.getKey(),stringEntry.getValue());
        }
        objectMap.put("profile",byUid.toMap());
        return objectMap;
    }

}
