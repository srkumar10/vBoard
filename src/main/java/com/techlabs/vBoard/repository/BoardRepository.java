package com.techlabs.vBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.vBoard.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	public Board findByName(String name);

}
