package com.mycom.myapp.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	
	@Override
	public UserResultDto insertUser(UserDto userDto) {
		UserResultDto userResultDto = new UserResultDto();
        
        try {
            Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
             
            if( optionalUser.isPresent()  ) {
                userResultDto.setResult("exist");
                return userResultDto;
            }
            
            User user = new User();
            
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword()); // 나중에 암호화 활 것
//            user.setProfileImg(userDto.getProfileImg()); // 회원 가입 시 넣어야할까요?

            User savedUser = userRepository.save(user); 
            userResultDto.setResult("success");
            
        }catch( Exception e ) {
            e.printStackTrace();
            userResultDto.setResult("fail");
        }
        
        return userResultDto;

	}
}


