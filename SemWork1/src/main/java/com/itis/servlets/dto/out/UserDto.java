package com.itis.servlets.dto.out;

import com.itis.servlets.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private Long age;
	private Long avatarId;
	private String token;

	public static UserDto from(User user) {
		return UserDto.builder()
				.id(user.getId())
				.email(user.getEmail())
				.age(user.getAge())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.avatarId(user.getAvatarId())
				.build();
	}
}
