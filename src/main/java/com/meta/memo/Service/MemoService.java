package com.meta.memo.service;

import com.meta.memo.domain.Memo;
import com.meta.memo.dto.MemoRequestDto;
import com.meta.memo.dto.MemoResponseDto;
import com.meta.memo.repository.MemoRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MemoService {
    private final MemoRepository memoRepository;

    public MemoService(JdbcTemplate jdbcTemplate) {
        this.memoRepository = new MemoRepository(jdbcTemplate);
    }

    public MemoResponseDto createMemo(MemoRequestDto memoRequestDto) {
        // RequestDto -> Entity 변환
        Memo memo = new Memo(memoRequestDto);

        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        Memo savedMemo = memoRepository.save(memo);

        // Entity -> ResponseDto 변환
        MemoResponseDto memoResponseDto = new MemoResponseDto(savedMemo);
        return memoResponseDto;
    }


    public List<MemoResponseDto> getMemos() {
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        List<MemoResponseDto> responseDtoList = memoRepository.findAll();
        return responseDtoList;
    }

    public Long updateMemo(Long id, MemoRequestDto memoRequestDto) {
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);

        // 해당 id의 메모가 데이터베이스에 존재하는지 확인
        Memo foundMemo = memoRepository.findById(id);

        // 메모 내용 수정
        if (foundMemo != null) {
            Long updatedId = memoRepository.updateMemo(id, memoRequestDto);
            return updatedId;
        } else {
            throw new IllegalArgumentException("선택한 id의 메모는 존재하지 않습니다.");
        }
    }

    public Long deleteMemo(Long id) {
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);

        // 해당 id의 메모가 데이터베이스에 존재하는지 확인
        Memo foundMemo = memoRepository.findById(id);

        // 메모 삭제
        if (foundMemo != null) {
            Long deletedId = memoRepository.delete(id);
            return deletedId;
        } else {
            throw new IllegalArgumentException("선택한 id의 메모는 존재하지 않습니다.");
        }
    }
}
