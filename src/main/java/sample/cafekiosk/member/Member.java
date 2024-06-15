package sample.cafekiosk.member;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String password;
	private String nickname;
	private String imageUrl;

	private int age;
	private String city;

	@Enumerated(EnumType.STRING)
	private MemberRole memberRole;


	// 유저 권한 설정 메소드
	// public void authorizeUser() {
	// 	this.role = Role.USER;
	// }

	// 비밀번호 암호화 메소드
	// public void passwordEncode(PasswordEncoder passwordEncoder) {
	// 	this.password = passwordEncoder.encode(this.password);
	// }


}