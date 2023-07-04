package com.noticeboard.board.service;

import com.noticeboard.board.domain.Board;
import com.noticeboard.board.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HomeService {

    private final HomeRepository homeRepository;

    @Autowired
    public HomeService(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    public List<Board> findAll(int startPage) {
        return homeRepository.findAll(startPage);
    }

    public long totalBoard() {
        return homeRepository.totalBoard();
    }

    public List<Board> findSearch(String search, int startPage) {
        return homeRepository.findSearch(search, startPage);
    }

    public long totalSearch(String search) {
        return homeRepository.totalSearch(search);
    }

}
