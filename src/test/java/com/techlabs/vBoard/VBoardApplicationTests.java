package com.techlabs.vBoard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.techlabs.vBoard.VBoardApplication;
import com.techlabs.vBoard.domain.Board;
import com.techlabs.vBoard.repository.BoardRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VBoardApplication.class)
@WebAppConfiguration
public class VBoardApplicationTests {
	
	@Autowired
	private BoardRepository boardRepository;

	@Test
	public void findByNameTest() {		
		Board board1 = new Board(1L, "tinytots", "testpass1", "Sample board text1");
		Board board2 = new Board(2L, "stjoseph", "testpass2", "Sample board text2");
		Board board3 = new Board(3L, "rifelspublic", "testpass3", "Sample board text3");
		Board board4 = new Board(4L, "govtboys", "testpass4", "Sample board text4");
		Board board5 = new Board(5L, "shaheen", "testpass5", "Sample board text5");

		boardRepository.save(board1);
		boardRepository.save(board2);
		boardRepository.save(board3);
		boardRepository.save(board4);
		boardRepository.save(board5);
		
		System.out.println(boardRepository.findByName("govtboys"));

		
	}

}
