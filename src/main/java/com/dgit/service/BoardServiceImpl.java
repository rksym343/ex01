package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;
import com.dgit.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDAO dao;

	@Transactional // 테이블 여러개 사용해도 하나하나마다 connectino close 하지 않고 한번에 connection 사용함~
	@Override
	public void regist(BoardVO board) throws Exception {
		// TODO Auto-generated method stub
		dao.create(board);
		
		
		// 파일경로를 DB에 tbl_attach 테이블에  입력
		if(board.getFiles() != null){// 보호처리
			for(String filename : board.getFiles()){
				dao.addAttach(filename);	
			}
		}
		
	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		
		
		BoardVO vo =  dao.read(bno);
		
		
		List<String> files = dao.selectAttach(bno);
		vo.setFiles(files.toArray(new String[files.size()]));
		
		return vo;
	}

	@Transactional
	@Override
	public void modify(BoardVO board) throws Exception {
		// TODO Auto-generated method stub
		dao.update(board);
	}

	@Transactional
	@Override
	public void remove(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteAttachByBno(bno);
		dao.delete(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		// TODO Auto-generated method stub
		return dao.listAll();
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listCriteria(cri);
	}

	@Override
	public int totalCount() throws Exception {
		// TODO Auto-generated method stub
		return dao.totalCount();
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listSearch(cri);
	}

	@Override
	public int searchCount(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchCount(cri);
	}

	@Override
	public void removeFile(String fullname) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteAttach(fullname);
	}

	@Override
	public void addFile(String fullname, int bno) throws Exception {
		// TODO Auto-generated method stub
		dao.addAttachByBno(fullname, bno);		
	}

}
