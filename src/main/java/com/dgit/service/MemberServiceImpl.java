package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgit.domain.MemberVO;
import com.dgit.persistence.MemberDao;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao dao;

	@Override
	public String getTime() {
		
		return null;
	}

	@Override
	public void insertMember(MemberVO vo) {
		dao.insertMember(vo);
	}

	@Override
	public MemberVO readMember(String userid) throws Exception {
		// TODO Auto-generated method stub
		return dao.readMember(userid);
	}

	@Override
	public MemberVO login(String userid, String userpw) throws Exception {
		// TODO Auto-generated method stub
		return dao.login(userid, userpw);
	}

	@Override
	public List<MemberVO> selectAll() throws Exception {
		// TODO Auto-generated method stub
		return dao.selectAll();
	}

}
