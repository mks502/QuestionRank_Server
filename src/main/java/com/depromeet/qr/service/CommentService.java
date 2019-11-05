package com.depromeet.qr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.depromeet.qr.repository.CommentRepository;

@Service
public class CommentService {
	@Autowired
	CommentRepository commentRepository;
	
}
