package com.backend.api.domain.friend.service;

import com.backend.api.domain.friend.dto.response.FriendCursorRes;
import com.backend.api.domain.friend.dto.response.FriendRes;
import com.backend.api.domain.friend.entity.Friend;
import com.backend.api.domain.friend.repository.FriendRepository;
import com.backend.api.domain.member.entity.Member;
import com.backend.api.domain.member.repository.MemberRepository;
import com.backend.api.domain.notice.service.RedisPubService;
import com.backend.api.global.common.code.ErrorCode;
import com.backend.api.global.exception.BaseExceptionHandler;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class FriendService {

	private final RedisPubService redisPubService;
	private final FriendRepository friendRepository;
	private final MemberRepository memberRepository;
	private final int PAGE_SIZE = 10;

	public List<FriendRes> getAllFriends(Long followerId) {
		List<Friend> friendList = friendRepository.findByFollower_IdOrderByFollowing_AssetDesc(followerId);
		return friendList.stream()
			.map(friend ->
				new FriendRes(
					friend.getFollowing().getId(),
					friend.getFollowing().getNickname(),
					friend.getFollowing().getAsset(),
					redisPubService.isUserLoggedIn(friend.getFollowing().getId()))
			).toList();
	}

	public FriendCursorRes getFriendsWithCursor(Long followerId, Long cursor) {
		Pageable pageable = PageRequest.of(0, PAGE_SIZE);
		Page<Friend> friendList = friendRepository.findByFollower_IdAndIdLessThanOrderByIdDesc(followerId, cursor, pageable);
		List<FriendRes> friendResList = friendList.stream()
			.map(friend ->
				new FriendRes(
					friend.getFollowing().getId(),
					friend.getFollowing().getNickname(),
					friend.getFollowing().getAsset(),
					redisPubService.isUserLoggedIn(friend.getFollowing().getId()))
			).toList();
		return new FriendCursorRes(friendResList,
			!friendResList.isEmpty() ? friendResList.get(friendResList.size() - 1).memberId() : cursor);
	}

	public List<FriendRes> searchFriends(Long followerId, String nickname) {
		List<Friend> friendList = friendRepository.findByFollower_IdAndFollowing_NicknameContainingOrderByFollowing_AssetDesc(followerId, nickname);
		return friendList.stream()
			.map(friend ->
				new FriendRes(
					friend.getFollowing().getId(),
					friend.getFollowing().getNickname(),
					friend.getFollowing().getAsset(),
					redisPubService.isUserLoggedIn(friend.getFollowing().getId()))
			)
			.toList();
	}

	@Transactional
	public void deleteFriend(Long followerId, Long followingId) {
		friendRepository.deleteFriendByFollower_IdAndFollowing_IdOrFollower_IdAndFollowing_Id(followerId, followingId, followingId, followerId);
	}

	public Boolean checkFriend(Long followerId, Long followingId) {
		return friendRepository.existsByFollower_IdAndFollowing_Id(followerId, followingId);
	}
}
