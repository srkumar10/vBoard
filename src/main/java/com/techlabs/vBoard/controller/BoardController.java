package com.techlabs.vBoard.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techlabs.vBoard.domain.Board;
import com.techlabs.vBoard.dto.RegistrationForm;
import com.techlabs.vBoard.repository.BoardRepository;

@Controller
public class BoardController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardRepository BoardRepository;
	
	@RequestMapping(value="/{boardName}", method=RequestMethod.GET)
	public String showBoard(@PathVariable("boardName") String boardName, Model model) {
		
		LOGGER.debug("Rendering view for board:" + boardName);
		
		Board board = BoardRepository.findByName(boardName);
		
		if(board == null) {
			LOGGER.debug("Board not found:" + boardName);
			return "newboard";
		}
		
		model.addAttribute("boardName", boardName);
		model.addAttribute("boardContent", board.getContent());
		return "board";
	}
	
	
	@RequestMapping(value="/{boardName}/edit", method=RequestMethod.GET)
	public String editBoard(@PathVariable("boardName") String boardName, Model model) {
		
		LOGGER.debug("Rendering edit for board:" + boardName);
		
		Board board = BoardRepository.findByName(boardName);
		
		if(board == null) {
			LOGGER.debug("Board not found:" + boardName);
			return "home";
		}
		
		System.out.println("Editing board: " + board);		
		
		return "editboard";
	}
	
	@RequestMapping(value="/{boardName}/edit", method=RequestMethod.POST)
	public String doEditBoard(@PathVariable("boardName") String boardName,
			WebRequest request, RedirectAttributes redirectAttributes, Model model) {
		
		Board board = BoardRepository.findByName(boardName);
		
		if(board == null) {
			LOGGER.debug("Board not found: " + boardName);
			return "home";
		}
		
		if(board.getPassword().equals(request.getParameter("password"))) {
			board.setContent(request.getParameter("content"));
			BoardRepository.save(board);
			return "redirect:/" + request.getParameter("boardName");
		}
		
		// Display the error
		redirectAttributes.addAttribute("error", "Password Mismatch");
		return "redirect:/" + request.getParameter("boardName") + "/edit";
		
	}
	
	
	
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerBoard(@Valid RegistrationForm registrationForm, BindingResult result, WebRequest request, RedirectAttributes redirectAttributes) {
		
		System.out.println("BoardName: " + request.getParameter("boardName"));
		System.out.println("Password: " + request.getParameter("password"));
		
		//If validation has errors, add error attribute and redirect the error message
		if(result.hasErrors()) {
			System.out.println(result.getAllErrors());
			redirectAttributes.addAttribute("error", "Password should be more thatn 3 characters and less than 100");
			return "redirect:/" + request.getParameter("boardName");
		}
		
		// If there is no validation error, create the board and redirect to the boardName page.
		Board board = new Board();
		board.setName(request.getParameter("boardName"));
		board.setPassword(request.getParameter("password"));
		
		BoardRepository.save(board);
		
		return "redirect:/" + request.getParameter("boardName");
	}

}
