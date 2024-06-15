package sample.cafekiosk.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

	USER("유저"),
	DORMANT("휴먼 유저"),
	ADMIN("관리자");

	private final String grade;

}
