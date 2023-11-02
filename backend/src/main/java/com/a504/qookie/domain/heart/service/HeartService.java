package com.a504.qookie.domain.heart.service;

import com.a504.qookie.domain.heart.dto.HeartRequest;
import com.a504.qookie.domain.heart.dto.HeartResponse;
import com.a504.qookie.domain.heart.entity.Heart;
import com.a504.qookie.domain.heart.repository.HeartRepository;
import com.a504.qookie.domain.member.entity.Member;
import com.a504.qookie.domain.message.dto.MessageResponse;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;

    @Transactional
    public Heart create(Member member, HeartRequest heartRequest) {

        Heart heart = new Heart(member, heartRequest);

        return heartRepository.save(heart);
    }

    @Transactional
    public List<HeartResponse> list(Member member) {
        return heartRepository.findAllByMember(member)
                .stream()
                .map(HeartResponse::new)
                .toList();
    }

    @Transactional
    public Heart saveReply(MessageResponse messageResponse) throws NoSuchElementException {
        Optional<Heart> optionalHeart = heartRepository.findById(messageResponse.getHeartId());
        Heart heart = optionalHeart.orElseThrow(NoSuchElementException::new);
        return heart.saveReply(messageResponse.getContent());
    }
}